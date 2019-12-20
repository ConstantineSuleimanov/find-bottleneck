package com.epam.gitservice.jsonTemplate.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GitInfoResponse {
    private int resultCode;
    private Error error;
    private List<Repository> repositoryList;
    private Repository repository;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Error{
        private int errorCode;
        private String errorMessage;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Repository{
        private String repositoryName;
        private String currentBranch;
        private List<String> branchList;
    }
}
