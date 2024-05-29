package com.panel.todo.Service;

import com.panel.todo.Entity.Item;
import com.panel.todo.Exception.ItemNotFoundException;

import java.util.List;

public interface ItemService {
    Item save(Item item,String t);
    void delete(int id) throws ItemNotFoundException;
    Item update(Item item) throws ItemNotFoundException;
    Item findById(int id) throws ItemNotFoundException;

    List<Item> findByName(String title);

    List<Item> findAll();
}
