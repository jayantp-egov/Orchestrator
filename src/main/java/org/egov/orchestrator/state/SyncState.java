package org.egov.orchestrator.state;

import org.egov.orchestrator.context.SyncContext;

public interface SyncState {
    void next(SyncContext syncContext);
    void previous(SyncContext syncContext);
    void handle(Object payload);
}
