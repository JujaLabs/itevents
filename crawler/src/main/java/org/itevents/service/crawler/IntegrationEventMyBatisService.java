package org.itevents.service.crawler;

import java.util.List;
import java.util.stream.Collectors;
import javax.inject.Inject;
import org.itevents.dao.api.IntegrationEventDao;
import org.itevents.dao.model.IntegrationEvent;
import org.springframework.stereotype.Service;

/**
 * Created by vaa25 on 25.04.16.
 */
@Service
public class IntegrationEventMyBatisService implements IntegrationEventService {
    @Inject
    private IntegrationEventDao integrationEventDao;
    @Override
    public List<String> getParsedUrlsForFutureEventsFromDatabase(final String integrationName) {
        final List<IntegrationEvent> integrationEvents =
                integrationEventDao.getIntegrationEventsByIntegrationName(integrationName);
        return integrationEvents.stream().map(IntegrationEvent::getIntegrationEventUrl).collect(Collectors.toList());
    }
}
