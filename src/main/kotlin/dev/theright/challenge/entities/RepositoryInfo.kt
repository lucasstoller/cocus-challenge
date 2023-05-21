package dev.theright.challenge.entities

data class RepositoryInfo(
    val name: String,
    val owner: String,
    val branches: List<Branch>,
)