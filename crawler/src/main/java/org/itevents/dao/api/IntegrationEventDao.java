package org.itevents.dao.api;

import java.util.List;
import org.itevents.dao.model.IntegrationEvent;

/**
 * Created by vaa25 on 17.07.2015.
 */
public interface IntegrationEventDao {

    List<IntegrationEvent> getIntegrationEventsByIntegrationName(String integrationName);

    int addIntegrationEvent(IntegrationEvent integrationEvent);
}
