package dev.theright.challenge.services

import dev.theright.challenge.controllers.GithubUserNotFoundException
import dev.theright.challenge.entities.Repository
import dev.theright.challenge.entities.RepositoryInfo
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.WebClientResponseException
import reactor.core.publisher.Flux

@Service
class RepositoryService(
    private val githubClient: WebClient,
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
        return githubClient.get()
            .uri("/users/{username}/repos", username)
            .retrieve()
            .bodyToFlux(Repository::class.java)
    }
}