package org.itevents.service;

import org.itevents.model.Currency;

import java.util.List;

/**
 * Created by vaa25 on 17.07.2015.
 */
public interface CurrencyService {

    Currency getCurrency(int id);

    List<Currency> getAllCurrencies();

    void addCurrency(Currency currency);

    void updateCurrency(Currency currency);

    Currency removeCurrency(Currency currency);

}
