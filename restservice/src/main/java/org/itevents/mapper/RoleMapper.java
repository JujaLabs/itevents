package org.itevents.mapper;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.itevents.model.Role;

/**
 * Created by vaa25 on 17.07.2015.
 */
public interface RoleMapper {

    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "name", column = "name"),
    })
    @Select("SELECT id, name FROM roles WHERE id = #{id}")
    Role getRole(int id);
}
