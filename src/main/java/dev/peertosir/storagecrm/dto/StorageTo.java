package dev.peertosir.storagecrm.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import dev.peertosir.storagecrm.domain.Storage;

import java.util.Objects;


@JsonNaming(PropertyNamingStrategies.LowerCamelCaseStrategy.class)
public class StorageTo {
    private final Integer id;
    private final String title;
    private final String address;
    private final String storagePhone;

    @JsonCreator
    public StorageTo(Integer id, String title, String address, String storagePhone) {
        this.id = id;
        this.title = title;
        this.address = address;
        this.storagePhone = storagePhone;
    }

    public Integer getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getAddress() {
        return address;
    }

    public String getStoragePhone() {
        return storagePhone;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        StorageTo storage = (StorageTo) o;

        return Objects.equals(id, storage.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "Storage{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", address='" + address + '\'' +
                ", storagePhone='" + storagePhone + '\'' +
                '}';
    }

    public static Storage toStorage(StorageTo storageTo) {
        if (storageTo == null) {
            return null;
        }
        return new Storage(
                storageTo.getId(),
                storageTo.getTitle(),
                storageTo.getAddress(),
                storageTo.getStoragePhone()
        );
    }

    public static StorageTo fromStorage(Storage storage) {
        if (storage == null) {
            return null;
        }

        return new StorageTo(
                storage.getId(),
                storage.getTitle(),
                storage.getAddress(),
                storage.getStoragePhone()
        );
    }
}
