package dev.peertosir.storagecrm.domain;

public class StorageItem {
    private final Integer itemId;
    private final Integer storageId;
    private final String storageAddress;
    private final Integer amount;

    public StorageItem(Integer itemId, Integer storageId, String storageAddress, Integer amount) {
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

        StorageItem that = (StorageItem) o;

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
