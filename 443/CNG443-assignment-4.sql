CREATE USER IF NOT EXISTS 'cng443user'@'localhost' IDENTIFIED BY '1234';
CREATE DATABASE IF NOT EXISTS `hms`;
GRANT ALL PRIVILEGES ON `hms`.* TO 'cng443user'@'localhost';

CREATE TABLE IF NOT EXISTS `hms`.`person` (
 `id` int(11) DEFAULT '0',
 `name` varchar(64) COLLATE utf8_unicode_ci NOT NULL,
 `date_of_birth` bigint(20) DEFAULT NULL,
 PRIMARY KEY (`id`),
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='person data';