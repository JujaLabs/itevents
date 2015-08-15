package org.itevents.service;

import org.itevents.dao.CurrencyDao;
import org.itevents.model.Currency;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.List;

/**
 * Created by vaa25 on 09.08.2015.
 */
@Service("currencyService")
@Transactional
public class CurrencyServiceImpl implements CurrencyService {

    @Inject
    private CurrencyDao currencyDao;

    @Override
    public Currency getCurrency(int id) {
        return currencyDao.getCurrency(id);
    }

    @Override
    public List<Currency> getAllCurrencies() {
        return currencyDao.getAllCurrencies();
    }

    @Override
    public void addCurrency(Currency currency) {
        currencyDao.addCurrency(currency);
    }

    @Override
    public void updateCurrency(Currency currency) {
        currencyDao.updateCurrency(currency);
    }

    @Override
    public Currency removeCurrency(Currency currency) {
        Currency deletingCurrency = currencyDao.getCurrency(currency.getId());
        if (deletingCurrency != null) {
            currencyDao.removeCurrency(currency);
        }
        return deletingCurrency;
    }
}
