package org.itevents.mybatis.mapper;

import org.apache.ibatis.annotations.*;
import org.itevents.dao.TechnologyDao;
import org.itevents.model.Technology;

import java.util.List;

/**
 * Created by vaa25 on 17.07.2015.
 */
public interface TechnologyMapper extends TechnologyDao {

    @ResultType(Technology.class)
    @Select("SELECT * FROM technology WHERE id = #{id}")
    Technology getTechTag(int id);

    @ResultType(Technology.class)
    @Select("SELECT * FROM technology WHERE id = #{id}")
    List<Technology> getAllTechTags();

    @ResultType(Technology.class)
    @Select("SELECT * FROM technology WHERE name IN #{names}")
    List<Technology> getSeveralTechTags(String[] names);

    @Insert("INSERT INTO technology(name) VALUES(#{name})")
    @Options(useGeneratedKeys = true)
    void addTechTag(Technology technology);

    @Update("UPDATE technology SET name=#{name} WHERE id =#{id}")
    void updateTechTag(Technology technology);

    @Delete("DELETE FROM technology WHERE id =#{id}")
    void removeTechTag(Technology technology);

}
