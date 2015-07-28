package org.runelight.http;

import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;

import com.mysql.jdbc.AbandonedConnectionCleanupThread;

public final class RSContextListener implements ServletContextListener {

	private static final Logger LOG = Logger.getLogger(RSContextListener.class);
	
	@Override
	public void contextDestroyed(ServletContextEvent servletContextEvent) {
        try {
            AbandonedConnectionCleanupThread.shutdown();
        } catch(InterruptedException e) {
        	LOG.error("InterruptedException occured while attempting to shutdown the Abandoned Connection Cleanup Thread.", e);
        }
        
        Enumeration<Driver> drivers = DriverManager.getDrivers();
        Driver d = null;
        while(drivers.hasMoreElements()) {
            try {
                d = drivers.nextElement();
                DriverManager.deregisterDriver(d);
                LOG.info(String.format("Driver %s deregistered", d));
            } catch(SQLException e) {
            	LOG.error(String.format("Error deregistering driver %s", d), e);
            }
        }
	}

	@Override
	public void contextInitialized(ServletContextEvent servletContextEvent) {
		
	}

}
