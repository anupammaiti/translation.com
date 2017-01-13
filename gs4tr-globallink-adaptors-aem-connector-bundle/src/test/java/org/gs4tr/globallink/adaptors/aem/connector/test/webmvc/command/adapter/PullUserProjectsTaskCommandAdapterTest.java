package org.gs4tr.globallink.adaptors.aem.connector.test.webmvc.command.adapter;

import static org.mockito.Mockito.when;

import java.net.URL;

import org.gs4tr.globallink.adaptors.aem.connector.model.AemConnection;
import org.gs4tr.globallink.adaptors.aem.connector.model.AemConnectionProxyInfo;
import org.gs4tr.globallink.adaptors.aem.connector.webmvc.handler.command.adapter.impl.PullUserProjectsTaskCommandAdapter;
import org.gs4tr.globallink.adaptors.aem.connector.webmvc.handler.command.impl.PullUserProjectsTaskCommand;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class PullUserProjectsTaskCommandAdapterTest extends AbstractTaskCommandAdapterTest {
    
    private static final String WEB_SERVICES_URL = "http://web-services-url";
    
    private static final String USER_NAME = "userName";
    
    private static final String USER_PASSWORD = "userPassword";
    
    private static final String PROXY_HOST = "proxyHost";
    
    private static final int PROXY_PORT = 1234;
    
    private static final String PROXY_USER_NAME = "proxyUserName";
    
    private static final String PROXY_USER_PASSWORD = "proxyUserPassword";
    
    private PullUserProjectsTaskCommandAdapter adapter;
        
    @Before
    public void initializeTest() {
	
	adapter = new PullUserProjectsTaskCommandAdapter();	
    }
    
    @Test
    public void adaptToPullUserProjectsTaskCommandWithoutProxy() throws Exception {
	
	when(request.getParameter(AemConnection.WEB_SERVICES_URL)).thenReturn(WEB_SERVICES_URL);
	when(request.getParameter(AemConnection.USER_NAME)).thenReturn(USER_NAME);
	when(request.getParameter(AemConnection.USER_PASSWORD)).thenReturn(USER_PASSWORD);
	
	PullUserProjectsTaskCommand command = adapter.adaptFromServletRequest(request);
	
	Assert.assertNotNull(command);
	
	AemConnection aemConnection = command.getConnection();
	
	Assert.assertNotNull(aemConnection);
	
	Assert.assertEquals(new URL(WEB_SERVICES_URL), aemConnection.getWebServicesURL());
	Assert.assertEquals(USER_NAME, aemConnection.getUserName());
	Assert.assertEquals(USER_PASSWORD, aemConnection.getUserPassword());
	
	AemConnectionProxyInfo aemConnectionProxyInfo = aemConnection.getProxyInfo();
	
	Assert.assertNotNull(aemConnectionProxyInfo);
	
	Assert.assertEquals(Boolean.FALSE, aemConnectionProxyInfo.isProxyEnabled());
    }
    
    @Test
    public void adaptToPullUserProjectsTaskCommandWithAnonymousProxy() throws Exception {
	
	when(request.getParameter(AemConnection.WEB_SERVICES_URL)).thenReturn(WEB_SERVICES_URL);
	when(request.getParameter(AemConnection.USER_NAME)).thenReturn(USER_NAME);
	when(request.getParameter(AemConnection.USER_PASSWORD)).thenReturn(USER_PASSWORD);
	
	when(request.getParameter(AemConnectionProxyInfo.PROXY_ENABLED)).thenReturn(String.valueOf(Boolean.TRUE));
	when(request.getParameter(AemConnectionProxyInfo.PROXY_HOST)).thenReturn(PROXY_HOST);
	when(request.getParameter(AemConnectionProxyInfo.PROXY_PORT)).thenReturn(String.valueOf(PROXY_PORT));
	
	PullUserProjectsTaskCommand command = adapter.adaptFromServletRequest(request);
	
	Assert.assertNotNull(command);
	
	AemConnection aemConnection = command.getConnection();
	
	Assert.assertNotNull(aemConnection);
	
	Assert.assertEquals(new URL(WEB_SERVICES_URL), aemConnection.getWebServicesURL());
	Assert.assertEquals(USER_NAME, aemConnection.getUserName());
	Assert.assertEquals(USER_PASSWORD, aemConnection.getUserPassword());
	
	AemConnectionProxyInfo aemConnectionProxyInfo = aemConnection.getProxyInfo();
	
	Assert.assertNotNull(aemConnectionProxyInfo);
	
	Assert.assertEquals(Boolean.TRUE, aemConnectionProxyInfo.isProxyEnabled());
	Assert.assertEquals(PROXY_HOST, aemConnectionProxyInfo.getProxyHost());
	Assert.assertEquals(PROXY_PORT, aemConnectionProxyInfo.getProxyPort());
    }
    
    @Test
    public void adaptToPullUserProjectsTaskCommandWithProxy() throws Exception {
	
	when(request.getParameter(AemConnection.WEB_SERVICES_URL)).thenReturn(WEB_SERVICES_URL);
	when(request.getParameter(AemConnection.USER_NAME)).thenReturn(USER_NAME);
	when(request.getParameter(AemConnection.USER_PASSWORD)).thenReturn(USER_PASSWORD);
	
	when(request.getParameter(AemConnectionProxyInfo.PROXY_ENABLED)).thenReturn(String.valueOf(Boolean.TRUE));
	when(request.getParameter(AemConnectionProxyInfo.PROXY_HOST)).thenReturn(PROXY_HOST);
	when(request.getParameter(AemConnectionProxyInfo.PROXY_PORT)).thenReturn(String.valueOf(PROXY_PORT));
	when(request.getParameter(AemConnectionProxyInfo.PROXY_USER_NAME)).thenReturn(PROXY_USER_NAME);
	when(request.getParameter(AemConnectionProxyInfo.PROXY_USER_PASSWORD)).thenReturn(PROXY_USER_PASSWORD);
	
	PullUserProjectsTaskCommand command = adapter.adaptFromServletRequest(request);
	
	Assert.assertNotNull(command);
	
	AemConnection aemConnection = command.getConnection();
	
	Assert.assertNotNull(aemConnection);
	
	Assert.assertEquals(new URL(WEB_SERVICES_URL), aemConnection.getWebServicesURL());
	Assert.assertEquals(USER_NAME, aemConnection.getUserName());
	Assert.assertEquals(USER_PASSWORD, aemConnection.getUserPassword());
	
	AemConnectionProxyInfo aemConnectionProxyInfo = aemConnection.getProxyInfo();
	
	Assert.assertNotNull(aemConnectionProxyInfo);
	
	Assert.assertEquals(Boolean.TRUE, aemConnectionProxyInfo.isProxyEnabled());
	Assert.assertEquals(PROXY_HOST, aemConnectionProxyInfo.getProxyHost());
	Assert.assertEquals(PROXY_PORT, aemConnectionProxyInfo.getProxyPort());
	Assert.assertEquals(PROXY_USER_NAME, aemConnectionProxyInfo.getProxyUserName());
	Assert.assertEquals(PROXY_USER_PASSWORD, aemConnectionProxyInfo.getProxyUserPassword());
    }
    
}