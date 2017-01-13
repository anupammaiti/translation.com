package org.gs4tr.globallink.adaptors.aem.connector.webmvc.handler.command.impl;

import org.gs4tr.globallink.adaptors.aem.connector.model.AemConnection;
import org.gs4tr.globallink.adaptors.aem.connector.webmvc.handler.command.AbstractTaskCommand;

public class PullUserProjectsTaskCommand extends AbstractTaskCommand {
    
    public static final String CONFIG_PATH = "configPath";
    
    private String configPath;
    
    private AemConnection _connection;
    
    public String getConfigPath() {
        return configPath;
    }

    public AemConnection getConnection() {
        return _connection;
    }

    public void setConfigPath(String configPath) {
	this.configPath = configPath;
    }

    public void setConnection(AemConnection connection) {
        _connection = connection;
    }
    
}