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
    Technology getTechnology(int id);

    @ResultType(Technology.class)
    @Select("SELECT * FROM technology")
    List<Technology> getAllTechnologies();

    @ResultType(Technology.class)
    @Select({"<script>",
            "SELECT * FROM technology WHERE lower(name) IN ",
            "<foreach item='name' index='index' collection='names' open='(' separator=',' close=')'>",
            "   #{name}",
            "</foreach>",
            "</script>"})
    List<Technology> getSeveralTechnologies(@Param("names") String[] names);

    @Insert("INSERT INTO technology(name) VALUES(#{name})")
    @Options(useGeneratedKeys = true)
    void addTechnology(Technology technology);

    @Update("UPDATE technology SET name=#{name} WHERE id =#{id}")
    void updateTechnology(Technology technology);

    @Delete("DELETE FROM technology WHERE id =#{id}")
    void removeTechnology(Technology technology);

}
