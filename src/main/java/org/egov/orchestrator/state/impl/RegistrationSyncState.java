package org.egov.orchestrator.state.impl;

import lombok.extern.slf4j.Slf4j;
import org.egov.orchestrator.context.SyncContext;
import org.egov.orchestrator.state.SyncState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component("registrationSyncState")
@Slf4j
public class RegistrationSyncState implements SyncState {

    @Autowired
    private ApplicationContext applicationContext;

    @Override
    public void next(SyncContext syncContext) {
        syncContext.setSyncState(applicationContext.getBean(DeliverySyncState.class));
    }

    @Override
    public void previous(SyncContext syncContext) {
        log.warn("This is the initial state");
    }

    @Override
    public void handle(Object payload) {
        String myPayload = (String) payload;
        if (!((String) payload).contains("2")) {
            log.info("Handling registration {}", myPayload);
        } else {
            throw new RuntimeException(myPayload);
        }
    }
}
