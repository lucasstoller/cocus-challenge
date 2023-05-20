package dev.theright.challenge.controllers

import dev.theright.challenge.entities.Branch
import dev.theright.challenge.entities.Repository
import dev.theright.challenge.infra.GithubClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Flux

@RestController
@RequestMapping("/api/v1/users/{username}/repos")
class RepositoryController(
    val githubClient: GithubClient
) {
    @GetMapping
    fun listUserRepos(
        @PathVariable("username") username: String
    ): Flux<Repository> {
        println(username)
        val client: WebClient = githubClient.build()
        val uri = "/users/$username/repos"
        return client
            .get()
            .uri(uri)
            .retrieve()
            .bodyToFlux(Repository::class.java)
    }
}
