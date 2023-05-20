package dev.theright.challenge.entities;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

data class Branch(
    val name: String,
) {
    lateinit var lastCommit: String

    @JsonProperty("commit")
    private fun unpackNested(commit: Map<String, Any>) {
        lastCommit = commit.get("sha").toString();
    }
}
