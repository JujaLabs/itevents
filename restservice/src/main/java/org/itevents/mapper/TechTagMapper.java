package org.itevents.mapper;

import org.apache.ibatis.annotations.*;
import org.itevents.model.TechTag;

/**
 * Created by vaa25 on 17.07.2015.
 */
public interface TechTagMapper {

    @ResultType(TechTag.class)
    @Select("SELECT * FROM technologies WHERE id = #{id}")
    TechTag getTechTag(int id);

    @Insert("INSERT INTO technologies(name) VALUES(#{name})")
    @Options(useGeneratedKeys = true)
    void addTechTag(TechTag techTag);

    @Update("UPDATE technologies SET name=#{name} WHERE id =#{id}")
    void updateTechTag(TechTag techTag);

    @Delete("DELETE FROM technologies WHERE id =#{id}")
    void removeTechTag(int id);

}
