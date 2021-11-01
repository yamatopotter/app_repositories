package br.com.matheus.projetofinal

import android.app.Application
import android.app.Presentation
import br.com.matheus.projetofinal.data.di.DataModule
import br.com.matheus.projetofinal.domain.di.DomainModule
import br.com.matheus.projetofinal.presentation.di.PresentationModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App: Application() {
    override fun onCreate(){
        super.onCreate()

        startKoin{
            androidContext(this@App)
        }

        DataModule.load()
        DomainModule.load()
        PresentationModule.load()
    }
}