package com.epam.gitservice.jsonTemplate.request;

import lombok.Data;


@Data
public class Credential {
    private String userId;
    private String password;
}