package dev.peertosir.storagecrm.service.impl;

import dev.peertosir.storagecrm.dao.ItemDao;
import dev.peertosir.storagecrm.domain.Item;
import dev.peertosir.storagecrm.service.ItemService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class ItemServiceImplTest {

    @Autowired
    private ItemService itemService;

    @MockBean
    private ItemDao itemDao;

    @Test
    void getItem() {
        LocalDateTime time = LocalDateTime.now();
        Integer id = 1;
        Item expected = new Item(id, "Test", "barcode123", time, 0);
        when(itemDao.getItem(id)).thenReturn(expected);

        Item actualItem = itemService.getItem(1);


        assertEquals(actualItem.getId(), expected.getId());
        assertEquals(actualItem.getTitle(), expected.getTitle());
        assertEquals(actualItem.getBarcode(), expected.getBarcode());
        assertEquals(actualItem.getCreatedAt(), expected.getCreatedAt());
    }
}