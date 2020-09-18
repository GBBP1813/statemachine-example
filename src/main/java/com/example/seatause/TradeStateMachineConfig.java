package com.example.seatause;

import com.example.seatause.actions.*;
import com.example.seatause.enums.TradeOrderEventEnum;
import com.example.seatause.enums.TradeOrderStatusEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.StateMachinePersist;
import org.springframework.statemachine.config.EnableStateMachineFactory;
import org.springframework.statemachine.config.StateMachineConfigurerAdapter;
import org.springframework.statemachine.config.StateMachineFactory;
import org.springframework.statemachine.config.builders.StateMachineConfigurationConfigurer;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;
import org.springframework.statemachine.persist.StateMachineRuntimePersister;
import org.springframework.statemachine.service.DefaultStateMachineService;
import org.springframework.statemachine.service.StateMachineService;

import java.util.EnumSet;

/**
 * @auther BoomBoomPow
 * @date 2019/11/29 8:23 PM
 *
 * Revision History
 * Date | Programmer | Notes
 * ----------------------------------------------------------
 **/

@Configuration
public class TradeStateMachineConfig {

    @Autowired
    private CreateTradeOrderAction createTradeOrderAction;

    @Autowired
    private DeliveredAction deliveredAction;

    @Autowired
    private WaitCreatePayOrderAction waitCreatePayOrderAction;

    @Autowired
    private NeedPayGuard needPayGuard;

    @Autowired
    private SignContractAction signContractAction;

    @Autowired
    private JudgePayResultGuard judgePayResultGuard;

    @Autowired
    private PayAllAction payAllAction;

    @Autowired
    private PayingAction payingAction;

    @Autowired
    private TradeEndAction tradeEndAction;

    @Autowired
    private StartPayAction startPayAction;

    @Autowired
    private CreatePayOrderAction createPayOrderAction;

    @Autowired
    EventListener eventListener;

    @Bean("tradeOrderStateMachineService")
    public StateMachineService<TradeOrderStatusEnum, TradeOrderEventEnum> tradeOrderStateMachineServie(
            StateMachineFactory<TradeOrderStatusEnum, TradeOrderEventEnum> tradeStateMachineFactory,
            StateMachinePersist<TradeOrderStatusEnum, TradeOrderEventEnum, String> tradeStateMachinePersist){
        return new DefaultStateMachineService<>(tradeStateMachineFactory, tradeStateMachinePersist);

    }

    @Configuration
    @EnableStateMachineFactory(name = "tradeOrderStateMachine")
    public class MachineConfig extends StateMachineConfigurerAdapter<TradeOrderStatusEnum, TradeOrderEventEnum> {

        @Autowired
        @Qualifier(value = "tradeStateMachinePersister")
        private StateMachineRuntimePersister<TradeOrderStatusEnum, TradeOrderEventEnum, String> tradeStateMachinePersister;

        @Override
        public void configure(StateMachineConfigurationConfigurer<TradeOrderStatusEnum, TradeOrderEventEnum> config)
                throws Exception {
            config
                    .withConfiguration()
                    .listener(eventListener)
                    .and()
                    .withPersistence()
                    .runtimePersister(tradeStateMachinePersister);
        }


        @Override
        public void configure(StateMachineStateConfigurer<TradeOrderStatusEnum, TradeOrderEventEnum> states)
                throws Exception {
            states.withStates()
                    .initial(TradeOrderStatusEnum.SALES_ORDER)
                    .choice(TradeOrderStatusEnum.IS_NEEDED_PAY)
                    .choice(TradeOrderStatusEnum.PAY_RESULT_JUDGE)
                    .end(TradeOrderStatusEnum.TRADE_END)
                    .states(EnumSet.allOf(TradeOrderStatusEnum.class));
        }

        @Override
        public void configure(StateMachineTransitionConfigurer<TradeOrderStatusEnum, TradeOrderEventEnum> transitions)
                throws Exception {
            transitions.withExternal()
                    .source(TradeOrderStatusEnum.SALES_ORDER).target(TradeOrderStatusEnum.TRADE_ORDER_CREATED)
                    .event(TradeOrderEventEnum.CREATE_TRADE_ORDER)
                    .action(createTradeOrderAction)

                    .and().withExternal()
                    .source(TradeOrderStatusEnum.TRADE_ORDER_CREATED)
                    .target(TradeOrderStatusEnum.IS_NEEDED_PAY)
                    .event(TradeOrderEventEnum.SIGN_CONTRACT)
                    .action(signContractAction)

                    .and().withChoice()
                    .source(TradeOrderStatusEnum.IS_NEEDED_PAY)
                    .first(TradeOrderStatusEnum.DELIVERED, needPayGuard, deliveredAction)
                    .last(TradeOrderStatusEnum.WAIT_CREATE_PAY_ORDER, waitCreatePayOrderAction)

                    .and().withExternal()
                    .source(TradeOrderStatusEnum.WAIT_CREATE_PAY_ORDER)
                    .target(TradeOrderStatusEnum.WAIT_PAYED)
                    .event(TradeOrderEventEnum.CREATE_PAY_ORDER)
                    .action(createPayOrderAction)

                    .and().withExternal()
                    .source(TradeOrderStatusEnum.WAIT_PAYED)
                    .target(TradeOrderStatusEnum.PAY_RESULT_JUDGE)
                    .event(TradeOrderEventEnum.PAYING)
                    .action(startPayAction)

                    .and().withChoice()
                    .source(TradeOrderStatusEnum.PAY_RESULT_JUDGE)
                    .first(TradeOrderStatusEnum.PAYING, judgePayResultGuard, payingAction)
                    .last(TradeOrderStatusEnum.PAYED, payAllAction)

                    .and().withExternal()
                    .source(TradeOrderStatusEnum.PAYED)
                    .target(TradeOrderStatusEnum.DELIVERED)
                    .event(TradeOrderEventEnum.DELIVER_COMPLETE)
                    .action(deliveredAction)

                    .and().withExternal()
                    .source(TradeOrderStatusEnum.DELIVERED)
                    .target(TradeOrderStatusEnum.TRADE_END)
                    .event(TradeOrderEventEnum.TRADE_COMPLETE)
                    .action(tradeEndAction);




        }


    }



}
