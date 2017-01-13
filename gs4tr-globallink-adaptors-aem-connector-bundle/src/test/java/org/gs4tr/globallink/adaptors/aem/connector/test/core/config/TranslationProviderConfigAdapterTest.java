package org.gs4tr.globallink.adaptors.aem.connector.test.core.config;

import static org.mockito.Mockito.when;

import java.net.URL;

import org.gs4tr.globallink.adaptors.aem.connector.core.config.TranslationProviderConfig;
import org.gs4tr.globallink.adaptors.aem.connector.core.config.TranslationProviderConfigAdapter;
import org.junit.Assert;
import org.junit.Test;

public class TranslationProviderConfigAdapterTest extends AbstractTranslationProviderConfigAdapterTest {
    
    @Test
    public void adaptFromContentResource() throws Exception {
	
	initWebServicesParameters();
	
	initProjectParameters();
	
	TranslationProviderConfigAdapter configAdapter = new TranslationProviderConfigAdapter();
	
	TranslationProviderConfig config = configAdapter.adaptFromResource(configContentResource);
	
	Assert.assertNotNull(config);
	
	Assert.assertEquals(new URL(WEB_SERVICES_URL), config.getWebServicesURL());
	
	Assert.assertEquals(USER_NAME, config.getUserName());
	Assert.assertEquals(USER_PASSWORD, config.getUserPassword());
	Assert.assertEquals(PROJECT_SHORT_CODE, config.getProjectShortCode());
	Assert.assertEquals(PROJECT_DEFAULT_CLASSIFIER, config.getProjectDefaultClassifier());
    }
    
    @Test
    public void adaptFromContentResourceWhenWebServicesUrlMissing() throws Exception {
	
	initWebServicesParameters();
	
	initProjectParameters();
	
	when(configProperties.containsKey(
		TranslationProviderConfig.WEB_SERVICES_URL))
		.thenReturn(Boolean.FALSE);
	
	TranslationProviderConfigAdapter configAdapter = new TranslationProviderConfigAdapter();
	
	TranslationProviderConfig config = configAdapter.adaptFromResource(configContentResource);
	
	Assert.assertNotNull(config);
	
	Assert.assertNull(config.getWebServicesURL());
	
	Assert.assertEquals(USER_NAME, config.getUserName());
	Assert.assertEquals(USER_PASSWORD, config.getUserPassword());
	Assert.assertEquals(PROJECT_SHORT_CODE, config.getProjectShortCode());
	Assert.assertEquals(PROJECT_DEFAULT_CLASSIFIER, config.getProjectDefaultClassifier());
    }
    
    @Test
    public void adaptFromPageResource() throws Exception {
	
	initWebServicesParameters();
	
	initProjectParameters();
			
	TranslationProviderConfigAdapter configAdapter = new TranslationProviderConfigAdapter();
	
	TranslationProviderConfig config = configAdapter.adaptFromResource(configContentResource);
	
	Assert.assertNotNull(config);
	
	Assert.assertEquals(new URL(WEB_SERVICES_URL), config.getWebServicesURL());
	
	Assert.assertEquals(USER_NAME, config.getUserName());
	Assert.assertEquals(USER_PASSWORD, config.getUserPassword());
	Assert.assertEquals(PROJECT_SHORT_CODE, config.getProjectShortCode());
	Assert.assertEquals(PROJECT_DEFAULT_CLASSIFIER, config.getProjectDefaultClassifier());
    }
    
}