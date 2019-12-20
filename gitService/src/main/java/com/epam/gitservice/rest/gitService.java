package com.epam.gitservice.rest;

import com.epam.gitservice.jsonTemplate.request.GitRequest;
import com.epam.gitservice.jsonTemplate.response.GitInfoResponse;
import com.epam.gitservice.jsonTemplate.response.GitPullResponse;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.Ref;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/git")
public class gitService {
    private static final String GIT_EXTENSION = ".git";
    private static final String SLASH = "/";

    @RequestMapping("/clone/{path}")
    public String clone(@PathVariable String path) {
        //CredentialsProvider cp = new UsernamePasswordCredentialsProvider("userId", "password");
        return String.format("Clone service stub. Path : %s", path);
    }

    @PostMapping(value = "update", consumes = "application/json", produces = "application/json")
    public @ResponseBody
    GitPullResponse updateRepository(@RequestBody GitRequest request) {
        GitPullResponse response = new GitPullResponse();
        try {
            Git git = Git.open(new File(SLASH + request.getUserId() + SLASH + request.getRepositoryName() + SLASH + GIT_EXTENSION));
            git.pull();
            response.setResultCode(0);
            return response;
        } catch (IOException e) {
            //TODO: Error Handler
            response.setError(new GitPullResponse.Error(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.name()));
            return response;
        }
    }

    @PostMapping(value = "/repositoriesInfo", consumes = "application/json", produces = "application/json")
    public @ResponseBody
    GitInfoResponse getAllRepositoryInfo(@RequestBody GitRequest request) {
        GitInfoResponse infoResponse = new GitInfoResponse();
        List<GitInfoResponse.Repository> repositoryList = new ArrayList<>();
        try {
            String userFolderPath = SLASH + request.getUserId();
            File userFolder = new File(userFolderPath);
            for (String repositoryFolder : userFolder.list()) {
                Git git = Git.open(new File(userFolderPath + SLASH + repositoryFolder + SLASH + GIT_EXTENSION));
                GitInfoResponse.Repository repository = new GitInfoResponse.Repository();
                repository.setBranchList(getBranchList(git.branchList().call()));
                repository.setRepositoryName(repositoryFolder);
                repository.setCurrentBranch(git.getRepository().getBranch());
                repositoryList.add(repository);
            }
            infoResponse.setRepositoryList(repositoryList);
            infoResponse.setResultCode(0);
            return infoResponse;
        } catch (IOException | GitAPIException e) {
            //TODO: Error Handler
            infoResponse.setError(new GitInfoResponse.Error(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.name()));
            return infoResponse;
        }
    }

    @PostMapping(value = "/info", consumes = "application/json", produces = "application/json")
    public @ResponseBody
    GitInfoResponse getRepositoryInfo(@PathVariable String repositoryName, @RequestBody GitRequest request) {
        GitInfoResponse infoResponse = new GitInfoResponse();
        try {
            String repositoryFolder = SLASH + request.getUserId() + SLASH + request.getRepositoryName() + SLASH + GIT_EXTENSION;
            Git git = Git.open(new File(repositoryFolder));
            GitInfoResponse.Repository repository = new GitInfoResponse.Repository();
            repository.setBranchList(getBranchList(git.branchList().call()));
            repository.setRepositoryName(repositoryFolder);
            repository.setCurrentBranch(git.getRepository().getBranch());
            infoResponse.setRepository(repository);
            infoResponse.setResultCode(0);
            return infoResponse;
        } catch (IOException | GitAPIException e) {
            //TODO: Error Handler
            infoResponse.setError(new GitInfoResponse.Error(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.name()));
            return infoResponse;
        }
    }

    private List<String> getBranchList(List<Ref> listRef) {
        List<String> listBranch = new ArrayList<>();
        for (Ref ref : listRef) {
            listBranch.add(ref.getName());
        }
        return listBranch;
    }
}
