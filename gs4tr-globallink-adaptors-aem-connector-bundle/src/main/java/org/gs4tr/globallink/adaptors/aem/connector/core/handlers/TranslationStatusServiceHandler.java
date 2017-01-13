package org.gs4tr.globallink.adaptors.aem.connector.core.handlers;

import org.gs4tr.projectdirector.ws.client.SubmissionServiceImpl;
import org.gs4tr.projectdirector.ws.dto.ItemStatusEnum;
import org.gs4tr.projectdirector.ws.dto.Submission;

public abstract class TranslationStatusServiceHandler {
    
    public Boolean isTranslationJobExists(String pdJobTicket,
	    SubmissionServiceImpl pdSubmissionService) {
	
	Submission pdSubmission = pdSubmissionService.findByTicket(pdJobTicket);
	if (pdSubmission != null) {
	    
	    return Boolean.TRUE;
	}
	
	return Boolean.FALSE;
    }
    
    public Boolean isTranslationJobStarted(String pdJobTicket,
	    SubmissionServiceImpl pdSubmissionService) {
	
	Submission pdSubmission = pdSubmissionService.findByTicket(pdJobTicket);
	if (pdSubmission != null) {
	
	    if (!pdSubmission.getStatus().equals(ItemStatusEnum.CREATING)) {
	
		return Boolean.TRUE;
	    }
	}
	
	return Boolean.FALSE;
    }
        
}