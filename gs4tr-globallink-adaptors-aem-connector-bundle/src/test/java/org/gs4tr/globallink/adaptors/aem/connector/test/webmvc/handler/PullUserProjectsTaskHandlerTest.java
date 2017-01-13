package org.gs4tr.globallink.adaptors.aem.connector.test.webmvc.handler;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.apache.sling.api.resource.ModifiableValueMap;
import org.apache.sling.api.resource.Resource;

import org.junit.Before;
import org.junit.Test;

public class PullUserProjectsTaskHandlerTest {
    
    private ModifiableValueMap connectionProperties;
    
    private Resource connection;
        
    @Before
    public void initialize() throws Exception {
	
	connectionProperties = mock(ModifiableValueMap.class);
	
	connection = mock(Resource.class);
	when(connection.adaptTo(ModifiableValueMap.class)).thenReturn(connectionProperties);	
    }
    
    @Test
    public void pullUserProjectWithValidConnection() throws Exception {
	
    }
    
}