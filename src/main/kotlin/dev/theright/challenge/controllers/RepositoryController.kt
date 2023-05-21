package dev.theright.challenge.controllers

import dev.theright.challenge.entities.RepositoryInfo
import dev.theright.challenge.services.BranchService
import dev.theright.challenge.services.RepositoryService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/api/v1/users/{username}/repos")
class RepositoryController(
    private val repositoryService: RepositoryService,
    private val branchService: BranchService
) {
    @GetMapping
    fun listUserRepos(
        @PathVariable("username") username: String
    ): Mono<ResponseEntity<List<RepositoryInfo>>> {
        return repositoryService.getRepositories(username)
            .flatMap { repository ->
                branchService.getBranches(username, repository.name)
                    .collectList()
                    .map { branches ->
                        RepositoryInfo(repository.name, repository.owner, branches)
                    }
            }
            .collectList()
            .map { repositories ->
                ResponseEntity(repositories, HttpStatus.OK)
            }
    }
}
