package com.epam.gitservice.jsonTemplate.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GitRequest {
    private String userId;
    private String RepositoryName;
    private String branchName;
    private boolean newBranch = false;
    private String repositoryUrl;
    private Credential credential;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Credential{
        private String userId;
        private String password;
    }
}
