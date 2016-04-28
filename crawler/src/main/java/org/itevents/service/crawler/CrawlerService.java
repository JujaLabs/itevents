package org.itevents.service.crawler;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import javax.inject.Inject;
import org.itevents.service.crawler.integration.SampleIntegration;
import org.itevents.service.crawler.interfaces.EngineObserver;
import org.itevents.service.crawler.interfaces.Integration;
import org.itevents.service.crawler.interfaces.IntegrationEventData;
import org.springframework.stereotype.Service;

/**
 * Created by vaa25 on 06.04.2016.
 */
@Service
public class CrawlerService implements EngineObserver {
    @Inject
    private IntegrationEventService integrationEventService;
    private List<Integration> integrations;
    private List<Future<?>> futures;
    private List<IntegrationEventData> entities;

    public String startCrawling() throws InterruptedException, ExecutionException {
        integrations = loadIntegrations();
        setParsedUrlsInIntegrations();
        futures = new ArrayList<>(integrations.size());
        entities = new ArrayList<>(10);
        addMeInIntegrations();
        new IntegrationLauncher().launch();
        return work();
    }

    private void setParsedUrlsInIntegrations() {
        for (final Integration integration : integrations) {
            integration.setParsed(
                    integrationEventService.getParsedUrlsForFutureEventsFromDatabase(
                    integration.getIntegrationName()));
        }
    }

    private String work() {
        final StringBuilder builder = new StringBuilder(
            String.format("Result is: Integrations %s, Entities: %s%n",
                integrations.size(), entities.size()));
        for (final Integration integration : integrations) {
            builder
                .append(integration.getIntegrationName())
                .append(String.format("%n"));
        }
        return builder.toString();
    }

    private List<Integration> loadIntegrations() {
        return Collections.singletonList(new SampleIntegration());
    }

    private void addMeInIntegrations() {
        for (final Integration integration : integrations) {
            integration.addObserver(this);
        }
    }

    @Override
    public final void handleNewIntegrationEvents(final Collection<IntegrationEventData> integrationEventDatas) {
        synchronized (this) {
            entities.addAll(integrationEventDatas);
        }
    }

    class IntegrationLauncher {
        private final ExecutorService service;

        public IntegrationLauncher() {
            service = Executors.newFixedThreadPool(integrations.size());
        }

        private void launch() throws ExecutionException, InterruptedException {
            startIntegrations();
            service.shutdown();
            joinAllIntegrations();
        }

        private void startIntegrations() {
            for (final Integration integration : integrations) {
                futures.add(service.submit(integration));
            }
        }

        private void joinAllIntegrations() throws InterruptedException,
            ExecutionException {
            for (final Future<?> future : futures) {
                future.get();
            }
        }

    }
}
