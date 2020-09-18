package com.example.seatause.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
public enum TradeOrderEventEnum {

    CREATE_TRADE_ORDER("create_trade_order", "创建交易订单"),
    SIGN_CONTRACT("sign_contract", "合同签署完成"),
    CREATE_PAY_ORDER("create_pay_order","创建支付单"),
    PAYING("paying", "支付中"),
    PAY_COMPLETED("pay_complete","支付完成"),
    DELIVER_COMPLETE("deliver_complete", "交付完成"),
    CREATE_SUB_ORDER("create_sub_order", "创建子订单"),
    TRADE_COMPLETE("trade_complete", "交易完成");


    private String code;
    private String message;

}
