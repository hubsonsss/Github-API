package com.example.githubapi.records;

public record GithubRepositoryResponse(String name,
                                       boolean fork,
                                       Owner owner) {
    public record Owner (String login){}
}
