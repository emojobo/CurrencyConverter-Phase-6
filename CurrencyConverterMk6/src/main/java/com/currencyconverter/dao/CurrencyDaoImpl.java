package com.currencyconverter.dao;

import com.currencyconverter.model.Currency;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;

public class CurrencyDaoImpl implements CurrencyDao{
    static final String PRINT = "SELECT abbreviation, description, exchange_rate FROM CURRENCY;";
    static final String DROPDOWN = "SELECT abbreviation, description FROM CURRENCY;";
    static final String SELECT = "SELECT * FROM CURRENCY WHERE abbreviation=";

    public void populateDropdown(Statement statement, Map<String, String> currencyMap) {
        try {
            ResultSet rs = statement.executeQuery(DROPDOWN);

            while(rs.next()) {
                String key = rs.getString("abbreviation");
                String value = rs.getString("description");
                currencyMap.put(key, value);
            }
        }
        catch (SQLException e) {
            printSQLException(e);
        }
    }

    public void print(Statement statement) {
        try {
            ResultSet rs = statement.executeQuery(PRINT);

            while(rs.next()) {
                String abbrv = rs.getString("abbreviation");
                String description = rs.getString("description");
                Double exchange = rs.getDouble("exchange_rate");

                System.out.println("Abbrv: " + abbrv);
                System.out.println("Desc: " + description);
                System.out.println("Rate: " + exchange);
            }
        }
        catch (SQLException e) {
            printSQLException(e);
        }
    }

    public void findByAbbrv(String abbrv, Currency currency, Statement statement) {
        try {
            String sql = SELECT + "'" + abbrv + "';";
            ResultSet rs = statement.executeQuery(sql);


            while(rs.next()) {
                currency.setAbbrv(rs.getString("abbreviation"));
                currency.setDescription(rs.getString("description"));
                currency.setExchangeRate(rs.getDouble("exchange_rate"));
            }
        }
        catch (SQLException e) {
            printSQLException(e);
        }
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

    private static void printSQLException(SQLException ex) {
        for (Throwable e : ex) {
            if (e instanceof SQLException) {
                e.printStackTrace(System.err);
                System.err.println("SQLState: " + ((SQLException) e).getSQLState());
                System.err.println("Error Code: " + ((SQLException) e).getErrorCode());
                System.err.println("Message: " + e.getMessage());
                Throwable t = ex.getCause();
                while (t != null) {
                    System.out.println("Cause: " + t);
                    t = t.getCause();
                }
            }
        }
    }
}
