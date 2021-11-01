package br.com.matheus.projetofinal.domain

import br.com.matheus.projetofinal.core.UseCase
import br.com.matheus.projetofinal.data.model.Repo
import br.com.matheus.projetofinal.data.repositories.RepoRepositories
import kotlinx.coroutines.flow.Flow

class ListUserRepositoriesUseCase (
    private val repository : RepoRepositories
    ): UseCase<String, List<Repo>>() {

    override suspend fun execute(param: String): Flow<List<Repo>> {
        return repository.listRepositories(param)
    }

}