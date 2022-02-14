package dev.peertosir.storagecrm.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import dev.peertosir.storagecrm.domain.Item;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ItemTo {
    private final Integer id;
    @NotNull
    private final String title;
    @NotNull
    private final String barcode;
    @NotNull
    private final LocalDateTime createdAt;
    private final Integer amount;

    @JsonCreator
    public ItemTo(Integer id, String title, String barcode, LocalDateTime createdAt, Integer amount) {
        this.id = id;
        this.title = title;
        this.barcode = barcode;
        this.createdAt = createdAt;
        this.amount = amount;
    }

    public Integer getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getBarcode() {
        return barcode;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public Integer getAmount() {
        return amount;
    }

    public static ItemTo fromItem(Item item) {
        if (item == null) {
            return null;
        }

        return new ItemTo(
                item.getId(),
                item.getTitle(),
                item.getBarcode(),
                item.getCreatedAt(),
                item.getAmount()
        );
    }

    public static Item toItem(ItemTo item) {
        if (item == null) {
            return null;
        }

        return new Item(
                item.getId(),
                item.getTitle(),
                item.getBarcode(),
                item.getCreatedAt(),
                null
        );
    }
}
