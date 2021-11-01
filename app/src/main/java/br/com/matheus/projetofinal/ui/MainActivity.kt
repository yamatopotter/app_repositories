package br.com.matheus.projetofinal.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import androidx.appcompat.widget.SearchView
import br.com.matheus.projetofinal.R
import br.com.matheus.projetofinal.core.createDialog
import br.com.matheus.projetofinal.core.createProgressDialog
import br.com.matheus.projetofinal.core.hideSoftKeyboard
import br.com.matheus.projetofinal.databinding.ActivityMainBinding
import br.com.matheus.projetofinal.presentation.MainViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity(), SearchView.OnQueryTextListener {

    private val dialog by lazy { createProgressDialog() }
    private val viewModel by viewModel<MainViewModel>()
    private val adapter by lazy {RepoListAdapter()}
    private val binding by lazy{ ActivityMainBinding.inflate(layoutInflater)}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        binding.rvRepos.adapter = adapter

        viewModel.getRepoList("yamatopotter")
        viewModel.repos.observe(this){
            when(it) {
                MainViewModel.State.Loading -> {
                    dialog.show()
                }
                is MainViewModel.State.Error -> {
                    createDialog{
                        setMessage(it.error.message)
                    }.show()
                    dialog.dismiss()
                }
                is MainViewModel.State.Success -> {
                    dialog.dismiss()
                    adapter.submitList(it.list)
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        val searchView = menu.findItem(R.id.action_search).actionView as SearchView
        searchView.setOnQueryTextListener(this)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        query?.let{viewModel.getRepoList(it)}
        binding.root.hideSoftKeyboard()
        return true
    }

    override fun onQueryTextChange(newText: String?):Boolean{
        Log.e(TAG, "onQueryTextSubmit: $newText")
        return false
    }

    companion object{
        private const val TAG = "TAG"
    }

}