package com.epam.gitservice.rest;

import com.epam.gitservice.jsonTemplate.request.GitRequest;
import com.epam.gitservice.jsonTemplate.response.GitInfoResponse;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.transport.CredentialsProvider;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/git")
public class gitService {

    @RequestMapping("/clone/{path}")
    public String clone(@PathVariable String path) {
        //CredentialsProvider cp = new UsernamePasswordCredentialsProvider("userId", "password");
        return String.format("Clone service stub. Path : %s", path);
    }

    @PostMapping(value = "update", consumes = "application/json", produces = "application/json")
    public String updateRepository(@RequestBody GitRequest request, HttpServletResponse response) {
        try {
            Git git = Git.open(new File("/" + request.getUserId() + "/" + request.getRepositoryName() + ".git"));
            git.pull();
            return "Success Update";
        } catch (IOException e) {
            //Error Handler
            return "";
        }
    }

    @PostMapping(value = "/info", consumes = "application/json", produces = "application/json")
    public @ResponseBody
    GitInfoResponse getRepositoryInfo(@RequestBody GitRequest request, HttpServletResponse response) {
        GitInfoResponse infoResponse = new GitInfoResponse();
        try {
            Git git = Git.open(new File("/" + request.getUserId()));
            GitInfoResponse.Repository repository = new GitInfoResponse.Repository();
            repository.setBranchList(getBranchList(git.branchList().call()));
//            repository.setRepositoryName(db;
            repository.setCurrentBranch(git.getRepository().getBranch());
            List<GitInfoResponse.Repository> repositoryList = new ArrayList<>();
            repositoryList.add(repository);
            infoResponse.setRepositoryList(repositoryList);
            infoResponse.setResultCode(0);
            return infoResponse;
        } catch (IOException | GitAPIException e) {
            //Error Handler
            infoResponse.setError(new GitInfoResponse.Error(200, "Error"));
            return infoResponse;
        }
    }

    private List<String> getBranchList(List<Ref> listRef){
        List<String> listBranch = new ArrayList();
        for (Ref ref : listRef) {
            listBranch.add(ref.getName());
        }
        return listBranch;
    }
}
