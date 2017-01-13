package org.gs4tr.globallink.adaptors.aem.connector.core;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Properties;
import org.apache.felix.scr.annotations.Property;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.Service;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.gs4tr.globallink.adaptors.aem.connector.core.config.TranslationProviderConfig;
import org.gs4tr.globallink.adaptors.aem.connector.i18n.Messages;
import org.gs4tr.globallink.adaptors.aem.connector.util.constraints.StringConstraints;

import com.adobe.granite.translation.api.TranslationConfig;
import com.adobe.granite.translation.api.TranslationConstants.TranslationMethod;
import com.adobe.granite.translation.api.TranslationException;
import com.adobe.granite.translation.api.TranslationService;
import com.adobe.granite.translation.api.TranslationServiceFactory;
import com.adobe.granite.translation.core.TranslationCloudConfigUtil;
import com.adobe.granite.translation.core.common.AbstractTranslationServiceFactory;

@Component(immediate = true, metatype = true)
@Service
@Properties(value = {
	@Property(name = TranslationServiceFactory.PROPERTY_TRANSLATION_FACTORY,
		value = "GlobalLink", label = "GlobalLink Connector Translation Service Factory",
		description = "GlobalLink Connector Translation Service Factory Description") })
public class TranslationServiceFactoryImpl extends
	AbstractTranslationServiceFactory implements TranslationServiceFactory {

    private static final Log LOGGER = LogFactory.getLog(TranslationServiceFactoryImpl.class);

    private static final List<TranslationMethod> TRANSLATION_METHODS;

    static {
	TRANSLATION_METHODS = new ArrayList<TranslationMethod>();
	TRANSLATION_METHODS.add(TranslationMethod.HUMAN_TRANSLATION);
    }
    
    @Reference
    private ResourceResolverFactory resourceResolverFactory;
    
    @Reference
    private TranslationCloudConfigUtil translationCloudConfigUtil;

    @Reference
    private TranslationConfig translationConfig;
    
    
    public TranslationService createTranslationService(
	    TranslationMethod translationMethod, String tpConfigPath)
		    throws TranslationException {
	
	LOGGER.info(String.format(
		Messages.getString("TranslationServiceFactoryImpl.0"),
		String.valueOf(tpConfigPath),
		String.valueOf(translationMethod != null ? translationMethod.name() : null)));
		
	if (tpConfigPath == null) {
	    LOGGER.error("Path to translation provider configuration cannot be null!!!");
	} else if (tpConfigPath.isEmpty()) {
	    LOGGER.error("Path to translation provider configuration cannot be empty!!!");
	} else if (!tpConfigPath.startsWith(StringConstraints.CQ_CLOUD_SERVICE_CONFIG_PREFIX)) {
	    LOGGER.error("Invalid tanslation configuration path is provided!!!");
	}
	
	TranslationProviderConfig tpConfig = getConfiguration(tpConfigPath);
	if (tpConfig == null) {
	    LOGGER.error("Unable to get translation provider configuration!!!");
	}
	
	TranslationServiceImpl service = new TranslationServiceImpl(
		translationConfig.getLanguages(), getServiceFactoryName(),
		translationConfig, tpConfig, resourceResolverFactory);
	
	LOGGER.info("Translation service created.");
	
	return service;
    }
    
    private TranslationProviderConfig getConfiguration(String tpConfigPath) {
	
	TranslationProviderConfig tpConfig = null;
	
	if (tpConfigPath != null && !tpConfigPath.isEmpty()
		&& tpConfigPath.startsWith(StringConstraints.CQ_CLOUD_SERVICE_CONFIG_PREFIX)) {
	    
	    tpConfig = (TranslationProviderConfig) translationCloudConfigUtil
		    .getCloudConfigObjectFromPath(TranslationProviderConfig.class,
			    tpConfigPath);
	}
	
	return tpConfig;
    }
    
    
    public Class<?> getServiceCloudConfigClass() {
	return TranslationProviderConfig.class;
    }

    
    public List<TranslationMethod> getSupportedTranslationMethods() {
	return TRANSLATION_METHODS;
    }

}