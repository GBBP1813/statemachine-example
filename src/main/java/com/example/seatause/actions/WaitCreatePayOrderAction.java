package com.example.seatause.actions;

import com.example.seatause.enums.TradeOrderEventEnum;
import com.example.seatause.enums.TradeOrderStatusEnum;
import com.example.seatause.service.MachineConstant;
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
public class WaitCreatePayOrderAction implements Action<TradeOrderStatusEnum, TradeOrderEventEnum> {
    @Override
    public void execute(StateContext<TradeOrderStatusEnum, TradeOrderEventEnum> stateContext) {
        System.out.println("待创建支付交易单");
        stateContext.getExtendedState().getVariables().put(MachineConstant.NEED_NEXT_STEP, true);
        stateContext.getExtendedState().getVariables().put(MachineConstant.NEXT_EVENT, TradeOrderEventEnum.CREATE_PAY_ORDER);
    }
}
