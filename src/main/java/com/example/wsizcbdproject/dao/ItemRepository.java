package com.example.wsizcbdproject.dao;

import com.example.wsizcbdproject.Entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Integer> {
    // Extends basics functions from JpaRepository
}
