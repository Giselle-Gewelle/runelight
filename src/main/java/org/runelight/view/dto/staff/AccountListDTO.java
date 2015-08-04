package org.runelight.view.dto.staff;

import java.util.List;

import org.runelight.view.dto.account.UserDTO;

public final class AccountListDTO {
	
	private final int currentPage;
	private final int pageCount;
	private final String usernameSearch;
	private final String ipSearch;
	private final List<UserDTO> accountList;
	private final String sort;
	private final String sortDir;
	
	public AccountListDTO(int currentPage, int pageCount, String usernameSearch, String ipSearch, List<UserDTO> accountList, String sort, String sortDir) {
		this.currentPage = currentPage;
		this.pageCount = pageCount;
		this.ipSearch = ipSearch;
		this.usernameSearch = usernameSearch;
		this.accountList = accountList;
		this.sort = sort;
		this.sortDir = sortDir;
	}
	
	public int getCurrentPage() {
		return currentPage;
	}
	public int getPageCount() {
		return pageCount;
	}
	public String getIpSearch() {
		return ipSearch;
	}
	public String getUsernameSearch() {
		return usernameSearch;
	}
	public List<UserDTO> getAccountList() {
		return accountList;
	}
	public String getSort() {
		return sort;
	}
	public String getSortDir() {
		return sortDir;
	}
	
}
