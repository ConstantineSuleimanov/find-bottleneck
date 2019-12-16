package com.epam.gitservice.jsonTemplate.response;

import java.util.List;

public class GitInfoResponse {
    private int resultCode;
    private Error error;
    private List<Repository> repositoryList;

    public static class Error{
        private int errorCode;
        private String errorMessage;

        public Error(int errorCode, String errorMessage) {
            this.errorCode = errorCode;
            this.errorMessage = errorMessage;
        }

        public int getErrorCode() {
            return errorCode;
        }

        public void setErrorCode(int errorCode) {
            this.errorCode = errorCode;
        }

        public String getErrorMessage() {
            return errorMessage;
        }

        public void setErrorMessage(String errorMessage) {
            this.errorMessage = errorMessage;
        }
    }

    public static class Repository{
        private String repositoryName;
        private String currentBranch;
        private List<String> branchList;

        public String getRepositoryName() {
            return repositoryName;
        }

        public void setRepositoryName(String repositoryName) {
            this.repositoryName = repositoryName;
        }

        public String getCurrentBranch() {
            return currentBranch;
        }

        public void setCurrentBranch(String currentBranch) {
            this.currentBranch = currentBranch;
        }

        public List<String> getBranchList() {
            return branchList;
        }

        public void setBranchList(List<String> branchList) {
            this.branchList = branchList;
        }
    }

    public int getResultCode() {
        return resultCode;
    }

    public void setResultCode(int resultCode) {
        this.resultCode = resultCode;
    }

    public Error getError() {
        return error;
    }

    public void setError(Error error) {
        this.error = error;
    }

    public List<Repository> getRepositoryList() {
        return repositoryList;
    }

    public void setRepositoryList(List<Repository> repositoryList) {
        this.repositoryList = repositoryList;
    }
}
