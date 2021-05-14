package com.example.wsizcbdproject.rest;

import com.example.wsizcbdproject.Entity.Item;
import com.example.wsizcbdproject.service.ItemsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ItemRestController {

    private final ItemsService itemsService;

    @Autowired
    public ItemRestController(ItemsService itemsService) {
        this.itemsService = itemsService;
    }

    @GetMapping("/items")
    public List<Item> findAll() {
        return itemsService.findAllByOrderByNameAsc();
    }

    @GetMapping("/items/{itemId}")
    public Item getItem(@PathVariable int itemId) {

        Item item = itemsService.findById(itemId);
        if (item == null)
            throw new RuntimeException("No item found with id=" + itemId);

        return itemsService.findById(itemId);
    }

    @PostMapping("/items")
    public Item addItem(@RequestBody Item item) {
        // Setting id value to 0 in case id value was passed by RequestBody
        // it will be auto-set in DB (starting from 1)
        item.setId(0);
        itemsService.save(item);
        return item;
    }

    @PutMapping("/items")
    public Item updateItem(@RequestBody Item item) {
        itemsService.save(item);
        return item;
    }

    @DeleteMapping("/items/{itemId}")
    public String deleteItem(@PathVariable int itemId) {
        Item tempItem = itemsService.findById(itemId);
        if (tempItem == null)
            throw new RuntimeException("No item found with id=" + itemId);

        itemsService.deleteById(itemId);

        return String
                .format("Item [id=%d, name=%s, price=%f]",
                        tempItem.getId(),
                        tempItem.getName(),
                        tempItem.getPrice());
    }
}
