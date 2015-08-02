package org.runelight.db.dao.account;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;

import org.apache.log4j.Logger;

public final class CreateAccountDAO {
	
	private static final Logger LOG = Logger.getLogger(CreateAccountDAO.class);
	
	public static int usernameExists(Connection con, String username) {
		try {
			String sql = "CALL `account_checkUsername`(?);";
			CallableStatement stmt = con.prepareCall(sql);
			stmt.setString("in_username", username);
			stmt.registerOutParameter("out_returnCode", Types.BIT);
			stmt.execute();
			
			byte returnCode = stmt.getByte("out_returnCode");
			
			return (returnCode == 1 ? 1 : 0);
		} catch(SQLException e) {
			LOG.error("SQLException occurred while attempting to check if the username [" + username + "] exists.", e);
			return -1;
		}
	}
	
}
