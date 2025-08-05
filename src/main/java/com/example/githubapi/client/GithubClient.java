package com.example.githubapi.client;

import com.example.githubapi.exception.GithubUserNotFoundException;
import com.example.githubapi.records.GithubBranchResponse;
import com.example.githubapi.records.GithubRepositoryResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Component
public class GithubClient {
    private final RestTemplate restTemplate;

    public GithubClient() {
        this.restTemplate = new RestTemplate();
    }

    public List<GithubRepositoryResponse> getUserRepositories(String username) {
        String url = "https://api.github.com/users/" + username + "/repos";
        try{
            GithubRepositoryResponse[] response = restTemplate.getForObject(url, GithubRepositoryResponse[].class);
            return Arrays.asList(response);
        } catch (HttpClientErrorException e) {
            if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
                throw new GithubUserNotFoundException("User not found");
            }
            throw e;
        }
    }

    public List<GithubBranchResponse> getBranches(String owner, String repo) {
        String url = "https://api.github.com/repos/" + owner + "/" + repo + "/branches";
        GithubBranchResponse[] branches = restTemplate.getForObject(url, GithubBranchResponse[].class);
        return Arrays.asList(branches);
    }
}
