package com.example.seatause.enums;


import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.stream.Stream;

/**
 * @auther BoomBoomPow
 * @date 2019/11/29 8:23 PM
 *
 * Revision History
 * Date | Programmer | Notes
 * ----------------------------------------------------------
 **/
@Getter
@AllArgsConstructor
public enum TradeOrderStatusEnum {

    SALES_ORDER("sales_order","销售下单"),
    TRADE_ORDER_CREATED("trade_order_created","交易订单已创建"),
    WAIT_CREATE_PAY_ORDER("wait_create_pay_order", "待创建支付单"),
    WAIT_PAYED("wait_payed","支付单已创建，待支付"),
    PAYING("paying", "支付中"),
    PAYED("payed","支付完成"),
    DELIVERED("delivered", "已交付"),
    TRADE_END("trade_end","交易结束"),

    PAY_RESULT_JUDGE("pay_result_judge", "支付结果判断"),
    IS_NEEDED_PAY("is_needed_pay", "判断是否要支付费用");

    private String code;
    private String message;


    public static TradeOrderStatusEnum getByCode(String code) {
        TradeOrderStatusEnum tradeOrderEventEnum = Stream.of(TradeOrderStatusEnum.values())
                .filter(item -> item.getCode().equals(code))
                .findFirst()
                .orElse(null);
        return tradeOrderEventEnum;
    }

    public static void main(String[] args) {
        System.out.println(TradeOrderStatusEnum.getByCode("sales_order"));
    }

}
