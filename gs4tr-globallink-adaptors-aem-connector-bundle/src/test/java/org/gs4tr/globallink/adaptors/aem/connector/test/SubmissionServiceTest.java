package org.gs4tr.globallink.adaptors.aem.connector.test;

import java.net.URL;

import org.gs4tr.projectdirector.ws.client.ServiceLocator;
import org.gs4tr.projectdirector.ws.dto.Priority;
import org.gs4tr.projectdirector.ws.dto.Project;
import org.gs4tr.projectdirector.ws.dto.SubmissionInfo;

import org.junit.Ignore;
import org.junit.Test;

public class SubmissionServiceTest {
    
    @Ignore @Test
    public void start() throws Exception {
	
	String pdSubmissionTicket = "4YESyxwCtA1iuZXf28+A0k5Ze6Fev8K3";
	
	ServiceLocator pdServiceLocator = new ServiceLocator(
		new URL("http://172.16.10.199:8080/PD/services/"),
		30000, "day", "password1!", Boolean.FALSE);
	
	SubmissionInfo pdSubmissionInfo = new SubmissionInfo();
	
	pdSubmissionInfo.setName("Translation API Test 01");	
	pdSubmissionInfo.setPriority(Priority.HIGH);
	
	Project pdProject = pdServiceLocator.getProjectService().findProjectByShortCode("AEM000039");
	
	pdSubmissionInfo.setProjectTicket(pdProject.getTicket());
	
	pdServiceLocator.getSubmissionService().startSubmission(pdSubmissionTicket, pdSubmissionInfo);
    }
    
}