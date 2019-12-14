package com.epam.gitservice.rest;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/git")
public class gitService {

    @RequestMapping("/clone/{path}")
    public String clone(@PathVariable String path) {
        return String.format("Clone service stub. Path : %s", path);
    }
}
