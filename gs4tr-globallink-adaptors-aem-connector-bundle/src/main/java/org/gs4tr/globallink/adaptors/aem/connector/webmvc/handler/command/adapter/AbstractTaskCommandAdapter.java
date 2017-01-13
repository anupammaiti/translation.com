package org.gs4tr.globallink.adaptors.aem.connector.webmvc.handler.command.adapter;

import org.apache.commons.logging.Log;
import org.apache.sling.api.SlingHttpServletRequest;
import org.gs4tr.globallink.adaptors.aem.connector.webmvc.handler.command.AbstractTaskCommand;

public abstract class AbstractTaskCommandAdapter {
    
    public abstract <T extends AbstractTaskCommand> T adaptFromServletRequest(SlingHttpServletRequest request);
    
    protected abstract Log getLogger();
    
}