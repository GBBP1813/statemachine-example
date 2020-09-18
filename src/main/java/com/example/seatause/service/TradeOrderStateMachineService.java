package com.example.seatause.service;


import com.example.seatause.enums.TradeOrderEventEnum;

import java.util.Map;

/**
 * 状态机服务service
 * @auther BoomBoomPow
 * @date 2019/11/29 8:23 PM
 *
 * Revision History
 * Date | Programmer | Notes
 * ----------------------------------------------------------
 **/

public interface TradeOrderStateMachineService {

    /**
     *
     * @param id   交易订单id
     * @param eventEnum   事件
     * @param params  参数
     * @return
     * @throws Exception
     */
    <T> T submitEvent(String id, TradeOrderEventEnum eventEnum, Map<String, Object> params) throws Exception;
}
