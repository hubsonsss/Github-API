# GitHub Repositories API

A Spring Boot application that fetches **public GitHub user repositories** along with their branches. Forked repositories are filtered out.

## Endpoint
GET /github/{username}/repositories

### ✅ Sample Response:

```json
[
  {
    "repositoryName": "my-repo",
    "ownerLogin": "user123",
    "branches": [
      {
        "name": "main",
        "commitSha": "abc123"
      }
    ]
  }
]
```
# Testing
Includes integration tests using MockWebServer to mock GitHub API responses.

# Technologies
- Java 21
- Spring Boot 3.5
- RestTemplate
- Maven
