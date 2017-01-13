package org.gs4tr.globallink.adaptors.aem.connector.webmvc.handler.command.impl;

import org.gs4tr.globallink.adaptors.aem.connector.webmvc.handler.command.AbstractTaskCommand;

public class DeleteConfigTaskCommand extends AbstractTaskCommand {
    
    public static final String CONFIG_PATH = "configPath";
        
    private String _configPath;

    public String getConfigPath() {
        return _configPath;
    }

    public void setConfigPath(String configPath) {
        _configPath = configPath;
    }
    
}