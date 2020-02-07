package com.currencyconverter.model;

public class Currency {
    String abbrv;
    String description;
    Double exchangeRate;

    public String getAbbrv() {
        return abbrv;
    }

    public void setAbbrv(String abbrv) {
        this.abbrv = abbrv;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getExchangeRate() {
        return exchangeRate;
    }

    public void setExchangeRate(Double exchangeRate) {
        this.exchangeRate = exchangeRate;
    }

    @Override
    public String toString() {
        return "Currency [abbrv=" + abbrv + ", description=" + description + ", exchangeRate=" + exchangeRate + "]";
    }
}
