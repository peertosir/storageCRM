package dev.peertosir.storagecrm.exception;

public class StorageNotFoundException extends RuntimeException {
    public StorageNotFoundException(String message) {
        super(message);
    }
}
