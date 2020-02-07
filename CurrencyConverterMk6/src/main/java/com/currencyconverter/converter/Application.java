package com.currencyconverter.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class Application {
    @Autowired
    CurrencyConverter currencyConverter;

    public static void main(String[] args) {
        //SpringApplication.run(CurrencyConverter.class, args);

        SpringApplicationBuilder builder = new SpringApplicationBuilder(CurrencyConverter.class);
        builder.headless(false);
        ConfigurableApplicationContext context = builder.run(args);
    }
}
