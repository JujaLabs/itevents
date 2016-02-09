package org.itevents.dao.mybatis.mapper;

import org.apache.ibatis.annotations.*;
import org.itevents.dao.TechnologyDao;
import org.itevents.dao.mybatis.builder.TechnologySqlBuilder;
import org.itevents.dao.model.Technology;

import java.util.List;

/**
 * Created by vaa25 on 17.07.2015.
 */
public interface TechnologyMapper extends TechnologyDao {

    @Override
    @ResultType(Technology.class)
    @Select("SELECT * FROM technology WHERE id = #{id}")
    Technology getTechnology(int id);

    @Override
    @ResultType(Technology.class)
    @Select("SELECT * FROM technology ORDER BY name")
    List<Technology> getAllTechnologies();

    @Override
    @ResultType(Technology.class)
    @SelectProvider(type = TechnologySqlBuilder.class, method = "getTechnologiesByNames")
    List<Technology> getTechnologiesByNames(String[] names);

    @ResultType(Technology.class)
    @Select("SELECT * FROM technology t JOIN event_technology et ON t.id=et.technology_id AND et.event_id = #{eventId}" +
            " ORDER BY name")
    List<Technology> getTechnologiesByEventId(int eventId);

    @ResultType(Technology.class)
    @Select("SELECT * FROM technology t JOIN filter_technology ft ON t.id=ft.technology_id AND ft.filter_id = #{filterId}" +
            " ORDER BY name")
    List<Technology> getTechnologiesByFilterId(int filterId);

    @Override
    @Insert("INSERT INTO technology(name) VALUES(#{name})")
    @Options(useGeneratedKeys = true)
    void addTechnology(Technology technology);

}
