package com.example.seatause;

import com.example.seatause.enums.TradeOrderEventEnum;
import com.example.seatause.enums.TradeOrderStatusEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.listener.StateMachineListenerAdapter;
import org.springframework.statemachine.state.State;
import org.springframework.statemachine.transition.Transition;
import org.springframework.stereotype.Component;

/**
 * @auther BoomBoomPow
 * @date 2019/11/29 8:23 PM
 *
 * Revision History
 * Date | Programmer | Notes
 * ----------------------------------------------------------
 **/

@Component
@Slf4j
public class EventListener  extends StateMachineListenerAdapter<TradeOrderStatusEnum, TradeOrderEventEnum> {


    @Override
    public void stateContext(StateContext<TradeOrderStatusEnum, TradeOrderEventEnum> context) {

        if (context.getStage() == StateContext.Stage.EVENT_NOT_ACCEPTED) {
            String event =  context.getMessageHeaders().get("event").toString();
            String orderId = context.getMessageHeaders().get("orderId").toString();
            log.error("触发事件时状态非法：orderId={},状态={},event={}", orderId, context.getSource().getId().name(), event);
            context.getStateMachine().setStateMachineError(new RuntimeException("触发事件与订单状态不匹配"));
        }
    }

    @Override
    public void stateChanged(State<TradeOrderStatusEnum, TradeOrderEventEnum> from, State<TradeOrderStatusEnum, TradeOrderEventEnum> to) {
        log.info("stateChanged...");
    }

    @Override
    public void stateEntered(State<TradeOrderStatusEnum, TradeOrderEventEnum> state) {
        log.info("stateEntered...");

    }

    @Override
    public void stateExited(State<TradeOrderStatusEnum, TradeOrderEventEnum> state) {
        log.info("stateExited...");

    }

    @Override
    public void eventNotAccepted(Message<TradeOrderEventEnum> event) {

        log.info("eventNotAccepted...");

    }

    @Override
    public void transition(Transition<TradeOrderStatusEnum, TradeOrderEventEnum> transition) {
        log.info("transition...");
    }

    @Override
    public void transitionStarted(Transition<TradeOrderStatusEnum, TradeOrderEventEnum> transition) {
        log.info("transitionStarted...");

    }

    @Override
    public void transitionEnded(Transition<TradeOrderStatusEnum, TradeOrderEventEnum> transition) {
        log.info("transitionEnded...");

    }

    @Override
    public void stateMachineStarted(StateMachine<TradeOrderStatusEnum, TradeOrderEventEnum> stateMachine) {
        log.info("stateMachineStarted...");

    }

    @Override
    public void stateMachineStopped(StateMachine<TradeOrderStatusEnum, TradeOrderEventEnum> stateMachine) {
        log.info("stateMachineStopped...");

    }

    @Override
    public void stateMachineError(StateMachine<TradeOrderStatusEnum, TradeOrderEventEnum> stateMachine, Exception exception) {
        log.info("stateMachineError...");

    }

    @Override
    public void extendedStateChanged(Object key, Object value) {
        log.info("extendedStateChanged...");

    }

}
