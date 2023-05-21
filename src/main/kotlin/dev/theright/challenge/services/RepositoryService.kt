package dev.theright.challenge.services

import dev.theright.challenge.controllers.GithubUserNotFoundException
import dev.theright.challenge.entities.Repository
import dev.theright.challenge.entities.RepositoryInfo
import dev.theright.challenge.infra.GithubClient
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClientResponseException
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Service
class RepositoryService(
    private val githubClient: GithubClient,
    private val branchService: BranchService
) {
    fun getRepositoryInfo(username: String): Flux<RepositoryInfo> {
        return getRepositories(username)
            .flatMap { repository ->
                branchService.getBranches(username, repository.name)
                    .collectList()
                    .map { branches ->
                        RepositoryInfo(repository.name, repository.owner, branches)
                    }
            }.onErrorResume(WebClientResponseException.NotFound::class.java) {
                throw GithubUserNotFoundException(it, username)
            }
    }

    private fun getRepositories(username: String): Flux<Repository> {
        return githubClient.build().get()
            .uri("/users/{username}/repos", username)
            .retrieve()
            .bodyToFlux(Repository::class.java)
    }
}