package com.example.seatause.controller;


import com.example.seatause.dao.TradeDao;
import com.example.seatause.enums.TradeOrder;
import com.example.seatause.enums.TradeOrderEventEnum;
import com.example.seatause.service.MachineConstant;
import com.example.seatause.service.TradeOrderStateMachineService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @auther BoomBoomPow
 * @date 2019/11/29 8:23 PM
 *
 * Revision History
 * Date | Programmer | Notes
 * ----------------------------------------------------------
 **/

@RestController
@Slf4j
public class TradeController {

    @Autowired
    @Lazy
    private TradeOrderStateMachineService tradeOrderStateMachineService;

    @Autowired
    private TradeDao tradeDao;

    @GetMapping("/testMachine")
    public void testMachine()  {
        ExecutorService executorService = Executors.newFixedThreadPool(5);
        TradeOrder tradeOrder = tradeDao.selectById(1L);
        Long id = tradeOrder.getId();

        for (int i = 1; i <= 6; i++) {
            int x = i;
            Map<String, Object> params = new HashMap<>();
            params.put("nickname", "gyf");
            params.put("orderId",String.valueOf(x));
            executorService.execute(() -> {
                try {
                    tradeOrderStateMachineService.submitEvent(String.valueOf(x), TradeOrderEventEnum.CREATE_TRADE_ORDER, params);
                } catch (Exception e) {
                    log.error("exception {}", e.getMessage());
                }
            });
        }

    }

    @GetMapping("/dao")
    public void test() {
        TradeOrder tradeOrder = tradeDao.selectById(1L);
        System.out.println(tradeOrder);
    }

    @GetMapping("/testMachine5")
    public void testMachine5() {

        Map<String, Object> params = new HashMap<>();
        params.put("nickname", "gyf");
        params.put("orderId",String.valueOf(MachineConstant.DEFAULT_MACHINE_ID));
        try {
            tradeOrderStateMachineService.submitEvent(MachineConstant.DEFAULT_MACHINE_ID, TradeOrderEventEnum.CREATE_TRADE_ORDER, params);
        } catch (Exception e) {
            log.error("exception {}", e.getMessage());
        }
    }
}
