package com.epam.gitservice.rest;

import com.epam.gitservice.jsonTemplate.request.GitRequest;
import com.epam.gitservice.jsonTemplate.response.ErrorResponse;
import com.epam.gitservice.jsonTemplate.response.GitInfoResponse;
import com.epam.gitservice.jsonTemplate.response.GitPullResponse;
import com.epam.gitservice.jsonTemplate.response.Response;
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
public class GitService {
    private static final String GIT_EXTENSION = ".git";
    private static final String SLASH = "/";

    @RequestMapping("/clone/{path}")
    public String clone(@PathVariable String path) {
        //CredentialsProvider cp = new UsernamePasswordCredentialsProvider("userId", "password");
        return String.format("Clone service stub. Path : %s", path);
    }

    @PostMapping(value = "update", consumes = "application/json", produces = "application/json")
    public @ResponseBody
    Response updateRepository(@RequestBody GitRequest request) {
        try {
            Git git = Git.open(new File(SLASH + request.getUserId() + SLASH + request.getRepositoryName() + SLASH + GIT_EXTENSION));
            git.pull();
            GitPullResponse response = new GitPullResponse();
            response.setResultCode(HttpStatus.OK);
            return response;
        } catch (IOException e) {
            //TODO: Error Handler
            return new ErrorResponse(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(value = "/repositoriesInfo", consumes = "application/json", produces = "application/json")
    public @ResponseBody
    Response getAllRepositoryInfo(@RequestBody GitRequest request) {
        try {
            GitInfoResponse response = new GitInfoResponse();
            List<GitInfoResponse.Repository> repositoryList = new ArrayList<>();
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
            response.setRepositoryList(repositoryList);
            response.setResultCode(HttpStatus.OK);
            return response;
        } catch (IOException | GitAPIException e) {
            //TODO: Error Handler
            return new ErrorResponse(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(value = "/info", consumes = "application/json", produces = "application/json")
    public @ResponseBody
    Response getRepositoryInfo(@RequestBody GitRequest request) {
        try {
            GitInfoResponse response = new GitInfoResponse();
            String repositoryFolder = SLASH + request.getUserId() + SLASH + request.getRepositoryName() + SLASH + GIT_EXTENSION;
            Git git = Git.open(new File(repositoryFolder));
            GitInfoResponse.Repository repository = new GitInfoResponse.Repository();
            repository.setBranchList(getBranchList(git.branchList().call()));
            repository.setRepositoryName(repositoryFolder);
            repository.setCurrentBranch(git.getRepository().getBranch());
            response.setRepository(repository);
            response.setResultCode(HttpStatus.OK);
            return response;
        } catch (IOException | GitAPIException e) {
            //TODO: Error Handler
            return new ErrorResponse(HttpStatus.BAD_REQUEST);
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
