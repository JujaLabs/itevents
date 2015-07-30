package org.itevents.mapper;

import org.apache.ibatis.annotations.*;
import org.itevents.model.Currency;

/**
 * Created by vaa25 on 17.07.2015.
 */
public interface CurrencyMapper {

    @ResultType(Currency.class)
    @Select("SELECT * FROM currencies WHERE id = #{id}")
    Currency getCurrency(int id);

    @Insert("INSERT INTO currencies(name) VALUES(#{name})")
    @Options(useGeneratedKeys = true)
    void addCurrency(Currency currency);

    @Update("UPDATE currencies SET name=#{name} WHERE id =#{id}")
    void updateCurrency(Currency currency);

    @Delete("DELETE FROM currencies WHERE id =#{id}")
    void removeCurrency(int id);

}
