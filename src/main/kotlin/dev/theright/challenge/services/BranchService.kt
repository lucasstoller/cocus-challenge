package dev.theright.challenge.services

import dev.theright.challenge.entities.Branch
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Flux

@Service
class BranchService(private val githubWebClient: WebClient) {
    fun getBranches(username: String, repositoryName: String): Flux<Branch> {
        return githubWebClient.get()
            .uri("/repos/{username}/{repositoryName}/branches", username, repositoryName)
            .retrieve()
            .bodyToFlux(Branch::class.java)
    }
}