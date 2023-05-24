package dev.theright.challenge.controllers

class GithubUserNotFoundException(e: Exception, val username: String) : Exception(e)