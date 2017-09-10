package com.alexsullivan.griointerviewapp.github

val githubBaseApiUrl = "https://api.github.com/"
val githubBaseUrl = "https://github.com/"

fun buildGithubUserUrl(name: String) = "$githubBaseUrl$name/"
fun buildGithubRepoUrl(userName: String, repoName: String) = "https://github.com/$userName/$repoName"