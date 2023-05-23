package dev.theright.challenge.services

import dev.theright.challenge.entities.Branch
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.WebClient.RequestHeadersUriSpec
import org.springframework.web.reactive.function.client.WebClient.ResponseSpec
import reactor.core.publisher.Flux
import reactor.test.StepVerifier

class BranchServiceTest {
    private lateinit var githubClient: WebClient
    private lateinit var branchService: BranchService

    @BeforeEach
    fun setUp() {
        githubClient = mock(WebClient::class.java)
        branchService = BranchService(githubClient)
    }

    @Test
    fun testGetBranches() {
        val username = "testUser"
        val repositoryName = "testRepo"
        val branches = listOf(
            Branch("branch1"),
            Branch("branch2"),
            Branch("branch3")
        )

        val requestHeadersUriSpec = mock(RequestHeadersUriSpec::class.java)
        val responseSpec = mock(ResponseSpec::class.java)

        `when`(githubClient.get()).thenReturn(requestHeadersUriSpec)
        `when`(requestHeadersUriSpec.uri("/repos/{username}/{repositoryName}/branches", username, repositoryName))
            .thenReturn(requestHeadersUriSpec)
        `when`(requestHeadersUriSpec.retrieve()).thenReturn(responseSpec)
        `when`(responseSpec.bodyToFlux(Branch::class.java)).thenReturn(Flux.fromIterable(branches))

        val result = branchService.getBranches(username, repositoryName)

        StepVerifier.create(result)
            .expectNextSequence(branches)
            .verifyComplete()
    }
}
