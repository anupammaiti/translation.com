package org.gs4tr.globallink.adaptors.aem.connector.webmvc.handler.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Properties;
import org.apache.felix.scr.annotations.Property;
import org.apache.felix.scr.annotations.Service;
import org.apache.sling.commons.json.JSONArray;
import org.apache.sling.commons.json.JSONException;
import org.apache.sling.commons.json.JSONObject;
import org.gs4tr.globallink.adaptors.aem.connector.i18n.Messages;
import org.gs4tr.globallink.adaptors.aem.connector.model.AemConnection;
import org.gs4tr.globallink.adaptors.aem.connector.util.constraints.HandlerConstraints;
import org.gs4tr.globallink.adaptors.aem.connector.webmvc.handler.AbstractTaskHandler;
import org.gs4tr.globallink.adaptors.aem.connector.webmvc.handler.TaskHandler;
import org.gs4tr.globallink.adaptors.aem.connector.webmvc.handler.command.AbstractTaskCommand;
import org.gs4tr.globallink.adaptors.aem.connector.webmvc.handler.command.TaskCommand;
import org.gs4tr.globallink.adaptors.aem.connector.webmvc.handler.command.impl.PullUserProjectsTaskCommand;
import org.gs4tr.projectdirector.ws.client.ProjectServiceImpl;
import org.gs4tr.projectdirector.ws.client.ServiceLocator;
import org.gs4tr.projectdirector.ws.dto.Project;

@Component(immediate = true)
@Service(value = TaskHandler.class)
@Properties({
    @Property(name = HandlerConstraints.TASK_NAME,
	    value = HandlerConstraints.PULL_USER_PROJECTS_TASK_NAME)})
public class PullUserProjectsTaskHandler extends AbstractTaskHandler {

    private static final Log LOGGER = LogFactory.getLog(PullUserProjectsTaskHandler.class);
        
    private static final String PROJECT_NAME = "text";
    
    private static final String PROJECT_SHORT_CODE = "value";
    
    public String doTask(TaskCommand taskCommand) {
	
	LOGGER.info(Messages.getString("PullUserProjectsTaskHandler.0"));
	return (new JSONArray()).toString();
//	if (taskCommand == null) {
//	    return null;
//	}
//	
//	AemConnection connection = ((PullUserProjectsTaskCommand) taskCommand).getConnection();
//	if (connection != null) {
//	
//	    Project[] pdUserProjects = getUserProjects(connection);
//	    if (pdUserProjects != null && pdUserProjects.length > 0) {
//		
//		JSONArray projectInfos = new JSONArray();
//		
//		for (Project userProject : pdUserProjects) {
//		
//		    try {
//			
//			JSONObject projectInfo = getUserProjectInfo(userProject);
//			projectInfos.put(projectInfo);
//		    
//		    } catch (JSONException exception) {
//			LOGGER.error(exception.getMessage(), exception);
//		    }
//		}
//		
//		return projectInfos.toString();
//	    }
//	}
//
//	return null;
    }
    
    private Project[] getUserProjects(AemConnection connection) {
	
	ServiceLocator serviceLocator = connection.adaptTo(ServiceLocator.class);
	
	ProjectServiceImpl projectService = serviceLocator.getProjectService();
	
	return projectService.getUserProjects(Boolean.FALSE);
    }
    
    private JSONObject getUserProjectInfo(Project project) throws JSONException {
	
	JSONObject projectInfo = new JSONObject();
	
	String projectName = project.getProjectInfo().getName();
	String projectShortCode = project.getProjectInfo().getShortCode();
	
	projectInfo.put(PROJECT_NAME, projectName);
	projectInfo.put(PROJECT_SHORT_CODE, projectShortCode);
			
	return projectInfo;
    }
    
    public Class<? extends AbstractTaskCommand> getTaskCommandClass() {
	return PullUserProjectsTaskCommand.class;
    }
        
}