
USE `runelight`;


INSERT INTO `account_users` (
	`username`, `passwordHash`, `passwordSalt`, `ageRange`, `countryCode`, `creationDate`, `creationIP`
) VALUES (
	'Gigi', 'nothing to see here', 'move along', 2, 225, '2015-07-25 09:10:00', '127.0.0.1'
);


INSERT INTO `media_news` (
	`authorId`, `date`, `category`, `title`, `description`, `body`
) VALUES (
	1, '2015-07-26 10:00:00', 1, 'Test title 1', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Mauris luctus sodales nunc, at mattis metus tempus id. Nulla eget laoreet ligula. Duis et est suscipit, finibus orci sit amet, lobortis lacus. Fusce porta dictum lacus sit amet aliquam. Duis suscipit tincidunt vestibulum. Sed eleifend mattis ullamcorper. Nam vel lorem nulla. Fusce quis diam faucibus, tempus magna non, aliquam nisi. Suspendisse dictum leo id ante pretium aliquam. Vivamus eleifend augue at ex ornare scelerisque. Suspendisse iaculis tempus accumsan.', 
	'Article 1'
), (
	1, '2015-07-28 06:59:00', 2, 'Test title 2', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Mauris luctus sodales nunc, at mattis metus tempus id. Nulla eget laoreet ligula. Duis et est suscipit, finibus orci sit amet, lobortis lacus. Fusce porta dictum lacus sit amet aliquam. Duis suscipit tincidunt vestibulum. Sed eleifend mattis ullamcorper. Nam vel lorem nulla. Fusce quis diam faucibus, tempus magna non, aliquam nisi.', 
	'Article 2'
), (
	1, '2015-07-28 21:00:00', 3, 'Test title 3', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Mauris luctus sodales nunc, at mattis metus tempus id. Nulla eget laoreet ligula. Duis et est suscipit, finibus orci sit amet, lobortis lacus. Fusce porta dictum lacus sit amet aliquam. Duis suscipit tincidunt vestibulum. Sed eleifend mattis ullamcorper. Nam vel lorem nulla.', 
	'Article 3'
), (
	1, '2015-07-29 07:00:00', 4, 'Test title 4', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Mauris luctus sodales nunc, at mattis metus tempus id. Nulla eget laoreet ligula. Duis et est suscipit, finibus orci sit amet, lobortis lacus. Fusce porta dictum lacus sit amet aliquam. Duis suscipit tincidunt vestibulum. Sed eleifend mattis ullamcorper. Nam vel lorem nulla. Fusce quis diam faucibus, tempus magna non, aliquam nisi. Suspendisse dictum leo id ante pretium aliquam.', 
	'Article 4'
), (
	1, '2015-07-29 09:00:00', 6, 'Test title 5', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Mauris luctus sodales nunc, at mattis metus tempus id. Nulla eget laoreet ligula. Duis et est suscipit, finibus orci sit amet, lobortis lacus. Fusce porta dictum lacus sit amet aliquam. Duis suscipit tincidunt vestibulum. Sed eleifend mattis ullamcorper. Nam vel lorem nulla. Fusce quis diam faucibus, tempus magna non, aliquam nisi.', 
	'Article 5'
);



-- Testing

INSERT INTO `account_users` ( 
	`username`, `passwordHash`, `passwordSalt`, `ageRange`, `countryCode`, `creationDate`, `creationIP`
) VALUES ( 
	'test', '', '', '3', '225', '2015-08-02 12:56:00', '127.0.0.1'
);


