package org.gs4tr.globallink.adaptors.aem.connector.test.webmvc.command.adapter;

import static org.mockito.Mockito.when;

import java.net.URL;

import org.gs4tr.globallink.adaptors.aem.connector.model.AemConnection;
import org.gs4tr.globallink.adaptors.aem.connector.model.AemConnectionProxyInfo;
import org.gs4tr.globallink.adaptors.aem.connector.webmvc.handler.command.adapter.impl.SaveConnectionConfigTaskCommandAdapter;
import org.gs4tr.globallink.adaptors.aem.connector.webmvc.handler.command.impl.SaveConnectionConfigTaskCommand;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class SaveConnectionConfigTaskCommandAdapterTest extends AbstractTaskCommandAdapterTest {
        
    private SaveConnectionConfigTaskCommandAdapter adapter;
    
    @Before
    public void initializeTest() throws Exception {
	
	adapter = new SaveConnectionConfigTaskCommandAdapter();
    }
    
    @Test
    public void saveConnectionConfigTest() throws Exception {
	
	when(request.getParameter(AemConnection.WEB_SERVICES_URL)).thenReturn("http://web-services-url");
	when(request.getParameter(AemConnection.USER_NAME)).thenReturn("userName");
	when(request.getParameter(AemConnection.USER_PASSWORD)).thenReturn("userPassword");
	
	SaveConnectionConfigTaskCommand command = adapter.adaptFromServletRequest(request);
	
	Assert.assertNotNull(command);
	
	AemConnection aemConnection = command.getConnection();
	
	Assert.assertEquals(new URL("http://web-services-url"), aemConnection.getWebServicesURL());
	Assert.assertEquals("userName", aemConnection.getUserName());
	Assert.assertEquals("userPassword", aemConnection.getUserPassword());
    }
    
    @Test
    public void saveConnectionConfigWithValidProxyConfig() throws Exception {
	
	when(request.getParameter(AemConnection.WEB_SERVICES_URL)).thenReturn("http://web-services-url");
	when(request.getParameter(AemConnection.USER_NAME)).thenReturn("userName");
	when(request.getParameter(AemConnection.USER_PASSWORD)).thenReturn("userPassword");
	when(request.getParameter(AemConnectionProxyInfo.PROXY_ENABLED)).thenReturn("true");
	when(request.getParameter(AemConnectionProxyInfo.PROXY_HOST)).thenReturn("proxyHost");
	when(request.getParameter(AemConnectionProxyInfo.PROXY_PORT)).thenReturn("1234");
	
	SaveConnectionConfigTaskCommand command = adapter.adaptFromServletRequest(request);
	
	Assert.assertNotNull(command);
	
	AemConnection aemConnection = command.getConnection();
	
	Assert.assertEquals(new URL("http://web-services-url"), aemConnection.getWebServicesURL());
	Assert.assertEquals("userName", aemConnection.getUserName());
	Assert.assertEquals("userPassword", aemConnection.getUserPassword());
	Assert.assertEquals(Boolean.TRUE, aemConnection.getProxyInfo().isProxyEnabled());
	Assert.assertEquals("proxyHost", aemConnection.getProxyInfo().getProxyHost());
	Assert.assertEquals(1234, aemConnection.getProxyInfo().getProxyPort());
    }

}