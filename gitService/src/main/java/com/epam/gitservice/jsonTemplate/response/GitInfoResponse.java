package com.epam.gitservice.jsonTemplate.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class GitInfoResponse extends Response {
    private int resultCode;
    private List<Repository> repositoryList;
    private Repository repository;
}
