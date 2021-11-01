package br.com.matheus.projetofinal.data.repositories

import br.com.matheus.projetofinal.data.model.Repo
import kotlinx.coroutines.flow.Flow

interface RepoRepositories {
    suspend fun listRepositories(user:String) : Flow<List<Repo>>
}