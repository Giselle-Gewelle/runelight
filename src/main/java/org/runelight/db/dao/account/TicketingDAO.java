package org.runelight.db.dao.account;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;
import org.runelight.util.DateUtil;
import org.runelight.view.dto.account.UserSessionDTO;
import org.runelight.view.dto.account.ticketing.InboxDTO;
import org.runelight.view.dto.account.ticketing.MessageQueueDTO;
import org.runelight.view.dto.account.ticketing.MessageViewDTO;
import org.runelight.view.dto.account.ticketing.ThreadDTO;

public final class TicketingDAO {
	
	private static final Logger LOG = Logger.getLogger(TicketingDAO.class);
	
	public static final int 
		MESSAGE_NOT_FOUND = 0,
		MESSAGE_FOUND = 1,
		MESSAGE_UNAUTHORIZED = 2;
	
	private final Connection con;
	private final UserSessionDTO user;
	
	public TicketingDAO(Connection con, UserSessionDTO user) {
		this.con = con;
		this.user = user;
	}
	
	public int getActivity(Date minDate) {
		try {
			CallableStatement stmt = con.prepareCall("CALL `account_ticketingActivityCheck`(?, ?, ?, ?);");
			stmt.setString("in_username", user.getUsername());
			stmt.setString("in_ip", user.getIP());
			stmt.setString("in_minDate", DateUtil.SQL_DATETIME_FORMAT.format(minDate));
			stmt.registerOutParameter("out_count", Types.TINYINT);
			stmt.execute();
			
			return stmt.getInt("out_count");
		} catch(SQLException e) {
			LOG.error("SQLException occurred while attempting to check the ticketing activity (submissions) for the user [" + user.getFormattedUsername() + "].", e);
			return -1;
		}
	}
	
	public boolean sendMessage(boolean isReply, int topicId, String title, int messageNum, String message, String receiverName, boolean canReply, boolean includeTitleInMsg) {
		try {
			CallableStatement stmt = con.prepareCall("CALL `account_ticketingSendMessage`(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);");
			stmt.setBoolean("in_isReply", isReply);
			stmt.setInt("in_topicId", topicId);
			stmt.setString("in_title", title);
			stmt.setInt("in_messageNum", messageNum);
			stmt.setString("in_date", DateUtil.SQL_DATETIME_FORMAT.format(new Date()));
			stmt.setString("in_message", message);
			stmt.setString("in_author", user.getUsername());
			stmt.setBoolean("in_authorStaff", user.isStaff());
			stmt.setString("in_authorIP", user.getIP());
			stmt.setInt("in_authorId", user.getAccountId());
			stmt.setString("in_receiver", receiverName);
			stmt.setBoolean("in_canReply", canReply);
			stmt.setBoolean("in_includeTitleInMsg", includeTitleInMsg);
			stmt.registerOutParameter("out_successful", Types.BIT);
			stmt.execute();
			
			return stmt.getBoolean("out_successful");
		} catch(SQLException e) {
			if(isReply) {
				LOG.error("SQLException occurred while attempting to reply to the topic [" + topicId + "], reply sent by [" + user.getFormattedUsername() + "].", e);
			} else {
				LOG.error("SQLException occurred while attempting to send a new ticketing message, sent by [" + user.getFormattedUsername() + "].", e);
			}
			return false;
		}
	}
	
	public boolean deleteMessage(int messageId) {
		try {
			CallableStatement stmt = con.prepareCall("CALL `account_ticketingDeleteMessage`(?, ?, ?);");
			stmt.setInt("in_id", messageId);
			stmt.setString("in_username", user.getUsername());
			stmt.registerOutParameter("out_successful", Types.BIT);
			stmt.execute();
			
			return stmt.getBoolean("out_successful");
		} catch(SQLException e) {
			LOG.error("SQLException occurred while attempting to delete the message [" + messageId + "], requested by [" + user.getFormattedUsername() + "].", e);
			return false;
		}
	}
	
	public int messageExists(int messageId) {
		try {
			CallableStatement stmt = con.prepareCall("CALL `account_ticketingCheckMessageId`(?, ?);");
			stmt.setInt("in_id", messageId);
			stmt.setString("in_username", user.getUsername());
			stmt.execute();
			
			ResultSet result = stmt.getResultSet();
			if(result == null || !result.next()) {
				return MESSAGE_NOT_FOUND;
			}
			
			if(result.getString("authorName").equals(user.getUsername())) {
				if(result.getBoolean("authorDelete")) {
					return MESSAGE_NOT_FOUND;
				}
			} else if(result.getString("receiverName").equals(user.getUsername())) {
				if(result.getBoolean("receiverDelete")) {
					return MESSAGE_NOT_FOUND;
				}
			} else {
				return MESSAGE_UNAUTHORIZED;
			}
			
			return MESSAGE_FOUND;
		} catch(SQLException e) {
			LOG.error("SQLException occurred while attempting to check if the message [" + messageId + "] exists, requested by [" + user.getFormattedUsername() + "].", e);
			return MESSAGE_NOT_FOUND;
		}
	}
	
	public ThreadDTO getThread(int lastMessageId) {
		try {
			CallableStatement stmt = con.prepareCall("CALL `account_ticketingGetThread`(?, ?, ?, ?, ?, ?, ?, ?);");
			stmt.setInt("in_id", lastMessageId);
			stmt.setString("in_username", user.getUsername());
			stmt.setString("in_date", DateUtil.SQL_DATETIME_FORMAT.format(new Date()));
			stmt.registerOutParameter("out_topicId", Types.INTEGER);
			stmt.registerOutParameter("out_messageNum", Types.SMALLINT);
			stmt.registerOutParameter("out_mainTitle", Types.VARCHAR);
			stmt.registerOutParameter("out_canReply", Types.BIT);
			stmt.registerOutParameter("out_authorName", Types.VARCHAR);
			stmt.execute();
			
			ResultSet results = stmt.getResultSet();
			if(results == null) {
				return null;
			}
			
			int topicId = stmt.getInt("out_topicId");
			if(topicId < 1) {
				return null;
			}
			
			List<MessageViewDTO> messageList = new LinkedList<>();
			while(results.next()) {
				messageList.add(new MessageViewDTO(
					results.getInt("id"), results.getTimestamp("date"), results.getString("message"), results.getString("authorName"), results.getBoolean("authorStaff"), results.getTimestamp("readOn")
				));
			}
			
			if(messageList.size() < 1) {
				return null;
			}
			
			return new ThreadDTO(
				topicId, lastMessageId, stmt.getInt("out_messageNum"), stmt.getString("out_mainTitle"), stmt.getBoolean("out_canReply"), stmt.getString("out_authorName"), user.getUsername(), messageList
			);
		} catch(SQLException e) {
			LOG.error("SQLException occurred while attempting to fetch the messages before [" + lastMessageId + "], requested by [" + user.getFormattedUsername() + "].", e);
			return null;
		}
	}
	
	public InboxDTO getInbox() {
		try {
			CallableStatement stmt = con.prepareCall("CALL `account_ticketingGetMessageQueue`(?);");
			stmt.setString("in_username", user.getUsername());
			stmt.execute();
			
			ResultSet results = stmt.getResultSet();
			if(results == null) {
				return null;
			}
			
			List<MessageQueueDTO> receivedMessageList = new LinkedList<>();
			List<MessageQueueDTO> sentMessageList = new LinkedList<>();
			List<MessageQueueDTO> readMessageList = new LinkedList<>();
			while(results.next()) {
				if(results.getString("receiverName").equals(user.getUsername()) && !results.getBoolean("receiverDelete")) {
					if(results.getTimestamp("readOn") == null) {
						receivedMessageList.add(new MessageQueueDTO(
							results.getInt("id"), results.getString("title"), results.getTimestamp("date"), results.getInt("messageNum")
						));
					} else {
						readMessageList.add(new MessageQueueDTO(
							results.getInt("id"), results.getString("title"), results.getTimestamp("date"), results.getInt("messageNum")
						));
					}
				} else if(results.getString("authorName").equals(user.getUsername()) && !results.getBoolean("authorDelete")) {
					sentMessageList.add(new MessageQueueDTO(
						results.getInt("id"), results.getString("title"), results.getTimestamp("date"), results.getInt("messageNum")
					));
				}
			}
			
			return new InboxDTO(receivedMessageList, sentMessageList, readMessageList);
		} catch(SQLException e) {
			LOG.error("SQLException occurred while attempting to fetch the inbox for [" + user.getFormattedUsername() + "].");
			return null;
		}
	}
	
}
