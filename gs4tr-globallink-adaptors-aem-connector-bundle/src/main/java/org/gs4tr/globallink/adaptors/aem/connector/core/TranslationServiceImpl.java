package org.gs4tr.globallink.adaptors.aem.connector.core;

import java.io.InputStream;
import java.util.Date;
import java.util.Map;

import javax.jcr.Node;
import javax.jcr.RepositoryException;
import javax.jcr.Session;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.felix.scr.annotations.Properties;
import org.apache.felix.scr.annotations.Property;
import org.apache.felix.scr.annotations.Service;
import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.sling.api.resource.ValueMap;
import org.gs4tr.globallink.adaptors.aem.connector.core.config.TranslationProviderConfig;
import org.gs4tr.globallink.adaptors.aem.connector.core.handlers.TranslationJobStatusHandler;
import org.gs4tr.globallink.adaptors.aem.connector.core.handlers.TranslationObjectStatusHandler;
import org.gs4tr.globallink.adaptors.aem.connector.core.util.TranslationContraints;
import org.gs4tr.globallink.adaptors.aem.connector.core.util.TranslationJobStatusHelper;
import org.gs4tr.globallink.adaptors.aem.connector.core.util.TranslationJobUtil;
import org.gs4tr.globallink.adaptors.aem.connector.core.util.TranslationProviderUtil;
import org.gs4tr.globallink.adaptors.aem.connector.core.util.TranslationReferenceUtil;
import org.gs4tr.globallink.adaptors.aem.connector.i18n.Messages;
import org.gs4tr.globallink.adaptors.aem.connector.util.constraints.StringConstraints;
import org.gs4tr.projectdirector.ws.client.DocumentServiceImpl;
import org.gs4tr.projectdirector.ws.client.SubmissionServiceImpl;
import org.gs4tr.projectdirector.ws.client.TargetServiceImpl;
import org.gs4tr.projectdirector.ws.dto.Batch;
import org.gs4tr.projectdirector.ws.dto.DocumentInfo;
import org.gs4tr.projectdirector.ws.dto.DocumentTicket;
import org.gs4tr.projectdirector.ws.dto.LanguagePhaseInfo;
import org.gs4tr.projectdirector.ws.dto.RepositoryItem;
import org.gs4tr.projectdirector.ws.dto.ResourceInfo;
import org.gs4tr.projectdirector.ws.dto.Submission;
import org.gs4tr.projectdirector.ws.dto.TmStatistics;

import com.adobe.granite.comments.Comment;
import com.adobe.granite.comments.CommentCollection;
import com.adobe.granite.translation.api.TranslationConfig;
import com.adobe.granite.translation.api.TranslationConstants.ContentType;
import com.adobe.granite.translation.api.TranslationConstants.TranslationMethod;
import com.adobe.granite.translation.api.TranslationConstants.TranslationStatus;
import com.adobe.granite.translation.api.TranslationException;
import com.adobe.granite.translation.api.TranslationException.ErrorCode;
import com.adobe.granite.translation.api.TranslationMetadata;
import com.adobe.granite.translation.api.TranslationObject;
import com.adobe.granite.translation.api.TranslationResult;
import com.adobe.granite.translation.api.TranslationScope;
import com.adobe.granite.translation.api.TranslationService;
import com.adobe.granite.translation.api.TranslationState;
import com.adobe.granite.translation.core.common.TranslationResultImpl;

@Service(value = TranslationService.class)
@Properties(value = { @Property(name = "translationService", value = "GlobalLink") })
public class TranslationServiceImpl extends TranslationProviderServiceImpl implements TranslationService {

    private static final Log LOGGER = LogFactory.getLog(TranslationServiceImpl.class);

    private static final String TRANSLATION_ATTRIBUTION = "Translations by GlobalLink";
    
    private static final String TRANSLATION_LABEL = "GlobalLink";
    
    private TranslationProviderConfig _translationProviderConfig;

    private ResourceResolverFactory _resourceResolverFactory;
    
    public TranslationServiceImpl(Map<String, String> languages, String name,
	    TranslationConfig config, TranslationProviderConfig tpConfig,
	    ResourceResolverFactory resourceResolverFactory) {

	super(languages, null, name, TRANSLATION_LABEL,
		TRANSLATION_ATTRIBUTION, StringConstraints.CQ_CLOUD_SERVICE_CONFIG_PATH,
		TranslationMethod.HUMAN_TRANSLATION, config);

	_translationProviderConfig = tpConfig;
	
	_resourceResolverFactory = resourceResolverFactory;
	
    }

    
    public void addTranslationJobComment(String jobId, Comment jobComment)
	    throws TranslationException {
	
	throw new TranslationException(
		Messages.getString("TranslationService.Error.0"),
		ErrorCode.SERVICE_NOT_IMPLEMENTED);
    }

    
    public void addTranslationObjectComment(String jobId,
	    TranslationObject object, Comment objectComment)
		    throws TranslationException {
	
	throw new TranslationException(
		Messages.getString("TranslationService.Error.1"),
		ErrorCode.SERVICE_NOT_IMPLEMENTED);
    }

    
    public String createTranslationJob(String jobName, String jobDesc,
	    String srcLang, String tgtLang, Date dueDate,
	    TranslationState jobState, TranslationMetadata jobMetadata)
		    throws TranslationException {
	
	LOGGER.info(String.format(
		Messages.getString("TranslationServiceImpl.0"),
		String.valueOf(jobName), String.valueOf(srcLang), String.valueOf(tgtLang),
		String.valueOf(jobState.getStatus().name())));

	ResourceResolver resolver = null;
	
	String jobId = null;

	try {

	    resolver = getResourceResolver();
	    
	    jobId = TranslationReferenceUtil.create(srcLang, tgtLang, resolver);

	} catch (Exception exception) {
	    throw new TranslationException(exception.getMessage(), ErrorCode.GENERAL_EXCEPTION);
	} finally {
	    if (resolver != null && resolver.isLive()) {
		resolver.close(); resolver = null;
	    }
	}
	
	return jobId;
    }

    
    public String detectLanguage(String srcText, ContentType cntType)
	    throws TranslationException {

	throw new TranslationException(
		Messages.getString("TranslationService.Error.2"),
		ErrorCode.SERVICE_NOT_IMPLEMENTED);
    }

    
    public TranslationResult[] getAllStoredTranslations(String srcString,
	    String srcLang, String tgtLang, ContentType cntType, String cntCategory,
	    String userId, int maxResults) throws TranslationException {
	
	throw new TranslationException(
		Messages.getString("TranslationService.Error.3"),
		ErrorCode.SERVICE_NOT_IMPLEMENTED);
    }

   

    
    protected Log getLogger() {
	return LOGGER;
    }

    public ResourceResolverFactory getResolverFactory() {
	return _resourceResolverFactory;
    }
    
    @SuppressWarnings("deprecation")
    public ResourceResolver getResourceResolver() throws LoginException {
	
	// TODO Check why Sling ServiceResourceResolver doesn't work in Sling 2.9
	
	/*	
	ResourceResolver resolver = null;
	
	try {
	    
	    LOGGER.debug("Trying to get service resource resolver...");
	    Map<String, Object> authInfo = new HashMap<String, Object>();
	    authInfo.put(ResourceResolverFactory.SUBSERVICE, "GlobalLinkTranslationService");
            resolver = getResolverFactory().getServiceResourceResolver(authInfo);
            LOGGER.debug("Service resource resolver successfully created.");

            return resolver;
            
	} catch (Exception exception) {
	    
	    LOGGER.warn("Unable to get service resource resolver.");
	    LOGGER.debug("Trying to get administrative resource resolver");
	    resolver = getResolverFactory().getAdministrativeResourceResolver(null);
	    LOGGER.debug("Administrative resource resolver successfully created.");
	    	    
	    return resolver;
	}
	*/
	
	return getResolverFactory().getAdministrativeResourceResolver(null);
    }

    
    public InputStream getTranslatedObject(String jobId, TranslationObject object)
	    throws TranslationException {

	String objectTitle = object.getTitle();
	
	LOGGER.info(String.format(
		Messages.getString("TranslationService.GetTranslatedObject.0"),
		String.valueOf(objectTitle)));

	String objectId = object.getId();
	if (objectId == null) {
	    
	    throw new TranslationException(
		    Messages.getString("TranslationService.GetTranslatedObject.6"),
		    ErrorCode.GENERAL_EXCEPTION);
	}
	
	TranslationProviderConfig config = getTranslationProviderConfig();
	
	RepositoryItem pdRepoItem = getTargetRepositoryItem(objectId, config);
	if (pdRepoItem != null) {
	
	    try {
		
		LOGGER.info(String.format(
			Messages.getString("TranslationService.GetTranslatedObject.1"),
			String.valueOf(objectTitle),
			String.valueOf(objectId)));
		
		InputStream pdRepoItemInputStream = pdRepoItem.getInputStream();
		
		LOGGER.info(Messages
			.getString("TranslationService.GetTranslatedObject.4")
			.concat(String.valueOf(pdRepoItemInputStream.available())));
		
		return pdRepoItemInputStream;
		
	    } catch (Exception exception) {
		throw new TranslationException(exception.getMessage(), ErrorCode.GENERAL_EXCEPTION);
	    }	
	
	} else {

	    LOGGER.error(Messages.getString("TranslationService.GetTranslatedObject.5"));
	    
	    throw new TranslationException(
		    Messages.getString("TranslationService.GetTranslatedObject.3"),
		    ErrorCode.GENERAL_EXCEPTION);
	}
    }
    
    /*
    private InputStream getTranslatedObjectInputStream(RepositoryItem pdRepoItem)
	    throws TranslationException {

	try {

	    InputStream pdRepoItemInputStream = pdRepoItem.getInputStream();

	    LOGGER.info(Messages
		    .getString("TranslationService.GetTranslatedObject.4")
		    .concat(String.valueOf(pdRepoItemInputStream.available())));
	    
	    return pdRepoItemInputStream;
	    
	} catch (Exception exception) {
	    
	    LOGGER.error(exception.getMessage(), exception);
	    
	    throw new TranslationException(
			Messages.getString("TranslationService.GetTranslatedObject.3"),
			ErrorCode.GENERAL_EXCEPTION);
	}
    }
    */
    
    
    public CommentCollection<Comment> getTranslationJobCommentCollection(String jobId)
	    throws TranslationException {

	throw new TranslationException(
		Messages.getString("TranslationService.Error.6"),
		ErrorCode.SERVICE_NOT_IMPLEMENTED);
    }

    
    public TranslationStatus getTranslationJobStatus(String jobId)
	    throws TranslationException {

	LOGGER.info(String.format(
		Messages.getString("TranslationService.GetTranslationJobStatus.0"),
		String.valueOf(jobId)));

	ResourceResolver resolver = null;

	try {

	    resolver = getResourceResolver();
	    
	    TranslationStatus jobStatus = TranslationJobUtil.getStatus(jobId, resolver);
	    if (jobStatus == null || jobStatus.equals(TranslationStatus.UNKNOWN_STATE)) {
		
		throw new TranslationException(
			"Unable to get translation job internal status!!!",
			ErrorCode.GENERAL_EXCEPTION);
	    }
	    
	    TranslationProviderConfig pdConfig = getTranslationProviderConfig();
	    	    
	    SubmissionServiceImpl pdSubmissionService = pdConfig.adaptTo(SubmissionServiceImpl.class);
	    
	    TranslationStatus jobExternalStatus = TranslationJobStatusHelper
		    .getTranslationJobStatus(jobId, pdSubmissionService, resolver);

	    if (jobExternalStatus != null) {
	    	
	    	if(jobExternalStatus.equals(TranslationStatus.TRANSLATED) && jobStatus.equals(TranslationStatus.SCOPE_REQUESTED)) {
	    		
	    		return TranslationStatus.SCOPE_COMPLETED;
	    	}

		jobStatus = jobExternalStatus;		
		LOGGER.info(Messages
			.getString("TranslationService.GetTranslationJobStatus.1")
			.concat(jobStatus.name()));
	    }
	    	    
	    LOGGER.info("Translation job external status: " + jobExternalStatus);
	    return jobStatus;

	} catch (LoginException exception) {
	    LOGGER.error(exception.getMessage(), exception);
	} catch (RepositoryException exception) {
	    LOGGER.error(exception.getMessage(), exception);
	} finally {
	    if (resolver != null && resolver.isLive()) {
		resolver.close(); resolver = null;
	    }
	}

	return TranslationStatus.ERROR_UPDATE;
    }

    
    public CommentCollection<Comment> getTranslationObjectCommentCollection(
	    String jobId, TranslationObject object) throws TranslationException {

	throw new TranslationException(
		Messages.getString("TranslationService.Error.7"),
		ErrorCode.SERVICE_NOT_IMPLEMENTED);
    }

    
    public TranslationStatus[] getTranslationObjectsStatus(
	    String jobId, TranslationObject[] objects) throws TranslationException {

	TranslationStatus[] statuses = new TranslationStatus[objects.length];

	for (int index = 0; index < objects.length; index++) {
	    statuses[index] = getTranslationObjectStatus(jobId, objects[index]);
	}

	return statuses;
    }

    
    public TranslationStatus getTranslationObjectStatus(String jobId,
	    TranslationObject object) throws TranslationException {
	
	LOGGER.info(String.format(
		Messages.getString("TranslationServiceImpl.3"),
		String.valueOf(jobId),
		String.valueOf(object.getTitle()),
		String.valueOf(object.getTranslationObjectSourcePath())));
	
	String objectId = object.getId();
	if (objectId != null) {
	    
	    LOGGER.info("Translation object ID: " + objectId);
	    
	    TranslationProviderConfig tpConfig = getTranslationProviderConfig();
	    
	    TargetServiceImpl pdService = tpConfig.adaptTo(TargetServiceImpl.class);
	    if (pdService == null) {
		
		throw new TranslationException(
			"Unable to create GlobalLink Submision Target Service!!!",
			ErrorCode.GENERAL_EXCEPTION);
	    }

	    TranslationStatus objectStatus = TranslationJobStatusHelper.getTranslationObjectStatus(objectId, pdService);
	    if (objectStatus == null) {
		
		throw new TranslationException(
			"Unable to get translation object status!!!",
			ErrorCode.GENERAL_EXCEPTION);
	    }
	    
	    return objectStatus;
	}
	
	return getTranslationJobStatus(jobId);
    }

    public TranslationProviderConfig getTranslationProviderConfig() throws TranslationException {
	
	if (_translationProviderConfig == null) {
	    
	    throw new TranslationException(
		    "Unable to get translation provider configuration!!!",
		    ErrorCode.GENERAL_EXCEPTION);
	}
	
	return _translationProviderConfig;
    }

    
    public boolean isDirectionSupported(String srcLang, String tgtLang)
	    throws TranslationException {

	if (availableLanguageMap != null && !availableLanguageMap.isEmpty()) {
	    
	    if (availableLanguageMap.containsKey(srcLang)
		    && availableLanguageMap.containsKey(tgtLang)) {

		return Boolean.TRUE;
	    }

	} else {
	    
	    availableLanguageMap = tc.getLanguages();
	    
	    if (availableLanguageMap.containsKey(srcLang)
		    && availableLanguageMap.containsKey(tgtLang)) {

		return Boolean.TRUE;
	    }
	}
	
	return Boolean.FALSE;
    }

    
    public void storeTranslation(String srcString, String srcLang,
	    String tgtLang, String tgtString, ContentType cntType, String cntCategory,
	    String userId, int rating, String path) throws TranslationException {

	throw new TranslationException(
		Messages.getString("TranslationService.Error.8"),
		ErrorCode.SERVICE_NOT_IMPLEMENTED);
    }

    
    public void storeTranslation(String[] srcStrings, String srcLang,
	    String tgtLang, String[] tgtStrings, ContentType cntType, String cntCategory,
	    String userId, int rating, String path) throws TranslationException {

	throw new TranslationException(
		Messages.getString("TranslationService.Error.8"),
		ErrorCode.SERVICE_NOT_IMPLEMENTED);
    }

    
    public Map<String, String> supportedLanguages() {
	return availableLanguageMap;
    }

    
    public TranslationResult[] translateArray(String[] srcStrings, String srcLang,
	    String tgtLang, ContentType cntType, String cntCategory)
		    throws TranslationException {

	TranslationResult[] tgtStrings = new TranslationResult[srcStrings.length];

	for (int index = 0; index < srcStrings.length; index++) {
	    
	    String tgtString = srcStrings[index];
	    
	    tgtStrings[index] = translateString(
		    tgtString, srcLang, tgtLang, cntType,
		    cntCategory);
	}

	return tgtStrings;
    }

    
    public TranslationResult translateString(String srcString, String srcLang,
	    String tgtLang, ContentType cntType, String cntCategory)
		    throws TranslationException {

	LOGGER.info(String.format(Messages.getString("TranslationService.1"),
		String.valueOf(srcString), String.valueOf(srcLang),
		String.valueOf(tgtLang)));

	return new TranslationResultImpl(srcString,
		srcLang, tgtLang, cntType, cntCategory, srcString, 0,
		"admin");
    }

    
    public void updateTranslationJobMetadata(String jobId,
	    TranslationMetadata jobMetadata, TranslationMethod method)
		    throws TranslationException {
	
	throw new TranslationException(
		"Update translation job metadata method not implemented!!!",
		ErrorCode.SERVICE_NOT_IMPLEMENTED);
    }

    
    public TranslationStatus updateTranslationJobState(String jobId, TranslationState jobState)
	    throws TranslationException {
	
	LOGGER.info("Update translation job state...");
		
	TranslationStatus jobStatus = jobState.getStatus();
	
	if (TranslationStatus.SCOPE_REQUESTED == jobStatus) {
		
		return TranslationStatus.SCOPE_REQUESTED;
	    	    
	} else {
	    	    
	    ResourceResolver resolver = null;

	    try {

		resolver = getResourceResolver();

		if (!(TranslationStatus.TRANSLATION_IN_PROGRESS.equals(jobStatus)
			|| TranslationStatus.TRANSLATED.equals(jobStatus)
			|| TranslationStatus.READY_FOR_REVIEW.equals(jobStatus)
			|| TranslationStatus.COMPLETE.equals(jobStatus)
			|| TranslationStatus.ARCHIVE.equals(jobStatus))) {

		    TranslationProviderConfig tpConfig = getTranslationProviderConfig();
		    
		    SubmissionServiceImpl pdSubmissionService = tpConfig.adaptTo(SubmissionServiceImpl.class);
		    if (isTranslationJobExists(jobId, pdSubmissionService, resolver)) {
			
			if (!isTranslationJobStarted(jobId, pdSubmissionService, resolver)) {
			    
			    String pdSubmTicket = TranslationProviderUtil.startSubmission(jobId, tpConfig, resolver);
			    if (pdSubmTicket != null && !pdSubmTicket.isEmpty()) {

				LOGGER.info(Messages.getString("TranslationServiceImpl.pdSubmissionStarted"));

				return TranslationStatus.COMMITTED_FOR_TRANSLATION;
			    }
			}

		    } else {

			return TranslationJobStatusHelper
				.getTranslationJobStatus(jobId, pdSubmissionService,
					resolver);
		    }
		}
				
		return new TranslationJobStatusHandler().handle(jobId,
			jobState.getStatus(), getTranslationProviderConfig(),
			resolver);

	    } catch (LoginException exception) {
		LOGGER.error(exception.getMessage(), exception);	
	    } catch (RepositoryException exception) {
		LOGGER.error(exception.getMessage(), exception);
	    } finally {
		if (resolver != null && resolver.isLive()) {
		    resolver.close();
		}
	    }

	    return TranslationStatus.ERROR_UPDATE;
	}
    }

    
    public TranslationStatus[] updateTranslationObjectsState(
	    String jobId, TranslationObject[] objects, TranslationState[] states)
		    throws TranslationException {

	TranslationStatus[] statuses = new TranslationStatus[objects.length];

	for (int index = 0; index < objects.length; index++) {
	    statuses[index] = updateTranslationObjectState(jobId, objects[index], states[index]);
	}

	return statuses;
    }

    
    public TranslationStatus updateTranslationObjectState(String jobId,
	    TranslationObject object, TranslationState objectState)
		    throws TranslationException {
	
	LOGGER.info(String.format(
		Messages.getString("TranslationService.UpdateTranslationObjectState.0"),
		String.valueOf(object.getTitle()),
		String.valueOf(objectState.getStatus().name())));
	
	ResourceResolver resolver = null;
	
	try {
	    
	    resolver = getResourceResolver();
	    
	    TranslationProviderConfig pdConfig = getTranslationProviderConfig();
	    
	    
    	// Start submission if all translation objects uploaded
	    if (TranslationStatus.COMMITTED_FOR_TRANSLATION.equals(objectState.getStatus())) {
	    			
		SubmissionServiceImpl pdService = pdConfig.adaptTo(SubmissionServiceImpl.class);
		
		if(!isTranslationJobUploaded(jobId, object.getId(), pdService, resolver)) {
			
			//uploadTranslationObject(jobId, object);
			
			String jobTicket = TranslationReferenceUtil.getExternalJobId(jobId, resolver);
			if (jobTicket != null) {
			   // pdDocumentInfo.setSubmissionTicket(jobTicket);
				
			}			
			
			return TranslationStatus.SUBMITTED;
			
		}
		
		if (isTranslationJobExists(jobId, pdService, resolver)
			&& isTranslationJobUploaded(jobId, object.getId(), pdService, resolver)
			&& !isTranslationJobStarted(jobId, pdService, resolver)) {
		    
		    String pdSubmTicket = TranslationProviderUtil.startSubmission(jobId, pdConfig, resolver);
		    if (pdSubmTicket != null && !pdSubmTicket.isEmpty()) {
			LOGGER.info(Messages.getString("TranslationServiceImpl.pdSubmissionStarted"));
		    }
		}
		
		return new TranslationObjectStatusHandler().handle(jobId,
			object, objectState.getStatus(), pdConfig,
			resolver);
	    }
	    
	} catch (LoginException exception) {
	    LOGGER.error(exception.getMessage(), exception);
	} catch (RepositoryException exception) {
	    LOGGER.error(exception.getMessage(), exception);
	} finally {
	    if (resolver != null && resolver.isLive()) {
		resolver.close(); resolver = null;
	    }
	}
	
	return objectState.getStatus();
    }

    
    public String uploadTranslationObject(String jobId, TranslationObject object)
	    throws TranslationException {

	LOGGER.info(Messages
		.getString("TranslationService.UploadTranslationObject.0")
		.concat(String.valueOf(object.getTitle())));
		
	ResourceResolver resolver = null;

	try {

	    resolver = getResourceResolver();

	    TranslationProviderConfig pdConfig = getTranslationProviderConfig();
	    
	    String srcLang = TranslationReferenceUtil.getSourceLanguageByJobId(jobId, resolver);
	    String tgtLang = TranslationReferenceUtil.getTargetLanguageByJobId(jobId, resolver);

	    DocumentInfo pdDocumentInfo = createDocumentInfo(pdConfig, srcLang, tgtLang);
	    if (pdDocumentInfo != null) {
		
		String jobTicket = TranslationReferenceUtil.getExternalJobId(jobId, resolver);
		if (jobTicket != null) {
		    pdDocumentInfo.setSubmissionTicket(jobTicket);
		}

		ResourceInfo pdResourceInfo = createResourceInfo(pdConfig, object);
		pdDocumentInfo.setName(pdResourceInfo.getName());
		
		DocumentServiceImpl pdDocumentService = pdConfig.adaptTo(DocumentServiceImpl.class);
		
		@SuppressWarnings("deprecation") // TODO Change this
		InputStream objectInputStream = object.getMimeType().startsWith("text")
			? object.getTranslationObjectXMLInputStream()
				: object.getTranslationObjectInputStream();

		DocumentTicket pdDocumentTicket = pdDocumentService
			.submitDocumentWithBinaryResource(pdDocumentInfo, pdResourceInfo,
				objectInputStream);

		if (jobTicket == null) {

		    TranslationReferenceUtil.setExternalJobId(jobId,
			    pdDocumentTicket.getSubmissionTicket(),
			    resolver);
		    
		}

		if (resolver != null && resolver.isLive()) {
		    resolver.close(); resolver = null;
		}
		
		String pdObjectId = pdDocumentTicket.getTicketId();
		if (pdObjectId != null && !pdObjectId.isEmpty()) {
		
		    LOGGER.info(String.format(
			    Messages.getString("TranslationService.UploadTranslationObject.8"),
			    pdObjectId));
		    
		    return pdObjectId;
		}
	    }

	} catch (Exception exception) {	    
	    LOGGER.error(exception.getMessage(), exception);
	} finally {	    
	    if (resolver != null && resolver.isLive()) {
		resolver.close(); resolver = null;
	    }
	}

	throw new TranslationException(
		Messages.getString("TranslationService.UploadTranslationObject.9"),
		ErrorCode.GENERAL_EXCEPTION);
    }

	
	public TranslationScope getFinalScope(String jobId) throws TranslationException {
		
		
		ResourceResolver resolver = null;
		
		TranslationScopeImpl transScope  = new TranslationScopeImpl();
				
		
		try {
			
		 resolver = getResourceResolver();
		    
		 TranslationProviderConfig pdConfig = getTranslationProviderConfig();
		    
		 TargetServiceImpl targService = pdConfig.adaptTo(TargetServiceImpl.class);
		 TranslationJobStatusHandler transJobHandler = new TranslationJobStatusHandler();
		    
		 Resource jobReference = transJobHandler.getJobReference(jobId, resolver);
		
		if (jobReference != null) {
		    
		    ValueMap properties = jobReference.adaptTo(ValueMap.class);
		    if (properties.containsKey(TranslationContraints.TRANSLATION_OBJECT_EXTERNAL_ID)) {
			
			String jobExternalId = properties.get(
				TranslationContraints.TRANSLATION_OBJECT_EXTERNAL_ID,
				String.class);
			
			if (jobExternalId != null && !jobExternalId.isEmpty()) {
			    
			    SubmissionServiceImpl pdSubmissionService = pdConfig.adaptTo(SubmissionServiceImpl.class);
			    if (pdSubmissionService != null) {
				    
						Submission sub = pdSubmissionService.findByTicket(jobExternalId);
						Batch batch[]  = sub.getBatches();
						
						sub = pdSubmissionService.findByTicket(jobExternalId);
						Batch batches[]  = sub.getBatches();
						String batchName = batches[0].getName();
						String targetLanguage = batches[0].getTargetLanguages()[0];
						String phaseName = "analysis";
						
						LanguagePhaseInfo langInfo = targService.getLanguagePhaseInfo(sub.getTicket(), batchName, targetLanguage, phaseName);
						
				    	TmStatistics tmStats = langInfo.getTmStatistics();
				    	int newWords = tmStats.getNoMatchWordCount();
				    	int fuzzyWords = tmStats.getFuzzyWordCount1().getWordCount();
				    	int hundredPercWords = tmStats.getOneHundredMatchWordCount();
				    	int repsWords = tmStats.getRepetitionWordCount();
				    				
				    	Session session = getResourceResolver().adaptTo(Session.class);
				    	Node root = session.getRootNode(); 
				    				    	
				    	Node costNode = root.getNode("etc/cloudservices/gs4tr-translation/language-cost/"+targetLanguage+"/jcr:content");
				    	double newWordsCost =  Double.parseDouble(costNode.getProperty("newWords").getString());
				    	double fuzzyWordsCost =  Double.parseDouble(costNode.getProperty("fuzzyWords").getString());
				    	double hundMatchesCost =  Double.parseDouble(costNode.getProperty("hundMatches").getString());
				    	double repsWordsCost =  Double.parseDouble(costNode.getProperty("repsWords").getString());
				    	String pmCostSel =  costNode.getProperty("pmCostSelection").getString();
				    	double pmCost =  Double.parseDouble(costNode.getProperty("pmCost").getString());
				    					    	
				    	double finalCost = (newWordsCost * newWords) + (fuzzyWordsCost * fuzzyWords) + (hundMatchesCost * hundredPercWords) + (repsWordsCost * repsWords);
				    					    	
				    	if(!pmCostSel.isEmpty() && pmCostSel.equals("Multi")) {
				    		
				    		finalCost = finalCost * pmCost;
				    	}
				    	
				    	if(!pmCostSel.isEmpty() && pmCostSel.equals("Fixed")) {
				    		
				    		finalCost = finalCost + pmCost;
				    	}  				    	
				    	
				    	finalCost = (double) Math.round(finalCost * 100) / 100;
				    	
				    	transScope.getScopeMap().put("New Words", newWords+"");
				    	transScope.getScopeMap().put("Fuzzy Words", fuzzyWords+"");
				    	transScope.getScopeMap().put("Repetitions", repsWords+"");
				    	transScope.getScopeMap().put("100% Words", hundredPercWords+"");
				    	transScope.getScopeMap().put("Total Cost", "$"+finalCost);	
				    	
				    	/*
			    		 * Scope completed, now cancel the submission.
			    		 */
			    		new TranslationJobStatusHandler().handle(jobId,
				    			TranslationStatus.CANCEL, getTranslationProviderConfig(),
				    			resolver);
				    	
			    }
			}
		    }
		}
		    
		}
	    catch (RepositoryException e) {
		
	    	LOGGER.error(e.getMessage(), e);
		}
		 catch (Exception exception) {
			    LOGGER.error(exception.getMessage(), exception);
			}
		
		return transScope;
	}

	

}