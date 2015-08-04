package org.runelight.controller.impl.staff;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.runelight.db.dao.staff.AccountsDAO;
import org.runelight.util.URLUtil;
import org.runelight.view.dto.staff.AccountListDTO;

public final class Accounts extends StaffPage {
	
	public static final int ACCOUNTS_PER_PAGE = 20;
	private static final String 
		DEFAULT_SORT = "creationDate", 
		DEFAULT_SORT_DIR = "DESC";
	private static final List<String> SORTABLE_FIELDS = new ArrayList<String>() {
		
		private static final long serialVersionUID = 3010810245564653935L;

		{
			add("creationDate");
			add("username");
			add("currentIP");
			add("creationIP");
		}
		
	};
	
	@Override
	public void init() {
		super.init();
		
		if(!isAuthorized()) {
			return;
		}
		
		switch(getDest()) {
			case "accounts/list.ws":
				setupAccountList();
				break;
			case "accounts/details.ws":
				setupAccountDetails();
				break;
		}
	}
	
	private void setupAccountDetails() {
		
	}
	
	private void setupAccountList() {
		int page = URLUtil.getIntParam(getRequest(), "page");
		if(page < 1 || page > Short.MAX_VALUE) {
			page = 1;
		}
		
		String usernameSearch = getRequest().getParameter("usernameSearch");
		if(usernameSearch != null) {
			if(usernameSearch.length() < 1 || usernameSearch.length() > 12) {
				usernameSearch = null;
			} else {
				Pattern pattern = Pattern.compile("^[a-z0-9_]{1,12}$");
				Matcher matcher = pattern.matcher(usernameSearch);
				if(!matcher.find()) {
					usernameSearch = null;
				}
			}
		}
		
		if(usernameSearch == null) {
			usernameSearch = "";
		}
		
		if(!usernameSearch.equals("")) {
			usernameSearch = "%" + usernameSearch + "%";
		}
		
		String ipSearch = getRequest().getParameter("ipSearch");
		if(ipSearch != null) {
			if(ipSearch.length() < 1 || ipSearch.length() > 50) {
				ipSearch = null;
			} else {
				Pattern pattern = Pattern.compile("^[0-9\\.]{1,50}$");
				Matcher matcher = pattern.matcher(ipSearch);
				if(!matcher.find()) {
					ipSearch = null;
				}
			}
		}
		
		if(ipSearch == null) {
			ipSearch = "";
		}
		
		if(!ipSearch.equals("")) {
			ipSearch = "%" + ipSearch + "%";
		}
		
		String sort = getRequest().getParameter("sort");
		if(sort == null || !SORTABLE_FIELDS.contains(sort)) {
			sort = DEFAULT_SORT;
		}
		
		String sortDir = getRequest().getParameter("sortDir");
		if(sortDir == null || (!sortDir.equals("DESC") && !sortDir.equals("ASC"))) {
			sortDir = DEFAULT_SORT_DIR;
		}
		
		AccountListDTO accountListDTO = AccountsDAO.getAccountList(getDbConnection(), page, usernameSearch, ipSearch, sort, sortDir);
		if(accountListDTO != null) {
			getRequest().setAttribute("accountList", accountListDTO);
		}
	}
	
}
