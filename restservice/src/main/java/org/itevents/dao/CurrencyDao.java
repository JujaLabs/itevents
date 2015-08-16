package org.itevents.dao;

import org.itevents.model.Currency;

import java.util.List;

/**
 * Created by vaa25 on 17.07.2015.
 */
public interface CurrencyDao {

    Currency getCurrency(int id);

    List<Currency> getAllCurrencies();

    void addCurrency(Currency currency);

    void updateCurrency(Currency currency);

    void removeCurrency(Currency currency);

}
