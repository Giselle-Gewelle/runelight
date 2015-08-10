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
import org.runelight.view.dto.account.ticketing.MessageQueueDTO;
import org.runelight.view.dto.account.ticketing.MessageViewDTO;
import org.runelight.view.dto.account.ticketing.ThreadDTO;

public final class TicketingDAO {
	
	private static final Logger LOG = Logger.getLogger(TicketingDAO.class);
	
	public static final int 
		TYPE_RECEIVED = 0,
		TYPE_READ = 1,
		TYPE_SENT = 2;
	
	private Connection con;
	
	public TicketingDAO(Connection con) {
		this.con = con;
	}
	
	public ThreadDTO getThread(UserSessionDTO user, int lastMessageId) {
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
				topicId, lastMessageId, stmt.getString("out_mainTitle"), stmt.getBoolean("out_canReply"), stmt.getString("out_authorName"), user.getUsername(), messageList
			);
		} catch(SQLException e) {
			LOG.error("SQLException occurred while attempting to fetch the messages before [" + lastMessageId + "], made by [" + user.getFormattedUsername() + "].", e);
			return null;
		}
	}
	
	public List<MessageQueueDTO> getMessages(UserSessionDTO user, int type) {
		try {
			CallableStatement stmt = con.prepareCall("CALL `account_ticketingGetMessageQueue`(?, ?);");
			stmt.setString("in_username", user.getUsername());
			stmt.setInt("in_type", type);
			stmt.execute();
			
			ResultSet results = stmt.getResultSet();
			if(results == null) {
				return null;
			}
			
			List<MessageQueueDTO> messageList = new LinkedList<>();
			while(results.next()) {
				messageList.add(new MessageQueueDTO(
					results.getInt("id"), results.getString("title"), results.getTimestamp("date"), results.getInt("messageNum")
				));
			}
			
			if(messageList.size() < 1) {
				return null;
			}
			
			return messageList;
		} catch(SQLException e) {
			LOG.error("SQLException occurred while attempting to fetch the received messages for the user [" + user.getFormattedUsername() + "].");
			return null;
		}
	}
	
}
