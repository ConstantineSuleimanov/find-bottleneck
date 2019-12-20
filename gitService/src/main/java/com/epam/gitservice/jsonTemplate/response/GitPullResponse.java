package com.epam.gitservice.jsonTemplate.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GitPullResponse {
    private int resultCode;
    private Error error;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Error{
        private int errorCode;
        private String errorMessage;
    }
}
