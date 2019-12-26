package com.epam.gitservice.rest;

import com.epam.gitservice.jsonTemplate.request.GitRequest;
import com.epam.gitservice.jsonTemplate.response.*;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.Ref;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/git")
public class GitService {
    @Value("${repository.path}")
    private String defaultPath;

    private static final String GIT_EXTENSION = ".git";
    private static final String SLASH = "/";

    @RequestMapping(value = "/clone", consumes = "application/json", produces = "application/json")
    public @ResponseBody
    Response clone(@RequestBody GitRequest request) {
        Git call = null;
        try {
            Git git = cloneRepository(request);
            GitCloneResponse response = new GitCloneResponse();
            String repositoryFolder = getRepositoryFolder(request);
            Repository repository = fillRepositoryInfo(git, repositoryFolder);
            response.setResultCode(HttpStatus.OK);
            response.setRepository(repository);
            return response;
        } catch (GitAPIException | IOException e) {
            //TODO: Error Handler
            return new ErrorResponse(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(value = "/update", consumes = "application/json", produces = "application/json")
    public @ResponseBody
    Response updateRepository(@RequestBody GitRequest request) {
        try {
            Git git = Git.open(new File(getGitFolder(request)));
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
            List<Repository> repositoryList = new ArrayList<>();
            String userFolderPath = getUserFolder(request);
            File userFolder = new File(userFolderPath);
            for (String repositoryFolder : userFolder.list()) {
                Git git = Git.open(new File(userFolderPath + SLASH + repositoryFolder + SLASH + GIT_EXTENSION));
                Repository repository = fillRepositoryInfo(git, repositoryFolder);
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
            String repositoryFolder = getGitFolder(request);
            Git git = Git.open(new File(repositoryFolder));
            Repository repository = fillRepositoryInfo(git, repositoryFolder);
            response.setRepository(repository);
            response.setResultCode(HttpStatus.OK);
            return response;
        } catch (IOException | GitAPIException e) {
            //TODO: Error Handler
            return new ErrorResponse(HttpStatus.BAD_REQUEST);
        }
    }

    private Git cloneRepository(GitRequest request) throws IOException, GitAPIException {
        Git call;
        File directory = getPreparedDirectory(request);
        call = Git.cloneRepository()
                .setURI(request.getRepositoryUrl())
                .setDirectory(directory)
                .call();
        return call;
    }

    private List<String> getBranchList(List<Ref> listRef) {
        List<String> listBranch = new ArrayList<>();
        for (Ref ref : listRef) {
            listBranch.add(ref.getName());
        }
        return listBranch;
    }

    private File getPreparedDirectory(@RequestBody GitRequest request) throws IOException {
        File directory = new File(getRepositoryFolder(request));
        if (directory.isDirectory()) {
            FileUtils.deleteDirectory(directory);
        }
        return directory;
    }

    private String getGitFolder(GitRequest request) {
        return getRepositoryFolder(request) + SLASH + GIT_EXTENSION;
    }

    private String getUserFolder(GitRequest request){
        return defaultPath + SLASH + request.getUserId();
    }

    private String getRepositoryFolder(GitRequest request) {
        return getUserFolder(request) + SLASH + request.getRepositoryName();
    }

    private Repository fillRepositoryInfo(Git git, String repositoryFolder) throws GitAPIException, IOException {
        Repository repository = new Repository();
        repository.setBranchList(getBranchList(git.branchList().call()));
        repository.setRepositoryName(repositoryFolder);
        repository.setCurrentBranch(git.getRepository().getBranch());
        return repository;
    }
}
