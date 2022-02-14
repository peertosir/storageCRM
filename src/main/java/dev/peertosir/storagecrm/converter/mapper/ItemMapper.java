package dev.peertosir.storagecrm.converter.mapper;

import dev.peertosir.storagecrm.domain.Item;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import static dev.peertosir.storagecrm.converter.TimestampConverter.getLocalDate;

public class ItemMapper implements RowMapper<Item> {
    @Override
    public Item mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Item(
                rs.getInt("id"),
                rs.getString("title"),
                rs.getString("barcode"),
                getLocalDate(rs.getTimestamp("created_at")),
                rs.getInt("amount")
        );
    }
}
