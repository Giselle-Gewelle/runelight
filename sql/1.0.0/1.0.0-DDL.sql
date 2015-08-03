
-- TEXT length = 65535

DROP DATABASE IF EXISTS `runelight`;

CREATE DATABASE `runelight` 
	DEFAULT CHARACTER SET utf8 
	DEFAULT COLLATE utf8_general_ci;
	
USE `runelight`;


DROP TABLE IF EXISTS `account_users`;
CREATE TABLE `account_users` (
	`accountId`		INT(10)			UNSIGNED NOT NULL AUTO_INCREMENT UNIQUE, 
	`username`		VARCHAR(12)		NOT NULL UNIQUE, 
	`passwordHash`	CHAR(128)		NOT NULL, 
	`passwordSalt`	CHAR(50)		NOT NULL, 
	`ageRange`		TINYINT(1)		UNSIGNED NOT NULL, 
	`countryCode`	TINYINT(3)		UNSIGNED NOT NULL, 
	
	`creationDate`	DATETIME		NOT NULL, 
	`creationIP`	VARCHAR(128)	NOT NULL,
	
	`staff`			BIT				NOT NULL DEFAULT 0, 
	`pmod`			BIT				NOT NULL DEFAULT 0, 
	`fmod`			BIT				NOT NULL DEFAULT 0,
	
	`lastLoginDate`	DATETIME		NULL, 
	`lastLoginIP`	VARCHAR(128)	NULL, 
	
	PRIMARY KEY (`accountId`)
) ENGINE=InnoDB;


DROP TABLE IF EXISTS `account_sessions`;
CREATE TABLE `account_sessions` (
	`sessionId`		BIGINT(20)		UNSIGNED NOT NULL AUTO_INCREMENT UNIQUE, 
	`accountId`		INT(10)			UNSIGNED NOT NULL, 
	`ip`			VARCHAR(128)	NOT NULL, 
	`hash`			CHAR(128)		NOT NULL, 
	`startDate`		DATETIME		NOT NULL, 
	`endDate`		DATETIME		NOT NULL, 
	
	`startMod`		VARCHAR(30)		NOT NULL, 
	`currentMod`	VARCHAR(30)		NOT NULL, 
	`startDest`		VARCHAR(128)	NOT NULL, 
	`currentDest`	VARCHAR(128)	NOT NULL, 
	
	PRIMARY KEY (`sessionId`), 
	FOREIGN KEY `accountId` REFERENCES `account_users` (`accountId`)
) ENGINE=InnoDB;


DROP TABLE IF EXISTS `media_news`;
CREATE TABLE `media_news` (
	`id`			MEDIUMINT(8)	UNSIGNED NOT NULL AUTO_INCREMENT UNIQUE,
	`authorId`		INT(10)			UNSIGNED NOT NULL, 
	`date`			DATETIME		NOT NULL, 
	`category`		TINYINT(2)		NOT NULL, 
	`iconName`		VARCHAR(50)		NOT NULL DEFAULT 'general',
	`title`			VARCHAR(50)		NOT NULL, 
	`description`	VARCHAR(1024)	NOT NULL, 
	`body`			TEXT			NOT NULL,
	
	PRIMARY KEY(`id`),
	FOREIGN KEY (`authorId`) REFERENCES `account_users` (`accountId`)
) ENGINE=InnoDB;



DELIMITER $$


DROP PROCEDURE IF EXISTS `account_submitLoginSession` $$ 
CREATE PROCEDURE `account_submitLoginSession` (
	IN `in_accountId`	INT(10),
	IN `in_ip`			VARCHAR(128),
	IN `in_sessionHash`	CHAR(128),
	IN `in_date`		DATETIME, 
	IN `in_endDate`		DATETIME, 
	IN `in_mod`			VARCHAR(30),
	IN `in_dest`		VARCHAR(128)
) 
BEGIN 
	INSERT INTO `account_sessions` (
		`accountId`, `ip`, `hash`, `startDate`, `endDate`, `startMod`, `currentMod`, `startDest`, `currentDest`
	) VALUES (
		`in_accountId`, `in_ip`, `in_sessionHash`, `in_date`, `in_endDate`, `in_mod`, `in_mod`, `in_dest`, `in_dest`
	);
	
	UPDATE `account_users` 
	SET `lastLoginDate` = `in_date`, 
		`lastLoginIP` = `in_ip` 
	WHERE `accountId` = `in_accountId` 
	LIMIT 1;
END $$


DROP PROCEDURE IF EXISTS `account_getUserForUsername` $$ 
CREATE PROCEDURE `account_getUserForUsername` (
	`in_username`	VARCHAR(12)
) 
BEGIN 
	SELECT `accountId`, `passwordHash`, `passwordSalt` 
	FROM `account_users` 
	WHERE `username` = `in_username` 
	LIMIT 1;
END $$


DROP PROCEDURE IF EXISTS `account_creationFloodCheck` $$ 
CREATE PROCEDURE `account_creationFloodCheck` (
	IN `in_smallDate`		DATETIME, 
	IN `in_largeDate`		DATETIME, 
	IN `in_maxForLarge`		TINYINT(3), 
	IN `in_ip`				VARCHAR(128),
	OUT `out_returnCode`	BIT
) 
BEGIN 
	DECLARE `creationCount` TINYINT(3) DEFAULT 0;
	
	SELECT COUNT(`accountId`) INTO `creationCount` 
	FROM `account_users` 
	WHERE `creationIP` = `in_ip` 
		AND `creationDate` > `in_smallDate` 
	ORDER BY `creationDate` DESC 
	LIMIT 1;
		
	IF (`creationCount` < 1) THEN 
		SELECT COUNT(`accountId`) INTO `creationCount` 
		FROM `account_users` 
		WHERE `creationIP` = `in_ip` 
			AND `creationDate` > `in_largeDate` 
		ORDER BY `creationDate` DESC 
		LIMIT 5;
		
		IF (`creationCount` >= `in_maxForLarge`) THEN 
			SET `out_returnCode` = 1;
		END IF;
	ELSE 
		SET `out_returnCode` = 1;
	END IF;
END $$


DROP PROCEDURE IF EXISTS `account_checkUsername` $$
CREATE PROCEDURE `account_checkUsername` (
	IN `in_username`		VARCHAR(12),
	OUT `out_returnCode`	BIT
) 
BEGIN 
	SELECT COUNT(`accountId`) INTO `out_returnCode` 
	FROM `account_users` 
	WHERE `username` = `in_username` 
	LIMIT 1;
END $$


DROP PROCEDURE IF EXISTS `account_createUserAccount` $$
CREATE PROCEDURE `account_createUserAccount` (
	IN `in_username`		VARCHAR(12),
	IN `in_passwordHash`	CHAR(128),
	IN `in_passwordSalt`	VARCHAR(50),
	IN `in_ageRange`		TINYINT(1),
	IN `in_countryCode`		TINYINT(3), 
	IN `in_date`			DATETIME, 
	IN `in_ip`				VARCHAR(128),
	OUT `out_returnCode`	TINYINT(1)
) 
BEGIN 
	INSERT INTO `account_users` (
		`username`, `passwordHash`, `passwordSalt`, `ageRange`, `countryCode`, `creationDate`, `creationIP`
	) VALUES (
		`in_username`, `in_passwordHash`, `in_passwordSalt`, `in_ageRange`, `in_countryCode`, `in_date`, `in_ip`
	);
	
	SELECT ROW_COUNT() INTO `out_returnCode`;
END $$



DROP PROCEDURE IF EXISTS `media_getTitleNews` $$
CREATE PROCEDURE `media_getTitleNews` () 
BEGIN 
	SELECT `id`, `date`, `title`, `iconName`, `description` 
	FROM `media_news` 
	ORDER BY `date` DESC 
	LIMIT 5;
END $$


DROP PROCEDURE IF EXISTS `media_getNewsList` $$
CREATE PROCEDURE `media_getNewsList` (
	IN `in_cat`			TINYINT(2),
	IN `in_page`		SMALLINT(5),
	IN `in_limit`		TINYINT(3),
	OUT `out_pageCount`	SMALLINT(5),
	OUT `out_realPage`	SMALLINT(5)
) 
BEGIN 
	DECLARE `newsCount`	SMALLINT(5);
	DECLARE `start`		SMALLINT(5);
	
	SELECT COUNT(`id`) INTO `newsCount` 
	FROM `media_news` 
	WHERE (`in_cat` = 0) OR (`category` = `in_cat`);
	
	SET `out_pageCount` = CEIL(`newsCount` / `in_limit`);
	
	IF (`in_page` > `out_pageCount`) THEN 
		SET `out_realPage` = `out_pageCount`;
	ELSE 
		SET `out_realPage` = `in_page`;
	END IF;
	
	SET `start` = (`out_realPage` * `in_limit`) - `in_limit`;
	
	SELECT `id`, `category`, `title`, `date` 
	FROM `media_news` 
	WHERE (`in_cat` = 0) OR (`category` = `in_cat`) 
	ORDER BY `date` DESC 
	LIMIT `start`,`in_limit`;
END $$


DROP PROCEDURE IF EXISTS `media_getNewsItem` $$
CREATE PROCEDURE `media_getNewsItem` (
	IN `in_articleId`	MEDIUMINT(8),
	OUT `out_nextId`	MEDIUMINT(8),
	OUt `out_prevId`	MEDIUMINT(8)
)
BEGIN
	DECLARE `articleDate` DATETIME DEFAULT NULL;
	
	SELECT `date` INTO `articleDate`
	FROM `media_news` 
	WHERE `id` = `in_articleId` 
	LIMIT 1;
	
	IF (`articleDate` IS NOT NULL) THEN 
		SELECT `id` INTO `out_prevId` 
		FROM `media_news` 
		WHERE `date` > `articleDate` 
		ORDER BY `date` ASC 
		LIMIT 1;
		
		SELECT `id` INTO `out_nextId` 
		FROM `media_news` 
		WHERE `date` < `articleDate` 
		ORDER BY `date` DESC 
		LIMIT 1;
		
		SELECT `id`, `title`, `date`, `category`, `body` 
		FROM `media_news` 
		WHERE `id` = `in_articleId` 
		LIMIT 1;
	END IF;
END $$


DELIMITER ;




