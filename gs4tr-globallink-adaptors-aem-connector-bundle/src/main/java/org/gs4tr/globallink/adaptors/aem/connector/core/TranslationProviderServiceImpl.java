package org.gs4tr.globallink.adaptors.aem.connector.core;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;

import javax.jcr.RepositoryException;

import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.felix.scr.annotations.Component;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
import org.gs4tr.globallink.adaptors.aem.connector.core.config.TranslationProviderConfig;
import org.gs4tr.globallink.adaptors.aem.connector.core.util.TranslationContraints;
import org.gs4tr.globallink.adaptors.aem.connector.core.util.TranslationJobUtil;
import org.gs4tr.globallink.adaptors.aem.connector.core.util.TranslationProviderUtil;
import org.gs4tr.globallink.adaptors.aem.connector.core.util.TranslationReferenceUtil;
import org.gs4tr.globallink.adaptors.aem.connector.i18n.Messages;
import org.gs4tr.globallink.adaptors.aem.connector.util.constraints.PropertyConstraints;
import org.gs4tr.globallink.adaptors.aem.connector.util.constraints.StringConstraints;
import org.gs4tr.projectdirector.ws.client.ProjectServiceImpl;
import org.gs4tr.projectdirector.ws.client.SubmissionServiceImpl;
import org.gs4tr.projectdirector.ws.client.TargetServiceImpl;
import org.gs4tr.projectdirector.ws.dto.ClaimScopeEnum;
import org.gs4tr.projectdirector.ws.dto.DocumentInfo;
import org.gs4tr.projectdirector.ws.dto.FileFormatProfile;
import org.gs4tr.projectdirector.ws.dto.ItemStatusEnum;
import org.gs4tr.projectdirector.ws.dto.Project;
import org.gs4tr.projectdirector.ws.dto.RepositoryItem;
import org.gs4tr.projectdirector.ws.dto.ResourceInfo;
import org.gs4tr.projectdirector.ws.dto.Submission;
import org.gs4tr.projectdirector.ws.dto.SubmissionInfo;
import org.gs4tr.projectdirector.ws.dto.Target;
import org.gs4tr.projectdirector.ws.dto.TargetInfo;

import com.adobe.granite.translation.api.TranslationConfig;
import com.adobe.granite.translation.api.TranslationConstants.TranslationMethod;
import com.adobe.granite.translation.api.TranslationConstants.TranslationStatus;
import com.adobe.granite.translation.api.TranslationException;
import com.adobe.granite.translation.api.TranslationObject;
import com.adobe.granite.translation.core.common.AbstractTranslationService;

@Component(componentAbstract = true)
public abstract class TranslationProviderServiceImpl extends AbstractTranslationService {
    
    protected TranslationProviderServiceImpl(
	    Map<String, String> languages, Map<String, String> categories,
	    String name, String label, String attribution,
	    String configPath, TranslationMethod method, TranslationConfig config) {
    
	super(languages, categories, name, label, attribution, configPath, method, config);
    }

    protected DocumentInfo createDocumentInfo(TranslationProviderConfig pdConfig, String srcLang, String tgtLang)
	    throws TranslationException {
	
	String pdProjectTicket = TranslationProviderUtil.findProjectTicket(pdConfig);
	if (pdProjectTicket != null) {
	    
	    DocumentInfo pdDocumentInfo = new DocumentInfo();
	    	    
	    pdDocumentInfo.setProjectTicket(pdProjectTicket);
	    
	    String srcLocale = getLocale(srcLang).toLanguageTag();
	    pdDocumentInfo.setSourceLocale(srcLocale);
	    
	    String tgtLocale = getLocale(tgtLang).toLanguageTag();
	    pdDocumentInfo.setTargetInfos(new TargetInfo[] { createTargetInfo(tgtLocale) });
	    	    	    
	    return pdDocumentInfo;
	}
	
	return null;
    }
        
    protected ResourceInfo createResourceInfo(TranslationProviderConfig pdConfig, TranslationObject object)
	    throws IOException, TranslationException {
	
	ResourceInfo resourceInfo = new ResourceInfo();
	
	resourceInfo.setEncoding(StringConstraints.UTF_8);
	resourceInfo.setSize(getObjectSize(object));
	
	String objectMimeType = object.getMimeType();
	
	String classifier = pdConfig.getClassifier(objectMimeType);
	
	if (isMimeTypeParsable(objectMimeType) && isClassifierConfigured(classifier)) {
		
	    getLogger().info(Messages
		    .getString("TranslationServiceImpl.pdClassifier")
		    .concat(classifier));
	    
	    resourceInfo.setClassifier(classifier);
	    resourceInfo.setName(TranslationProviderUtil.createDocumentName(object.getTitle()));
	    
	} else {
	    
	    if (isMimeTypeParsable(objectMimeType)) {
		
		getLogger().info(Messages
			.getString("TranslationServiceImpl.pdMimeType")
			.concat(objectMimeType));
		
		resourceInfo.setMimeType(objectMimeType);
		resourceInfo.setName(TranslationProviderUtil.createDocumentName(object.getTitle()));
				
	    } else {
	    
		getLogger().info(Messages
			.getString("TranslationServiceImpl.pdMimeType")
			.concat(StringConstraints.MIME_TYPE_APPLICATION_OCTECT_STREAM));
		
		resourceInfo.setMimeType(StringConstraints.MIME_TYPE_APPLICATION_OCTECT_STREAM);
		
		resourceInfo.setName(object.getTitle().replaceAll(
			TranslationContraints.INVALID_CHARACTERS,
			TranslationContraints.INVALID_CHARACTER_REPLACER));
	    }
	}
		
	return resourceInfo;
    }
    
    protected SubmissionInfo createSubmissionInfo(String jobId, String pdProjectTicket) {
	
	SubmissionInfo pdSubmissionInfo = new SubmissionInfo();
	
	pdSubmissionInfo.setProjectTicket(pdProjectTicket);
	pdSubmissionInfo.setClaimScope(ClaimScopeEnum.FILE);
		
	return pdSubmissionInfo;
    }
    
    protected TargetInfo createTargetInfo(String tgtLang) {
	
	TargetInfo pdTargetInfo = new TargetInfo();
		
	pdTargetInfo.setTargetLocale(tgtLang);
	
	return pdTargetInfo;
    }
    
    protected FileFormatProfile[] getFileFormatProfiles(TranslationProviderConfig config) {
	
	if (config != null) {
	    
	    ProjectServiceImpl service = config.adaptTo(ProjectServiceImpl.class);
	    
	    Project project = service.findProjectByShortCode(config.getProjectShortCode());
	    if (project != null) {
		
		return project.getFileFormatProfiles();
	    }
	}
	
	return null;
    }
        
    protected Locale getLocale(String language) {
	
	if (language.length() == 2) {
	    return new Locale(language);
	}
	
	if (language.length() == 5) {
	    return new Locale(language.substring(0, 2), language.substring(3));
	}

	return null;
    }
    
    protected abstract Log getLogger();

    protected String getMimeType(String mimeType, TranslationProviderConfig config) {
	
	if (isMimeTypeConfigured(mimeType, config)) {
	    
	    return mimeType;
	}
	
	return StringConstraints.MIME_TYPE_APPLICATION_OCTECT_STREAM;
    }
    
    @SuppressWarnings("resource")
    protected Long getObjectSize(TranslationObject object) throws IOException, TranslationException {
	
	// TODO Change this
	@SuppressWarnings("deprecation")
	InputStream inputStream = object.getMimeType().startsWith("text")
		    ? object.getTranslationObjectXMLInputStream()
			    : object.getTranslationObjectInputStream();
	
	Long inputStreamSize = Long.valueOf(IOUtils.toByteArray(inputStream).length);
	
	getLogger().info(Messages
		.getString("TranslationServiceImpl.pdObjectSize")
		.concat(String.valueOf(inputStreamSize)));
	
	return inputStreamSize;
    }
    
    protected String getProjectTicket(TranslationProviderConfig config) {
	
	if (config != null) {
	    
	    ProjectServiceImpl projectService = config.adaptTo(ProjectServiceImpl.class);
	    if (projectService != null) {
	    
		Project project = projectService.findProjectByShortCode(config.getProjectShortCode());
		if (project != null) {
		    		    
		    return project.getTicket();
		}
	    }
	}
	
	return null;
    }
    
    protected RepositoryItem getTargetRepositoryItem(String objectId, TranslationProviderConfig config) {
	
	// TODO Delete this
	try { Thread.sleep(2000L); } catch (InterruptedException exception) {}
	
	TargetServiceImpl pdService = config.adaptTo(TargetServiceImpl.class);
	
	Target[] targets = pdService.getCompletedTargetsByDocuments(new String[] { objectId }, Integer.MAX_VALUE);
	if (targets != null && targets.length > 0) {

	    return pdService.downloadTargetResource(targets[0].getTicket());
	}
	
	return null;
    }
    
    protected Boolean isClassifierConfigured(String pdClassifier) {
	return (pdClassifier != null && !pdClassifier.isEmpty()) ? Boolean.TRUE : Boolean.FALSE;
    }
    
    protected Boolean isMimeTypeConfigured(String mimeType, TranslationProviderConfig config) {
	
	if (mimeType != null && !mimeType.isEmpty()) {
	
	    FileFormatProfile[] pdProfiles = getFileFormatProfiles(config);
	    if (pdProfiles != null && pdProfiles.length > 0) {
	    
		for (FileFormatProfile pdProfile : pdProfiles) {
		
		    String pdMimeType = pdProfile.getMimeType();
		    if (pdMimeType != null && pdMimeType.equals(mimeType)) {
		    
			return Boolean.TRUE;
		    }
		}
	    }
	}
	
	return Boolean.FALSE;
    }
    
    protected Boolean isMimeTypeParsable(String mimeType) {
	
	if (mimeType != null && !mimeType.isEmpty()) {
	    
	    if (mimeType.equals(StringConstraints.MIME_TYPE_TEXT_HTML)
		    || mimeType.equals(StringConstraints.MIME_TYPE_TEXT_XML)) {
		
		return Boolean.TRUE;
	    }
	}
	
	return Boolean.FALSE;
    }
    
    protected Boolean isTranslationJobExists(String jobId, SubmissionServiceImpl service, ResourceResolver resolver)
	    throws RepositoryException {
	
	String jobExternalId = TranslationReferenceUtil.getExternalJobId(jobId, resolver);
	
	Submission submission = service.findByTicket(jobExternalId);
	if (submission != null) {
	    
	    return Boolean.TRUE;
	}
	
	return Boolean.FALSE;
    }
    
    protected Boolean isTranslationJobStarted(String jobId, SubmissionServiceImpl pdService, ResourceResolver resolver)
		    throws RepositoryException {
	
	if (isTranslationJobExists(jobId, pdService, resolver)) {
	
	    String jobExternalId = TranslationReferenceUtil.getExternalJobId(jobId, resolver);
	
	    Submission submission = pdService.findByTicket(jobExternalId);
	
	    if (!submission.getStatus().equals(ItemStatusEnum.CREATING)) {
	    
		return Boolean.TRUE;
	    }
	}
	
	return Boolean.FALSE;
    }
    
    protected Boolean isTranslationJobUploaded(String jobId, String objectId,
	    SubmissionServiceImpl pdService, ResourceResolver resolver)
		    throws RepositoryException {
		
	Resource jobResource = TranslationJobUtil.findResource(jobId, resolver);
	if (jobResource != null) {
	    
	    Iterator<Resource> iterator = jobResource
		    .getChild(StringConstraints.CHILD_PAGES)
		    .listChildren();
	    
	    while (iterator.hasNext()) {
		
		Resource objectResource = iterator.next();
		
		ValueMap objectProperties = objectResource.adaptTo(ValueMap.class);
		
		if (objectProperties.containsKey(
			PropertyConstraints.TRANSLATION_OBJECT_ID)
			&& objectProperties.get(
				PropertyConstraints.TRANSLATION_OBJECT_ID,
				String.class).equals(objectId)) {
		    
		    
		} else if (objectProperties.containsKey(PropertyConstraints.TRANSLATION_STATUS)) {
		    
		    String objectStatus = objectProperties
			    .get(PropertyConstraints.TRANSLATION_STATUS, String.class);
		    
		    if (objectStatus != null && !objectStatus.isEmpty()) {
			
			if (!TranslationStatus.COMMITTED_FOR_TRANSLATION
				.equals(TranslationStatus.fromString(objectStatus))) {
			    
			    return Boolean.FALSE;
			}
		    }
		}
	    }
	    
	    return Boolean.TRUE;
	}
	
	return Boolean.FALSE;
    }
    
}