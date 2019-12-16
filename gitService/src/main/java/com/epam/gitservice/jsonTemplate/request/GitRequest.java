package com.epam.gitservice.jsonTemplate.request;

import lombok.Data;

@Data
public class GitRequest {
    private String userId;
    private String repositoryName;
    private String branchName;
    private boolean newBranch = false;
    private String repositoryUrl;
    private Credential credential;
}
