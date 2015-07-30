package org.itevents.mapper;

import org.apache.ibatis.annotations.*;
import org.itevents.model.Currency;

/**
 * Created by vaa25 on 17.07.2015.
 */
public interface CurrencyMapper {

    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "name", column = "name"),
    })
    @Select("SELECT id, name FROM currencies WHERE id = #{id}")
    Currency getCurrency(int id);

    @Insert("INSERT INTO currencies(id, name) VALUES(#{id}, #{name})")
    @Options(useGeneratedKeys = true)
    Currency addCurrency(Currency currency);

    @Update("UPDATE currencies SET id=#{id}, name=#{name} WHERE id =#{id}")
    void updateCurrency(Currency currency);

    @Delete("DELETE FROM currencies WHERE id =#{id}")
    void removeCurrency(int id);

}
