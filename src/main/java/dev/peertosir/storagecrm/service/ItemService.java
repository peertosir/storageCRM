package dev.peertosir.storagecrm.service;

import dev.peertosir.storagecrm.common.LimitOffset;
import dev.peertosir.storagecrm.domain.Item;

import java.util.Collection;

public interface ItemService {
    Item getItem(Integer id);
    Collection<Item> getItems(LimitOffset limitOffset);
    Integer createItem(Item item);
    void updateItem(Integer id, Item item);
    boolean deleteItem(Integer id);
}
