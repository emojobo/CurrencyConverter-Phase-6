package com.currencyconverter.dao;

import com.currencyconverter.model.Currency;

import java.util.List;

public interface CurrencyDao {
    Currency findByName(String abbrv);

    List<Currency> findAll();
}
