package io.almighty.rs.db;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.log4j.Logger;

public final class RSDataSource {
	
	private static final Logger LOG = Logger.getLogger(RSDataSource.class);
	
	private static DataSource dataSource = null;
	
	public static void init() {
		if(dataSource != null) {
			LOG.error("RSDataSource is already initialized!");
			return;
		}
		
		try {
			Context context = new InitialContext();
			dataSource = (DataSource) context.lookup("java:comp/env/jdbc/almighty");
			
			LOG.info("RSDataSource initialized.");
		} catch(NamingException e) {
			LOG.error("NamingException occured while attempting to initialize the RSDataSource.", e);
		}
	}
	
	public static Connection getConnection() throws SQLException {
		LOG.info("Polling DataSource for a valid connection.");
		
		return dataSource.getConnection();
	}
	
	public static void closeConnection(Connection connection) {
		try {
			if(connection == null || connection.isClosed()) {
				LOG.warn("Attempting to close a null or already closed connection.");
				return;
			}
			
			connection.close();
			
			LOG.info("Database connection closed.");
		} catch(SQLException e) {
			LOG.error("SQLException occured while attempting to close a database connection!", e);
		}
	}
	
}
