package com.example.seatause;

import com.example.seatause.enums.TradeOrderEventEnum;
import com.example.seatause.enums.TradeOrderStatusEnum;
import lombok.Builder;
import lombok.Data;

/**
 * @auther BoomBoomPow
 * @date 2019/11/29 8:23 PM
 *
 * Revision History
 * Date | Programmer | Notes
 * ----------------------------------------------------------
 **/

@Data
@Builder
public class TradeContext {

    /**
     * 待恢复的状态
     */
    private TradeOrderStatusEnum restoreStatus;

    /**
     * 附带的数据
     */
    private Object payload;

    /**
     * 要触发的事件
     */
    private TradeOrderEventEnum tradeEventEnum;


}
