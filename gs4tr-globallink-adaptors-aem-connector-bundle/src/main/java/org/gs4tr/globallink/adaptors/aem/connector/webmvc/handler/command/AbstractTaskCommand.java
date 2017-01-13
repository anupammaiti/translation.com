package org.gs4tr.globallink.adaptors.aem.connector.webmvc.handler.command;

import org.apache.sling.api.resource.ResourceResolver;

public abstract class AbstractTaskCommand implements TaskCommand {
    
    private ResourceResolver _resourceResolver;

    public ResourceResolver getResourceResolver() {
        return _resourceResolver;
    }

    
    public void setResourceResolver(ResourceResolver resourceResolver) {
        _resourceResolver = resourceResolver;
    }
    
}