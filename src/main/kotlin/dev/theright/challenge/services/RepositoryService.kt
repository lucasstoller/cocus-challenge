package dev.theright.challenge.services

import dev.theright.challenge.entities.Repository
import dev.theright.challenge.infra.GithubClient
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux

@Service
class RepositoryService(private val githubClient: GithubClient) {
    fun getRepositories(username: String): Flux<Repository> {
        return githubClient.build().get()
            .uri("/users/{username}/repos", username)
            .retrieve()
            .bodyToFlux(Repository::class.java)
    }
}