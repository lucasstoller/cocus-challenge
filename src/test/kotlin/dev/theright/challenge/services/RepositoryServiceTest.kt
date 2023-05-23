import dev.theright.challenge.entities.Branch
import dev.theright.challenge.entities.Repository
import dev.theright.challenge.entities.RepositoryInfo
import dev.theright.challenge.services.BranchService
import dev.theright.challenge.services.RepositoryService
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Flux
import reactor.test.StepVerifier

class RepositoryServiceTest {
    private lateinit var githubClient: WebClient
    private lateinit var branchService: BranchService
    private lateinit var repositoryService: RepositoryService

    @BeforeEach
    fun setup() {
        githubClient = mockk()
        branchService = mockk()
        repositoryService = RepositoryService(githubClient, branchService)
    }

    @Test
    fun `getRepositoryInfo should return repository info for existing user`() {
        val username = "johnDoe"
        val repository1 = Repository("repo1").also { it.owner = "johnDoe" }
        val repository2 = Repository("repo2").also { it.owner = "johnDoe" }
        val branch1 = Branch("branch1")
        val branch2 = Branch("branch2")
        val expectedRepositoryInfo1 = RepositoryInfo("repo1", "johnDoe", listOf(branch1))
        val expectedRepositoryInfo2 = RepositoryInfo("repo2", "johnDoe", listOf(branch2))

        val repositories = listOf(repository1, repository2)
        val branchFlux1 = Flux.just(branch1)
        val branchFlux2 = Flux.just(branch2)

        every { githubClient.get() } returns mockk()
        every { githubClient.get().uri("/users/{username}/repos", username) } returns mockk()
        every { githubClient.get().uri("/users/{username}/repos", username).retrieve() } returns mockk()
        every { githubClient.get().uri("/users/{username}/repos", username).retrieve().bodyToFlux(Repository::class.java) } returns Flux.fromIterable(repositories)

        every { branchService.getBranches(username, repository1.name) } returns branchFlux1
        every { branchService.getBranches(username, repository2.name) } returns branchFlux2

        StepVerifier.create(repositoryService.getRepositoryInfo(username))
            .expectNext(expectedRepositoryInfo1, expectedRepositoryInfo2)
            .verifyComplete()

        verify {
            githubClient.get()
            githubClient.get().uri("/users/{username}/repos", username)
            githubClient.get().uri("/users/{username}/repos", username).retrieve()
            githubClient.get().uri("/users/{username}/repos", username).retrieve().bodyToFlux(Repository::class.java)

            branchService.getBranches(username, repository1.name)
            branchService.getBranches(username, repository2.name)
        }

        confirmVerified(githubClient, branchService)
    }
}
