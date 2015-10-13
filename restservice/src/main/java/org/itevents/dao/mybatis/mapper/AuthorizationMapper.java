package org.itevents.dao.mybatis.mapper;

import org.apache.ibatis.annotations.*;
import org.itevents.dao.AuthorizationDao;
import org.itevents.model.Authorization;

import java.util.List;

public interface AuthorizationMapper extends AuthorizationDao {
    @Results({
            @Result(property = "id", column = "id", id = true)
    })
    @Select("SELECT * FROM users_authorization WHERE id = #{id}")
    Authorization getAuthorization(int id);

    @Override
    @ResultMap("getAuthorization-int")
    @Select("SELECT * FROM users_authorization")
    List<Authorization> getAllAuthorization();

    @Override
    @Insert("INSERT INTO users_authorization (email, password, activation, status) " +
            "VALUES(#{email}, #{password}, #{activation}, #{status})")
    @Options(useGeneratedKeys = true)
    void addAuthorization(Authorization authorization);

    @Override
    @Delete("DELETE FROM users_authorization WHERE id =#{id}")
    void removeAuthorization(Authorization authorization);
}