package br.com.matheus.projetofinal.data.repositories

import br.com.matheus.projetofinal.core.RemoteException
import br.com.matheus.projetofinal.data.services.GithubService
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException

class RepoRepositoriesImpl(private val service:GithubService) : RepoRepositories {
    override suspend fun listRepositories(user: String) = flow {
        try {
            val repoList = service.listRepos(user)
            emit(repoList)
        }
        catch (ex: HttpException){
            throw RemoteException(ex.message ?: "Não foi possivel fazer a busca no momento :(")
        }

    }
}