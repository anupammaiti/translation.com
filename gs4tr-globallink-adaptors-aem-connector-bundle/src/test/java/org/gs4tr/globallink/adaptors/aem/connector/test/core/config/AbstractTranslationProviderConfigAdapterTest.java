package org.gs4tr.globallink.adaptors.aem.connector.test.core.config;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ValueMap;

import org.gs4tr.globallink.adaptors.aem.connector.core.config.TranslationProviderConfig;
import org.gs4tr.globallink.adaptors.aem.connector.util.constraints.StringConstraints;

import org.junit.Before;

import com.day.cq.wcm.api.Page;

public abstract class AbstractTranslationProviderConfigAdapterTest {

    protected static final String WEB_SERVICES_URL = "http://testWebServicesURL.com/PD/services";
    
    protected static final String USER_NAME = "testUserName";
    
    protected static final String USER_PASSWORD = "testUserPassword";
    
    protected static final String PROJECT_SHORT_CODE = "testProjectShortCode";
    
    protected static final String PROJECT_DEFAULT_CLASSIFIER = "testProjectDefaultClassifier";
    
    protected ValueMap configProperties;

    protected Resource configResource;
    
    protected Resource configContentResource;
    
    protected Page configPage;
    
    protected void initProjectParameters() {
	
	when(configProperties.containsKey(
		TranslationProviderConfig.PROJECT_SHORT_CODE))
		.thenReturn(Boolean.TRUE);
	
	when(configProperties.get(
		TranslationProviderConfig.PROJECT_SHORT_CODE, String.class))
		.thenReturn(PROJECT_SHORT_CODE);
	
	when(configProperties.containsKey(
		TranslationProviderConfig.PROJECT_DEFAULT_CLASSIFIER))
		.thenReturn(Boolean.TRUE);
	
	when(configProperties.get(
		TranslationProviderConfig.PROJECT_DEFAULT_CLASSIFIER, String.class))
		.thenReturn(PROJECT_DEFAULT_CLASSIFIER);
    }
    
    @Before
    public void initResources() throws Exception {
	
	configProperties = mock(ValueMap.class);
	
	configContentResource = mock(Resource.class);
	when(configContentResource.adaptTo(ValueMap.class)).thenReturn(configProperties);
	
	configPage = mock(Page.class);
	when(configPage.getContentResource()).thenReturn(configContentResource);
	
	configResource = mock(Resource.class);
	when(configResource.adaptTo(Page.class)).thenReturn(configPage);
	when(configResource.isResourceType(StringConstraints.CQ_PAGE)).thenReturn(Boolean.TRUE);
    }
    
    protected void initWebServicesParameters() {
	
	when(configProperties.containsKey(
		TranslationProviderConfig.WEB_SERVICES_URL))
		.thenReturn(Boolean.TRUE);
	
	when(configProperties.get(
		TranslationProviderConfig.WEB_SERVICES_URL, String.class))
		.thenReturn(WEB_SERVICES_URL);
	
	when(configProperties.containsKey(
		TranslationProviderConfig.USER_NAME))
		.thenReturn(Boolean.TRUE);
	
	when(configProperties.get(
		TranslationProviderConfig.USER_NAME, String.class))
		.thenReturn(USER_NAME);
	
	when(configProperties.containsKey(
		TranslationProviderConfig.USER_PASSWORD))
		.thenReturn(Boolean.TRUE);
	
	when(configProperties.get(
		TranslationProviderConfig.USER_PASSWORD, String.class))
		.thenReturn(USER_PASSWORD);
    }
    
}