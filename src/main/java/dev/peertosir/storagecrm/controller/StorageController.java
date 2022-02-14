package dev.peertosir.storagecrm.controller;

import dev.peertosir.storagecrm.common.LimitOffset;
import dev.peertosir.storagecrm.dto.ItemTo;
import dev.peertosir.storagecrm.dto.StorageAddItemRequestTo;
import dev.peertosir.storagecrm.dto.StorageTo;
import dev.peertosir.storagecrm.exception.ItemAlreadyInStoreException;
import dev.peertosir.storagecrm.exception.ItemNotFoundException;
import dev.peertosir.storagecrm.exception.StorageNotFoundException;
import dev.peertosir.storagecrm.exception.http.BadRequestException;
import dev.peertosir.storagecrm.exception.http.NotFoundException;
import dev.peertosir.storagecrm.service.StorageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/storages")
public class StorageController {
    private static final Logger logger = LoggerFactory.getLogger(StorageController.class);

    private final StorageService storageService;

    public StorageController(StorageService storageService) {
        this.storageService = storageService;
    }

    @GetMapping
    public List<StorageTo> getStorages(
            @RequestParam(value = "limit", defaultValue = "10") int limit,
            @RequestParam(value = "offset", defaultValue = "0") int offset
    ) {
        LimitOffset limitOffset = new LimitOffset(limit, offset);
        return storageService.getStorages(limitOffset).stream().map(StorageTo::fromStorage).collect(Collectors.toList());
    }

    @GetMapping("{id}")
    public StorageTo getStorage(@PathVariable("id") Integer id) {
        try {
            return StorageTo.fromStorage(storageService.getStorage(id));
        } catch (StorageNotFoundException ex) {
            throw new NotFoundException(ex.getMessage());
        }
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Integer createStorage(@RequestBody StorageTo storageTo) {
        return storageService.createStorage(StorageTo.toStorage(storageTo));
    }

    @PutMapping("{id}")
    public void updateStorage(@PathVariable("id") Integer id, @RequestBody StorageTo storageTo) {
        logger.info(storageTo.toString());
        try {
            storageService.updateStorage(id, StorageTo.toStorage(storageTo));
        } catch (StorageNotFoundException ex) {
            throw new NotFoundException(ex.getMessage());
        }
    }

    @DeleteMapping("{id}")
    public void deleteStorage(@PathVariable("id") Integer id) {
        boolean isDeleted = storageService.deleteStorage(id);
        if (isDeleted) {
            throw new NotFoundException("Storage with id = [" + id + "] not found");
        }
    }

    @PostMapping("{id}/items/{itemId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void addItemToStorage(
            @PathVariable("id") Integer id,
            @PathVariable("itemId") Integer itemId,
            @Valid @RequestBody StorageAddItemRequestTo requestTo
    ) {
        try {
            storageService.addItemToStorage(id, itemId, requestTo.getAmount());
        } catch (ItemAlreadyInStoreException ex) {
            throw new BadRequestException(ex.getMessage());
        } catch (ItemNotFoundException | StorageNotFoundException ex) {
            throw new NotFoundException(ex.getMessage());
        }
    }

    @GetMapping("{id}/items")
    public List<ItemTo> getStorageItems(@PathVariable("id") Integer id) {
        try {
            return storageService.getStorageItems(id).stream().map(ItemTo::fromItem).collect(Collectors.toList());
        } catch (StorageNotFoundException ex) {
            throw new NotFoundException(ex.getMessage());
        }
    }

}
