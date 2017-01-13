package org.gs4tr.globallink.adaptors.aem.connector.webmvc.handler.command.adapter.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.sling.api.SlingHttpServletRequest;
import org.gs4tr.globallink.adaptors.aem.connector.webmvc.handler.command.AbstractTaskCommand;
import org.gs4tr.globallink.adaptors.aem.connector.webmvc.handler.command.adapter.AbstractTaskCommandAdapter;
import org.gs4tr.globallink.adaptors.aem.connector.webmvc.handler.command.impl.DeleteConfigTaskCommand;

public class DeleteConfigTaskCommandAdapter extends AbstractTaskCommandAdapter {
    
    private static final Log LOGGER = LogFactory.getLog(DeleteConfigTaskCommandAdapter.class);
    
    @Override
    @SuppressWarnings("unchecked")
    public <T extends AbstractTaskCommand> T adaptFromServletRequest(SlingHttpServletRequest request) {
	
	if (request != null) {
	    
	    DeleteConfigTaskCommand command = new DeleteConfigTaskCommand();
	    
	    command.setConfigPath(request.getParameter(DeleteConfigTaskCommand.CONFIG_PATH));
	    
	    return (T) command;
	}

	return null;
    }

    @Override
    protected Log getLogger() {
	return LOGGER;
    }

}
