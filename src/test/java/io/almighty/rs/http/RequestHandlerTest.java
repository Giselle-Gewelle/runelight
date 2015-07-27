package io.almighty.rs.http;

import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.when;

import java.io.IOException;
import java.sql.Connection;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import io.almighty.rs.db.RSDataSource;

@RunWith(PowerMockRunner.class)
@PrepareForTest(RSDataSource.class)
public final class RequestHandlerTest {

	@Mock private HttpServletRequest mockRequest;
	@Mock private HttpServletResponse mockResponse;
	@Mock private Connection mockConnection;
	
	@Before
	public void setup() {
		when(mockRequest.getServerName()).thenReturn("create.test.com");
		mockStatic(RSDataSource.class);
	}
	
	@Test
	public void testSubmitViewRequest_nullUri() throws IOException {
		when(mockRequest.getRequestURI()).thenReturn(null);
		
		RequestHandler.submitViewRequest(HttpRequestType.GET, mockRequest, mockResponse);
		
		verify(mockResponse, times(1)).sendError(404);
	}
	
	@Test
	public void testSubmitViewRequest_invalidUri() throws IOException {
		when(mockRequest.getRequestURI()).thenReturn("/gigi");
		
		RequestHandler.submitViewRequest(HttpRequestType.GET, mockRequest, mockResponse);
		
		verify(mockResponse, times(1)).sendError(404);
	}
	
	@Test
	public void testSubmitViewRequest_sslRequired() throws Exception {
		when(mockRequest.getRequestURI()).thenReturn("/index.html");
		when(mockRequest.isSecure()).thenReturn(false);
		
		RequestHandler.submitViewRequest(HttpRequestType.GET, mockRequest, mockResponse);
		
		verify(mockResponse, times(1)).sendError(403);
	}
	
	@Test
	public void testSubmitViewRequest_nullConnection() throws Exception {
		when(mockRequest.getRequestURI()).thenReturn("/index.html");
		when(mockRequest.isSecure()).thenReturn(true);
		when(RSDataSource.getConnection()).thenReturn(null);
		
		RequestHandler.submitViewRequest(HttpRequestType.GET, mockRequest, mockResponse);
		
		verify(mockResponse, times(1)).sendError(503);
	}
	
	@Test
	public void testSubmitViewRequest_closedConnection() throws Exception {
		when(mockRequest.getRequestURI()).thenReturn("/index.html");
		when(mockRequest.isSecure()).thenReturn(true);
		when(RSDataSource.getConnection()).thenReturn(mockConnection);
		when(mockConnection.isClosed()).thenReturn(true);
		
		RequestHandler.submitViewRequest(HttpRequestType.GET, mockRequest, mockResponse);
		
		verify(mockResponse, times(1)).sendError(503);
	}
	
	@Test
	public void testSubmitViewRequest_happy() throws Exception {
		when(mockRequest.getRequestURI()).thenReturn("/index.html");
		when(mockRequest.isSecure()).thenReturn(true);
		when(RSDataSource.getConnection()).thenReturn(mockConnection);
		when(mockConnection.isClosed()).thenReturn(false);
		
		RequestHandler.submitViewRequest(HttpRequestType.GET, mockRequest, mockResponse);
		
		PowerMockito.verifyStatic();
		RSDataSource.closeConnection(mockConnection);
	}
	
}
