package org.gs4tr.globallink.adaptors.aem.connector.core.util;

import javax.jcr.RepositoryException;

import org.apache.sling.api.resource.ResourceResolver;
import org.gs4tr.globallink.adaptors.aem.connector.core.config.TranslationProviderConfig;
import org.gs4tr.globallink.adaptors.aem.connector.i18n.Messages;
import org.gs4tr.globallink.adaptors.aem.connector.util.constraints.StringConstraints;
import org.gs4tr.projectdirector.ws.client.ProjectServiceImpl;
import org.gs4tr.projectdirector.ws.client.SubmissionServiceImpl;
import org.gs4tr.projectdirector.ws.dto.Date;
import org.gs4tr.projectdirector.ws.dto.Project;
import org.gs4tr.projectdirector.ws.dto.SubmissionInfo;

import com.adobe.granite.translation.api.TranslationException;
import com.adobe.granite.translation.api.TranslationException.ErrorCode;

public class TranslationProviderUtil {
    
    private static final String FILE_NAME_REGEX = "[^\\p{L}\\p{Nd}]+";
    
    private static final String FILE_NAME_REPLACER = "-";
    
    private static final String FILE_NAME_UNTITLED = "Untitled";
    
    public static String createDocumentName(String title) {
	
	String name = FILE_NAME_UNTITLED;
	
	if (title != null && !title.isEmpty()) {
	    
	    name = title.replaceAll(FILE_NAME_REGEX, FILE_NAME_REPLACER);
	}
	
	return name.concat(StringConstraints.XML_SUFFIX);
    }
    
    public static String startSubmission(String jobId, TranslationProviderConfig pdConfig,
	    ResourceResolver resolver) throws RepositoryException,
	    TranslationException {
	
	SubmissionInfo pdSubmInfo = new SubmissionInfo();
	
	String pdProjectTicket = findProjectTicket(pdConfig);
	pdSubmInfo.setProjectTicket(pdProjectTicket);
	
	String pdSubmName = TranslationProjectUtil.getProjectTitle(jobId, resolver);
	pdSubmInfo.setName(pdSubmName);
	
	Date jobDueDate = TranslationJobUtil.getDueDate(jobId, resolver);
	pdSubmInfo.setDateRequested(jobDueDate);

	if (pdSubmInfo.getDateRequested() == null) {
	
	    Date proDueDate = TranslationProjectUtil.getProjectDueDate(jobId, resolver);
	    pdSubmInfo.setDateRequested(proDueDate);
	}
	
	String pdSubmTicket = TranslationReferenceUtil.getExternalJobId(jobId, resolver);
	
	SubmissionServiceImpl pdService = pdConfig.adaptTo(SubmissionServiceImpl.class);	
	return pdService.startSubmission(pdSubmTicket, pdSubmInfo);	
    }
            
    public static String findProjectTicket(TranslationProviderConfig config)
	    throws TranslationException {
	
	ProjectServiceImpl pdProjectService = config.adaptTo(ProjectServiceImpl.class);
	
	String pdProjectShortCode = config.getProjectShortCode();
	if (pdProjectShortCode != null && !pdProjectShortCode.isEmpty()) {
	
	    Project pdProject = pdProjectService.findProjectByShortCode(pdProjectShortCode);
	    if (pdProject != null) {
		
		return pdProject.getTicket();
	    }
	    
	    throw new TranslationException(
		    Messages.getString("TranslationProviderUtil.Error.2"),
		    ErrorCode.GENERAL_EXCEPTION);
	}
	
	throw new TranslationException(
		Messages.getString("TranslationProviderUtil.Error.1"),
		ErrorCode.MISSING_PARAMETER);
    }
    
}