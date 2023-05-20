package dev.theright.challenge.entities;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

data class Repository(
    val name: String
) {
    lateinit var owner: String

    @JsonProperty("owner")
    private fun unpackNested(metadata: Map<String, Any>) {
        owner =  metadata["login"] as String
    }
}