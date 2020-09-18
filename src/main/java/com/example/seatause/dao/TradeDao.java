package com.example.seatause.dao;

import com.example.seatause.enums.TradeOrder;
import org.springframework.beans.factory.annotation.Autowired;
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
public class TradeDao {

    public TradeOrder selectById(Long id) {
        return new TradeOrder();
    }

    public void update(TradeOrder tradeOrder) {
    }

    public void insert(TradeOrder tradeOrder) {
    }


}
