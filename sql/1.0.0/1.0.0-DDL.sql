
DROP DATABASE IF EXISTS `runelight`;

CREATE DATABASE `runelight` 
	DEFAULT CHARACTER SET utf8 
	DEFAULT COLLATE utf8_general_ci;
	
USE `runelight`;


DROP TABLE IF EXISTS `account_users`;
CREATE TABLE `account_users` (
	`accountId`		INT(10)		UNSIGNED NOT NULL AUTO_INCREMENT UNIQUE, 
	
	PRIMARY KEY (`accountId`)
) ENGINE=InnoDB;