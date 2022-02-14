package dev.peertosir.storagecrm.dao;

import dev.peertosir.storagecrm.common.LimitOffset;
import dev.peertosir.storagecrm.converter.mapper.ItemMapper;
import dev.peertosir.storagecrm.dao.common.LimitOffsetDao;
import dev.peertosir.storagecrm.domain.Item;
import dev.peertosir.storagecrm.exception.ItemNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.Collection;

import static dev.peertosir.storagecrm.converter.TimestampConverter.getLocalDate;


@Repository
public class ItemDao {
    private static final Logger logger = LoggerFactory.getLogger(ItemDao.class);

    private static final String SELECT_QUERY =
            "SELECT i.ID, i.TITLE, i.BARCODE, i.CREATED_AT, sum(si.AMOUNT) as amount" +
            " FROM items i join STORAGE_ITEMS si on i.id = si.item_id GROUP BY i.ID";
    private static final String SELECT_BY_ID = SELECT_QUERY + "WHERE id = :id";
    private static final String INSERT_QUERY =
            "INSERT INTO items(title, barcode, created_at) VALUES" +
            "(:title, :barcode, :created_at)";
    private static final String UPDATE_QUERY =
            "UPDATE items SET title = :title, barcode = :barcode WHERE id = :id";
    private static final String DELETE_QUERY =
            "DELETE FROM items WHERE id = :id";

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public ItemDao(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Item getItem(Integer id) {
        try {
            Item item = jdbcTemplate.queryForObject(
                    SELECT_BY_ID,
                    new MapSqlParameterSource("id", id),
                    new ItemMapper()
            );
            return item;
        } catch (EmptyResultDataAccessException ex) {
            throw new ItemNotFoundException("Item with id " + id + " not found");
        }
    }

    public Collection<Item> getItems(LimitOffset limitOffset) {
        logger.info("Getting all items");
        return jdbcTemplate.query(
                SELECT_QUERY + LimitOffsetDao.getLimitAndOffsetQuery(limitOffset),
                new ItemMapper()
        );
    }

    public Integer createItem(Item item) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(
                INSERT_QUERY,
                new MapSqlParameterSource()
                        .addValue("title", item.getTitle())
                        .addValue("barcode", item.getBarcode())
                        .addValue("created_at", item.getCreatedAt()),
                keyHolder,
                new String[] {"id"}
        );

        return keyHolder.getKey().intValue();
    }

    public void updateItem(Integer id, Item item) {
        jdbcTemplate.update(
                UPDATE_QUERY,
                new MapSqlParameterSource()
                        .addValue("title", item.getTitle())
                        .addValue("barcode", item.getBarcode())
                        .addValue("id", id)
        );
    }

    public boolean deleteItem(Integer id) {
        return jdbcTemplate.update(
                DELETE_QUERY,
                new MapSqlParameterSource()
                        .addValue("id", id)
        ) > 0;
    }
}
