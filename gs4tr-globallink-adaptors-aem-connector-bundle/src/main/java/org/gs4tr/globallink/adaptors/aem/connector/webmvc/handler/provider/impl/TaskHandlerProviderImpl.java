package org.gs4tr.globallink.adaptors.aem.connector.webmvc.handler.provider.impl;

import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.Service;
import org.gs4tr.globallink.adaptors.aem.connector.util.constraints.HandlerConstraints;
import org.gs4tr.globallink.adaptors.aem.connector.webmvc.handler.TaskHandler;
import org.gs4tr.globallink.adaptors.aem.connector.webmvc.handler.provider.TaskHandlerProvider;

@Component(immediate = true)
@Service(value = TaskHandlerProvider.class)
public class TaskHandlerProviderImpl implements TaskHandlerProvider {

    @Reference(target = HandlerConstraints.DELETE_CONFIG_TARGET)
    private TaskHandler deleteConfigTaskHandler;
    
    @Reference(target = HandlerConstraints.PULL_USER_PROJECTS_TARGET)
    private TaskHandler pullUserProjectsTaskHandler;
    
    @Reference(target = HandlerConstraints.SAVE_CONNECTION_CONFIG_TARGET)
    private TaskHandler saveConnectionConfigTaskHandler;
    
    public TaskHandler getTaskHandler(String taskName) {
	
	if (HandlerConstraints.DELETE_CONFIG_TASK_NAME.equals(taskName)) {
	    return deleteConfigTaskHandler;
	}
	
	if (HandlerConstraints.PULL_USER_PROJECTS_TASK_NAME.equals(taskName)) {
	    return pullUserProjectsTaskHandler;	
	}
	
	if (HandlerConstraints.SAVE_CONNECTION_CONFIG_TASK_NAME.equals(taskName)) {
	    return saveConnectionConfigTaskHandler;
	}
	
	return null;
    }

}