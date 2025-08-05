package com.example.githubapi.records;

import java.util.List;

public record RepositoryResponse(String repositoryName,
                                 String ownerLogin,
                                 List<BranchResponse> branches) {}
