
USE `runelight`;


INSERT INTO `account_ticketingTopics` (
	`id`
) VALUES (
	1
), (
	2
);

INSERT INTO `account_ticketingMessages` (
	`topicId`, `title`, `messageNum`, `date`, `message`, `authorName`, `authorStaff`, `authorIP`, `actualAuthorId`, `receiverName`, `canReply`
) VALUES (
	1, 'Test title 1', 1, '2015-08-10 09:02:01', 'First test message in a test topic!', 'gigi', 1, '127.0.0.1', 1, 'almighty', 0
), (
	1, 'Re: Test title 1', 2, '2015-08-10 09:46:02', 'Here''s a reply to that message!', 'almighty', 0, '127.0.0.1', 2, 'gigi', 1
), (
	2, 'Help!', 1, '2015-08-10 10:22:30', 'Pls help acc stolen omg', 'balance', 0, '127.0.0.1', 3, 'gigi', 1
);