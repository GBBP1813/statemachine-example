package com.example.seatause.actions;

import com.example.seatause.enums.TradeOrderEventEnum;
import com.example.seatause.enums.TradeOrderStatusEnum;
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
public class SignContractAction implements Action<TradeOrderStatusEnum, TradeOrderEventEnum> {
    @Override
    public void execute(StateContext<TradeOrderStatusEnum, TradeOrderEventEnum> context) {
        System.out.println("合同签署完成");
    }
}
