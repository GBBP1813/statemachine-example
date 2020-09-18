package com.example.seatause.actions;

import com.example.seatause.enums.TradeOrderEventEnum;
import com.example.seatause.enums.TradeOrderStatusEnum;
import org.springframework.messaging.MessageHeaders;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.guard.Guard;
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
public class JudgePayResultGuard  implements Guard<TradeOrderStatusEnum, TradeOrderEventEnum> {

    @Override
    public boolean evaluate(StateContext<TradeOrderStatusEnum, TradeOrderEventEnum> context) {
        MessageHeaders headers = context.getMessageHeaders();
        System.out.println("判断是否支付完成，结果支付完成");
        return false;
    }
}
