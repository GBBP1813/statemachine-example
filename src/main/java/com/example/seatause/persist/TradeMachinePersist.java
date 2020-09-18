package com.example.seatause.persist;

import com.example.seatause.dao.TradeDao;
import com.example.seatause.enums.TradeOrder;
import com.example.seatause.enums.TradeOrderEventEnum;
import com.example.seatause.enums.TradeOrderStatusEnum;
import com.example.seatause.service.MachineConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.statemachine.StateMachineContext;
import org.springframework.statemachine.StateMachinePersist;
import org.springframework.statemachine.support.DefaultExtendedState;
import org.springframework.statemachine.support.DefaultStateMachineContext;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

/**
 * @auther BoomBoomPow
 * @date 2019/11/29 8:23 PM
 *
 * Revision History
 * Date | Programmer | Notes
 * ----------------------------------------------------------
 **/

@Slf4j
@Component("tradeMachinePersist")
public class TradeMachinePersist implements StateMachinePersist<TradeOrderStatusEnum, TradeOrderEventEnum, String> {

    @Autowired
    TradeDao tradeDao;

    @Override
    public void write(StateMachineContext<TradeOrderStatusEnum, TradeOrderEventEnum> stateMachineContext, String id) throws Exception {

        log.info("save trade order ,id:[{}]", id);
        TradeOrder tradeOrder = tradeDao.selectById(Long.parseLong(id));
        if (tradeOrder == null) {
            log.error("trade order id [{}] not exist", id);
            return;
        }
        tradeOrder.setStatus(stateMachineContext.getState().getCode());
        tradeDao.update(tradeOrder);

    }

    @Override
    public StateMachineContext<TradeOrderStatusEnum, TradeOrderEventEnum> read(String id) throws Exception {
        log.info("read context, id:[{}]", id);

        if (Objects.equals(MachineConstant.DEFAULT_MACHINE_ID, id)) {
            return new DefaultStateMachineContext<>(new ArrayList<>(), TradeOrderStatusEnum.SALES_ORDER, null, null, new DefaultExtendedState(), new HashMap<>(), id);
        }

        TradeOrder tradedOrder = tradeDao.selectById(Long.parseLong(id));
        if (tradedOrder == null) {
            log.info("trade order id [{}] not exist", id);
            return null;
        }
        return new DefaultStateMachineContext<>(new ArrayList<>(), TradeOrderStatusEnum.getByCode(tradedOrder.getStatus()),
                null, null, new DefaultExtendedState(), new HashMap<>(), id);
    }
}
