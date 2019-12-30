package com.epam.gitservice.jsonTemplate.response;

import org.springframework.http.HttpStatus;

public class GitPullResponse extends Response {
    public GitPullResponse(HttpStatus status) {
        setResultCode(status);
    }
}
