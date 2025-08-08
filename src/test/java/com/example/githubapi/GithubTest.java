package com.example.githubapi;

import com.example.githubapi.records.RepositoryResponse;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class GithubTest {

    private static MockWebServer mockWebServer;

    @LocalServerPort
    private int port;

    @Autowired
    private RestTemplate restTemplate;

    @BeforeAll
    static void setup() throws IOException {
        mockWebServer = new MockWebServer();
        mockWebServer.start();
        System.setProperty("github.api.url", mockWebServer.url("/").toString());
    }

    @AfterAll
    static void tearDown() throws IOException {
        mockWebServer.shutdown();
    }

    @Test
    void test() {
        String reposJson = """
                [
                  {"name":"repo1","fork":false,"owner":{"login":"abc"}},
                  {"name":"repo2","fork":true,"owner":{"login":"abc"}}
                ]
                """;
        String branchesJson = """
                [
                  {"name":"main","commit":{"sha":"abc123"}},
                  {"name":"dev","commit":{"sha":"xyz456"}}
                ]
                """;

        mockWebServer.enqueue(new MockResponse().setBody(reposJson).addHeader("Content-Type", "application/json"));
        mockWebServer.enqueue(new MockResponse().setBody(branchesJson).addHeader("Content-Type", "application/json"));

        RepositoryResponse[] response = restTemplate.getForObject("http://localhost:" + port + "/github/abc/repositories", RepositoryResponse[].class);

        assertThat(response).hasSize(1);
        assertThat(response[0].repositoryName()).isEqualTo("repo1");
        assertThat(response[0].branches()).hasSize(2);
        assertThat(response[0].branches().get(0).commitSha()).isEqualTo("abc123");
    }
}
