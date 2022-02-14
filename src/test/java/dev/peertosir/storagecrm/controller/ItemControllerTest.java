package dev.peertosir.storagecrm.controller;

import dev.peertosir.storagecrm.common.LimitOffset;
import dev.peertosir.storagecrm.domain.Item;
import dev.peertosir.storagecrm.exception.ItemNotFoundException;
import dev.peertosir.storagecrm.service.ItemService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ItemController.class)
class ItemControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ItemService itemService;

    @Test
    void getItemWithValidId() throws Exception {
        int id = 1;
        String title = "Test title";
        String barcode = "Test barcode";
        LocalDateTime time = LocalDateTime.of(2021, 2, 23, 23, 45, 1);

        when(itemService.getItem(1)).thenReturn(new Item(id, title, barcode, time, null));

        this.mockMvc.perform(get("/api/items/{id}", id)).andDo(print()).andExpect(status().isOk())
                .andExpectAll(
                        jsonPath("$.id").value(1),
                        jsonPath("$.title").value(title),
                        jsonPath("$.barcode").value(barcode),
                        jsonPath("$.created_at").value(time.toString())
                );

    }

    @Test
    void getItemWithInvalidId() throws Exception {
        int id = 43;

        when(itemService.getItem(id)).thenThrow(new ItemNotFoundException("Item with id " + id + " was not found"));

        this.mockMvc.perform(get("/api/items/{id}", id)).andDo(print()).andExpect(status().isNotFound());
    }

    @Test
    void getItems() throws Exception {
        int id = 1;
        String title = "Test title";
        String barcode = "Test barcode";
        LocalDateTime time = LocalDateTime.of(2021, 2, 23, 23, 45, 1);

        List<Item> items = new ArrayList<>();
        while (id < 5) {
            items.add(
                    new Item(
                            id,
                            title + id,
                            barcode + id,
                            time,
                            123
                    )
            );
            id++;
        }

        when(itemService.getItems(new LimitOffset(10, 0))).thenReturn(items);


        this.mockMvc.perform(get("/api/items")).andDo(print()).andExpect(status().isOk()).andExpectAll(
                jsonPath("$").isArray(),
                jsonPath("$", hasSize(4))
        );

    }
}