package com.example.wsizcbdproject.dao;

import com.example.wsizcbdproject.Entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Integer> {
    // Extends basics functions from JpaRepository

    //Creates Query based on method name - "SELECT * FROM items ORDER BY name ASC"
    List<Item> findAllByOrderByNameAsc();
}
