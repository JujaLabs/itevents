package org.itevents.service.transactional;

import org.itevents.dao.CurrencyDao;
import org.itevents.dao.exception.EntityNotFoundDaoException;
import org.itevents.dao.model.Currency;
import org.itevents.service.CurrencyService;
import org.itevents.service.exception.EntityNotFoundServiceException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.List;

/**
 * Created by vaa25 on 09.08.2015.
 */
@Service("currencyService")
@Transactional
public class MyBatisCurrencyService implements CurrencyService {

    @Inject
    private CurrencyDao currencyDao;

    @Override
    public Currency getCurrency(int id) {
        try {
            return currencyDao.getCurrency(id);
        } catch (EntityNotFoundDaoException e) {
            throw new EntityNotFoundServiceException(e.getMessage(), e);
        }
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
}
