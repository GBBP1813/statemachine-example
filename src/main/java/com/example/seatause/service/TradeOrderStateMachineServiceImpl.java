package com.example.seatause.service;


import com.example.seatause.enums.TradeOrderEventEnum;
import com.example.seatause.enums.TradeOrderStatusEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.access.StateMachineAccess;
import org.springframework.statemachine.access.StateMachineFunction;
import org.springframework.statemachine.service.StateMachineService;
import org.springframework.statemachine.support.StateMachineInterceptorAdapter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.Map;

/**
 * @auther BoomBoomPow
 * @date 2019/11/29 8:23 PM
 * <p>
 * Revision History
 * Date | Programmer | Notes
 * ----------------------------------------------------------
 **/

@Slf4j
@Service
public class TradeOrderStateMachineServiceImpl implements TradeOrderStateMachineService {

    @Autowired
    @Qualifier("tradeOrderStateMachineService")
    private StateMachineService stateMachineService;


    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public <T> T submitEvent(String id, TradeOrderEventEnum eventEnum, Map<String, Object> params) {
        T result = null;
        try {
            StateMachine<TradeOrderStatusEnum, TradeOrderEventEnum> stateMachine = stateMachineService.acquireStateMachine(id, false);
            Message<TradeOrderEventEnum> message = getMessage(eventEnum, params);
            //拦截状态机内部异常
            stateMachine.getStateMachineAccessor()
                    .doWithRegion((StateMachineFunction<StateMachineAccess<TradeOrderStatusEnum, TradeOrderEventEnum> >) function -> function.addStateMachineInterceptor(
                            new StateMachineInterceptorAdapter<TradeOrderStatusEnum, TradeOrderEventEnum>() {
                                @Override
                                public Exception stateMachineError(StateMachine<TradeOrderStatusEnum, TradeOrderEventEnum> machine,
                                                                   Exception exception) {
                                    // return null indicating handled error
                                    machine.getExtendedState().getVariables().put(MachineConstant.STATE_MACHINE_EXCEPTION, exception);
                                    return exception;
                                }
                            }));

            stateMachine.start();
            log.info("--------------状态机id:[{}],状态[{}]", stateMachine.getId(), stateMachine.getState().getId());
            boolean sendResult = stateMachine.sendEvent(message);

            if (stateMachine.hasStateMachineError()) {
                log.error("stateMachine occur error");
                throw (Exception) stateMachine.getExtendedState().getVariables().get(MachineConstant.STATE_MACHINE_EXCEPTION);
            }

            if (sendResult && params != null) {
                result = (T) stateMachine.getExtendedState().getVariables().get(MachineConstant.STATE_MACHINE_RESULT);
            }

            //判断是否需要执行下一事件
//            Boolean needNextStep = stateMachine.getExtendedState().get(MachineConstant.NEED_NEXT_STEP, Boolean.class);
//            needNextStep = Optional.ofNullable(needNextStep).orElse(false);
//
//            while (result && needNextStep && !eventEnum.equals(stateMachine.getExtendedState().get(MachineConstant.NEXT_EVENT, TradeOrderEventEnum.class))) {
//                TradeOrderEventEnum nextEvent = stateMachine.getExtendedState().get(MachineConstant.NEXT_EVENT, TradeOrderEventEnum.class);
//                if (needNextStep == null) {
//                    break;
//                }
//                stateMachine.getExtendedState().getVariables().put(MachineConstant.NEED_NEXT_STEP, false);
//                message = getMessage(nextEvent, params);
//                result = stateMachine.sendEvent(message);
//                needNextStep = stateMachine.getExtendedState().get(MachineConstant.NEED_NEXT_STEP, Boolean.class);
//                needNextStep = Optional.ofNullable(needNextStep).orElse(false);
//                if (stateMachine.hasStateMachineError()) {
//                    log.error("stateMachine occur error");
//                    throw (Exception) stateMachine.getExtendedState().getVariables().get(MachineConstant.STATE_MACHINE_EXCEPTION);
//                }
//
//            }
//            log.info("--------------状态机id:[{}],状态[{}]", stateMachine.getId(), stateMachine.getState().getId());
        } catch (Exception e) {
            log.error("stateMachine id[{}] occur exception:[{}]", e.getMessage());
        } finally {
            stateMachineService.releaseStateMachine(String.valueOf(id), true);
        }

        return result;
    }

    private Message<TradeOrderEventEnum> getMessage(TradeOrderEventEnum operate, Map<String, Object> params) {
        MessageBuilder<TradeOrderEventEnum> builder = MessageBuilder.withPayload(operate).setHeader("event", operate.getCode());
        if (!CollectionUtils.isEmpty(params)) {
            params.forEach(builder::setHeader);
        }
        return builder.build();
    }
}
