create table if not exists `items`
(
    `id` int NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `title`      varchar(100),
    `barcode`    varchar(25),
    `created_at` timestamp
);