package org.itevents.service.crawler;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import org.itevents.service.crawler.integration.SampleIntegration;
import org.itevents.service.crawler.interfaces.EngineObserver;
import org.itevents.service.crawler.interfaces.Integration;

/**
 * Created by vaa25 on 06.04.2016.
 */
public class Engine implements EngineObserver {
    private List<Integration> integrations;
    private List<Future<?>> futures;
    private List<Entity> entities;

    public String run() throws InterruptedException, ExecutionException {

        this.integrations = this.loadIntegrations();
        this.futures = new ArrayList<>(this.integrations.size());
        this.entities = new ArrayList<>(10);
        this.addMeInIntegrations();
        new IntegrationLauncher().launch();
        return this.work();
    }

    private String work() {
        final StringBuilder builder = new StringBuilder(
            String.format("Result is: Integrations %s, Entities: %s\n",
                this.integrations.size(), this.entities.size()));
        for (final Integration integration : this.integrations) {
            builder.append(integration.getIntegrationName()).append('\n');
        }
        return builder.toString();
    }

    private List<Integration> loadIntegrations() {
        return Collections.singletonList(new SampleIntegration());
    }

    private void addMeInIntegrations() {
        for (final Integration integration : this.integrations) {
            integration.addObserver(this);
        }
    }

    @Override
    public final void handleEvent(final Collection<Entity> collection) {
        synchronized (this) {
            this.entities.addAll(collection);
        }
    }

    class IntegrationLauncher {
        private final ExecutorService service;

        public IntegrationLauncher() {
            this.service = Executors.newFixedThreadPool(Engine.this.integrations.size());
        }

        private void launch() throws ExecutionException, InterruptedException {
            this.startIntegrations();
            this.service.shutdown();
            this.joinAllIntegrations();
        }

        private void startIntegrations() {
            for (final Integration integration : Engine.this.integrations) {
                Engine.this.futures.add(this.service.submit(integration));
            }
        }

        private void joinAllIntegrations() throws InterruptedException,
            ExecutionException {
            for (final Future<?> future : Engine.this.futures) {
                future.get();
            }
        }

    }
}
