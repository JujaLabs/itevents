package org.itevents.dao.mybatis.sql_session_dao;

import java.util.List;
import org.itevents.dao.IntegrationEventDao;
import org.itevents.dao.model.IntegrationEvent;
import org.springframework.stereotype.Component;

/**
 * Created by vaa25 on 09.12.2015.
 */
@Component("integrationEventDao")
public class IntegrationEventMyBatisDao extends AbstractMyBatisDao implements IntegrationEventDao {

    @Override
    public List<IntegrationEvent> getIntegrationEventsByIntegrationName(String integrationName) {
        return getSqlSession().selectList("org.itevents.dao.mybatis.mapper.IntegrationEventMapper.getIntegrationEventsByIntegrationName", integrationName);
    }

    @Override
    public void addIntegrationEvent(IntegrationEvent integrationEvent) {
        getSqlSession().insert("org.itevents.dao.mybatis.mapper.IntegrationEventMapper.addIntegrationEvent", integrationEvent);
    }
}
