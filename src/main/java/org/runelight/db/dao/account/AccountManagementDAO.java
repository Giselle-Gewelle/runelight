package org.runelight.db.dao.account;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Date;

import org.apache.log4j.Logger;
import org.runelight.security.Password;
import org.runelight.util.DateUtil;

public final class AccountManagementDAO {
	
	private static final Logger LOG = Logger.getLogger(AccountManagementDAO.class);
	
	public static boolean changePassword(Connection con, int accountId, String ip, Password oldPassword, Password newPassword) {
		try {
			String sql = "CALL `account_changePassword`(?, ?, ?, ?, ?, ?, ?, ?);";
			CallableStatement stmt = con.prepareCall(sql);
			stmt.setInt("in_accountId", accountId);
			stmt.setString("in_ip", ip);
			stmt.setString("in_date", DateUtil.SQL_DATETIME_FORMAT.format(new Date()));
			stmt.setString("in_passwordHash", newPassword.getHash());
			stmt.setString("in_passwordSalt", newPassword.getSalt());
			stmt.setString("in_oldHash", oldPassword.getHash());
			stmt.setString("in_oldSalt", oldPassword.getSalt());
			stmt.registerOutParameter("out_returnCode", Types.BIT);
			stmt.execute();
			
			return (stmt.getByte("out_returnCode") == 1);
		} catch(SQLException e) {
			LOG.error("SQLException occurred while attempting to change the password for the account [" + accountId + "].", e);
			return false;
		}
	}
	
	public static Password getUserPassword(Connection con, int accountId) {
		try {
			String sql = "CALL `account_getPasswordForAccountId`(?);";
			CallableStatement stmt = con.prepareCall(sql);
			stmt.setInt("in_accountId", accountId);
			stmt.execute();
			
			ResultSet result = stmt.getResultSet();
			if(result == null || !result.next()) {
				return null;
			}
			
			return new Password(result.getString("passwordHash"), result.getString("passwordSalt"));
		} catch(SQLException e) {
			LOG.error("SQLException occurred while attempting to fetch the password hash and salt for the account [" + accountId + "].", e);
			return null;
		}
	}
	
}
