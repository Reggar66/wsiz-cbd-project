package com.example.wsizcbdproject.controller;

import com.example.wsizcbdproject.service.ItemsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/store")
public class ItemController {

    ItemsService itemService;

    @Autowired
    public ItemController(ItemsService itemService) {
        this.itemService = itemService;
    }

    @GetMapping("/list")
    public String listItems() {

        return "list-items";
    }
}
