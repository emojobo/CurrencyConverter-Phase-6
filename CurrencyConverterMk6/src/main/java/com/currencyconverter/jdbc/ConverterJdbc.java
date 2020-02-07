package com.currencyconverter.jdbc;

import com.currencyconverter.model.Currency;
import org.h2.jdbcx.JdbcDataSource;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import javax.sql.DataSource;
import java.sql.*;
import java.util.*;

public class ConverterJdbc {
    static final String JDBC_DRIVER = "org.h2.Driver";
    static final String DB_URL = "jdbc:h2:~/test";
    static final String CREATE = "CREATE TABLE CURRENCY (id INT, abbreviation VARCHAR(3), description VARCHAR(250), exchange_rate DOUBLE);";
    static final String INSERT = "INSERT INTO CURRENCY (abbreviation, description, exchange_rate) ";
    static final String VALUES = "VALUES(";
    static final String PRINT = "SELECT abbreviation, description, exchange_rate FROM CURRENCY;";
    static final String DROPDOWN = "SELECT abbreviation, description FROM CURRENCY;";
    static final String SELECT = "SELECT * FROM CURRENCY WHERE abbreviation=";
    static final String DELETE = "DELETE FROM CURRENCY WHERE abbreviation=";

    //Database credentials
    static final String USER = "sa";
    static final String PASS = "";

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

    public void addCurrency(Currency currObj, Statement statement) {
        try {
            String sql = INSERT + VALUES + "'" + currObj.getAbbrv() + "'" + ", " + "'" + currObj.getDescription() + "'" + ", " + currObj.getExchangeRate() + ");";
            System.out.println("Attempting to insert to table " + sql);
            statement.execute(sql);
            System.out.println("Insertion successful!");
        }
        catch (SQLException e) {
            printSQLException(e);
        }
    }

    public void editCurrency(Currency currObj, Statement statement, String key) {
        try {
            String sql = DELETE + "'" + key + "';";
            statement.execute(sql);
            String sql2 = INSERT + VALUES + "'" + currObj.getAbbrv() + "'" + ", " + "'" + currObj.getDescription() + "'" + ", " + currObj.getExchangeRate() + ");";
            statement.execute(sql2);
        }
        catch (SQLException e) {
            printSQLException(e);
        }
    }

    public void delete(Statement statement, String abbrv) {
        try {
            String sql = DELETE + "'" + abbrv + "';";
            System.out.println("Attempting to delete from table " + sql);
            statement.execute(sql);
            System.out.println("Deletion successful!");
        }
        catch (SQLException e) {
            printSQLException(e);
        }
    }

    public void insert(Properties curr, Properties exchange, Statement statement) {
        Enumeration keys = curr.propertyNames();

        while (keys.hasMoreElements()) {
            String key = (String) keys.nextElement();
            String value = curr.getProperty(key);
            Double rate = Double.parseDouble(exchange.getProperty(key));

            try {
                String sql = INSERT + VALUES + "'" + key + "'" + ", " + "'" + value + "'" + ", " + rate + ");";
                System.out.println("Attempting to insert to table " + sql);
                statement.execute(sql);
                System.out.println("Insertion successful!");
            }
            catch (SQLException e) {
                printSQLException(e);
            }
        }
    }

    public static void printSQLException(SQLException ex) {
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
