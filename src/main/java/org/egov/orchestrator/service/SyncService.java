package org.egov.orchestrator.service;

import lombok.extern.slf4j.Slf4j;
import org.egov.orchestrator.context.SyncContext;
import org.egov.orchestrator.service.callable.SyncCallable;
import org.egov.orchestrator.service.model.ContextPayload;
import org.egov.orchestrator.service.model.SyncStats;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.IntStream;

@Service
@Slf4j
public class SyncService {

    private final ApplicationContext applicationContext;

    @Autowired
    public SyncService(ApplicationContext applicationContext) {

        this.applicationContext = applicationContext;
    }

    public void sync() {
        SyncStats syncStats = new SyncStats();
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        CompletionService<Boolean> service = new ExecutorCompletionService<>(executorService);
        List<ContextPayload> contextPayloadList = getContextPayloads();
        List<Future<Boolean>> resultList = new ArrayList<>();
        contextPayloadList.forEach(contextPayload -> {
            Future<Boolean> result = service.submit(new SyncCallable(contextPayload,
                    applicationContext.getBean(SyncContext.class)));
            resultList.add(result);
        });
        IntStream.range(0, resultList.size()).forEach(i -> {
            try {
                if (Boolean.TRUE.equals(service.take().get())) {
                    syncStats.setSuccessCount(syncStats.getSuccessCount() + 1);
                } else {
                    syncStats.setFailureCount(syncStats.getFailureCount() + 1);
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        if (syncStats.getSuccessCount() > 0 && syncStats.getFailureCount() > 0) {
            syncStats.setStatus("PARTIALLY_COMPLETED");
        } else if (syncStats.getFailureCount() == 0 && syncStats.getSuccessCount() > 0) {
            syncStats.setStatus("COMPLETED");
        } else {
            syncStats.setStatus("FAILED");
        }
        log.info(syncStats.toString());
    }

    private List<ContextPayload> getContextPayloads() {
        List<ContextPayload> contextPayloadList = new ArrayList<>();
        IntStream.range(1, 11).forEach(i -> {
            ContextPayload contextPayload = ContextPayload.builder()
                    .registrationPayload("registration_payload_" + i)
                    .deliveryPayload("delivery_payload_" + i)
                    .build();
            contextPayloadList.add(contextPayload);
        });
        return contextPayloadList;
    }
}
