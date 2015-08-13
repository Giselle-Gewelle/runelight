package org.runelight.controller.impl.account.ticketing;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.runelight.controller.Controller;
import org.runelight.db.dao.account.TicketingDAO;
import org.runelight.http.HttpRequestType;
import org.runelight.util.URLUtil;

public final class Feedback extends Controller {
	
	private static final Logger LOG = Logger.getLogger(Feedback.class);
	
	private static final int 
		ERROR_EMPTY_COMMENT = 0,
		ERROR_TOO_LONG = 1,
		ERROR_ACTIVITY_LIMIT = 2,
		ERROR_UNKNOWN = 3;
	
	private String subject;
	private String link;
	
	@Override
	public void init() {
		switch(getDest()) {
			case "privacy.html":
				setSubject("Privacy", "privacy.ws");
				break;
			case "complaint.html":
				setSubject("Complaint", "complaint.ws");
				break;
			case "other.html":
				setSubject("Other", "other.ws");
				break;
				
			case "privacy.ws":
				setSubject("Privacy", "privacy.html");
				submit();
				break;
			case "complaint.ws":
				setSubject("Complaint", "complaint.html");
				submit();
				break;
			case "other.ws":
				setSubject("Other", "other.html");
				submit();
				break;
		}
	}
	
	private void submit() {
		if(!getRequestType().equals(HttpRequestType.POST)) {
			try {
				getResponse().sendRedirect(URLUtil.getUrl("ticketing", link, true));
			} catch(IOException e) {
				LOG.error("IOException occurred while attempting to redirect a user back to the Customer Support feedback form.", e);
			}
			
			return;
		}
		
		HttpServletRequest request = getRequest();
		
		request.setAttribute("submitted", true);
		
		String comment = request.getParameter("inputProblem");
		if(comment == null) {
			setError(ERROR_EMPTY_COMMENT);
			return;
		}
		
		comment = comment.trim();
		if(comment.equals("") || comment.length() < 1) {
			setError(ERROR_EMPTY_COMMENT);
			return;
		}
		
		if(comment.length() > 400) {
			setError(ERROR_TOO_LONG);
			return;
		}
		
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MINUTE, -5);
		
		TicketingDAO ticketingDAO = new TicketingDAO(getDbConnection(), getLoginSession().getUser());
		int activity = ticketingDAO.getActivity(cal.getTime());
		if(activity > 0) {
			setError(ERROR_ACTIVITY_LIMIT);
			return;
		} else if(activity == -1) {
			setError(ERROR_UNKNOWN);
			return;
		}
		
		String title = "Feedback: " + subject;
		if(!ticketingDAO.sendMessage(false, 0, title, 1, comment, null, true, true)) {
			setError(ERROR_UNKNOWN);
		}
	}
	
	private void setSubject(String subject, String link) {
		getRequest().setAttribute("feedbackSubject", subject);
		getRequest().setAttribute("feedbackLink", link);
		this.subject = subject;
		this.link = link;
	}
	
	private void setError(int code) {
		getRequest().setAttribute("errorCode", code);
	}
	
	
	@Override
	public String getActualPage() {
		return "inc" + File.separatorChar + "feedback.ftl";
	}
	
	@Override
	public boolean isSecure() {
		return true;
	}
	
	@Override
	public boolean holdSecureSession() {
		return true;
	}
	
	@Override
	public boolean loginRequired() {
		return true;
	}

}
