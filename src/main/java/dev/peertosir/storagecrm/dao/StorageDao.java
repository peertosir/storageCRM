package dev.peertosir.storagecrm.dao;

import dev.peertosir.storagecrm.common.LimitOffset;
import dev.peertosir.storagecrm.converter.mapper.ItemMapper;
import dev.peertosir.storagecrm.dao.common.LimitOffsetDao;
import dev.peertosir.storagecrm.domain.Item;
import dev.peertosir.storagecrm.domain.Storage;
import dev.peertosir.storagecrm.dto.ItemTo;
import dev.peertosir.storagecrm.exception.ItemAlreadyInStoreException;
import dev.peertosir.storagecrm.exception.StorageNotFoundException;
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
import java.util.List;

@Repository
public class StorageDao {
    private static final Logger logger = LoggerFactory.getLogger(StorageDao.class);

    private final static String SELECT_QUERY = "SELECT * FROM storages ";

    private final static String SELECT_BY_ID_QUERY = SELECT_QUERY + "WHERE id=:id";

    private final static String INSERT_QUERY =
            "INSERT INTO storages(title, address, storage_phone) VALUES (:title, :address, :storagePhone)";

    private final static String UPDATE_QUERY =
            "UPDATE storages SET title=:title, address=:address, storage_phone=:storagePhone WHERE id=:id";

    private final static String DELETE_QUERY = "DELETE FROM storages WHERE id=:id";

    private final static String ADD_ITEM_TO_STORE_QUERY =
            "INSERT INTO storage_items(item_id, storage_id, amount) VALUES (:item_id, :storage_id, :amount)";

    private final static String CHECK_ITEM_IN_STORE_QUERY =
            "SELECT COUNT(*) as storage_item FROM storage_items WHERE item_id=:item_id and storage_id=:storage_id";

    private final static String SELECT_ITEMS_BY_STORAGE_ID =
            "SELECT i.ID, i.TITLE, i.BARCODE, i.CREATED_AT, si.AMOUNT as amount" +
            " FROM items i join STORAGE_ITEMS si on i.id = si.item_id WHERE si.storage_id = :storage_id";

    private static final RowMapper<Storage> ROW_MAPPER = ((rs, rowNum) -> new Storage(
            rs.getInt("id"),
            rs.getString("title"),
            rs.getString("address"),
            rs.getString("storage_phone"))
    );

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final ItemDao itemDao;

    public StorageDao(NamedParameterJdbcTemplate jdbcTemplate, ItemDao itemDao) {
        this.jdbcTemplate = jdbcTemplate;
        this.itemDao = itemDao;
    }

    public Collection<Storage> getStorages(LimitOffset limitOffset) {
        return jdbcTemplate.query(SELECT_QUERY + LimitOffsetDao.getLimitAndOffsetQuery(limitOffset), ROW_MAPPER);
    }

    public Storage getStorage(Integer id) {
        try {
            return jdbcTemplate.queryForObject(
                    SELECT_BY_ID_QUERY,
                    new MapSqlParameterSource()
                            .addValue("id", id),
                    ROW_MAPPER
            );
        } catch (EmptyResultDataAccessException ex) {
            throw new StorageNotFoundException("Storage with id " + id + " not found");
        }
    }

    public Integer createStorage(Storage storage) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(
                INSERT_QUERY,
                new MapSqlParameterSource()
                        .addValue("title", storage.getTitle())
                        .addValue("address", storage.getAddress())
                        .addValue("storagePhone", storage.getStoragePhone()),
                keyHolder,
                new String[]{"id"}
        );

        return keyHolder.getKey().intValue();
    }

    public void updateStorage(Integer id, Storage storage) {
        getStorage(id);
        logger.info(storage.getStoragePhone());
        jdbcTemplate.update(
                UPDATE_QUERY,
                new MapSqlParameterSource()
                        .addValue("title", storage.getTitle())
                        .addValue("address", storage.getAddress())
                        .addValue("storagePhone", storage.getStoragePhone())
                        .addValue("id", id)
        );
    }

    public boolean deleteStorage(Integer id) {
        return jdbcTemplate.update(
                DELETE_QUERY,
                new MapSqlParameterSource()
                        .addValue("id", id)
        ) > 0;
    }

    public void addItemToStorage(Integer id, Integer itemId, int amount) {
        getStorage(id);
        itemDao.getItem(itemId);

        Integer count = jdbcTemplate.queryForObject(
                CHECK_ITEM_IN_STORE_QUERY,
                new MapSqlParameterSource("item_id", itemId)
                        .addValue("storage_id", id),
                ((rs, rowNum) -> rs.getInt("storage_item"))
        );

        if (count != null && count != 0) {
            throw new ItemAlreadyInStoreException("Item is already in store");
        }

        jdbcTemplate.update(
                ADD_ITEM_TO_STORE_QUERY,
                new MapSqlParameterSource()
                        .addValue("item_id", itemId)
                        .addValue("storage_id", id)
                        .addValue("amount", amount)
        );
    }

    public Collection<Item> getStorageItems(Integer id) {
        getStorage(id);
        return jdbcTemplate.query(
                SELECT_ITEMS_BY_STORAGE_ID,
                new MapSqlParameterSource("storage_id", id),
                new ItemMapper()
        );
    }
}
