package com.epam.gitservice.jsonTemplate.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
class Credential {
    private String userId;
    private String password;
}
