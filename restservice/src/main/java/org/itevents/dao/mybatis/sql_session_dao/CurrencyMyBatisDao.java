package org.itevents.dao.mybatis.sql_session_dao;

import org.itevents.dao.CurrencyDao;
import org.itevents.dao.exception.EntityNotFoundDaoException;
import org.itevents.model.Currency;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by vaa25 on 09.12.2015.
 */
@Component("currencyDao")
public class CurrencyMyBatisDao extends AbstractMyBatisDao implements CurrencyDao {
    @Override
    public Currency getCurrency(int id) {
        Currency currency = getSqlSession().selectOne("org.itevents.dao.mybatis.mapper.CurrencyMapper.getCurrency", id);
        if (currency == null) {
            throw new EntityNotFoundDaoException("Currency with id = " + id + " not found");
        }
        return currency;
    }

    @Override
    public List<Currency> getAllCurrencies() {
        return getSqlSession().selectList("org.itevents.dao.mybatis.mapper.CurrencyMapper.getAllCurrencies");
    }

    @Override
    public void addCurrency(Currency currency) {
        getSqlSession().insert("org.itevents.dao.mybatis.mapper.CurrencyMapper.addCurrency", currency);
    }

    @Override
    public void updateCurrency(Currency currency) {
        getSqlSession().update("org.itevents.dao.mybatis.mapper.CurrencyMapper.updateCurrency", currency);
    }
}
