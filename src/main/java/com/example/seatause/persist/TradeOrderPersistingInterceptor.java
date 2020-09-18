package com.example.seatause.persist;

import lombok.Setter;
import org.springframework.statemachine.StateMachineContext;
import org.springframework.statemachine.StateMachinePersist;
import org.springframework.statemachine.persist.AbstractPersistingStateMachineInterceptor;
import org.springframework.statemachine.persist.StateMachineRuntimePersister;
import org.springframework.statemachine.support.StateMachineInterceptor;

/**
 * @auther BoomBoomPow
 * @date 2019/11/29 8:23 PM
 *
 * Revision History
 * Date | Programmer | Notes
 * ----------------------------------------------------------
 **/

public class TradeOrderPersistingInterceptor<S, E, T> extends AbstractPersistingStateMachineInterceptor<S, E, T> implements StateMachineRuntimePersister<S, E, T> {

    @Setter
    private StateMachinePersist<S, E, T> stateMachinePersist;


    @Override
    public void write(StateMachineContext<S,E> stateMachineContext, T contextObj) throws Exception {
        stateMachinePersist.write(stateMachineContext, contextObj);
    }

    @Override
    public StateMachineContext<S,E> read(T contextObj) throws Exception {
        return stateMachinePersist.read(contextObj);
    }

    @Override
    public StateMachineInterceptor<S,E> getInterceptor() {
        return this;
    }
}
