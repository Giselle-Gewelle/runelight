
USE `runelight`;







-- 0.1 Fixes

DELIMITER $$


-- Fix for: Soft-deleted news articles still appear in the news list
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


DELIMITER ;
