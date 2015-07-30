package org.itevents.mapper;

import org.apache.ibatis.annotations.*;
import org.itevents.model.Currency;
import org.itevents.model.TechTag;

/**
 * Created by vaa25 on 17.07.2015.
 */
public interface TechTagMapper {

    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "name", column = "name"),
    })
    @Select("SELECT id, name FROM technologies WHERE id = #{id}")
    TechTag getTechTag(int id);

    @Insert("INSERT INTO technologies(id, name) VALUES(#{id}, #{name})")
    @Options(useGeneratedKeys = true)
    TechTag addTechTag(TechTag techTag);

    @Update("UPDATE technologies SET id=#{id}, name=#{name} WHERE id =#{id}")
    void updateTechTag(TechTag techTag);

    @Delete("DELETE FROM technologies WHERE id =#{id}")
    void removeTechTag(int id);

}
