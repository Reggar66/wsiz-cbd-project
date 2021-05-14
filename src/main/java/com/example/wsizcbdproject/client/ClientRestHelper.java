package com.example.wsizcbdproject.client;

import com.example.wsizcbdproject.Entity.Item;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class ClientRestHelper {

    private final RestTemplate restTemplate = new RestTemplate();

    /**
     * Fetch data from db.
     *
     * @return array of items.
     */
    public Item[] fetchItems() {
        String uri = "http://localhost:8080/api/items";
        ResponseEntity<Item[]> result = restTemplate.getForEntity(uri, Item[].class);
        return result.getBody();
    }


    /**
     * Deletes given item.
     *
     * @param item item to deletion.
     * @return String with confirmation message.
     */
    public String deleteItem(Item item) {
        if (item == null)
            return "Item not selected or don't exist";

        String uri = String.format("http://localhost:8080/api/items/%d", item.getId());
        restTemplate.delete(uri);

        return "Deleted item: " + item.toString();
    }

    public void addItem(Item item) {
        if (item == null)
            return;

        String uri = "http://localhost:8080/api/items/";
        restTemplate.postForObject(uri, item, Item.class);
    }

    public void updateItem(Item item) {
        if (item == null)
            return;

        String uri = "http://localhost:8080/api/items/";
        restTemplate.put(uri, item);
    }
}
