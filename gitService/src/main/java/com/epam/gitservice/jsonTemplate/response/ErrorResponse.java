package com.epam.gitservice.jsonTemplate.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorResponse extends Response {
    private int errorCode;
    private String errorMessage;

    public ErrorResponse(HttpStatus status) {
        errorCode = status.value();
        errorMessage = status.name();
    }
}
