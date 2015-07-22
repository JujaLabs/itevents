package org.itevents.mapper;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
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
}
