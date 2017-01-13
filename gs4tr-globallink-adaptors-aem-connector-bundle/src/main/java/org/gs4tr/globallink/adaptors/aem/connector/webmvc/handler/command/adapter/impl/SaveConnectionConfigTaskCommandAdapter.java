package org.gs4tr.globallink.adaptors.aem.connector.webmvc.handler.command.adapter.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.sling.api.SlingHttpServletRequest;
import org.gs4tr.globallink.adaptors.aem.connector.webmvc.handler.command.AbstractTaskCommand;
import org.gs4tr.globallink.adaptors.aem.connector.webmvc.handler.command.adapter.AbstractConnectionAdapter;
import org.gs4tr.globallink.adaptors.aem.connector.webmvc.handler.command.impl.SaveConnectionConfigTaskCommand;

public class SaveConnectionConfigTaskCommandAdapter extends AbstractConnectionAdapter {
    
    private static final Log LOGGER = LogFactory.getLog(SaveConnectionConfigTaskCommandAdapter.class);
    
    @Override
    @SuppressWarnings("unchecked")
    public <T extends AbstractTaskCommand> T adaptFromServletRequest(SlingHttpServletRequest request) {
	
	if (request != null) {
	    
	    SaveConnectionConfigTaskCommand taskCommand = new SaveConnectionConfigTaskCommand();
	    
	    taskCommand.setConfigPath(request
		    .getParameter(SaveConnectionConfigTaskCommand.CONFIG_PATH));
	    
	    taskCommand.setUserProjects(request
		    .getParameter(SaveConnectionConfigTaskCommand.USER_PROJECTS));
	    
	    taskCommand.setConnection(adaptToConnection(request));
	    
	    return (T) taskCommand;
	}
	
	return null;
    }

    @Override
    protected Log getLogger() {
	return LOGGER;
    }

}