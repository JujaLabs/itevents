package org.itevents.service.crawler.interfaces;

/**
 * Created by vaa25 on 06.04.2016.
 */
public interface Integration extends IntegrationObservable, Runnable {
    String getIntegrationName();
}
