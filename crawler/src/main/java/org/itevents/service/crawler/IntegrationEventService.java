package org.itevents.service.crawler;

import java.util.List;

/**
 * Created by vaa25 on 25.04.16.
 */
public interface IntegrationEventService {
    List<String> getParsedUrlsForFutureEventsFromDatabase(String integrationName);
}
