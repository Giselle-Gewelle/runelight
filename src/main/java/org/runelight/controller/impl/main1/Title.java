package org.runelight.controller.impl.main1;

import java.util.List;

import org.runelight.controller.Controller;
import org.runelight.db.dao.media.NewsDAO;
import org.runelight.view.dto.media.NewsDTO;

public final class Title extends Controller {
	
	@Override
	public void init() {
		List<NewsDTO> newsList = NewsDAO.getTitleNews(getDbConnection());
		if(newsList != null) {
			getRequest().setAttribute("newsList", newsList);
		}
	}

}
