package org.itevents.service.crawler;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import org.itevents.service.crawler.integration.SampleIntegration;
import org.itevents.service.crawler.interfaces.EngineObserver;
import org.itevents.service.crawler.interfaces.Integration;

/**
 * Created by vaa25 on 06.04.2016.
 */
public class Engine implements EngineObserver {
    private List<Integration> integrations;
    private List<Thread> threads;
    private List<Entity> entities;

    public String run() throws InterruptedException {
        this.integrations = this.loadIntegrations();
        this.threads = new ArrayList<>(this.integrations.size());
        this.entities = new ArrayList<>(10);
        this.addMeInIntegrations();
        this.startIntegrations();
        this.joinAllIntegrations();
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

    private void joinAllIntegrations() throws InterruptedException {
        for (final Thread thread : this.threads) {
            thread.join();
        }
    }

    private void startIntegrations() {
        for (final Integration integration : this.integrations) {
            final Thread thread = new Thread(integration);
            thread.start();
            this.threads.add(thread);
        }
    }

    private void addMeInIntegrations() {
        for (final Integration integration : this.integrations) {
            integration.addObserver(this);
        }
    }


    private List<Integration> loadIntegrations() {
        return Collections.singletonList(new SampleIntegration());
    }


    @Override
    public final void handleEvent(final Collection<Entity> collection) {
        synchronized (this) {
            this.entities.addAll(collection);
        }
    }
}
