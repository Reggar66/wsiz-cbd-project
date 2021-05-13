package com.example.wsizcbdproject.controller;

import com.example.wsizcbdproject.Entity.Item;
import com.example.wsizcbdproject.service.ItemsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/store")
public class ItemController {

    ItemsService itemService;

    /**
     * Constructor used to inject dependencies.
     *
     * @param itemService Injected by Spring.
     */
    @Autowired
    public ItemController(ItemsService itemService) {
        this.itemService = itemService;
    }

    /**
     * Mapping for 'List of Items' form.
     *
     * @param model Injected via spring. Used to pass list of items to web form.
     * @return String to list-items.html
     */
    @GetMapping("/list")
    public String listItems(Model model) {
        model.addAttribute("items", itemService.findAll());
        return "list-items";
    }

    /**
     * Mapping for 'Add new Item' form
     *
     * @param model Injected by spring. Used for passing new Item object to web form.
     * @return String to item-form.html
     */
    @GetMapping("/showFormForAdd")
    public String showAddForm(Model model) {
        Item item = new Item();
        // Setting price of new item to 1.25 so it won't show "0.0" once we load the form.
        // It is still set by user in form.
        item.setPrice(1.25f);
        model.addAttribute("item", item);
        return "item-form";
    }

    /**
     * Mapping for 'Update' form
     *
     * @param itemId Requested param, ID of item needed to update.
     *               Received from list-items.html parameter named "itemId" that is embedded in site link.
     * @param model  Injected by Spring.
     *               Used to pass item required to update.
     * @return String to item-form.html
     */
    @GetMapping("/showFormForUpdate")
    public String showUpdateForm(@RequestParam("itemId") int itemId, Model model) {
        Item item = itemService.findById(itemId);
        model.addAttribute("item", item);
        return "item-form";
    }

    /**
     * Mapping for saving or updating item to database.
     *
     * @param item Received from item-form.html via user input.
     * @return Redirect string to list of items
     */
    @PostMapping("/save")
    public String saveItem(@ModelAttribute("item") Item item) {
        itemService.save(item);
        return "redirect:/store/list";
    }

    /**
     * Mapping for deleting item.
     *
     * @param itemId Requested param, ID of item needed to delete.
     *               Received from list-items.html parameter named "itemId" that is embedded in site link.
     * @return String to item-form.html
     */
    @GetMapping("/delete")
    public String deleteItem(@RequestParam("itemId") int itemId) {
        itemService.deleteById(itemId);
        return "redirect:/store/list";
    }
}
