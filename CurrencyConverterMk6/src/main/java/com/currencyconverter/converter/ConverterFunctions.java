package com.currencyconverter.converter;

import com.currencyconverter.dao.CurrencyDaoImpl;
import com.currencyconverter.jdbc.ConverterJdbc;
import com.currencyconverter.model.Currency;
import org.springframework.beans.factory.annotation.Autowired;

import javax.swing.*;
import java.math.RoundingMode;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.*;

public class ConverterFunctions {
    @Autowired
    private static CurrencyDaoImpl currencyDao = new CurrencyDaoImpl();

    private static ConverterJdbc search = new ConverterJdbc();
    private static Currency curr = new Currency();

    public static void refreshCurrencyDropdown(JComboBox currency, Statement statement) {
        Map<String, String> currencyMap = new HashMap<String, String>();

        currencyDao.populateDropdown(statement, currencyMap);

        for (Map.Entry mapElement : currencyMap.entrySet()) {
            String key = (String) mapElement.getKey();
            String value = (String) mapElement.getValue();
            String dropdownValue = key + ": " + value;
            currency.addItem(dropdownValue);
        }
    }

    public static double convert(double input, double result, JComboBox convertFrom, JComboBox convertTo, JTextField txtTo, Statement statement) {
        String Var1 = (String)convertFrom.getSelectedItem();
        String Var2 = (String)convertTo.getSelectedItem();
        String exchangeRate1 = Var1.substring(0,3);
        String exchangeRate2 = Var2.substring(0,3);

        if(Var1.contains("USD")) {
            curr.setAbbrv(exchangeRate2);
            currencyDao.findByAbbrv(curr.getAbbrv(), curr, statement);
            result = input * curr.getExchangeRate();
        }
        else if(Var2.contains("USD")) {
            curr.setAbbrv(exchangeRate1);
            currencyDao.findByAbbrv(curr.getAbbrv(), curr, statement);
            result = input / curr.getExchangeRate();
        }
        else {
            curr.setAbbrv(exchangeRate1);
            currencyDao.findByAbbrv(curr.getAbbrv(), curr, statement);
            Double from = curr.getExchangeRate();
            curr.setAbbrv(exchangeRate2);
            currencyDao.findByAbbrv(curr.getAbbrv(), curr, statement);
            Double to = curr.getExchangeRate();
            Double rate = to / from;

            result = input * rate;
        }

        DecimalFormat df = new DecimalFormat();
        df.setRoundingMode(RoundingMode.DOWN);
        String product = df.format(result);
        txtTo.setText("" + product);
        return Double.parseDouble(product);
    }

    public static void delete(JComboBox delete, Statement statement) {
        curr.setAbbrv(delete.getSelectedItem().toString().substring(0, 3));

        search.delete(statement, curr.getAbbrv());
    }

    public static void add(Currency currObj, Statement statement) {
        search.addCurrency(currObj, statement);
    }

    public static void edit(String selected, String abbrv, String desc, String rate, Statement statement) {
        String key = selected.substring(0,3);

        currencyDao.findByAbbrv(key, curr, statement);

        if(!abbrv.isEmpty()) {
            curr.setAbbrv(abbrv);
        }
        if(!desc.isEmpty()) {
            curr.setDescription(desc);
        }
        if(!rate.isEmpty()) {
            curr.setExchangeRate(Double.parseDouble(rate));
        }

        search.editCurrency(curr, statement, key);
    }

    public static void isNumber(JTextField txt) {
        if(!txt.getText().matches(".*\\d.*") || txt.getText().contains("-")) {
            int choice = JOptionPane.showConfirmDialog(null, "Please input a positive number",
                    "Inproper Input", JOptionPane.DEFAULT_OPTION);

            if(choice == 0) {
                txt.setText("");
            }
        }
    }

    public static void isString(JTextField txt) {
        if(txt.getText().matches(".*\\d.*")) {
            int choice = JOptionPane.showConfirmDialog(null, "Please input a text description",
                    "Inproper Input", JOptionPane.DEFAULT_OPTION);

            if(choice == 0) {
                txt.setText("");
            }
        }
    }

    public static void isProperFormat(JTextField txt) {
        if(txt.getText().length() > 3) {
            int choice = JOptionPane.showConfirmDialog(null, "Please input a 3 letter abbreviation for the currency",
                    "Inproper Input", JOptionPane.DEFAULT_OPTION);

            if(choice == 0) {
                txt.setText("");
            }
        }
    }
}
