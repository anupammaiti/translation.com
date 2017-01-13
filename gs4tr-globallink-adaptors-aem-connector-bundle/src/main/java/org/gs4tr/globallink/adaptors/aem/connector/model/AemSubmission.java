package org.gs4tr.globallink.adaptors.aem.connector.model;

import java.util.Date;

public class AemSubmission {
    
    public static final String NAME = "name";
    
    public static final String DATE_CREATED = "dateCreated";
    
    public static final String DATE_REQUESTED = "dateRequested";
    
    public static final String CONNECTION_PATH = "aemConnectionPath";
    
    public static final String REPOSITORY_PATH = "aemRepositoryPath";
    
    private String _name;
    
    private Date _dateCreated;
        
    private Date _dateRequested;
    
    private String _connectionPath;
    
    private String _repositoryPath;

    private AemSubmissionInfo _info;

    public String getName() {
        return _name;
    }

    public Date getDateCreated() {
        return _dateCreated;
    }

    public Date getDateRequested() {
        return _dateRequested;
    }

    public String getConnectionPath() {
        return _connectionPath;
    }
        
    public String getRepositoryPath() {
        return _repositoryPath;
    }

    public AemSubmissionInfo getInfo() {
        return _info;
    }

    public void setName(String name) {
        _name = name;
    }

    public void setDateCreated(Date dateCreated) {
        _dateCreated = dateCreated;
    }

    public void setDateRequested(Date dateRequested) {
        _dateRequested = dateRequested;
    }

    public void setConnectionPath(String connectionPath) {
        _connectionPath = connectionPath;
    }
    
    public void setRepositoryPath(String repositoryPath) {
        _repositoryPath = repositoryPath;
    }

    public void setInfo(AemSubmissionInfo info) {
        _info = info;
    }
    
}