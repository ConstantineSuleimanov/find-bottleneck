package com.epam.gitservice.jsonTemplate.request;

public class GitRequest {
    private String userId;
    private String RepositoryName;
    private String branchName;
    private boolean newBranch = false;
    private String repositoryUrl;
    private Credential credential;

    public static class Credential{
        private String userId;
        private String password;

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getRepositoryName() {
        return RepositoryName;
    }

    public void setRepositoryName(String repositoryName) {
        RepositoryName = repositoryName;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public boolean isNewBranch() {
        return newBranch;
    }

    public void setNewBranch(boolean newBranch) {
        this.newBranch = newBranch;
    }

    public String getRepositoryUrl() {
        return repositoryUrl;
    }

    public void setRepositoryUrl(String repositoryUrl) {
        this.repositoryUrl = repositoryUrl;
    }

    public Credential getCredential() {
        return credential;
    }

    public void setCredential(Credential credential) {
        this.credential = credential;
    }
}
