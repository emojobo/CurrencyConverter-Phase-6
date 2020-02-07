package com.currencyconverter.dao;

import com.currencyconverter.model.Currency;

import java.sql.Statement;
import java.util.List;
import java.util.Map;

public interface CurrencyDao {
    void findByAbbrv(String abbrv, Currency currency, Statement statement);

    void print(Statement statement);

    void populateDropdown(Statement statement, Map<String, String> currencyMap);
}
