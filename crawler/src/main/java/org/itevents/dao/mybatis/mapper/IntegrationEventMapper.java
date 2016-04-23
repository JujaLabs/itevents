package org.itevents.dao.mybatis.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.One;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.itevents.dao.IntegrationEventDao;
import org.itevents.dao.model.Event;
import org.itevents.dao.model.IntegrationEvent;

/**
 * Created by vaa25 on 17.07.2015.
 */
public interface IntegrationEventMapper extends IntegrationEventDao {

    @Override
    @Results({
        @Result(property = "id", column = "id", id = true),
        @Result(property = "integrationName", column = "integration_name"),
        @Result(property = "integrationEventUrl", column = "integration_event_url"),
        @Result(property = "event", column = "event_id", javaType = Event.class,
            one = @One(select = "org.itevents.dao.mybatis.mapper.EventMapper.getEvent"))

    })
    @Select("SELECT * FROM crawler WHERE integration_name=#{integrationName} ORDER BY integration_event_url")
    List<IntegrationEvent> getIntegrationEventsByIntegrationName(String integrationName);

    @Override
    @Insert("INSERT INTO crawler(integration_name, integration_event_url, event_id)" +
        " VALUES(#{integrationName}, #{integrationEventUrl}, #{event.id})")
    @Options(useGeneratedKeys = true)
    void addIntegrationEvent(IntegrationEvent integrationEvent);
}
