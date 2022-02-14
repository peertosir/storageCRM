package dev.peertosir.storagecrm.domain;

import java.time.LocalDateTime;
import java.util.Objects;

public class Item {
    private final Integer id;
    private final String title;
    private final String barcode;
    private final LocalDateTime createdAt;
    private final Integer amount;

    public Item(Integer id, String title, String barcode, LocalDateTime createdAt, Integer amount) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Item item = (Item) o;

        return Objects.equals(id, item.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Item{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", barcode='" + barcode + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}
