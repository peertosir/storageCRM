create table if not exists `storages`
(
    `id`           int NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `title`        varchar(100),
    `address`      varchar(200),
    `storage_phone` varchar(20)
);