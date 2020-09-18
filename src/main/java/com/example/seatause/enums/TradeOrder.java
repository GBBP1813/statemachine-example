package com.example.seatause.enums;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @auther BoomBoomPow
 * @date 2019/11/29 8:23 PM
 *
 * Revision History
 * Date | Programmer | Notes
 * ----------------------------------------------------------
 **/

@Data
@Accessors(chain = true)
public class TradeOrder implements Serializable {
    private Long id;
    private String tradeOrderCode;
    private String status;

}
