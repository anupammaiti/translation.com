package org.gs4tr.globallink.adaptors.aem.connector.webmvc.handler.command;

import org.apache.sling.api.resource.ResourceResolver;

public interface TaskCommand {
    
    void setResourceResolver(ResourceResolver resourceResolver);
    
}