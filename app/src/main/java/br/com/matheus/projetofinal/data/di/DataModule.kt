package br.com.matheus.projetofinal.data.di

import android.util.Log
import br.com.matheus.projetofinal.data.repositories.RepoRepositories
import br.com.matheus.projetofinal.data.repositories.RepoRepositoriesImpl
import br.com.matheus.projetofinal.data.services.GithubService
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.context.loadKoinModules
import org.koin.core.module.Module
import org.koin.dsl.module
import org.koin.experimental.builder.create
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

object DataModule {

    private const val OK_HTTP = "OkHttp"

    fun load(){
        loadKoinModules(networkModules() + repositoriesModule())
    }

    private fun networkModules(): Module {
        return module{
            single{
                val interceptor = HttpLoggingInterceptor{
                    Log.d(OK_HTTP, it)
                }

                interceptor.level = HttpLoggingInterceptor.Level.BODY

                OkHttpClient.Builder().
                    addInterceptor(interceptor).
                    build()
            }

            single{
                GsonConverterFactory.create(GsonBuilder().create())
            }

            single{
                createService<GithubService>(get(), get())
            }
        }
    }

    private fun repositoriesModule():Module{
        return module{
            single<RepoRepositories>{RepoRepositoriesImpl(get())}
        }
    }

    private inline fun<reified T> createService(client: OkHttpClient, factory: GsonConverterFactory):T{
        return Retrofit.Builder()
                .baseUrl("https://api.github.com/")
                .client(client)
                .addConverterFactory(factory)
                .build().create(T::class.java)
    }
}