package org.gs4tr.globallink.adaptors.aem.connector.test.util;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.Map;

import org.apache.jackrabbit.JcrConstants;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.api.wrappers.ValueMapDecorator;
import org.gs4tr.globallink.adaptors.aem.connector.util.RepositoryUtil;
import org.gs4tr.globallink.adaptors.aem.connector.util.constraints.StringConstraints;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class RepositoryUtilTest {
    
    private static final String SERVICE_CONFIG_PATH = "/etc/cloudservices/gs4tr-translation/pd/config";
    
    private static final String SERVICE_CONFIG_CONTENT_PATH = SERVICE_CONFIG_PATH + "/jcr:content";
        
    private Resource cloudServiceConfig;
    
    private Resource cloudServiceConfigContent;
            
    private Resource rootResource;
        
    private Resource parentResource;
    
    private Resource parentResourceContent;
    
    Map<String, Object> parentResourceContentMap = new HashMap<String, Object>();

    private Resource currentResource;
    
    private Resource currentResourceContent;
    
    Map<String, Object> currentResourceContentMap = new HashMap<String, Object>();
    
    private ResourceResolver resourceResolver;
    
    @Before
    public void initialize() {
	
	cloudServiceConfig = mock(Resource.class);
	cloudServiceConfigContent = mock(Resource.class);
	
	rootResource = mock(Resource.class);

	parentResource = mock(Resource.class);
	parentResourceContent = mock(Resource.class);
	
	currentResource = mock(Resource.class);
	currentResourceContent = mock(Resource.class);

	resourceResolver = mock(ResourceResolver.class);

	initializeCloudServiceConfig();
	
	initializeParentResource();
	initializeParentResourceContent();
	
	initializeCurrentResource();
	initializeCurrentResourceContent();
	
	initializeResourceResolver();
    }
    
    private void initializeCloudServiceConfig() {
	
	when(cloudServiceConfig.getChild(JcrConstants.JCR_CONTENT)).thenReturn(cloudServiceConfigContent);
	when(cloudServiceConfig.getPath()).thenReturn(SERVICE_CONFIG_PATH);
	
	when(cloudServiceConfigContent.getPath()).thenReturn(SERVICE_CONFIG_CONTENT_PATH);
    }
    
    private void initializeParentResource() {
	
	when(parentResource.adaptTo(ValueMap.class)).thenReturn(new ValueMapDecorator(null));
	when(parentResource.getChild(JcrConstants.JCR_CONTENT)).thenReturn(parentResourceContent);
	when(parentResource.getName()).thenReturn("parent");
	when(parentResource.getParent()).thenReturn(rootResource);
	when(parentResource.getPath()).thenReturn("/parent");
	when(parentResource.getResourceResolver()).thenReturn(resourceResolver);
    }
    
    private void initializeParentResourceContent() {
	
	when(parentResourceContent.adaptTo(ValueMap.class)).thenReturn(new ValueMapDecorator(parentResourceContentMap));
	when(parentResourceContent.getName()).thenReturn(JcrConstants.JCR_CONTENT);
	when(parentResourceContent.getParent()).thenReturn(parentResource);
	when(parentResourceContent.getPath()).thenReturn("/parent/jcr:content");
    }
    
    private void initializeCurrentResource() {
	
	when(currentResource.adaptTo(ValueMap.class)).thenReturn(new ValueMapDecorator(null));
	when(currentResource.getChild(JcrConstants.JCR_CONTENT)).thenReturn(currentResourceContent);
	when(currentResource.getName()).thenReturn("current");
	when(currentResource.getParent()).thenReturn(parentResource);
	when(currentResource.getPath()).thenReturn("/parent/current");
	when(currentResource.getResourceResolver()).thenReturn(resourceResolver);
    }
    
    private void initializeCurrentResourceContent() {
	
	when(currentResourceContent.adaptTo(ValueMap.class)).thenReturn(new ValueMapDecorator(currentResourceContentMap));
	when(currentResourceContent.getName()).thenReturn(JcrConstants.JCR_CONTENT);
	when(currentResourceContent.getParent()).thenReturn(currentResource);
	when(currentResourceContent.getPath()).thenReturn("/parent/current/jcr:content");
    }
    
    private void initializeResourceResolver() {
	
	when(resourceResolver.getResource(SERVICE_CONFIG_PATH)).thenReturn(cloudServiceConfig);
	when(resourceResolver.getResource(SERVICE_CONFIG_CONTENT_PATH)).thenReturn(cloudServiceConfigContent);
    }
    
    @Test
    public void findRepositoryConfigurationInCurrentResourceTest() throws Exception {
		
	currentResourceContentMap.put(StringConstraints.CQ_CLOUD_SERVICE_CONFIGS, SERVICE_CONFIG_PATH);
	
	Resource repository = RepositoryUtil.findRepository(currentResource);
	
	Assert.assertNotNull(repository);
	
	Assert.assertEquals(SERVICE_CONFIG_CONTENT_PATH, repository.getPath());
    }
    
    @Test
    public void findRepositoryConfigurationInParentResourceTest() throws Exception {
	
	parentResourceContentMap.put(StringConstraints.CQ_CLOUD_SERVICE_CONFIGS, SERVICE_CONFIG_PATH);
	
	Resource repository = RepositoryUtil.findRepository(currentResource);
	
	Assert.assertNotNull(repository);
	
	Assert.assertEquals(SERVICE_CONFIG_CONTENT_PATH, repository.getPath());
    }
    
    @Test
    public void invalidRepositoryConfigurationInCurrentResourceTest() throws Exception {
	
	currentResourceContentMap.put(StringConstraints.CQ_CLOUD_SERVICE_CONFIGS, "/invalid/repository/path");
	
	Resource repository = RepositoryUtil.findRepository(currentResource);
	
	Assert.assertNull(repository);
    }
    
    @Test
    public void invalidRepositoryConfigurationInParentResourceTest() throws Exception {
	
	parentResourceContentMap.put(StringConstraints.CQ_CLOUD_SERVICE_CONFIGS, "/invalid/repository/path");
	
	Resource repository = RepositoryUtil.findRepository(parentResource);
	
	Assert.assertNull(repository);
    }
    
    @Test
    public void missingRepositoryConfigurationTest() throws Exception {
	
	Resource repository = RepositoryUtil.findRepository(currentResource);
	
	Assert.assertNull(repository);
    }
    
}