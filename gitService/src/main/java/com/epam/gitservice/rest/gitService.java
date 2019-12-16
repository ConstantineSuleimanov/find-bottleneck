package com.epam.gitservice.rest;

import com.epam.gitservice.jsonTemplate.request.GitRequest;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.transport.CredentialsProvider;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;

@RestController
@RequestMapping("/api/git")
public class gitService {

    @RequestMapping("/clone/{path}")
    public String clone(@PathVariable String path) {
        //CredentialsProvider cp = new UsernamePasswordCredentialsProvider("userId", "password");
        return String.format("Clone service stub. Path : %s", path);
    }

    @PostMapping(value = "update", consumes = "application/json", produces = "application/json")
    public String updateRepository(@RequestBody GitRequest request, HttpServletResponse response){
        try {
            Git git = Git.open(new File("/" + request.getUserId() + "/" + request.getRepositoryName() + ".git"));
            return "Success Update";
        } catch (IOException e) {
            //Error Handler
            return "";
        }
    }

    @PostMapping(value = "/info", consumes = "application/json", produces = "application/json")
    public String getRepositoryInfo(@RequestBody GitRequest request, HttpServletResponse response){
        try {
            Git git = Git.open(new File("/" + request.getUserId() + "/" + request.getRepositoryName() + ".git"));
//            git.getRepository()
            return "Success Update";
        } catch (IOException e) {
            //Error Handler
            return "";
        }
    }
}
