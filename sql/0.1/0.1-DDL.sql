
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
	`currentIP`		VARCHAR(128)	NOT NULL, 
	
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
	`secure`		BIT				NOT NULL DEFAULT 1,
	
	`startMod`		VARCHAR(30)		NOT NULL, 
	`currentMod`	VARCHAR(30)		NOT NULL, 
	`startDest`		VARCHAR(128)	NOT NULL, 
	`currentDest`	VARCHAR(128)	NOT NULL, 
	
	PRIMARY KEY (`sessionId`), 
	FOREIGN KEY (`accountId`) REFERENCES `account_users` (`accountId`)
) ENGINE=InnoDB;


DROP TABLE IF EXISTS `account_loginAttempts`;
CREATE TABLE `account_loginAttempts` (
	`username`		VARCHAR(12)		NOT NULL, 
	`date`			DATETIME		NOT NULL, 
	`ip`			VARCHAR(128)	NOT NULL, 
	
	PRIMARY KEY (`username`, `date`)
) ENGINE=InnoDB;


DROP TABLE IF EXISTS `account_passwordChanges`;
CREATE TABLE `account_passwordChanges` (
	`accountId`		INT(10)			UNSIGNED NOT NULL, 
	`date`			DATETIME		NOT NULL, 
	`ip`			VARCHAR(128)	NOT NULL, 
	`oldHash`		CHAR(128)		NOT NULL,
	`newHash`		CHAR(128)		NOT NULL, 
	`oldSalt`		VARCHAR(50)		NOT NULL, 
	`newSalt`		VARCHAR(50)		NOT NULL, 
	
	PRIMARY KEY (`accountId`, `date`), 
	FOREIGN KEY (`accountId`) REFERENCES `account_users` (`accountId`)
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
	`deleted`		BIT				NOT NULL DEFAULT 0,
	
	PRIMARY KEY (`id`),
	FOREIGN KEY (`authorId`) REFERENCES `account_users` (`accountId`)
) ENGINE=InnoDB;



DELIMITER $$


-- Staff Center


DROP PROCEDURE IF EXISTS `staff_deleteNewsArticle` $$ 
CREATE PROCEDURE `staff_deleteNewsArticle` (
	IN `in_articleId`		MEDIUMINT(8),
	OUT `out_returnCode`	BIT 
) 
BEGIN 
	UPDATE `media_news` 
	SET `deleted` = 1 
	WHERE `id` = `in_articleId` 
	LIMIT 1;
	
	IF (ROW_COUNT() < 1) THEN 
		SET `out_returnCode` = 0;
	ELSE 
		SET `out_returnCode` = 1;
	END IF;
END $$


DROP PROCEDURE IF EXISTS `staff_submitNewsArticle` $$ 
CREATE PROCEDURE `staff_submitNewsArticle` (
	IN `in_updateId`		MEDIUMINT(8), 
	IN `in_authorId`		INT(10),
	IN `in_date`			DATETIME, 
	IN `in_title`			VARCHAR(50),
	IN `in_category`		TINYINT(2),
	IN `in_desc`			VARCHAR(1024),
	IN `in_article`			TEXT, 
	IN `in_icon`			VARCHAR(50),
	OUT `out_returnCode`	BIT, 
	OUT `out_id`			MEDIUMINT(8)
) 
BEGIN 
	IF (`in_updateId` > 0) THEN 
		UPDATE `media_news` 
		SET `title` = `in_title`, 
			`category` = `in_category`, 
			`iconName` = `in_icon`, 
			`description` = `in_desc`, 
			`body` = `in_article` 
		WHERE `id` = `in_updateId` 
			AND `deleted` = 0 
		LIMIT 1;
		
		IF (ROW_COUNT() > 0) THEN 
			SET `out_returnCode` = 1;
			SET `out_id` = `in_updateId`;
		ELSE 
			SET `out_returnCode` = 0;
		END IF;
	ELSE 
		INSERT INTO `media_news` (
			`authorId`, `date`, `category`, `iconName`, `title`, `description`, `body` 
		) VALUES (
			`in_authorId`, `in_date`, `in_category`, `in_icon`, `in_title`, `in_desc`, `in_article`
		);
		
		IF (ROW_COUNT() > 0) THEN 
			SET `out_returnCode` = 1;
			SET `out_id` = LAST_INSERT_ID();
		ELSE 
			SET `out_returnCode` = 0;
		END IF;
	END IF;
END $$


DROP PROCEDURE IF EXISTS `staff_getAccountRecentPasswordChanges` $$ 
CREATE PROCEDURE `staff_getAccountRecentPasswordChanges` (
	IN `in_id`		INT(10)
) 
BEGIN 
	SELECT `ip`, `date` 
	FROM `account_passwordChanges` 
	WHERE `accountId` = `in_id` 
	ORDER BY `date` DESC 
	LIMIT 5;
END $$


DROP PROCEDURE IF EXISTS `staff_getAccountRecentSessions` $$ 
CREATE PROCEDURE `staff_getAccountRecentSessions` (
	IN `in_id`		INT(10)
) 
BEGIN 
	SELECT `ip`, `startDate`, `endDate`, `secure`, `startMod`, `currentMod`, `startDest`, `currentDest` 
	FROM `account_sessions` 
	WHERE `accountId` = `in_id` 
	ORDER BY `startDate` DESC 
	LIMIT 5;
END $$


DROP PROCEDURE IF EXISTS `staff_getAccountRecentLoginAttempts` $$ 
CREATE PROCEDURE `staff_getAccountRecentLoginAttempts` (
	IN `in_username`	VARCHAR(12)
) 
BEGIN 
	SELECT `ip`, `date`  
	FROM `account_loginAttempts` 
	WHERE `username` = `in_username` 
	ORDER BY `date` DESC 
	LIMIT 5;
END $$


DROP PROCEDURE IF EXISTS `staff_getAccountDetails` $$
CREATE PROCEDURE `staff_getAccountDetails` (
	IN `in_id`		INT(10)
)
BEGIN 
	SELECT `accountId`, `username`, `ageRange`, `countryCode`, `creationDate`, `creationIP`, `lastLoginDate`, `currentIP`, `staff`, `pmod`, `fmod` 
	FROM `account_users` 
	WHERE `accountId` = `in_id` 
	LIMIT 1;
END $$


-- Account Management


DROP PROCEDURE IF EXISTS `account_changePassword` $$ 
CREATE PROCEDURE `account_changePassword` (
	IN `in_accountId`		INT(10),
	IN `in_ip`				VARCHAR(128),
	IN `in_date`			DATETIME,
	IN `in_passwordHash`	CHAR(128),
	IN `in_passwordSalt`	VARCHAR(50),
	IN `in_oldHash`			CHAR(128),
	IN `in_oldSalt`			VARCHAR(50),
	OUT `out_returnCode`	BIT 
) 
BEGIN 
	UPDATE `account_users` 
	SET `passwordHash` = `in_passwordHash`, 
		`passwordSalt` = `in_passwordSalt` 
	WHERE `accountId` = `in_accountId` 
	LIMIT 1;
	
	IF (ROW_COUNT() > 0) THEN
		SET `out_returnCode` = 1;
		
		INSERT INTO `account_passwordChanges` (
			`accountId`, `date`, `ip`, `newHash`, `oldHash`, `newSalt`, `oldSalt`
		) VALUES (
			`in_accountId`, `in_date`, `in_ip`, `in_passwordHash`, `in_oldHash`, `in_passwordSalt`, `in_oldSalt` 
		);
	ELSE 
		SET `out_returnCode` = 0;
	END IF;
END $$


DROP PROCEDURE IF EXISTS `account_getPasswordForAccountId` $$ 
CREATE PROCEDURE `account_getPasswordForAccountId` (
	`in_accountId`	INT(10)
) 
BEGIN 
	SELECT `passwordHash`, `passwordSalt` 
	FROM `account_users` 
	WHERE `accountId` = `in_accountId` 
	LIMIT 1;
END $$


-- Login Sessions


DROP PROCEDURE IF EXISTS `account_getLoginSessionDetails` $$ 
CREATE PROCEDURE `account_getLoginSessionDetails` (
	`in_sessionId`	BIGINT(20), 
	`in_secure`		BIT,
	`in_newMod`		VARCHAR(30),
	`in_newDest`	VARCHAR(50),
	`in_newHash`	CHAR(128),
	`in_endDate`	DATETIME
) 
BEGIN 
	UPDATE `account_sessions` 
	SET `hash` = `in_newHash`, 
		`secure` = `in_secure`, 
		`currentMod` = `in_newMod`, 
		`currentDest` = `in_newDest`, 
		`endDate` = `in_endDate` 
	WHERE `sessionId` = `in_sessionId` 
	LIMIT 1;
	
	SELECT `a`.`accountId`, `a`.`username`, `a`.`staff`, `a`.`fmod`, `a`.`pmod`, `a`.`currentIP`
	FROM `account_sessions` AS `s` 
		JOIN `account_users` AS `a` 
			ON `s`.`accountId` = `a`.`accountId` 
	WHERE `s`.`sessionId` = `in_sessionId` 
	LIMIT 1;
END $$


DROP PROCEDURE IF EXISTS `account_findLoginSession` $$ 
CREATE PROCEDURE `account_findLoginSession` (
	IN `in_hash`		CHAR(128),
	IN `in_ip`			VARCHAR(128),
	IN `in_minDate`		DATETIME
) 
BEGIN 
	SELECT `sessionId`, `secure`, `endDate` 
	FROM `account_sessions` 
	WHERE `hash` = `in_hash` 
		AND `ip` = `in_ip` 
		AND `endDate` > `in_minDate` 
	ORDER BY `endDate` DESC 
	LIMIT 1;
END $$


DROP PROCEDURE IF EXISTS `account_killLoginSession` $$ 
CREATE PROCEDURE `account_killLoginSession` (
	IN `in_sessionId`	BIGINT(20),
	IN `in_date`		DATETIME
) 
BEGIN 
	UPDATE `account_sessions` 
	SET `endDate` = `in_date` 
	WHERE `sessionId` = `in_sessionId` 
	LIMIT 1;
END $$


DROP PROCEDURE IF EXISTS `account_loginFloodCheck` $$ 
CREATE PROCEDURE `account_loginFloodCheck` (
	IN `in_ip`			VARCHAR(128),
	IN `in_dateCheck`	DATETIME,
	IN `in_maxAttempts`	TINYINT(2),
	OUT `out_attempts`	TINYINT(2)
) 
BEGIN 
	SELECT COUNT(`date`) INTO `out_attempts` 
	FROM `account_loginAttempts` 
	WHERE `date` > `in_dateCheck` 
		AND `ip` = `in_ip` 
	ORDER BY `date` DESC 
	LIMIT `in_maxAttempts`;
END $$


DROP PROCEDURE IF EXISTS `account_submitLoginSession` $$ 
CREATE PROCEDURE `account_submitLoginSession` (
	IN `in_accountId`	INT(10),
	IN `in_ip`			VARCHAR(128),
	IN `in_sessionHash`	CHAR(128),
	IN `in_date`		DATETIME, 
	IN `in_endDate`		DATETIME, 
	IN `in_mod`			VARCHAR(30),
	IN `in_dest`		VARCHAR(128),
	IN `in_secure`		BIT
) 
BEGIN 
	INSERT INTO `account_sessions` (
		`accountId`, `ip`, `hash`, `secure`, `startDate`, `endDate`, `startMod`, `currentMod`, `startDest`, `currentDest`
	) VALUES (
		`in_accountId`, `in_ip`, `in_sessionHash`, `in_secure`, `in_date`, `in_endDate`, `in_mod`, `in_mod`, `in_dest`, `in_dest`
	);
	
	UPDATE `account_users` 
	SET `lastLoginDate` = `in_date`, 
		`currentIP` = `in_ip` 
	WHERE `accountId` = `in_accountId` 
	LIMIT 1;
END $$


DROP PROCEDURE IF EXISTS `account_getUserForUsername` $$ 
CREATE PROCEDURE `account_getUserForUsername` (
	`in_username`	VARCHAR(12),
	`in_date`		DATETIME, 
	`in_ip`			VARCHAR(128)
) 
BEGIN 
	INSERT INTO `account_loginAttempts` (
		`username`, `date`, `ip`
	) VALUES (
		`in_username`, `in_date`, `in_ip`
	);
	
	SELECT `accountId`, `passwordHash`, `passwordSalt` 
	FROM `account_users` 
	WHERE `username` = `in_username` 
	LIMIT 1;
END $$


-- Account Creation


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
		`username`, `passwordHash`, `passwordSalt`, `ageRange`, `countryCode`, `creationDate`, `creationIP`, `currentIP` 
	) VALUES (
		`in_username`, `in_passwordHash`, `in_passwordSalt`, `in_ageRange`, `in_countryCode`, `in_date`, `in_ip`, `in_ip` 
	);
	
	SELECT ROW_COUNT() INTO `out_returnCode`;
END $$


-- News Viewing


DROP PROCEDURE IF EXISTS `media_getTitleNews` $$
CREATE PROCEDURE `media_getTitleNews` () 
BEGIN 
	SELECT `id`, `date`, `title`, `iconName`, `description` 
	FROM `media_news` 
	WHERE `deleted` = 0 
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
	WHERE ((`in_cat` = 0) OR (`category` = `in_cat`)) 
		AND `deleted` = 0;
	
	SET `out_pageCount` = CEIL(`newsCount` / `in_limit`);
	
	IF (`in_page` > `out_pageCount`) THEN 
		SET `out_realPage` = `out_pageCount`;
	ELSE 
		SET `out_realPage` = `in_page`;
	END IF;
	
	SET `start` = (`out_realPage` * `in_limit`) - `in_limit`;
	
	SELECT `id`, `category`, `title`, `date` 
	FROM `media_news` 
	WHERE ((`in_cat` = 0) OR (`category` = `in_cat`)) 
		AND `deleted` = 0 
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
			AND `deleted` = 0 
		ORDER BY `date` ASC 
		LIMIT 1;
		
		SELECT `id` INTO `out_nextId` 
		FROM `media_news` 
		WHERE `date` < `articleDate` 
			AND `deleted` = 0 
		ORDER BY `date` DESC 
		LIMIT 1;
		
		SELECT `id`, `title`, `date`, `category`, `description`, `body`, `iconName` 
		FROM `media_news` 
		WHERE `id` = `in_articleId` 
			AND `deleted` = 0 
		LIMIT 1;
	END IF;
END $$


DELIMITER ;




