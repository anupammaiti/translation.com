package org.gs4tr.globallink.adaptors.aem.connector.test.model.adapter;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.api.wrappers.ValueMapDecorator;

import org.gs4tr.globallink.adaptors.aem.connector.model.AemConnection;
import org.gs4tr.globallink.adaptors.aem.connector.model.AemConnectionProxyInfo;
import org.gs4tr.globallink.adaptors.aem.connector.model.adapter.impl.AemConnectionAdapter;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ConnectionAdapterTest {

    private static final String WEB_SERVICES_URL = "http://hostname.com:8080/PD/services";
    
    private static final String USER_NAME = "userName";
    
    private static final String USER_PASSWORD = "userPassword";
    
    private static final String PROXY_HOST = "http://proxy.com";
    
    private static final String PROXY_PORT = "1234";
    
    private static final String PROXY_USER_NAME = "proxyUserName";
    
    private static final String PROXY_USER_PASSWORD = "proxyUserPassword";
    
    private Resource resource;
    
    private Map<String, Object> resourceMap = new HashMap<String, Object>();
    
    @Before
    public void initialize() {
	
	resource = mock(Resource.class);	
	when(resource.adaptTo(ValueMap.class)).thenReturn(new ValueMapDecorator(resourceMap));
	when(resource.getPath()).thenReturn("/path/to/connection/configuration");
	
	resourceMap.put(AemConnection.WEB_SERVICES_URL, WEB_SERVICES_URL);
	resourceMap.put(AemConnection.USER_NAME, USER_NAME);
	resourceMap.put(AemConnection.USER_PASSWORD, USER_PASSWORD);
    }
    
    public void initializeProxy() {
	
	resourceMap.put(AemConnectionProxyInfo.PROXY_ENABLED, Boolean.TRUE);
	resourceMap.put(AemConnectionProxyInfo.PROXY_HOST, PROXY_HOST);
	resourceMap.put(AemConnectionProxyInfo.PROXY_PORT, PROXY_PORT);
    }
    
    public void initializeProxyUser() {
	
	resourceMap.put(AemConnectionProxyInfo.PROXY_USER_NAME, PROXY_USER_NAME);
	resourceMap.put(AemConnectionProxyInfo.PROXY_USER_PASSWORD, PROXY_USER_PASSWORD);
    }
    
    @Test
    public void adaptToConnectionWithoutProxyTest() throws Exception {
	
	AemConnection connection = new AemConnectionAdapter().adaptFromResource(resource);
	
	Assert.assertNotNull(connection);
	Assert.assertEquals(new URL(WEB_SERVICES_URL), connection.getWebServicesURL());
	Assert.assertEquals(USER_NAME, connection.getUserName());
	Assert.assertEquals(USER_PASSWORD, connection.getUserPassword());
    }
    
    @Test
    public void adaptToConnectionWithAnonimusProxyTest() throws Exception {
	
	initializeProxy();
	
	AemConnection connection = new AemConnectionAdapter().adaptFromResource(resource);
	
	Assert.assertNotNull(connection);
	Assert.assertEquals(new URL(WEB_SERVICES_URL), connection.getWebServicesURL());
	Assert.assertEquals(USER_NAME, connection.getUserName());
	Assert.assertEquals(USER_PASSWORD, connection.getUserPassword());
	Assert.assertEquals(Boolean.TRUE, connection.getProxyInfo().isProxyEnabled());
	Assert.assertEquals(PROXY_HOST, connection.getProxyInfo().getProxyHost());
	Assert.assertEquals(Integer.parseInt(PROXY_PORT), connection.getProxyInfo().getProxyPort());
    }
    
    @Test
    public void adaptToConnectionWithProxyTest() throws Exception {
	
	initializeProxy();
	initializeProxyUser();
	
	AemConnection connection = new AemConnectionAdapter().adaptFromResource(resource);
	
	Assert.assertNotNull(connection);
	Assert.assertEquals(new URL(WEB_SERVICES_URL), connection.getWebServicesURL());
	Assert.assertEquals(USER_NAME, connection.getUserName());
	Assert.assertEquals(USER_PASSWORD, connection.getUserPassword());
	Assert.assertEquals(Boolean.TRUE, connection.getProxyInfo().isProxyEnabled());
	Assert.assertEquals(PROXY_HOST, connection.getProxyInfo().getProxyHost());
	Assert.assertEquals(Integer.parseInt(PROXY_PORT), connection.getProxyInfo().getProxyPort());
	Assert.assertEquals(PROXY_USER_NAME, connection.getProxyInfo().getProxyUserName());
	Assert.assertEquals(PROXY_USER_PASSWORD, connection.getProxyInfo().getProxyUserPassword());
    }
    
}