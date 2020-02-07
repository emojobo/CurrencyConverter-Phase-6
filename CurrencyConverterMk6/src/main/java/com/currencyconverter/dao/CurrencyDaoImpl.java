package com.currencyconverter.dao;

import com.currencyconverter.jdbc.ConverterJdbc;
import com.currencyconverter.model.Currency;
import com.currencyconverter.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CurrencyDaoImpl implements CurrencyDao{
    NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    public void setNamedParameterJdbcTemplate(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    public Currency findByName(String abbrv) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("abbreviation", abbrv);

        String sql = "SELECT * FROM CURRENCY WHERE abbreviation=" + "'" + abbrv + "';";

        Currency result = namedParameterJdbcTemplate.queryForObject(sql, params, new CurrencyMapper());

        return result;
    }

    public List<Currency> findAll() {
        Map<String, Object> params = new HashMap<String, Object>();

        String sql = "";
        List<Currency> result = namedParameterJdbcTemplate.query(sql, params, new CurrencyMapper());

        return result;
    }

    private static final class CurrencyMapper implements RowMapper<Currency> {
        public Currency mapRow(ResultSet rs, int rowNum) throws SQLException {
            Currency currency = new Currency();
            currency.setAbbrv(rs.getString("abbreviation"));
            currency.setDescription(rs.getString("description"));
            currency.setExchangeRate(rs.getDouble("exchange_rate"));
            return currency;
        }
    }
}
