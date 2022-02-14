package dev.peertosir.storagecrm.converter;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class TimestampConverter {
    public static LocalDateTime getLocalDate(Timestamp timestamp) {
        return timestamp != null ? timestamp.toLocalDateTime() : null;
    }
}
