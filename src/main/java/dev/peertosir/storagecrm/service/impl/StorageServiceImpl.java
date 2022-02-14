package dev.peertosir.storagecrm.service.impl;

import dev.peertosir.storagecrm.common.LimitOffset;
import dev.peertosir.storagecrm.dao.StorageDao;
import dev.peertosir.storagecrm.domain.Item;
import dev.peertosir.storagecrm.domain.Storage;
import dev.peertosir.storagecrm.service.StorageService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

@Service
public class StorageServiceImpl implements StorageService {
    private final StorageDao storageDao;

    public StorageServiceImpl(StorageDao storageDao) {
        this.storageDao = storageDao;
    }

    @Override
    @Transactional
    public Storage getStorage(Integer id) {
        return storageDao.getStorage(id);
    }

    @Override
    @Transactional
    public Collection<Storage> getStorages(LimitOffset limitOffset) {
        return storageDao.getStorages(limitOffset);
    }

    @Override
    @Transactional
    public Integer createStorage(Storage storage) {
        return storageDao.createStorage(storage);
    }

    @Override
    @Transactional
    public void updateStorage(Integer id, Storage storage) {
        storageDao.updateStorage(id, storage);
    }

    @Override
    @Transactional
    public boolean deleteStorage(Integer id) {
        return storageDao.deleteStorage(id);
    }

    @Override
    @Transactional
    public void addItemToStorage(Integer id, Integer itemId, int amount) {
        storageDao.addItemToStorage(id, itemId, amount);
    }

    @Override
    public Collection<Item> getStorageItems(Integer id) {
        return storageDao.getStorageItems(id);
    }
}
