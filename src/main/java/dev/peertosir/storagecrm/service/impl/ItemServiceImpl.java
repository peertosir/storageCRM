package dev.peertosir.storagecrm.service.impl;

import dev.peertosir.storagecrm.common.LimitOffset;
import dev.peertosir.storagecrm.dao.ItemDao;
import dev.peertosir.storagecrm.domain.Item;
import dev.peertosir.storagecrm.service.ItemService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

@Service
public class ItemServiceImpl implements ItemService {
    private final ItemDao itemDao;

    public ItemServiceImpl(ItemDao itemDao) {
        this.itemDao = itemDao;
    }

    @Override
    @Transactional
    public Item getItem(Integer id) {
        return itemDao.getItem(id);
    }

    @Override
    @Transactional
    public Collection<Item> getItems(LimitOffset limitOffset) {
        return itemDao.getItems(limitOffset);
    }

    @Override
    @Transactional
    public Integer createItem(Item item) {
        return itemDao.createItem(item);
    }

    @Override
    @Transactional
    public void updateItem(Integer id, Item item) {
        itemDao.updateItem(id, item);
    }

    @Override
    @Transactional
    public boolean deleteItem(Integer id) {
        return itemDao.deleteItem(id);
    }

}
