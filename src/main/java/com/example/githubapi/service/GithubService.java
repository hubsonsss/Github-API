package com.example.githubapi.service;

import com.example.githubapi.client.GithubClient;
import com.example.githubapi.records.BranchResponse;
import com.example.githubapi.records.GithubRepositoryResponse;
import com.example.githubapi.records.RepositoryResponse;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GithubService {

    private final GithubClient githubClient;

    public GithubService(GithubClient githubClient) {
        this.githubClient = githubClient;
    }

    public List<RepositoryResponse> getRepositories(String username) {
        List<GithubRepositoryResponse> repositories = githubClient.getUserRepositories(username);

        return repositories.stream()
                .filter(repo -> !repo.fork())
                .map(repo -> {
                    List<BranchResponse> branches = githubClient.getBranches(repo.owner().login(), repo.name())
                            .stream()
                            .map(branch -> new BranchResponse(branch.name(), branch.commit().sha()))
                            .toList();
                    return new RepositoryResponse(repo.name(), repo.owner().login(), branches);
                })
                .collect(Collectors.toList());

    }
}
