package org.egov.orchestrator.context;

import lombok.Getter;
import lombok.Setter;
import org.egov.orchestrator.state.SyncState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
@Getter
@Setter
public class SyncContext {

    @Autowired
    @Qualifier("registrationSyncState")
    private SyncState syncState;

    // TODO: Context needs to have parameters which can tell a user
    //  1. whether or not a context was successfully handled
    //  2. if failed, then at which state
    //  3. And for what reason
    //  After each handle(Object payload) execution, update the parameters appropriately

    public void previousState() {
        syncState.previous(this);
    }

    public void nextState() {
        syncState.next(this);
    }

    public void handle(Object payload) {
        syncState.handle(payload);
    }
}
