package org.gs4tr.globallink.adaptors.aem.connector.test.webmvc.handler;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;

import org.junit.Before;
import org.junit.Test;

public class SaveRepositoryConfigTaskHandlerTest {
    
    private Resource repository;
    
    private ResourceResolver resolver;
    
    @Before
    public void initialize() throws Exception {
	
	repository = mock(Resource.class);
	
	resolver = mock(ResourceResolver.class);
	when(resolver.getResource("/repository/config/path")).thenReturn(repository);
    }
    
    @Test
    public void saveRepositoryConfig() throws Exception {
	
    }
    
}