package org.gs4tr.globallink.adaptors.aem.connector.core.util;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.sling.api.resource.ResourceResolver;
import org.gs4tr.projectdirector.ws.client.SubmissionServiceImpl;
import org.gs4tr.projectdirector.ws.client.TargetServiceImpl;
import org.gs4tr.projectdirector.ws.dto.ItemStatusEnum;
import org.gs4tr.projectdirector.ws.dto.Submission;
import org.gs4tr.projectdirector.ws.dto.Target;

import com.adobe.granite.translation.api.TranslationConstants.TranslationStatus;

public class TranslationJobStatusHelper {
    
    private static final Log LOGGER = LogFactory.getLog(TranslationJobStatusHelper.class);
    
    private static final Map<ItemStatusEnum, TranslationStatus> EXTERNAL_STATUS_MAPPING;
    
    static {
	EXTERNAL_STATUS_MAPPING = new HashMap<ItemStatusEnum, TranslationStatus>();
	EXTERNAL_STATUS_MAPPING.put(ItemStatusEnum.ARCHIVED, TranslationStatus.ARCHIVE);
	EXTERNAL_STATUS_MAPPING.put(ItemStatusEnum.CANCELLED, TranslationStatus.CANCEL);
	EXTERNAL_STATUS_MAPPING.put(ItemStatusEnum.COMPLETED, TranslationStatus.TRANSLATED);
	EXTERNAL_STATUS_MAPPING.put(ItemStatusEnum.DELIVERED, TranslationStatus.READY_FOR_REVIEW);
	EXTERNAL_STATUS_MAPPING.put(ItemStatusEnum.DOWNLOADED, TranslationStatus.READY_FOR_REVIEW);
	EXTERNAL_STATUS_MAPPING.put(ItemStatusEnum.FAILED, TranslationStatus.UNKNOWN_STATE);
	EXTERNAL_STATUS_MAPPING.put(ItemStatusEnum.IN_PROCESS, TranslationStatus.TRANSLATION_IN_PROGRESS);
	EXTERNAL_STATUS_MAPPING.put(ItemStatusEnum.PROCESSING, TranslationStatus.TRANSLATION_IN_PROGRESS);
	EXTERNAL_STATUS_MAPPING.put(ItemStatusEnum.PROCESSED, TranslationStatus.TRANSLATED);
	EXTERNAL_STATUS_MAPPING.put(ItemStatusEnum.WAITING, TranslationStatus.COMMITTED_FOR_TRANSLATION);
    }
    
    public static TranslationStatus getTranslationObjectStatus(String objectId, TargetServiceImpl pdService) {
	
	// TODO Delete this
	try { Thread.sleep(2000L); } catch (InterruptedException exception) {}
	
	LOGGER.info("Trying to get completed targets for document: " + objectId);
	
	Target[] pdTargets = pdService.getCompletedTargetsByDocuments(
		new String[] { objectId },
		Integer.MAX_VALUE);
	
	if (pdTargets != null && pdTargets.length > 0) {
	    return EXTERNAL_STATUS_MAPPING.get(pdTargets[0].getStatus());
	}
	
	LOGGER.error("Unable to find submission target with ID: " + String.valueOf(objectId));
	
	return null;
    }
    
    public static TranslationStatus getTranslationJobStatus(String jobId, SubmissionServiceImpl pdService,
	    ResourceResolver resolver) {
	
	try {
	
	    String pdTicket = TranslationReferenceUtil.getExternalJobId(jobId, resolver);
	
	    Submission pdSubmision = pdService.findByTicket(pdTicket);	    
	    if (pdSubmision != null) {
		
		return EXTERNAL_STATUS_MAPPING.get(pdSubmision.getStatus());
	    }
	    
	} catch (Exception exception) {
	    LOGGER.error(exception.getMessage(), exception);
	}
	
	return TranslationStatus.UNKNOWN_STATE;
    }
    
    public static Boolean isTranslationJobExists(String jobId, SubmissionServiceImpl pdService,
	    ResourceResolver resolver) {
	
	try {
	    
	    String pdTicket = TranslationReferenceUtil.getExternalJobId(jobId, resolver);
	    if (pdTicket == null) {
		
		return Boolean.FALSE;	
	    }
	    
	    Submission pdSubmission = pdService.findByTicket(pdTicket);
	    if (pdSubmission != null
		    && pdSubmission.getStatus().equals(ItemStatusEnum.CREATING)) {
		
		return Boolean.TRUE;
	    }
	    
	} catch (Exception exception) {
	    LOGGER.error(exception.getMessage(), exception);
	}
	
	return Boolean.FALSE;
    }
    
}