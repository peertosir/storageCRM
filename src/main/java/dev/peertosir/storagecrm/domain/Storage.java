package dev.peertosir.storagecrm.domain;

import java.util.Objects;
import java.util.Set;

public class Storage {
    private final Integer id;
    private final String title;
    private final String address;
    private final String storagePhone;

    public Storage(Integer id, String title, String address, String storagePhone) {
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

        Storage storage = (Storage) o;

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
}
