package org.runelight.db.dao.staff;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;
import org.runelight.controller.impl.staff.Accounts;
import org.runelight.util.DateUtil;
import org.runelight.util.StringUtil;
import org.runelight.view.dto.account.UserDTO;
import org.runelight.view.dto.staff.AccountListDTO;

public final class AccountsDAO {
	
	private static final Logger LOG = Logger.getLogger(AccountsDAO.class);
	
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
			int pageCount = (int) Math.ceil((double) accountCount / (double) Accounts.ACCOUNTS_PER_PAGE);
			
			if(page > pageCount) {
				page = pageCount;
			}
			
			int start = (page * Accounts.ACCOUNTS_PER_PAGE) - Accounts.ACCOUNTS_PER_PAGE;
			
			sql = 
			"SELECT `accountId`, `username`, `creationDate`, `creationIP`, `currentIP`, `staff`, `pmod`, `fmod` " + 
			"FROM `account_users` " + 
			"WHERE ((? = '') OR (`username` LIKE ?)) " + 
				"AND ((? = '') OR (`creationIP` LIKE ?) OR (`currentIP` LIKE ?)) " +
			"ORDER BY `" + sort + "` " + sortDir + " " + 
			"LIMIT " + start + "," + Accounts.ACCOUNTS_PER_PAGE + ";";
			
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
