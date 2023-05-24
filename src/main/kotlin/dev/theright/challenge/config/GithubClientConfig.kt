package dev.theright.challenge.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.client.WebClient
import java.util.Collections

@Configuration
class GithubClientConfig {
    @Bean
    fun githubWebClient(
        @Value("\${github.baseUrl}") baseUrl: String,
        @Value("\${github.token}") token: String,
        @Value("\${github.apiVersion}") githubApiVersion: String
    ): WebClient {
        return WebClient.builder()
            .baseUrl(baseUrl)
            .defaultCookie("cookieKey", "cookieValue")
            .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .defaultHeader(HttpHeaders.AUTHORIZATION, "BEARER $token")
            .defaultHeader("X-GitHub-Api-Version", githubApiVersion)
            .defaultUriVariables(Collections.singletonMap("url", baseUrl))
            .build()
    }
}

