package org.itevents.dao.mybatis.session;

import java.util.List;
import org.itevents.dao.api.IntegrationEventDao;
import org.itevents.dao.model.IntegrationEvent;
import org.itevents.dao.mybatis.sql_session_dao.AbstractMyBatisDao;
import org.springframework.stereotype.Component;

/**
 * Created by vaa25 on 09.12.2015.
 */
@Component("integrationEventDao")
@SuppressWarnings("PMD.LawOfDemeter")
public class IntegrationEventMyBatisDao extends AbstractMyBatisDao
    implements IntegrationEventDao {

    @Override
    public List<IntegrationEvent> getIntegrationEventsByIntegrationName(
        final String integrationName) {
        return getSqlSession().selectList(
            "org.itevents.dao.mybatis.mapper.IntegrationEventMapper" +
                ".getIntegrationEventsByIntegrationName",
            integrationName);
    }

    @Override
    public int addIntegrationEvent(final IntegrationEvent integrationEvent) {
        return getSqlSession().insert(
            "org.itevents.dao.mybatis.mapper.IntegrationEventMapper" +
                ".addIntegrationEvent",
            integrationEvent);
    }
}
