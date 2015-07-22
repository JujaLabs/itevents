package org.itevents.mapper;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
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
}
