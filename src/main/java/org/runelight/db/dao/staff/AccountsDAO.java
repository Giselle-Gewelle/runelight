package org.runelight.db.dao.staff;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
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
	
	public static AccountListDTO getAccountList(Connection con, int page, String usernameSearch, String ipSearch) {
		try {
			String sql = "CALL `staff_getAccountList`(?, ?, ?, ?, ?, ?);";
			CallableStatement stmt = con.prepareCall(sql);
			stmt.setInt("in_page", page);
			stmt.setInt("in_limit", Accounts.ACCOUNTS_PER_PAGE);
			stmt.setString("in_usernameSearch", usernameSearch);
			stmt.setString("in_ipSearch", ipSearch);
			stmt.registerOutParameter("out_realPage", Types.SMALLINT);
			stmt.registerOutParameter("out_pageCount", Types.SMALLINT);
			stmt.execute();
			
			ResultSet results = stmt.getResultSet();
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
			
			return new AccountListDTO(stmt.getInt("out_realPage"), stmt.getInt("out_pageCount"), 
					usernameSearch.replace("%", ""), 
					ipSearch.replace("%", ""),
					accountList);
		} catch(SQLException e) {
			LOG.error("SQLException occurred while attempting to fetch the administrative account list.", e);
			return null;
		}
	}
	
}
