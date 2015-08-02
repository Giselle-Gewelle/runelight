package org.runelight.controller.impl.account;

import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.when;

import java.sql.Connection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import org.runelight.db.dao.account.CreateAccountDAO;
import org.runelight.http.HttpRequestType;

@RunWith(PowerMockRunner.class)
@PrepareForTest(CreateAccountDAO.class)
public final class CreateAccountTest {

	private CreateAccount testObject;
	private String testIP = "127.0.0.1";
	private String testMod = "create";
	
	@Mock private HttpServletRequest mockRequest;
	@Mock private HttpServletResponse mockResponse;
	@Mock private Connection mockConnection;
	
	@Before
	public void setup() {
		testObject = new CreateAccount();
		testObject.setup(mockRequest, mockResponse, HttpRequestType.GET, testIP, 1L, mockConnection, testMod, "index.html");
		
		mockStatic(CreateAccountDAO.class);
	}
	
	@Test
	public void testValidatePasswords() {
		// TODO
	}
	
	@Test
	public void testValidateTerms() {
		// happy
		when(mockRequest.getParameter("agree_terms")).thenReturn("on");
		
		boolean result = testObject.validateTerms();
		assertEquals(true, result);
		
		
		// errors
		when(mockRequest.getParameter("agree_terms")).thenReturn(null);
		
		result = testObject.validateTerms();
		assertEquals(false, result);
		

		when(mockRequest.getParameter("agree_terms")).thenReturn("off");
		
		result = testObject.validateTerms();
		assertEquals(false, result);
	}
	
	@Test
	public void testValidateUsername() {
		// happy
		String testUsername = "test";
		when(mockRequest.getParameter("username")).thenReturn(testUsername);
		when(CreateAccountDAO.usernameExists(mockConnection, testUsername)).thenReturn(0); // Username does not exist
		
		int result = testObject.validateUsername();
		assertEquals(result, -1);
		assertEquals("test", testObject.getData().getUsername());
		assertEquals("Test", testObject.getData().getFormattedUsername());
		
		
		when(mockRequest.getParameter("username")).thenReturn(" te     st "); // Testing username formatting and space stripping
		when(CreateAccountDAO.usernameExists(mockConnection, testUsername)).thenReturn(0); // Username does not exist
		
		result = testObject.validateUsername();
		assertEquals(result, -1);
		assertEquals("te_st", testObject.getData().getUsername());
		assertEquals("Te St", testObject.getData().getFormattedUsername());
		
		
		// errors
		testUsername = "test";
		when(mockRequest.getParameter("username")).thenReturn(testUsername);
		when(CreateAccountDAO.usernameExists(mockConnection, testUsername)).thenReturn(1); // Username exists
		
		result = testObject.validateUsername();
		assertEquals(2, result);
		
		
		when(CreateAccountDAO.usernameExists(mockConnection, testUsername)).thenReturn(-1); // SQL error
		
		result = testObject.validateUsername();
		assertEquals(3, result);

		
		when(mockRequest.getParameter("username")).thenReturn("1234567890123");

		result = testObject.validateUsername();
		assertEquals(1, result);

		
		when(mockRequest.getParameter("username")).thenReturn("us$er");

		result = testObject.validateUsername();
		assertEquals(1, result);
	}
	
	@Test
	public void testValidateAgeAndCountry() {
		// happy
		String testAge = "3";
		String testCountry = "225";
		when(mockRequest.getParameter("age")).thenReturn(testAge);
		when(mockRequest.getParameter("country")).thenReturn(testCountry);
		
		boolean result = testObject.validateAgeAndCountry();
		assertEquals(true, result);
		assertEquals(testAge, testObject.getData().getAgeRange());
		assertEquals(testCountry, testObject.getData().getCountryCode());
		
		
		// errors
		testAge = "g";
		testCountry = "225";
		when(mockRequest.getParameter("age")).thenReturn(testAge);
		when(mockRequest.getParameter("country")).thenReturn(testCountry);
		
		result = testObject.validateAgeAndCountry();
		assertEquals(false, result);
		
		
		testAge = "5";
		testCountry = "g";
		when(mockRequest.getParameter("age")).thenReturn(testAge);
		when(mockRequest.getParameter("country")).thenReturn(testCountry);
		
		result = testObject.validateAgeAndCountry();
		assertEquals(false, result);
		
		
		testAge = "g";
		testCountry = "g";
		when(mockRequest.getParameter("age")).thenReturn(testAge);
		when(mockRequest.getParameter("country")).thenReturn(testCountry);
		
		result = testObject.validateAgeAndCountry();
		assertEquals(false, result);
		
		
		testAge = null;
		testCountry = null;
		when(mockRequest.getParameter("age")).thenReturn(testAge);
		when(mockRequest.getParameter("country")).thenReturn(testCountry);
		
		result = testObject.validateAgeAndCountry();
		assertEquals(false, result);
	}

}
