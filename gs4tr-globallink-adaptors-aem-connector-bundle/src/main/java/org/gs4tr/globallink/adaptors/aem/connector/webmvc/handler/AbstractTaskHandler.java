package org.gs4tr.globallink.adaptors.aem.connector.webmvc.handler;

public abstract class AbstractTaskHandler implements TaskHandler {

    private static final String CONTENT_DISPOSITION = "";
    
    private static final String CONTENT_TYPE = "text/html; charset=UTF-8";
    
    
    public String getResponseContentDisposition() {
	return CONTENT_DISPOSITION;
    }

    
    public String getResponseContentType() {
	return CONTENT_TYPE;
    }
    
}