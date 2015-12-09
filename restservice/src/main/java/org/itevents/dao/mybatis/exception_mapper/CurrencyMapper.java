package org.itevents.dao.mybatis.exception_mapper;

import org.itevents.dao.CurrencyDao;
import org.itevents.dao.exception.EntityNotFoundDaoException;
import org.itevents.model.Currency;
import org.mybatis.spring.support.SqlSessionDaoSupport;

import java.util.List;

/**
 * Created by vaa25 on 09.12.2015.
 */
public class CurrencyMapper extends SqlSessionDaoSupport implements CurrencyDao {
    @Override
    public Currency getCurrency(int id) {
        Currency currency = getSqlSession().selectOne("org.itevents.dao.mybatis.mapper.CurrencySqlMapper.getCurrency", id);
        if (currency == null) {
            throw new EntityNotFoundDaoException("Currency with id = " + id + " not found");
        }
        return currency;
    }

    @Override
    public List<Currency> getAllCurrencies() {
        return getSqlSession().selectList("org.itevents.dao.mybatis.mapper.CurrencySqlMapper.getAllCurrencies");
    }

    @Override
    public void addCurrency(Currency currency) {
        getSqlSession().insert("org.itevents.dao.mybatis.mapper.CurrencySqlMapper.addCurrency", currency);
    }

    @Override
    public void updateCurrency(Currency currency) {
        getSqlSession().update("org.itevents.dao.mybatis.mapper.CurrencySqlMapper.updateCurrency", currency);
    }
}
