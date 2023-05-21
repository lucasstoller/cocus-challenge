package dev.theright.challenge.services

import dev.theright.challenge.entities.Branch
import dev.theright.challenge.infra.GithubClient
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux

@Service
class BranchService(private val githubClient: GithubClient) {
    fun getBranches(username: String, repositoryName: String): Flux<Branch> {
        return githubClient.build().get()
            .uri("/repos/{username}/{repositoryName}/branches", username, repositoryName)
            .retrieve()
            .bodyToFlux(Branch::class.java)
    }
}