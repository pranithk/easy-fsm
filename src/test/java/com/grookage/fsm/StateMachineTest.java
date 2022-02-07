/*
 * Copyright 2015 Koushik R <rkoushik.14@gmail.com>.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.grookage.fsm;

import com.grookage.fsm.exceptions.InvalidStateException;
import com.grookage.fsm.exceptions.StateNotFoundException;
import com.grookage.fsm.models.entities.Context;
import org.junit.Assert;
import org.junit.Test;

/**
 * Entity by : koushikr.
 * on 26/10/15.
 */
public class StateMachineTest {

    @Test
    public void testForValidStateMachine() throws InvalidStateException{
        StateMachine<Context> stateMachine = StateMachineUtility.getValidStateMachine();
        stateMachine.validate();
    }

    @Test(expected = InvalidStateException.class)
    public void testForInvalidStateMachine() throws InvalidStateException{
        StateMachine<Context> stateMachine = StateMachineUtility.getInvalidStateMachine();
        stateMachine.validate();
    }

    @Test
    public void testAnyEvent() {
        Context stateContext = new Context();
        stateContext.setFrom(StateMachineUtility.TestState.STARTED);
        stateContext.setTo(StateMachineUtility.TestState.CREATED);
        stateContext.setCausedEvent(StateMachineUtility.TestEvent.CREATE);
        StateMachine<Context> stateMachine = StateMachineUtility.getValidStateMachine();
        stateMachine.anyTransition(context -> Assert.assertSame(StateMachineUtility.TestState.STARTED, context.getFrom()));
        stateMachine.fire(StateMachineUtility.TestEvent.CREATE, stateContext);
    }

    @Test(expected = StateNotFoundException.class)
    public void testInvalidTransitionOnAnyEvent() {
        Context stateContext = new Context();
        stateContext.setFrom(StateMachineUtility.TestState.STARTED);
        stateContext.setTo(StateMachineUtility.TestState.CREATED);
        stateContext.setCausedEvent(StateMachineUtility.TestEvent.CREATE);
        StateMachine<Context> stateMachine = StateMachineUtility.getValidStateMachine();
        stateMachine.anyTransition(context -> Assert.assertSame(StateMachineUtility.TestState.STARTED, context.getFrom()));
        stateMachine.fire(StateMachineUtility.TestEvent.FULFILL, stateContext);
    }

    @Test
    public void testForTransition() {
        Context stateContext = new Context();
        stateContext.setFrom(StateMachineUtility.TestState.STARTED);
        stateContext.setTo(StateMachineUtility.TestState.CREATED);
        stateContext.setCausedEvent(StateMachineUtility.TestEvent.CREATE);
        StateMachine<Context> stateMachine = StateMachineUtility.getValidStateMachine();
        stateMachine.forTransition(StateMachineUtility.TestEvent.CREATE,
                StateMachineUtility.TestState.STARTED,
                context -> Assert.assertSame(StateMachineUtility.TestState.STARTED, context.getFrom()));
        stateMachine.fire(StateMachineUtility.TestEvent.CREATE, stateContext);
    }
}
