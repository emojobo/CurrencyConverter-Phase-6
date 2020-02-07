package com.currencyconverter.jdbc;

import com.currencyconverter.model.Currency;

import java.sql.*;
import java.util.*;

public class ConverterJdbc {
    static final String INSERT = "INSERT INTO CURRENCY (abbreviation, description, exchange_rate) ";
    static final String VALUES = "VALUES(";
    static final String DELETE = "DELETE FROM CURRENCY WHERE abbreviation=";

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
