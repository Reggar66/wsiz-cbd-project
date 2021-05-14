package com.example.wsizcbdproject.service;

import com.example.wsizcbdproject.Entity.Item;
import com.example.wsizcbdproject.dao.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ItemServiceImpl implements ItemsService {

    private final ItemRepository itemRepository;

    @Autowired
    public ItemServiceImpl(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    @Override
    public List<Item> findAll() {
        return itemRepository.findAll();
    }

    @Override
    public Item findById(int id) {

        Optional<Item> result = itemRepository.findById(id);

        return result.orElse(null);
    }

    @Override
    public void save(Item item) {
        itemRepository.save(item);
    }

    @Override
    public void deleteById(int id) {
        itemRepository.deleteById(id);
    }

    @Override
    public List<Item> findAllByOrderByNameAsc() {
        return itemRepository.findAllByOrderByNameAsc();
    }
}
