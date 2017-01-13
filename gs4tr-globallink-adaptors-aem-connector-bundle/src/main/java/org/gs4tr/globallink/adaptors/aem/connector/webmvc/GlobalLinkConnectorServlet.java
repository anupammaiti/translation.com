package org.gs4tr.globallink.adaptors.aem.connector.webmvc;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.sling.SlingServlet;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.gs4tr.globallink.adaptors.aem.connector.util.constraints.HandlerConstraints;
import org.gs4tr.globallink.adaptors.aem.connector.webmvc.handler.TaskHandler;
import org.gs4tr.globallink.adaptors.aem.connector.webmvc.handler.command.TaskCommand;
import org.gs4tr.globallink.adaptors.aem.connector.webmvc.handler.provider.TaskHandlerProvider;

@SlingServlet(metatype = true, methods = { "POST" }, paths = "/bin/globallink-connector")
public class GlobalLinkConnectorServlet extends SlingAllMethodsServlet {

    private static final long serialVersionUID = -6867058534013832557L;
    
    private static final Log LOGGER = LogFactory.getLog(GlobalLinkConnectorServlet.class);

    private static final String CONTENT_DISPOSITION = "Content-Disposition";
    	    
    @Reference
    private TaskHandlerProvider taskHandlerProvider;
    
    @Override
    protected void doPost(SlingHttpServletRequest request, SlingHttpServletResponse response) {
	
	try {
	    
	    String taskName = request.getParameter(HandlerConstraints.TASK_NAME);
	    if (taskName != null && !taskName.isEmpty()) {
		
		TaskHandler taskHandler = taskHandlerProvider.getTaskHandler(taskName);
		if (taskHandler != null) {
		    
		    TaskCommand taskCommand = request.adaptTo(taskHandler.getTaskCommandClass());
		    if (taskCommand != null) {
			
			setResponseContentDisposition(taskHandler, response);
			
			setResponseContentType(taskHandler, response);
			
			String taskHandlerResponse = taskHandler.doTask(taskCommand);
			if (taskHandlerResponse != null) {
			    
			    response.getWriter().write(taskHandlerResponse);
			}
		    }
		}
	    }
	
	} catch (Exception exception) {
	    LOGGER.error(exception.getMessage(), exception);
	}
    }

    private void setResponseContentDisposition(TaskHandler taskHandler,
	    SlingHttpServletResponse response) {
	
	String disposition = taskHandler.getResponseContentDisposition();	
	if (disposition != null && !disposition.isEmpty()) {
	    
	    response.setHeader(CONTENT_DISPOSITION, disposition);
	}
    }
    
    private void setResponseContentType(TaskHandler taskHandler,
	    SlingHttpServletResponse response) {
	
	String type = taskHandler.getResponseContentType();	
	if (type != null && !type.isEmpty()) {
	    
	    response.setContentType(taskHandler.getResponseContentType());
	}
    }
    
}