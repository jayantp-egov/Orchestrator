package org.egov.orchestrator.service.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ContextPayload {
    private Object registrationPayload;
    private Object deliveryPayload;
}
