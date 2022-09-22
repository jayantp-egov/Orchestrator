package org.egov.orchestrator.web;

import org.egov.orchestrator.service.SyncService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SyncController {

    private SyncService syncService;

    @Autowired
    public SyncController(SyncService syncService) {

        this.syncService = syncService;
    }

    @GetMapping
    public ResponseEntity<String> sync() {
        syncService.sync();
        return ResponseEntity.ok("Synced");
    }
}
