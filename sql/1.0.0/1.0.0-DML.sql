
USE `runelight`;



-- Testing


INSERT INTO `account_users` ( 
	`username`, `passwordHash`, `passwordSalt`, `ageRange`, `countryCode`, `creationDate`, `creationIP`, `currentIP`, `staff`
) VALUES ( 
	'z', 'c6ea238d987fd6650c91c265a5b3b5e38157b5eb5793f68b5b7dfb3164c398d739fdb73809131e32f6c9b12b6c7bce8737da25f902efb9550f683cb83c1e6613', '#|-s)Xue&)?UJT|[qfZRO0l8E$9y8=D}-gOFwXG)x_MiG:Pd7*', '3', '225', 
	'2015-08-02 12:56:00', '127.0.0.1', '127.0.0.1', 1
), ( 
	'almighty', 'c6ea238d987fd6650c91c265a5b3b5e38157b5eb5793f68b5b7dfb3164c398d739fdb73809131e32f6c9b12b6c7bce8737da25f902efb9550f683cb83c1e6613', '#|-s)Xue&)?UJT|[qfZRO0l8E$9y8=D}-gOFwXG)x_MiG:Pd7*', '3', '225', 
	'2015-08-02 12:56:01', '127.0.0.1', '127.0.0.1', 1
), ( 
	'balance', 'c6ea238d987fd6650c91c265a5b3b5e38157b5eb5793f68b5b7dfb3164c398d739fdb73809131e32f6c9b12b6c7bce8737da25f902efb9550f683cb83c1e6613', '#|-s)Xue&)?UJT|[qfZRO0l8E$9y8=D}-gOFwXG)x_MiG:Pd7*', '3', '225', 
	'2015-08-02 12:56:02', '127.0.0.1', '127.0.0.2', 1
), ( 
	'compulsory', 'c6ea238d987fd6650c91c265a5b3b5e38157b5eb5793f68b5b7dfb3164c398d739fdb73809131e32f6c9b12b6c7bce8737da25f902efb9550f683cb83c1e6613', '#|-s)Xue&)?UJT|[qfZRO0l8E$9y8=D}-gOFwXG)x_MiG:Pd7*', '3', '225', 
	'2015-08-02 12:56:03', '127.0.0.1', '127.0.0.1', 1
), ( 
	'deathdealing', 'c6ea238d987fd6650c91c265a5b3b5e38157b5eb5793f68b5b7dfb3164c398d739fdb73809131e32f6c9b12b6c7bce8737da25f902efb9550f683cb83c1e6613', '#|-s)Xue&)?UJT|[qfZRO0l8E$9y8=D}-gOFwXG)x_MiG:Pd7*', '3', '225', 
	'2015-08-02 12:56:04', '127.0.0.1', '127.0.0.2', 1
), ( 
	'explode', 'c6ea238d987fd6650c91c265a5b3b5e38157b5eb5793f68b5b7dfb3164c398d739fdb73809131e32f6c9b12b6c7bce8737da25f902efb9550f683cb83c1e6613', '#|-s)Xue&)?UJT|[qfZRO0l8E$9y8=D}-gOFwXG)x_MiG:Pd7*', '3', '225', 
	'2015-08-02 12:56:05', '127.0.0.1', '127.0.0.1', 1
), ( 
	'fear', 'c6ea238d987fd6650c91c265a5b3b5e38157b5eb5793f68b5b7dfb3164c398d739fdb73809131e32f6c9b12b6c7bce8737da25f902efb9550f683cb83c1e6613', '#|-s)Xue&)?UJT|[qfZRO0l8E$9y8=D}-gOFwXG)x_MiG:Pd7*', '3', '225', 
	'2015-08-02 12:56:06', '127.0.0.1', '127.0.0.1', 1
), ( 
	'glutton', 'c6ea238d987fd6650c91c265a5b3b5e38157b5eb5793f68b5b7dfb3164c398d739fdb73809131e32f6c9b12b6c7bce8737da25f902efb9550f683cb83c1e6613', '#|-s)Xue&)?UJT|[qfZRO0l8E$9y8=D}-gOFwXG)x_MiG:Pd7*', '3', '225', 
	'2015-08-02 12:56:07', '127.0.0.2', '127.0.0.1', 1
), ( 
	'heat', 'c6ea238d987fd6650c91c265a5b3b5e38157b5eb5793f68b5b7dfb3164c398d739fdb73809131e32f6c9b12b6c7bce8737da25f902efb9550f683cb83c1e6613', '#|-s)Xue&)?UJT|[qfZRO0l8E$9y8=D}-gOFwXG)x_MiG:Pd7*', '3', '225', 
	'2015-08-02 12:56:08', '127.0.0.1', '127.0.0.1', 1
), ( 
	'iron', 'c6ea238d987fd6650c91c265a5b3b5e38157b5eb5793f68b5b7dfb3164c398d739fdb73809131e32f6c9b12b6c7bce8737da25f902efb9550f683cb83c1e6613', '#|-s)Xue&)?UJT|[qfZRO0l8E$9y8=D}-gOFwXG)x_MiG:Pd7*', '3', '225', 
	'2015-08-02 12:56:09', '127.0.0.1', '127.0.0.1', 1
), ( 
	'jail', 'c6ea238d987fd6650c91c265a5b3b5e38157b5eb5793f68b5b7dfb3164c398d739fdb73809131e32f6c9b12b6c7bce8737da25f902efb9550f683cb83c1e6613', '#|-s)Xue&)?UJT|[qfZRO0l8E$9y8=D}-gOFwXG)x_MiG:Pd7*', '3', '225', 
	'2015-08-02 12:56:10', '127.0.0.1', '127.0.0.1', 1
), ( 
	'k', 'c6ea238d987fd6650c91c265a5b3b5e38157b5eb5793f68b5b7dfb3164c398d739fdb73809131e32f6c9b12b6c7bce8737da25f902efb9550f683cb83c1e6613', '#|-s)Xue&)?UJT|[qfZRO0l8E$9y8=D}-gOFwXG)x_MiG:Pd7*', '3', '225', 
	'2015-08-02 12:56:11', '127.0.0.1', '127.0.0.1', 1
), ( 
	'love', 'c6ea238d987fd6650c91c265a5b3b5e38157b5eb5793f68b5b7dfb3164c398d739fdb73809131e32f6c9b12b6c7bce8737da25f902efb9550f683cb83c1e6613', '#|-s)Xue&)?UJT|[qfZRO0l8E$9y8=D}-gOFwXG)x_MiG:Pd7*', '3', '225', 
	'2015-08-02 12:56:12', '127.0.0.1', '127.0.0.1', 1
), ( 
	'miracle', 'c6ea238d987fd6650c91c265a5b3b5e38157b5eb5793f68b5b7dfb3164c398d739fdb73809131e32f6c9b12b6c7bce8737da25f902efb9550f683cb83c1e6613', '#|-s)Xue&)?UJT|[qfZRO0l8E$9y8=D}-gOFwXG)x_MiG:Pd7*', '3', '225', 
	'2015-08-02 12:56:13', '127.0.0.1', '127.0.0.1', 1
), ( 
	'n', 'c6ea238d987fd6650c91c265a5b3b5e38157b5eb5793f68b5b7dfb3164c398d739fdb73809131e32f6c9b12b6c7bce8737da25f902efb9550f683cb83c1e6613', '#|-s)Xue&)?UJT|[qfZRO0l8E$9y8=D}-gOFwXG)x_MiG:Pd7*', '3', '225', 
	'2015-08-02 12:56:14', '127.0.0.1', '127.0.0.1', 1
), ( 
	'overkill', 'c6ea238d987fd6650c91c265a5b3b5e38157b5eb5793f68b5b7dfb3164c398d739fdb73809131e32f6c9b12b6c7bce8737da25f902efb9550f683cb83c1e6613', '#|-s)Xue&)?UJT|[qfZRO0l8E$9y8=D}-gOFwXG)x_MiG:Pd7*', '3', '225', 
	'2015-08-02 12:56:15', '127.0.0.1', '127.0.0.1', 1
), ( 
	'power', 'c6ea238d987fd6650c91c265a5b3b5e38157b5eb5793f68b5b7dfb3164c398d739fdb73809131e32f6c9b12b6c7bce8737da25f902efb9550f683cb83c1e6613', '#|-s)Xue&)?UJT|[qfZRO0l8E$9y8=D}-gOFwXG)x_MiG:Pd7*', '3', '225', 
	'2015-08-02 12:56:16', '127.0.0.1', '127.0.0.1', 1
), ( 
	'question', 'c6ea238d987fd6650c91c265a5b3b5e38157b5eb5793f68b5b7dfb3164c398d739fdb73809131e32f6c9b12b6c7bce8737da25f902efb9550f683cb83c1e6613', '#|-s)Xue&)?UJT|[qfZRO0l8E$9y8=D}-gOFwXG)x_MiG:Pd7*', '3', '225', 
	'2015-08-02 12:56:17', '127.0.0.1', '127.0.0.1', 1
), ( 
	'roar', 'c6ea238d987fd6650c91c265a5b3b5e38157b5eb5793f68b5b7dfb3164c398d739fdb73809131e32f6c9b12b6c7bce8737da25f902efb9550f683cb83c1e6613', '#|-s)Xue&)?UJT|[qfZRO0l8E$9y8=D}-gOFwXG)x_MiG:Pd7*', '3', '225', 
	'2015-08-02 12:56:18', '127.0.0.1', '127.0.0.1', 1
), ( 
	'superstar', 'c6ea238d987fd6650c91c265a5b3b5e38157b5eb5793f68b5b7dfb3164c398d739fdb73809131e32f6c9b12b6c7bce8737da25f902efb9550f683cb83c1e6613', '#|-s)Xue&)?UJT|[qfZRO0l8E$9y8=D}-gOFwXG)x_MiG:Pd7*', '3', '225', 
	'2015-08-02 12:56:19', '127.0.0.1', '127.0.0.1', 1
), ( 
	'thunderbolt', 'c6ea238d987fd6650c91c265a5b3b5e38157b5eb5793f68b5b7dfb3164c398d739fdb73809131e32f6c9b12b6c7bce8737da25f902efb9550f683cb83c1e6613', '#|-s)Xue&)?UJT|[qfZRO0l8E$9y8=D}-gOFwXG)x_MiG:Pd7*', '3', '225', 
	'2015-08-02 12:56:20', '127.0.0.1', '127.0.0.1', 1
), ( 
	'underbelly', 'c6ea238d987fd6650c91c265a5b3b5e38157b5eb5793f68b5b7dfb3164c398d739fdb73809131e32f6c9b12b6c7bce8737da25f902efb9550f683cb83c1e6613', '#|-s)Xue&)?UJT|[qfZRO0l8E$9y8=D}-gOFwXG)x_MiG:Pd7*', '3', '225', 
	'2015-08-02 12:56:21', '127.0.0.1', '127.0.0.1', 1
), ( 
	'visionary', 'c6ea238d987fd6650c91c265a5b3b5e38157b5eb5793f68b5b7dfb3164c398d739fdb73809131e32f6c9b12b6c7bce8737da25f902efb9550f683cb83c1e6613', '#|-s)Xue&)?UJT|[qfZRO0l8E$9y8=D}-gOFwXG)x_MiG:Pd7*', '3', '225', 
	'2015-08-02 12:56:22', '127.0.0.1', '127.0.0.1', 1
), ( 
	'wind', 'c6ea238d987fd6650c91c265a5b3b5e38157b5eb5793f68b5b7dfb3164c398d739fdb73809131e32f6c9b12b6c7bce8737da25f902efb9550f683cb83c1e6613', '#|-s)Xue&)?UJT|[qfZRO0l8E$9y8=D}-gOFwXG)x_MiG:Pd7*', '3', '225', 
	'2015-08-02 12:56:23', '127.0.0.1', '127.0.0.1', 1
), ( 
	'x_axis', 'c6ea238d987fd6650c91c265a5b3b5e38157b5eb5793f68b5b7dfb3164c398d739fdb73809131e32f6c9b12b6c7bce8737da25f902efb9550f683cb83c1e6613', '#|-s)Xue&)?UJT|[qfZRO0l8E$9y8=D}-gOFwXG)x_MiG:Pd7*', '3', '225', 
	'2015-08-02 12:56:24', '127.0.0.1', '127.0.0.1', 1
), ( 
	'yourself', 'c6ea238d987fd6650c91c265a5b3b5e38157b5eb5793f68b5b7dfb3164c398d739fdb73809131e32f6c9b12b6c7bce8737da25f902efb9550f683cb83c1e6613', '#|-s)Xue&)?UJT|[qfZRO0l8E$9y8=D}-gOFwXG)x_MiG:Pd7*', '3', '225', 
	'2015-08-02 12:56:25', '127.0.0.1', '127.0.0.1', 1
), ( 
	'zombie', 'c6ea238d987fd6650c91c265a5b3b5e38157b5eb5793f68b5b7dfb3164c398d739fdb73809131e32f6c9b12b6c7bce8737da25f902efb9550f683cb83c1e6613', '#|-s)Xue&)?UJT|[qfZRO0l8E$9y8=D}-gOFwXG)x_MiG:Pd7*', '3', '225', 
	'2015-08-02 12:56:26', '127.0.0.1', '127.0.0.1', 1
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


