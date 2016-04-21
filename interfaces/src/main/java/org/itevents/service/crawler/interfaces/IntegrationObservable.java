package org.itevents.service.crawler.interfaces;

/**
 * Created by vaa25 on 06.04.2016.
 */
public interface IntegrationObservable {

    void addObserver(EngineObserver observer);

    void notifyObservers();
}
