package dev.peertosir.storagecrm.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class StorageItemTo {
    @NotNull
    private final Integer itemId;
    @NotNull
    private final Integer storageId;
    @NotNull
    private final String storageAddress;
    @NotNull
    @Positive
    private final Integer amount;

    @JsonCreator
    public StorageItemTo(Integer itemId, Integer storageId, String storageAddress, Integer amount) {
        this.itemId = itemId;
        this.storageId = storageId;
        this.storageAddress = storageAddress;
        this.amount = amount;
    }

    public Integer getItemId() {
        return itemId;
    }

    public Integer getStorageId() {
        return storageId;
    }

    public Integer getAmount() {
        return amount;
    }

    public String getStorageAddress() {
        return storageAddress;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        StorageItemTo that = (StorageItemTo) o;

        if (!itemId.equals(that.itemId)) return false;
        return storageId.equals(that.storageId);
    }

    @Override
    public int hashCode() {
        int result = itemId.hashCode();
        result = 31 * result + storageId.hashCode();
        return result;
    }
}
