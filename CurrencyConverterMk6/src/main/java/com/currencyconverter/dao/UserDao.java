package com.currencyconverter.dao;

import com.currencyconverter.model.User;

import java.util.List;

public interface UserDao {
    User findByName(String name);

    List<User> findAll();
}
