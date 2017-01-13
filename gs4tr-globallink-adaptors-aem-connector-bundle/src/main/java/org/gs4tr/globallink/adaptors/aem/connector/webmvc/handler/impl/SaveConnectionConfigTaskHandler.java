package org.gs4tr.globallink.adaptors.aem.connector.webmvc.handler.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Properties;
import org.apache.felix.scr.annotations.Property;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.Service;
import org.apache.sling.api.resource.ModifiableValueMap;
import org.apache.sling.api.resource.PersistenceException;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.gs4tr.globallink.adaptors.aem.connector.model.AemConnection;
import org.gs4tr.globallink.adaptors.aem.connector.model.AemConnectionProjectInfo;
import org.gs4tr.globallink.adaptors.aem.connector.model.AemConnectionProxyInfo;
import org.gs4tr.globallink.adaptors.aem.connector.util.constraints.HandlerConstraints;
import org.gs4tr.globallink.adaptors.aem.connector.webmvc.handler.AbstractTaskHandler;
import org.gs4tr.globallink.adaptors.aem.connector.webmvc.handler.TaskHandler;
import org.gs4tr.globallink.adaptors.aem.connector.webmvc.handler.command.AbstractTaskCommand;
import org.gs4tr.globallink.adaptors.aem.connector.webmvc.handler.command.TaskCommand;
import org.gs4tr.globallink.adaptors.aem.connector.webmvc.handler.command.impl.SaveConnectionConfigTaskCommand;

import com.adobe.granite.crypto.CryptoException;
import com.adobe.granite.crypto.CryptoSupport;

@Component(immediate = true)
@Service(value = TaskHandler.class)
@Properties({
    @Property(name = HandlerConstraints.TASK_NAME,
	    value = HandlerConstraints.SAVE_CONNECTION_CONFIG_TASK_NAME) })
public class SaveConnectionConfigTaskHandler extends AbstractTaskHandler {
    
    private static final Log LOGGER = LogFactory.getLog(SaveConnectionConfigTaskHandler.class);
    
    @Reference
    private CryptoSupport cryptoSupportService;
    
    public String doTask(TaskCommand taskCommand) {
	
	LOGGER.info("Trying to save connection configuration...");
	
	if (taskCommand == null) {
	    return null;
	}
	
	try {

	    SaveConnectionConfigTaskCommand command = (SaveConnectionConfigTaskCommand) taskCommand;
	    
	    AemConnection connection = command.getConnection();
	    if (connection == null) {
		return null;
	    }
	    
	    ResourceResolver resolver = command.getResourceResolver();
	    
	    Resource config = resolver.getResource(command.getConfigPath());
	    if (config != null) {
		
		ModifiableValueMap properties = config.adaptTo(ModifiableValueMap.class);
		setConnectionConfig(properties, connection);
			    			    			    
		resolver.commit();
		
		LOGGER.info("Connection configuration saved successufully.");
	    }
	    
	} catch (CryptoException exception) {
	    LOGGER.error(exception.getMessage(), exception);
	} catch (PersistenceException exception) {
	    LOGGER.error(exception.getMessage(), exception);
	}
	
	return null;
    }
    
    private void setConnectionConfig(ModifiableValueMap properties, AemConnection config) throws CryptoException {
	
	properties.put(AemConnection.WEB_SERVICES_URL, config.getWebServicesURL().toString());	    
	properties.put(AemConnection.USER_NAME, config.getUserName());	
	properties.put(AemConnection.USER_PASSWORD, cryptoSupportService.protect(config.getUserPassword()));
	
	setConnectionProxyConfig(properties, config.getProxyInfo());	
	setConnectionProjectInfo(properties, config.getProjectInfo());
    }
    
    private void setConnectionProxyConfig(ModifiableValueMap properties, AemConnectionProxyInfo config) {
	
	if (config.isProxyEnabled()) {
	    properties.put(AemConnectionProxyInfo.PROXY_ENABLED, Boolean.TRUE);
	} else {
	    properties.remove(AemConnectionProxyInfo.PROXY_ENABLED);
	}
	
	String proxyHost = config.getProxyHost();
	if (proxyHost != null) {
	    properties.put(AemConnectionProxyInfo.PROXY_HOST, proxyHost);
	} else {
	    properties.remove(AemConnectionProxyInfo.PROXY_HOST);
	}
	
	int proxyPort = config.getProxyPort();
	if (proxyPort != 0) {
	    properties.put(AemConnectionProxyInfo.PROXY_PORT, proxyPort);
	} else {
	    properties.remove(AemConnectionProxyInfo.PROXY_PORT);
	}	
	
	String proxyUserName = config.getProxyUserName();
	if (proxyUserName != null) {
	    properties.put(AemConnectionProxyInfo.PROXY_USER_NAME, proxyUserName);
	} else {
	    properties.remove(AemConnectionProxyInfo.PROXY_USER_NAME);
	}
	
	String proxyUserPassword = config.getProxyUserPassword();
	if (proxyUserPassword != null) {
	    properties.put(AemConnectionProxyInfo.PROXY_USER_PASSWORD, proxyUserPassword);
	} else {
	    properties.remove(AemConnectionProxyInfo.PROXY_USER_PASSWORD);
	}
    }
    
    private void setConnectionProjectInfo(ModifiableValueMap properties, AemConnectionProjectInfo config) {
	
	if (config.getShortCode() != null) {	    
	    properties.put(AemConnectionProjectInfo.SHORT_CODE, config.getShortCode());	    
	} else if (properties.containsKey(AemConnectionProjectInfo.SHORT_CODE)) {	    
	    properties.remove(AemConnectionProjectInfo.SHORT_CODE);
	}
	
	if (config.getDefaultClassifier() != null) {	    
	    properties.put(AemConnectionProjectInfo.DEFAULT_CLASSIFIER, config.getDefaultClassifier());	
	} else if (properties.containsKey(AemConnectionProjectInfo.DEFAULT_CLASSIFIER)) {	    
	    properties.remove(AemConnectionProjectInfo.DEFAULT_CLASSIFIER);
	}
	
	if (config.getCustomClassifier() != null) {	    
	    properties.put(AemConnectionProjectInfo.CUSTOM_CLASSIFIER, config.getCustomClassifier());	
	} else if (properties.containsKey(AemConnectionProjectInfo.CUSTOM_CLASSIFIER)) {	    
	    properties.remove(AemConnectionProjectInfo.CUSTOM_CLASSIFIER);
	}
    }
    
    public Class<? extends AbstractTaskCommand> getTaskCommandClass() {
	return SaveConnectionConfigTaskCommand.class;
    }

}