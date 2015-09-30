package org.itevents.dao.mybatis.mapper;

import org.apache.ibatis.annotations.*;
import org.itevents.dao.CurrencyDao;
import org.itevents.model.Currency;

import java.util.List;

/**
 * Created by vaa25 on 17.07.2015.
 */
public interface CurrencyMapper extends CurrencyDao {

    @ResultType(Currency.class)
    @Select("SELECT * FROM currency WHERE id = #{id}")
    Currency getCurrency(int id);

    @ResultType(Currency.class)
    @Select("SELECT * FROM currency")
    List<Currency> getAllCurrencies();

    @Insert("INSERT INTO currency(name) VALUES(#{name})")
    @Options(useGeneratedKeys = true)
    void addCurrency(Currency currency);

    @Update("UPDATE currency SET name=#{name} WHERE id =#{id}")
    void updateCurrency(Currency currency);

    @Delete("DELETE FROM currency WHERE id =#{id}")
    void removeCurrency(Currency currency);

}
