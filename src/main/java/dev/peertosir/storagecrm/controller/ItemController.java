package dev.peertosir.storagecrm.controller;

import dev.peertosir.storagecrm.common.LimitOffset;
import dev.peertosir.storagecrm.dto.ItemTo;
import dev.peertosir.storagecrm.exception.ItemNotFoundException;
import dev.peertosir.storagecrm.exception.http.NotFoundException;
import dev.peertosir.storagecrm.service.ItemService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/items")
public class ItemController {
    private final ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping("{id}")
    public ItemTo getItem(@PathVariable("id") Integer id) {
        try {
            return ItemTo.fromItem(itemService.getItem(id));
        } catch (ItemNotFoundException ex) {
            throw new NotFoundException(ex.getMessage());
        }
    }

    @GetMapping
    public List<ItemTo> getItems(
            @RequestParam(value = "limit", defaultValue = "10") int limit,
            @RequestParam(value = "offset", defaultValue = "0") int offset
            ) {
        LimitOffset limitOffset = new LimitOffset(limit, offset);
        return itemService.getItems(limitOffset).stream().map(ItemTo::fromItem).collect(Collectors.toList());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Integer createItem(@RequestBody ItemTo itemTo) {
        return itemService.createItem(ItemTo.toItem(itemTo));
    }

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateItem(@PathVariable("id") Integer id, @RequestBody ItemTo itemTo) {
        try {
            itemService.updateItem(id, ItemTo.toItem(itemTo));
        } catch (ItemNotFoundException ex) {
            throw new NotFoundException(ex.getMessage());
        }
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteItem(@PathVariable("id") Integer id) {
        boolean isDeleted = itemService.deleteItem(id);
        if (!isDeleted) {
            throw new NotFoundException("Item with id = [" + id + "] not found");
        }
    }
}
