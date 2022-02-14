package dev.peertosir.storagecrm.converter.mapper;

import dev.peertosir.storagecrm.domain.StorageItem;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class StorageItemMapper implements RowMapper<StorageItem> {
    @Override
    public StorageItem mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new StorageItem(
                rs.getInt("item_id"),
                rs.getInt("storage_id"),
                rs.getString("storage_address"),
                rs.getInt("amount")
        );
    }
}
