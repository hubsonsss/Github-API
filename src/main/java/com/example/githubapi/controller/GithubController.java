package com.example.githubapi.controller;


import com.example.githubapi.records.RepositoryResponse;
import com.example.githubapi.service.GithubService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/github")
public class GithubController {

    private final GithubService githubService;

    public GithubController(GithubService githubService) {
        this.githubService = githubService;
    }

    @GetMapping("/{username}/repositories")
    public ResponseEntity<List<RepositoryResponse>> getUserRepositories(@PathVariable String username) {
        List<RepositoryResponse> repositories = githubService.getRepositories(username);
        return ResponseEntity.ok(repositories);
    }
}
