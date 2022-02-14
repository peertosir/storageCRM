create table if not exists `storage_items`
(
    `item_id`         int,
    `storage_id`      int,
    `amount`          int,
    CONSTRAINT PK_storage_item PRIMARY KEY (item_id, storage_id),
    CONSTRAINT fk_item
        FOREIGN KEY(item_id)
            REFERENCES items(id),
    CONSTRAINT fk_storage
        FOREIGN KEY(storage_id)
            REFERENCES storages(id)
);