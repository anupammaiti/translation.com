package org.gs4tr.globallink.adaptors.aem.connector.test.webmvc.handler;

import static org.mockito.Matchers.anyBoolean;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.net.URL;

import org.apache.sling.api.resource.ModifiableValueMap;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.gs4tr.globallink.adaptors.aem.connector.model.AemConnection;
import org.gs4tr.globallink.adaptors.aem.connector.model.AemConnectionProxyInfo;
import org.gs4tr.globallink.adaptors.aem.connector.webmvc.handler.command.impl.SaveConnectionConfigTaskCommand;
import org.gs4tr.globallink.adaptors.aem.connector.webmvc.handler.impl.SaveConnectionConfigTaskHandler;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

public class SaveConnectionConfigTaskHandlerTest {
    
    private ModifiableValueMap connectionProperties;
    
    private Resource connection;
    
    private ResourceResolver resolver;
    
    @Before
    public void initialize() throws Exception {
	
	connectionProperties = mock(ModifiableValueMap.class);
	
	connection = mock(Resource.class);
	when(connection.adaptTo(ModifiableValueMap.class)).thenReturn(connectionProperties);
	
	resolver = mock(ResourceResolver.class);
	when(resolver.getResource("/connection/config/path")).thenReturn(connection);
    }
    
    @Ignore @Test
    public void saveConnectionConfigWithoutProxy() throws Exception {
	
	AemConnection aemConnection = new AemConnection();
	aemConnection.setWebServicesURL(new URL("http://web-services-url.com"));
	aemConnection.setUserName("userName");
	aemConnection.setUserPassword("userPassword");
	
	SaveConnectionConfigTaskCommand taskCommand = new SaveConnectionConfigTaskCommand();
	taskCommand.setConfigPath("/connection/config/path");
	taskCommand.setConnection(aemConnection);
	taskCommand.setResourceResolver(resolver);
	
	new SaveConnectionConfigTaskHandler().doTask(taskCommand);
	
	verify(resolver, times(1)).getResource(taskCommand.getConfigPath());
	
	verify(connectionProperties, times(1)).put(
		AemConnection.WEB_SERVICES_URL,
		taskCommand.getConnection().getWebServicesURL().toString());
	
	verify(connectionProperties, times(1)).put(
		AemConnection.USER_NAME,
		taskCommand.getConnection().getUserName());
	
	verify(connectionProperties, times(1)).put(
		AemConnection.USER_PASSWORD,
		taskCommand.getConnection().getUserPassword());
	
	verify(connectionProperties, times(0)).put(
		AemConnectionProxyInfo.PROXY_ENABLED,
		eq(anyBoolean()));
	
	verify(connectionProperties, times(0)).put(
		AemConnectionProxyInfo.PROXY_HOST,
		eq(anyString()));
	
	verify(connectionProperties, times(0)).put(
		AemConnectionProxyInfo.PROXY_PORT,
		eq(anyInt()));
	
	verify(connectionProperties, times(0)).put(
		AemConnectionProxyInfo.PROXY_USER_NAME,
		eq(anyString()));
	
	verify(connectionProperties, times(0)).put(
		AemConnectionProxyInfo.PROXY_USER_PASSWORD,
		eq(anyString()));
	
	verify(resolver, times(1)).commit();
    }
    
    @Ignore @Test
    public void saveConnectionConfigWithAnonymousProxy() throws Exception {
	
	AemConnectionProxyInfo aemConnectionProxyInfo = new AemConnectionProxyInfo();
	aemConnectionProxyInfo.setProxyEnabled(Boolean.TRUE);
	aemConnectionProxyInfo.setProxyHost("proxy-host");
	aemConnectionProxyInfo.setProxyPort(1234);
	
	AemConnection aemConnection = new AemConnection();
	aemConnection.setWebServicesURL(new URL("http://web-services-url.com"));
	aemConnection.setUserName("userName");
	aemConnection.setUserPassword("userPassword");	
	aemConnection.setProxyInfo(aemConnectionProxyInfo);
	
	SaveConnectionConfigTaskCommand taskCommand = new SaveConnectionConfigTaskCommand();
	taskCommand.setConfigPath("/connection/config/path");
	taskCommand.setConnection(aemConnection);
	taskCommand.setResourceResolver(resolver);
	
	new SaveConnectionConfigTaskHandler().doTask(taskCommand);
	
	verify(resolver, times(1)).getResource(taskCommand.getConfigPath());
	
	verify(connectionProperties, times(1)).put(
		AemConnection.WEB_SERVICES_URL,
		taskCommand.getConnection().getWebServicesURL().toString());
	
	verify(connectionProperties, times(1)).put(
		AemConnection.USER_NAME,
		taskCommand.getConnection().getUserName());
	
	verify(connectionProperties, times(1)).put(
		AemConnection.USER_PASSWORD,
		taskCommand.getConnection().getUserPassword());
	
	verify(connectionProperties, times(1)).put(
		AemConnectionProxyInfo.PROXY_ENABLED,
		Boolean.TRUE);
	
	verify(connectionProperties, times(1)).put(
		AemConnectionProxyInfo.PROXY_HOST,
		aemConnectionProxyInfo.getProxyHost());
	
	verify(connectionProperties, times(1)).put(
		AemConnectionProxyInfo.PROXY_PORT,
		aemConnectionProxyInfo.getProxyPort());
	
	verify(connectionProperties, times(0)).put(
		AemConnectionProxyInfo.PROXY_USER_NAME,
		eq(anyString()));
	
	verify(connectionProperties, times(0)).put(
		AemConnectionProxyInfo.PROXY_USER_PASSWORD,
		eq(anyString()));
	
	verify(resolver, times(1)).commit();
    }
    
    @Ignore @Test
    public void saveConnectionConfigWithProxy() throws Exception {
	
	AemConnectionProxyInfo aemConnectionProxyInfo = new AemConnectionProxyInfo();
	aemConnectionProxyInfo.setProxyEnabled(Boolean.TRUE);
	aemConnectionProxyInfo.setProxyHost("proxy-host");
	aemConnectionProxyInfo.setProxyPort(1234);
	aemConnectionProxyInfo.setProxyUserName("proxyUserName");
	aemConnectionProxyInfo.setProxyUserPassword("proxyUserPassword");
	
	AemConnection aemConnection = new AemConnection();
	aemConnection.setWebServicesURL(new URL("http://web-services-url.com"));
	aemConnection.setUserName("userName");
	aemConnection.setUserPassword("userPassword");	
	aemConnection.setProxyInfo(aemConnectionProxyInfo);
	
	SaveConnectionConfigTaskCommand taskCommand = new SaveConnectionConfigTaskCommand();
	taskCommand.setConfigPath("/connection/config/path");
	taskCommand.setConnection(aemConnection);
	taskCommand.setResourceResolver(resolver);
	
	new SaveConnectionConfigTaskHandler().doTask(taskCommand);
	
	verify(resolver, times(1)).getResource(taskCommand.getConfigPath());
	
	verify(connectionProperties, times(1)).put(
		AemConnection.WEB_SERVICES_URL,
		taskCommand.getConnection().getWebServicesURL().toString());
	
	verify(connectionProperties, times(1)).put(
		AemConnection.USER_NAME,
		taskCommand.getConnection().getUserName());
	
	verify(connectionProperties, times(1)).put(
		AemConnection.USER_PASSWORD,
		taskCommand.getConnection().getUserPassword());
	
	verify(connectionProperties, times(1)).put(
		AemConnectionProxyInfo.PROXY_ENABLED,
		Boolean.TRUE);
	
	verify(connectionProperties, times(1)).put(
		AemConnectionProxyInfo.PROXY_HOST,
		aemConnectionProxyInfo.getProxyHost());
	
	verify(connectionProperties, times(1)).put(
		AemConnectionProxyInfo.PROXY_PORT,
		aemConnectionProxyInfo.getProxyPort());
	
	verify(connectionProperties, times(1)).put(
		AemConnectionProxyInfo.PROXY_USER_NAME,
		aemConnectionProxyInfo.getProxyUserName());
	
	verify(connectionProperties, times(1)).put(
		AemConnectionProxyInfo.PROXY_USER_PASSWORD,
		aemConnectionProxyInfo.getProxyUserPassword());
	
	verify(resolver, times(1)).commit();
    }
    
}