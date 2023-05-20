package dev.theright.challenge.infra

import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import java.util.*


@Component
data class GithubClient(
    @Value("\${github.baseUrl}")
    var BASE_URL: String?,
    @Value("\${github.token}")
    var TOKEN: String,
    @Value("\${github.apiVersion}")
    var GITHUB_API_VERSION: String
) {
    fun build(): WebClient {
        return WebClient.builder()
            .baseUrl(BASE_URL!!)
            .defaultCookie("cookieKey", "cookieValue")
            .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .defaultHeader(HttpHeaders.AUTHORIZATION, "BEARER $TOKEN")
            .defaultHeader("X-GitHub-Api-Version", GITHUB_API_VERSION)
            .defaultUriVariables(Collections.singletonMap("url", BASE_URL))
            .build()
    }
}

