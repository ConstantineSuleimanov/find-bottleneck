package com.epam.gitservice.jsonTemplate.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class GitCloneResponse extends Response {
    private String repositoryPath;
    private String status;
    private Repository repository;
}
