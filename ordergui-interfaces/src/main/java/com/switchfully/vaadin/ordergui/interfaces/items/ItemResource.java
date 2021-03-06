package com.switchfully.vaadin.ordergui.interfaces.items;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Component
public class ItemResource {

    private RestTemplate restTemplate;

    @Autowired
    public ItemResource(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<Item> getItems() {
        Item[] items = restTemplate.getForObject("http://localhost:9000/items", Item[].class);
        return Arrays.asList(items);
    }


    public void save(Item item) {
        restTemplate.postForObject("http://localhost:9000/items", item, Item.class);
    }

    public void update(Item item) {
        restTemplate.put("http://localhost:9000/items/" + item.getId(), item);
    }

    public Item getItemById(String id) {
        Item item = restTemplate.getForObject("http://localhost:9000/items/" + id, Item.class);
        return item;
    }
}
