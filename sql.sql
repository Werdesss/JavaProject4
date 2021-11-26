CREATE USER IF NOT EXISTS 'cng443user'@'localhost' IDENTIFIED BY '1234';
CREATE DATABASE IF NOT EXISTS `hms`;
GRANT ALL PRIVILEGES ON `hms`.* TO 'cng443user'@'localhost';

CREATE TABLE IF NOT EXISTS `hms`.`person` (
 `id` int(11) DEFAULT '0',
 `name` varchar(64) COLLATE utf8_unicode_ci NOT NULL,
 `date_of_birth` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='person data';


CREATE TABLE IF NOT EXISTS `hms`.`customer` (
 `customer_id` int(11) NOT NULL,
 `orders` varchar(64) DEFAULT NULL,
 `creditCardDetails` varchar(64) DEFAULT NULL,
  constraint fk_pid FOREIGN KEY (customer_id) references person(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='customer data';

CREATE TABLE IF NOT EXISTS `hms`.`booking` (
 `customer_id` int(11) NOT NULL,
 `bookingID` int(11) NOT NULL PRIMARY KEY AUTO_INCREMENT, 
 `bookingDate` varchar(64) DEFAULT NULL,
  constraint fk_pid1 FOREIGN KEY (customer_id) references customer(customer_id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='booking data';

alter table hms.person add column gender char (1) after name;
alter table hms.customer add column registrationDate bigint(20);


INSERT INTO hms.person  VALUES ('1','mert','m','1283174502729');   
INSERT INTO hms.person  VALUES ('2','irem','f','1283174502730');  
INSERT INTO hms.person  VALUES ('3','kemal','m','1283174502713');

INSERT INTO hms.customer (customer_id, creditCardDetails,registrationDate) VALUES ('1','master','1283174502729');   
INSERT INTO hms.customer (customer_id, creditCardDetails,registrationDate) VALUES ('2','visa','1283174502730');  
INSERT INTO hms.customer (customer_id, creditCardDetails,registrationDate) VALUES ('3','money','1283174502730');    

INSERT INTO hms.booking VALUES ('1','1','1283174502729');   
INSERT INTO hms.booking VALUES ('2','2','1283174502730');  
INSERT INTO hms.booking VALUES ('3','3','1283174502700'); 


