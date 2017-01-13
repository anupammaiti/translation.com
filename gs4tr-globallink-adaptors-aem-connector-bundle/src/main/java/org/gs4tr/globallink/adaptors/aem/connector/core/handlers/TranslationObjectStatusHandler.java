package org.gs4tr.globallink.adaptors.aem.connector.core.handlers;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.sling.api.resource.ResourceResolver;
import org.gs4tr.globallink.adaptors.aem.connector.core.config.TranslationProviderConfig;
import org.gs4tr.projectdirector.ws.client.TargetServiceImpl;
import org.gs4tr.projectdirector.ws.dto.Target;

import com.adobe.granite.translation.api.TranslationConstants.TranslationStatus;
import com.adobe.granite.translation.api.TranslationObject;

public class TranslationObjectStatusHandler {

    private static final Log LOGGER = LogFactory.getLog(TranslationObjectStatusHandler.class);
    
    public TranslationStatus handle(String jobId,
	    TranslationObject object, TranslationStatus objectStatus,
	    TranslationProviderConfig pdConfig,
	    ResourceResolver resolver) {
	
	if (TranslationStatus.READY_FOR_REVIEW.equals(objectStatus)) {
	    
	    return handleReadyForReview(object, pdConfig);
	}
	
	return objectStatus;
    }
    
    private TranslationStatus handleReadyForReview(TranslationObject object,
	    TranslationProviderConfig pdConfig) {
	
	LOGGER.info("Trying to send download confirmation...");
	
	String objectId = object.getId();
	
	if (objectId != null && !objectId.isEmpty()) {
	    
	    TargetServiceImpl pdTargetService = pdConfig.adaptTo(TargetServiceImpl.class);
	    
	    String[] pdTargetTickets = new String[] { objectId };
	    
	    Target[] pdTargets = pdTargetService.getCompletedTargetsByDocuments(pdTargetTickets, 1);
	    
	    if (pdTargets != null && pdTargets.length > 0) {
		
		pdTargetService.sendDownloadConfirmation(pdTargets[0].getTicket());
		
		LOGGER.info("Download confirmation successfully sent.");
		
		return TranslationStatus.READY_FOR_REVIEW;
	    
	    } else {
		
		LOGGER.warn("Unable to find target.");
	    }
	}
	
	LOGGER.warn("Unable to send download confirmation.");
	
	return TranslationStatus.ERROR_UPDATE;
    }
    
}