package com.example.seatause.actions;

import com.example.seatause.enums.TradeOrderEventEnum;
import com.example.seatause.enums.TradeOrderStatusEnum;
import org.springframework.messaging.MessageHeaders;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.guard.Guard;
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
public class NeedPayGuard implements Guard<TradeOrderStatusEnum, TradeOrderEventEnum> {

    @Override
    public boolean evaluate(StateContext<TradeOrderStatusEnum, TradeOrderEventEnum> context) {
        MessageHeaders headers = context.getMessageHeaders();
        System.out.println("判断是否要直接支付费用， 结果需要支付");
        return false;
    }
}
