package org.itevents.mybatis.mapper;

import org.apache.ibatis.annotations.*;
import org.itevents.model.TechTag;

/**
 * Created by vaa25 on 17.07.2015.
 */
public interface TechTagMapper {

    @ResultType(TechTag.class)
    @Select("SELECT * FROM technology WHERE id = #{id}")
    TechTag getTechTag(int id);

    @Insert("INSERT INTO technology(name) VALUES(#{name})")
    @Options(useGeneratedKeys = true)
    void addTechTag(TechTag techTag);

    @Update("UPDATE technology SET name=#{name} WHERE id =#{id}")
    void updateTechTag(TechTag techTag);

    @Delete("DELETE FROM technology WHERE id =#{id}")
    void removeTechTag(int id);

}
