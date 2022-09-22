package org.egov.orchestrator.service.model;

import lombok.Data;

@Data
public class SyncStats {
    private String status;
    private int successCount;
    private int failureCount;
}
