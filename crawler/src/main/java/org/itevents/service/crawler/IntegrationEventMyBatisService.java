package org.itevents.service.crawler;

import org.itevents.dao.api.IntegrationEventDao;
import org.itevents.dao.model.IntegrationEvent;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by vaa25 on 25.04.16.
 */
@Service
public class IntegrationEventMyBatisService implements IntegrationEventService {
    @Inject
    private IntegrationEventDao integrationEventDao;
    @Override
    public List<String> getParsedUrlsForFutureEventsFromDatabase(String integrationName) {
        List<IntegrationEvent> integrationEvents = integrationEventDao.getIntegrationEventsByIntegrationName(integrationName);
        List<String>result=new ArrayList<>(integrationEvents.size());
        for (IntegrationEvent integrationEvent : integrationEvents) {
            result.add(integrationEvent.getIntegrationEventUrl());
        }
        return result;
    }
}
