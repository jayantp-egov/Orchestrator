package org.egov.orchestrator.service.callable;

import lombok.extern.slf4j.Slf4j;
import org.egov.orchestrator.context.SyncContext;
import org.egov.orchestrator.service.model.ContextPayload;

import java.util.concurrent.Callable;

@Slf4j
public class SyncCallable implements Callable<Boolean> {

    private final ContextPayload contextPayload;

    private final SyncContext syncContext;

    public SyncCallable(ContextPayload contextPayload, SyncContext syncContext) {

        this.contextPayload = contextPayload;
        this.syncContext = syncContext;
    }

    @Override
    public Boolean call() throws Exception {
        try {
            syncContext.handle(contextPayload.getRegistrationPayload());
            syncContext.nextState();
            syncContext.handle(contextPayload.getDeliveryPayload());
        } catch (RuntimeException r) {
            log.error("Error occurred", r);
            return false;
        }
        return true;
    }
}
