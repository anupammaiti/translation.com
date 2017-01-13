package org.gs4tr.globallink.adaptors.aem.connector.core.handlers;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.sling.api.resource.PersistenceException;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
import org.gs4tr.globallink.adaptors.aem.connector.core.config.TranslationProviderConfig;
import org.gs4tr.globallink.adaptors.aem.connector.core.util.TranslationContraints;
import org.gs4tr.globallink.adaptors.aem.connector.i18n.Messages;
import org.gs4tr.globallink.adaptors.aem.connector.util.constraints.StringConstraints;
import org.gs4tr.projectdirector.ws.client.SubmissionServiceImpl;

import com.adobe.granite.translation.api.TranslationConstants.TranslationStatus;

public class TranslationJobStatusHandler {
    
    private static final Log LOGGER = LogFactory.getLog(TranslationJobStatusHandler.class);
    
    public Resource getJobReference(String jobId, ResourceResolver resolver) {
	
	String jobReferencePath = StringConstraints.SUBMISSION_PATH_PREFIX.concat(jobId);
	
	return resolver.getResource(jobReferencePath);
    }
    
    public TranslationStatus handle(String jobId, TranslationStatus jobStatus,
	    TranslationProviderConfig pdConfig,
	    ResourceResolver resolver) {
	
	if (TranslationStatus.ARCHIVE.equals(jobStatus)) {
	
	    return handleArchive(jobId, pdConfig, resolver);
	
	} else if (TranslationStatus.CANCEL.equals(jobStatus)) {
	
	    return handleCancel(jobId, pdConfig, resolver);
	}
		
	return jobStatus;
    }
    
    private TranslationStatus handleArchive(String jobId,
	    TranslationProviderConfig pdConfig,
	    ResourceResolver resolver) {

	LOGGER.info(String.format(
		Messages.getString("TranslationJobStatusHandler.HandleArchive.0"),
		String.valueOf(jobId)));
	
	Resource jobReference = getJobReference(jobId, resolver);
	if (jobReference != null) {
	
	    try {
		
		LOGGER.info(String.format(
			Messages.getString("TranslationJobStatusHandler.HandleArchive.1"),
			String.valueOf(jobId)));
		
		resolver.delete(jobReference);
		resolver.commit();
		
		LOGGER.info(String.format(
			Messages.getString("TranslationJobStatusHandler.HandleArchive.2"),
			String.valueOf(jobId)));
		
		LOGGER.info(String.format(
			Messages.getString("TranslationJobStatusHandler.HandleArchive.3"),
			String.valueOf(jobId)));
		
		return TranslationStatus.ARCHIVE;
	    
	    } catch (PersistenceException exception) {
	    
		LOGGER.error(String.format(
			Messages.getString("TranslationJobStatusHandler.HandleArchive.4"),
			String.valueOf(jobId)));
	    }
	}
	
	return TranslationStatus.ERROR_UPDATE;
    }
    
    private TranslationStatus handleCancel(String jobId,
	    TranslationProviderConfig pdConfig,
	    ResourceResolver resolver) {
	
	LOGGER.info(String.format(
		Messages.getString("TranslationJobStatusHandler.HandleCancel.0"),
		String.valueOf(jobId)));
	
	Resource jobReference = getJobReference(jobId, resolver);
	if (jobReference != null) {
	    
	    ValueMap properties = jobReference.adaptTo(ValueMap.class);
	    if (properties.containsKey(TranslationContraints.TRANSLATION_OBJECT_EXTERNAL_ID)) {
		
		String jobExternalId = properties.get(
			TranslationContraints.TRANSLATION_OBJECT_EXTERNAL_ID,
			String.class);
		
		if (jobExternalId != null && !jobExternalId.isEmpty()) {
		    
		    SubmissionServiceImpl pdSubmissionService = pdConfig.adaptTo(SubmissionServiceImpl.class);
		    if (pdSubmissionService != null) {
			
			try {
			    
			    pdSubmissionService.cancelSubmission(jobExternalId);
			    
			    LOGGER.info(String.format(
				    Messages.getString("TranslationJobStatusHandler.HandleCancel.1"),
				    String.valueOf(jobId)));
			    
			    return TranslationStatus.CANCEL;
			    
			} catch (Exception exception) {
			    LOGGER.error(exception.getMessage(), exception);
			}
		    }
		}
	    }
	}
	
	return TranslationStatus.ERROR_UPDATE;
    }
            
}