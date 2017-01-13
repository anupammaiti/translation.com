package org.gs4tr.globallink.adaptors.aem.connector.util.constraints;

public class HandlerConstraints {
    
    public static final int DEFAULT_TIMEOUT = 30000;

    public static final String TASK_NAME = "taskName";
    
    public static final String DELETE_CONFIG = "GS4TR Connector Task Handler - Delete Config";
    public static final String DELETE_CONFIG_TASK_NAME = "deleteConfig";
    public static final String DELETE_CONFIG_TARGET = "(taskName=deleteConfig)";
    
    public static final String PULL_USER_PROJECTS = "GS4TR Connector Task Handler - Pull User Projects";
    public static final String PULL_USER_PROJECTS_TASK_NAME = "pullUserProjects";
    public static final String PULL_USER_PROJECTS_TARGET = "(taskName=pullUserProjects)";
    
    public static final String SAVE_REPOSITORY_CONFIG = "GS4TR Connector Task Handler - Save Repository Config";
    public static final String SAVE_REPOSITORY_CONFIG_TASK_NAME = "saveRepositoryConfig";
    public static final String SAVE_REPOSITORY_CONFIG_TARGET = "(taskName=saveRepositoryConfig)";
    
    public static final String SAVE_CONNECTION_CONFIG = "GS4TR Connector Task Handler - Save Connection Config";
    public static final String SAVE_CONNECTION_CONFIG_TASK_NAME = "saveConnectionConfig";
    public static final String SAVE_CONNECTION_CONFIG_TARGET = "(taskName=saveConnectionConfig)";
    
}