package org.runelight.db.dao.staff;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;
import org.runelight.controller.impl.account.CreateAccount;
import org.runelight.controller.impl.staff.accounts.StaffAccounts;
import org.runelight.dto.AccountDetailsDTO;
import org.runelight.dto.AccountListDTO;
import org.runelight.dto.IPDateDTO;
import org.runelight.dto.SessionDetailsDTO;
import org.runelight.dto.UserDTO;
import org.runelight.util.CountryUtil;
import org.runelight.util.DateUtil;
import org.runelight.util.StringUtil;

public final class StaffAccountsDAO {
	
	private static final Logger LOG = Logger.getLogger(StaffAccountsDAO.class);
	
	public static AccountDetailsDTO getAccountDetails(Connection con, int accountId) {
		try {
			String sql = "CALL `staff_getAccountDetails`(?);";
			CallableStatement stmt = con.prepareCall(sql);
			stmt.setInt("in_id", accountId);
			stmt.execute();
			
			ResultSet results = stmt.getResultSet();
			if(results == null || !results.next()) {
				return null;
			}

			List<IPDateDTO> passwordChangeList = new LinkedList<>();
			List<SessionDetailsDTO> sessionList = new LinkedList<>();
			List<IPDateDTO> loginAttemptList = new LinkedList<>();
			
			sql = "CALL `staff_getAccountRecentPasswordChanges`(?);";
			stmt = con.prepareCall(sql);
			stmt.setInt("in_id", accountId);
			stmt.execute();
			
			ResultSet secondaryResults = stmt.getResultSet();
			
			if(secondaryResults != null) {
				while(secondaryResults.next()) {
					passwordChangeList.add(new IPDateDTO(secondaryResults.getString("ip"), DateUtil.SHORT_TIME_FORMAT.format(secondaryResults.getTimestamp("date"))));
				}
			}
			
			sql = "CALL `staff_getAccountRecentSessions`(?);";
			stmt = con.prepareCall(sql);
			stmt.setInt("in_id", accountId);
			stmt.execute();
			
			secondaryResults = stmt.getResultSet();
			
			if(secondaryResults != null) {
				while(secondaryResults.next()) {
					sessionList.add(new SessionDetailsDTO(secondaryResults.getString("ip"), 
							DateUtil.MSG_CENTER_FORMAT.format(secondaryResults.getTimestamp("startDate")), DateUtil.MSG_CENTER_FORMAT.format(secondaryResults.getTimestamp("endDate")), 
							secondaryResults.getString("startMod"), secondaryResults.getString("currentMod"), secondaryResults.getString("startDest"), secondaryResults.getString("currentDest"),
							secondaryResults.getBoolean("secure")));
				}
			}
			
			sql = "CALL `staff_getAccountRecentLoginAttempts`(?);";
			stmt = con.prepareCall(sql);
			stmt.setString("in_username", results.getString("username"));
			stmt.execute();
			
			secondaryResults = stmt.getResultSet();
			
			if(secondaryResults != null) {
				while(secondaryResults.next()) {
					loginAttemptList.add(new IPDateDTO(secondaryResults.getString("ip"), DateUtil.SHORT_TIME_FORMAT.format(secondaryResults.getTimestamp("date"))));
				}
			}
			
			return new AccountDetailsDTO(
				results.getInt("accountId"), results.getString("username"), StringUtil.formatUsername(results.getString("username")), 
				CreateAccount.AGE_RANGE_MAP.get(results.getString("ageRange")), CountryUtil.COUNTRY_MAP.get(results.getString("countryCode")), 
				DateUtil.SHORT_TIME_FORMAT.format(results.getTimestamp("creationDate")), results.getString("creationIP"), 
				(results.getTimestamp("lastLoginDate") == null ? null : DateUtil.SHORT_TIME_FORMAT.format(results.getTimestamp("lastLoginDate"))), 
				results.getString("currentIP"), 
				results.getBoolean("staff"), results.getBoolean("fmod"), results.getBoolean("pmod"), 
				passwordChangeList, sessionList, loginAttemptList
			);
		} catch(SQLException e) {
			LOG.error("SQLException occurred while attempting to load user account details for [" + accountId + "].", e);
			return null;
		}
	}
	
	public static AccountListDTO getAccountList(Connection con, int page, String usernameSearch, String ipSearch, String sort, String sortDir) {
		try {
			/*
			 * Inline because of the special sorting parameters.
			 */
			
			String sql = 
			"SELECT COUNT(`accountId`) AS `accountCount` " + 
			"FROM `account_users` " + 
			"WHERE ((? = '') OR (`username` LIKE ?)) " + 
				"AND ((? = '') OR (`creationIP` LIKE ?) OR (`currentIP` LIKE ?));";
			
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setString(1, usernameSearch);
			stmt.setString(2, usernameSearch);
			stmt.setString(3, ipSearch);
			stmt.setString(4, ipSearch);
			stmt.setString(5, ipSearch);
			stmt.execute();
			
			ResultSet results = stmt.getResultSet();
			if(results == null || !results.next()) {
				return null;
			}
			
			
			int accountCount = results.getInt("accountCount");
			int pageCount = (int) Math.ceil((double) accountCount / (double) StaffAccounts.ACCOUNTS_PER_PAGE);
			
			if(page > pageCount) {
				page = pageCount;
			}
			
			int start = (page * StaffAccounts.ACCOUNTS_PER_PAGE) - StaffAccounts.ACCOUNTS_PER_PAGE;
			
			sql = 
			"SELECT `accountId`, `username`, `creationDate`, `creationIP`, `currentIP`, `staff`, `pmod`, `fmod` " + 
			"FROM `account_users` " + 
			"WHERE ((? = '') OR (`username` LIKE ?)) " + 
				"AND ((? = '') OR (`creationIP` LIKE ?) OR (`currentIP` LIKE ?)) " +
			"ORDER BY `" + sort + "` " + sortDir + " " + 
			"LIMIT " + start + "," + StaffAccounts.ACCOUNTS_PER_PAGE + ";";
			
			stmt = con.prepareStatement(sql);
			stmt.setString(1, usernameSearch);
			stmt.setString(2, usernameSearch);
			stmt.setString(3, ipSearch);
			stmt.setString(4, ipSearch);
			stmt.setString(5, ipSearch);
			stmt.execute();
			
			results = stmt.getResultSet();
			if(results == null) {
				return null;
			}
			
			List<UserDTO> accountList = new LinkedList<>();
			while(results.next()) {
				UserDTO acc = new UserDTO();
				acc.setAccountId(results.getInt("accountId"));
				acc.setUsername(results.getString("username"));
				acc.setFormattedUsername(StringUtil.formatUsername(acc.getUsername()));
				acc.setCreationDate(DateUtil.SQL_DATETIME_FORMAT.format(results.getTimestamp("creationDate")));
				acc.setCreationIP(results.getString("creationIP"));
				acc.setCurrentIP(results.getString("currentIP"));
				acc.setStaff(results.getBoolean("staff"));
				acc.setPmod(results.getBoolean("pmod"));
				acc.setFmod(results.getBoolean("fmod"));
				
				accountList.add(acc);
			}
			
			if(accountList.size() < 1) {
				return null;
			}
			
			return new AccountListDTO(page, pageCount, 
					usernameSearch.replace("%", ""), 
					ipSearch.replace("%", ""),
					accountList, sort, sortDir);
		} catch(SQLException e) {
			LOG.error("SQLException occurred while attempting to fetch the administrative account list.", e);
			return null;
		}
	}
	
}
