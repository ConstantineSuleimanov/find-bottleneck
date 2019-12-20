package com.epam.gitservice.jsonTemplate.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GitInfoResponse extends Response {
    private int resultCode;
    private List<Repository> repositoryList;
    private Repository repository;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Repository{
        private String repositoryName;
        private String currentBranch;
        private List<String> branchList;
    }
}
