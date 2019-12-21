package com.epam.gitservice.jsonTemplate.response;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public abstract class Response {
    private int resultCode;

    public void setResultCode(HttpStatus status){
        this.resultCode = status.value();
    }
}
