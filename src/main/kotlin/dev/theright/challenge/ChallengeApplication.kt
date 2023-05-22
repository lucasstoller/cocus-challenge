package dev.theright.challenge

import io.swagger.v3.oas.annotations.OpenAPIDefinition
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
@OpenAPIDefinition(
	info = io.swagger.v3.oas.annotations.info.Info(
		title = "Cocus Challenge",
		version = "1.0.0",
		description = "Cocus Challenge API - Get github user's repositories and branches"
	)
)
class CocusChallengeApplication

fun main(args: Array<String>) {
	runApplication<CocusChallengeApplication>(*args)
}
