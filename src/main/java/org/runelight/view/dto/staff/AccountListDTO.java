package org.runelight.view.dto.staff;

import java.util.List;

import org.runelight.view.dto.account.UserDTO;

public final class AccountListDTO {
	
	private final int currentPage;
	private final int pageCount;
	private final String usernameSearch;
	private final List<UserDTO> accountList;
	
	public AccountListDTO(int currentPage, int pageCount, String usernameSearch, List<UserDTO> accountList) {
		this.currentPage = currentPage;
		this.pageCount = pageCount;
		this.usernameSearch = usernameSearch;
		this.accountList = accountList;
	}
	
	public int getCurrentPage() {
		return currentPage;
	}
	public int getPageCount() {
		return pageCount;
	}
	public String getUsernameSearch() {
		return usernameSearch;
	}
	public List<UserDTO> getAccountList() {
		return accountList;
	}
	
}
