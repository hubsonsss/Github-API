package com.example.githubapi.records;

public record GithubBranchResponse(String name,
                                   Commit commit) {
    public record Commit(String sha) {}
}
