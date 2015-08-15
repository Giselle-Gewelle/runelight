package org.runelight.controller.impl.staff.news;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.runelight.controller.impl.staff.StaffPage;
import org.runelight.db.dao.media.NewsDAO;
import org.runelight.db.dao.staff.StaffNewsDAO;
import org.runelight.dto.NewsItemDTO;
import org.runelight.http.HttpRequestType;
import org.runelight.util.URLUtil;

public class StaffNewsDelete extends StaffPage {
	
	private static final Logger LOG = Logger.getLogger(StaffNewsDelete.class);
	
	@Override
	public void init() {
		super.init();
		
		if(!isAuthorized()) {
			return;
		}
		
		int deleteId = URLUtil.getIntParam(getRequest(), "id");
		if(deleteId > 1 && deleteId < Short.MAX_VALUE) {
			NewsItemDTO newsItem = NewsDAO.getNewsItem(getDbConnection(), deleteId);
			if(newsItem != null) {
				getRequest().setAttribute("deleteArticle", newsItem);
				
				if(getRequestType().equals(HttpRequestType.POST)) {
					getRequest().setAttribute("attemptingDelete", true);
					
					String confirm = getRequest().getParameter("confirm");
					if(confirm != null && confirm.equals("Confirm")) {
						getRequest().setAttribute("deleteSuccessful", StaffNewsDAO.deleteArticle(getDbConnection(), deleteId));
					} else {
						try {
							setRedirecting(true);
							getResponse().sendRedirect(URLUtil.getUrl("news", "newsitem.ws?id=" + deleteId));
						} catch (IOException e) {
							LOG.error("IOException occurred while attempting to cancel news article deletion.", e);
						}
					}
				} else {
					getRequest().setAttribute("attemptingDelete", false);
				}
			}
		}
	}
	
}
