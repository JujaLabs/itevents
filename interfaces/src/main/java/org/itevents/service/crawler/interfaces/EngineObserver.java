package org.itevents.service.crawler.interfaces;

import java.util.Collection;

/**
 * Created by vaa25 on 06.04.2016.
 */
public interface EngineObserver {

    void handleNewIntegrationEvents(Collection<IntegrationEventData> integrationEventDatas);
}
