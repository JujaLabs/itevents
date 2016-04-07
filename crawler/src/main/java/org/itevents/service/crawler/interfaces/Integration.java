package org.itevents.service.crawler.interfaces;

import java.util.concurrent.Callable;

/**
 * Created by vaa25 on 06.04.2016.
 */
public interface Integration extends IntegrationObservable, Callable<Void> {
    String getIntegrationName();
}
