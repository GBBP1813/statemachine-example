package com.example.seatause.persist;



import com.example.seatause.enums.TradeOrderEventEnum;
import com.example.seatause.enums.TradeOrderStatusEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.StateMachinePersist;
import org.springframework.statemachine.persist.StateMachineRuntimePersister;

/**
 * @auther BoomBoomPow
 * @date 2019/11/29 8:23 PM
 *
 * Revision History
 * Date | Programmer | Notes
 * ----------------------------------------------------------
 **/

@Configuration
public class TradeMachinePersister {

    @Autowired
    @Qualifier(value = "tradeMachinePersist")
    private StateMachinePersist<TradeOrderStatusEnum, TradeOrderEventEnum, String> tradeStateMachinePersist;


    @Bean(name = "tradeStateMachinePersister")
    public StateMachineRuntimePersister tradeStateMachineGetPersister(){
        TradeOrderPersistingInterceptor<TradeOrderStatusEnum, TradeOrderEventEnum, String>
                interceptor = new TradeOrderPersistingInterceptor<>();
        interceptor.setStateMachinePersist(tradeStateMachinePersist);
        return interceptor;
    }
}
