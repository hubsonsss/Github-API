package com.example.githubapi.client;

import com.example.githubapi.exception.GithubUserNotFoundException;
import com.example.githubapi.exception.NoRepositoryException;
import com.example.githubapi.records.GithubBranchResponse;
import com.example.githubapi.records.GithubRepositoryResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Component
public class GithubClient {

    private final RestTemplate restTemplate;
    private final String githubApiUrl;

    public GithubClient(RestTemplate restTemplate, @Value("${github.api.url}") String githubApiUrl) {
        this.restTemplate = restTemplate;
        this.githubApiUrl = githubApiUrl;
    }

    public List<GithubRepositoryResponse> getUserRepositories(String username) {
        String url = githubApiUrl + "/users/" + username + "/repos";
        try {
            GithubRepositoryResponse[] response = restTemplate.getForObject(url, GithubRepositoryResponse[].class);
            if (response == null || response.length == 0) {
                throw new NoRepositoryException("User has no repositories");
            }
            return Arrays.asList(response);
        } catch (HttpClientErrorException e) {
            if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
                throw new GithubUserNotFoundException("User not found");
            }
            throw e;
        }
    }

    public List<GithubBranchResponse> getBranches(String owner, String repo) {
        String url = githubApiUrl + "/repos/" + owner + "/" + repo + "/branches";
        GithubBranchResponse[] branches = restTemplate.getForObject(url, GithubBranchResponse[].class);
        return Arrays.asList(branches);
    }
}