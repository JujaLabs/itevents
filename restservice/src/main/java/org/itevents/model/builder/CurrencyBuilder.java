package org.itevents.model.builder;

import org.itevents.model.Currency;

/**
 * Created by vaa25 on 03.10.2015.
 */
public class CurrencyBuilder {
    private int id;
    private String name;

    private CurrencyBuilder() {
    }

    public static CurrencyBuilder aCurrency() {
        return new CurrencyBuilder();
    }

    public CurrencyBuilder id(int id) {
        this.id = id;
        return this;
    }

    public CurrencyBuilder name(String name) {
        this.name = name;
        return this;
    }

    public CurrencyBuilder but() {
        return aCurrency().id(id).name(name);
    }

    public Currency build() {
        Currency currency = new Currency();
        currency.setId(id);
        currency.setName(name);
        return currency;
    }
}
