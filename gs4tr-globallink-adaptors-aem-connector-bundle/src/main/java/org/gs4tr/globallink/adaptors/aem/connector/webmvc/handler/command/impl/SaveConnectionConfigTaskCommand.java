package org.gs4tr.globallink.adaptors.aem.connector.webmvc.handler.command.impl;

import org.gs4tr.globallink.adaptors.aem.connector.model.AemConnection;
import org.gs4tr.globallink.adaptors.aem.connector.webmvc.handler.command.AbstractTaskCommand;

public class SaveConnectionConfigTaskCommand extends AbstractTaskCommand {
    
    public static final String CONFIG_PATH = "configPath";
    
    public static final String USER_PROJECTS = "userProjects";
    
    private String _configPath;
    
    private AemConnection _connection;

    private String _userProjects;
    
    public String getConfigPath() {
        return _configPath;
    }

    public AemConnection getConnection() {
        return _connection;
    }

    public String getUserProjects() {
        return _userProjects;
    }

    public void setConfigPath(String configPath) {
        _configPath = configPath;
    }

    public void setConnection(AemConnection connection) {
        _connection = connection;
    }

    public void setUserProjects(String userProjects) {
        _userProjects = userProjects;
    }
        
}