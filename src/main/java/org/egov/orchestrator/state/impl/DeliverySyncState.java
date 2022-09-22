package org.egov.orchestrator.state.impl;

import lombok.extern.slf4j.Slf4j;
import org.egov.orchestrator.context.SyncContext;
import org.egov.orchestrator.state.SyncState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class DeliverySyncState implements SyncState {

    @Autowired
    private ApplicationContext applicationContext;
    @Override
    public void next(SyncContext syncContext) {
        log.warn("This is the terminal state");
    }

    @Override
    public void previous(SyncContext syncContext) {
        log.warn("Registration is already done");
    }

    @Override
    public void handle(Object payload) {
        String myPayload = (String) payload;
        log.info("Handling delivery {}", myPayload);
    }
}
