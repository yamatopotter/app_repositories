package br.com.matheus.projetofinal.data.repositories

import br.com.matheus.projetofinal.data.services.GithubService
import kotlinx.coroutines.flow.flow

class RepoRepositoriesImpl(private val service:GithubService) : RepoRepositories {
    override suspend fun listRepositories(user: String) = flow {
        val repoList = service.listRepos(user)
        emit(repoList)
    }
}