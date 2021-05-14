package com.example.wsizcbdproject.service;

import com.example.wsizcbdproject.Entity.Item;

import java.util.List;

public interface ItemsService {
    List<Item> findAll();

    Item findById(int id);

    void save(Item item);

    void deleteById(int id);

    List<Item> findAllByOrderByNameAsc();
}
