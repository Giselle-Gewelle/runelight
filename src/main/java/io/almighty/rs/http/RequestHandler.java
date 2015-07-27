package io.almighty.rs.http;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import io.almighty.rs.Config;
import io.almighty.rs.controller.Controller;
import io.almighty.rs.controller.impl.account.CreateAccount;
import io.almighty.rs.controller.impl.main1.Title;
import io.almighty.rs.db.RSDataSource;
import io.almighty.rs.util.ModUtil;

public final class RequestHandler {
	
	private static final Logger LOG = Logger.getLogger(RequestHandler.class);
	
	private static final Map<String, Class<? extends Controller>> CONTROLLER_MAP = new HashMap<String, Class<? extends Controller>>() {

		private static final long serialVersionUID = -6523666098696536388L;
		
		{
			put("main1 title.ws", Title.class);
			put("create index.html", CreateAccount.class);
		}
		
	};
	
	public static void sendError(HttpServletResponse response, int errorCode) {
		try {
			response.sendError(errorCode);
		} catch(IOException e) {
			LOG.error("IOException occured while attempting to send an error code back to the client.", e);
		}
	}
	
	static void submitViewRequest(HttpRequestType requestType, HttpServletRequest request, HttpServletResponse response) {
		String uri = request.getRequestURI();
		String requestIP = request.getRemoteAddr();
		long requestTime = Calendar.getInstance().getTimeInMillis();
		
		String mod = ModUtil.subdomainToMod(request.getServerName().split("\\.")[0]);
		
		String dest = "";
		if(uri != null && uri.length() > 1) {
			dest = uri.substring(1);
		} else {
			sendError(response, 404);
			return;
		}
		
		if(!CONTROLLER_MAP.containsKey(mod + " " + dest)) {
			LOG.info("Controller not found: " + mod + ":" + dest);
			sendError(response, 404);
			return;
		}
		
		LOG.info("View request received for " + mod + ":" + dest + " by " + requestIP + " at " + requestTime);

		Controller controller = null;
		try {
			Class<? extends Controller> controllerClass = CONTROLLER_MAP.get(mod + " " + dest);
			controller = controllerClass.newInstance();
			
			if(controller.isSecure() && !request.isSecure()) {
				// TODO make this secure
				LOG.warn("Client attempted an insecure connection on a secure-only section of the website, 403 response returned.");
				sendError(response, 403);
				return;
			}
		} catch(Exception e) {
			LOG.error("Controller is null: " + mod + ":" + dest, e);
			sendError(response, 404);
			return;
		}
		
		Connection con = null;
		
		try {
			con = RSDataSource.getConnection();
			
			if(con == null || con.isClosed()) {
				LOG.error("The database connection that was fetched is already closed!");
				sendError(response, 503);
				return;
			}
		} catch(SQLException e) {
			LOG.error("SQLException occured while attempting to fetch a database connection.", e);
			sendError(response, 503);
			return;
		}
		
		request.setAttribute("rsTime", requestTime);
		request.setAttribute("hostName", Config.getHostName());
		request.setAttribute("sslEnabled", Config.isSslEnabled());
		request.setAttribute("gameName", Config.getGameName());
		request.setAttribute("companyName", Config.getCompanyName());
		
		try {
			controller.setup(request, response, requestType, requestIP, requestTime, con, mod, dest);
			controller.init();
			
			char s = File.separatorChar;
			String location = new StringBuilder().append(s).append("WEB-INF").append(s).append("view").append(s).append(mod).append(s).append(dest).append(".ftl").toString();
			RequestDispatcher dispatcher = request.getRequestDispatcher(location);
			if(dispatcher != null && !response.isCommitted()) {
				dispatcher.forward(request, response);
			}
		} catch(Exception e) {
			LOG.error("Exception occured while attempting to load the controller for a view request.", e);
			sendError(response, 500);
		} finally {
			RSDataSource.closeConnection(con);
		}
	}
	
}
