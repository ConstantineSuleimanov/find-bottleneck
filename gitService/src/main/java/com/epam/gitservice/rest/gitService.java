package com.epam.gitservice.rest;

import com.epam.gitservice.jsonTemplate.request.GitRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;

@RestController
@RequestMapping("/api/git")
@Slf4j
public class gitService {

    @Value("${repository.path}")
    private String defaultPath;

    private String PATH_DELIMITER = "/";

    @RequestMapping("/clone")
    public String clone(@RequestBody GitRequest request) {
        Git call = null;
        try {
            File directory = getPreparedDirectory(request);
            call = Git.cloneRepository()
                    .setURI(request.getRepositoryUrl())
                    .setDirectory(directory)
                    .call();
        } catch (GitAPIException e) {
            log.error("Error while trying clone git repository. Request: {}. Cause {}", request, e.getCause());
        } catch (IOException e) {
            log.error("Error while trying to prepare folder for cloning. Request: {}, path : {}, Cause {}", request, getRepositoryPath(request), e.getCause());
        }
        return call == null ? "fuckup" : call.toString();

    }

    @PostMapping(value = "update", consumes = "application/json", produces = "application/json")
    public String updateRepository(@RequestBody GitRequest request, HttpServletResponse response) {
        try {
            Git git = Git.open(new File("/" + request.getUserId() + "/" + request.getRepositoryName() + ".git"));
            return "Success Update";
        } catch (IOException e) {
            //Error Handler
            return "";
        }
    }

    @PostMapping(value = "/info", consumes = "application/json", produces = "application/json")
    public String getRepositoryInfo(@RequestBody GitRequest request, HttpServletResponse response) {
        try {
            Git git = Git.open(new File("/" + request.getUserId() + "/" + request.getRepositoryName() + ".git"));
//            git.getRepository()
            return "Success Update";
        } catch (IOException e) {
            //Error Handler
            return "";
        }
    }

    private File getPreparedDirectory(@RequestBody GitRequest request) throws IOException {
        File directory = new File(getRepositoryPath(request));
        if (directory.isDirectory()) {
            FileUtils.deleteDirectory(directory);
        }
        return directory;
    }

    private String getRepositoryPath(GitRequest request) {
        return defaultPath + PATH_DELIMITER + request.getUserId() + PATH_DELIMITER + request.getRepositoryName();
    }
}
