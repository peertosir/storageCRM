package dev.peertosir.storagecrm.service;

import dev.peertosir.storagecrm.common.LimitOffset;
import dev.peertosir.storagecrm.domain.Item;
import dev.peertosir.storagecrm.domain.Storage;

import java.util.Collection;

public interface StorageService {
    Storage getStorage(Integer id);
    Collection<Storage> getStorages(LimitOffset limitOffset);
    Integer createStorage(Storage storage);
    void updateStorage(Integer id, Storage storage);
    boolean deleteStorage(Integer id);
    void addItemToStorage(Integer id, Integer itemId, int amount);
    Collection<Item> getStorageItems(Integer id);
}
