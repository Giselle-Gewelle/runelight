package org.runelight.controller.impl.media;

import org.runelight.controller.Controller;
import org.runelight.db.dao.media.NewsDAO;
import org.runelight.util.URLUtil;
import org.runelight.view.dto.media.NewsDTO;

public final class News extends Controller {

	@Override
	public void init() {
		switch(getDest()) {
			case "newsitem.ws":
				fetchNewsItem();
				break;
			case "list.ws":
				// TODO
				break;
		}
	}
	
	private void fetchNewsItem() {
		int id = URLUtil.getIntParam(getRequest(), "id");
		if(id < 1 || id > 16777215) {
			return;
		}
		
		NewsDTO newsItem = NewsDAO.getNewsItem(getDbConnection(), id);
		if(newsItem != null) {
			getRequest().setAttribute("newsItem", newsItem);
		}
	}

}
