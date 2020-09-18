package com.example.seatause.actions;


import com.example.seatause.dao.TradeDao;
import com.example.seatause.enums.TradeOrder;
import com.example.seatause.enums.TradeOrderEventEnum;
import com.example.seatause.enums.TradeOrderStatusEnum;
import com.example.seatause.service.MachineConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;
import org.springframework.stereotype.Service;

/**
 * @auther BoomBoomPow
 * @date 2019/11/29 8:23 PM
 *
 * Revision History
 * Date | Programmer | Notes
 * ----------------------------------------------------------
 **/
@Service
public class CreateTradeOrderAction implements Action<TradeOrderStatusEnum, TradeOrderEventEnum> {


    @Autowired
    TradeDao tradeDao;

    @Override
    public void execute(StateContext<TradeOrderStatusEnum, TradeOrderEventEnum> stateContext) {
        String id = stateContext.getMessageHeaders().get("orderId", String.class);
        TradeOrder tradeOrder = tradeDao.selectById(Long.parseLong(id));
        if (tradeOrder == null) {
            TradeOrder order = new TradeOrder();
            order.setId(6L).setStatus("31313").setTradeOrderCode("313131313");
            tradeDao.insert(order);
        }
        System.out.println("创建交易订单");
        stateContext.getExtendedState().getVariables().put(MachineConstant.NEED_NEXT_STEP, false);
        stateContext.getExtendedState().getVariables().put(MachineConstant.NEXT_EVENT, TradeOrderEventEnum.SIGN_CONTRACT);

    }
}
