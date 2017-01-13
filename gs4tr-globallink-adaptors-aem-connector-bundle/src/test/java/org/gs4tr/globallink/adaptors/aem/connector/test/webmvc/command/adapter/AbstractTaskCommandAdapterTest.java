package org.gs4tr.globallink.adaptors.aem.connector.test.webmvc.command.adapter;

import static org.mockito.Mockito.mock;

import org.apache.sling.api.SlingHttpServletRequest;

import org.junit.BeforeClass;

public abstract class AbstractTaskCommandAdapterTest {
    
    protected static SlingHttpServletRequest request;
    
    @BeforeClass
    public static void initializeTests() {
		
	request = mock(SlingHttpServletRequest.class);
    }
    
}