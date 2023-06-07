package com.fadli.submission3.ui.home

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.fadli.submission3.R
import com.fadli.submission3.data.DataUser
import com.fadli.submission3.databinding.ActivityMainBinding
import com.fadli.submission3.ui.adapter.UserAdapter
import com.fadli.submission3.ui.favorite.Favorite
import com.fadli.submission3.ui.themes.ChangeThemes
import com.fadli.submission3.ui.themes.ThemesViewModel
import com.fadli.submission3.util.Resource
import com.fadli.submission3.util.ViewStateCallback

class MainActivity : AppCompatActivity(), ViewStateCallback<List<DataUser>> {

    private lateinit var _binding: ActivityMainBinding
    private lateinit var _adapter: UserAdapter
    private lateinit var _query: String
    private lateinit var _homeViewModel: HomeViewModel
    private lateinit var _themeViewModel: ThemesViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(_binding.root)

        _adapter = UserAdapter()
        _homeViewModel = HomeViewModel(application)
        _themeViewModel = ThemesViewModel(application)

        _binding.mainSearch.rvItemUser.apply {
            adapter = _adapter
            layoutManager = LinearLayoutManager(this@MainActivity,LinearLayoutManager.VERTICAL, false)
        }
        _binding.mainSearch.apply {
            _themeViewModel.getChangeTheme().observe(this@MainActivity) { isDarkModeActive ->
                if (isDarkModeActive) {
                    searchIcon.apply { setImageResource(R.drawable.ic_baseline_search_24_white) }
                } else {
                    searchIcon.apply { setImageResource(R.drawable.ic_baseline_search_24) }
                }
            }
        }



    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.option_menu,menu)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu.findItem(R.id.search_view).actionView as SearchView

        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.queryHint = resources.getString(R.string.search_hint)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                _query = query.toString()
                searchView.clearFocus()
                _homeViewModel.searchUser(_query).observe(this@MainActivity) {
                    when (it) {
                        is Resource.Loading -> onLoading()
                        is Resource.Success -> it.data?.let { it1 -> onSuccess(it1) }
                        is Resource.Error -> onFailed(it.message)
                    }
                }
                _homeViewModel
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.favorite -> {
                val i = Intent(this@MainActivity, Favorite::class.java)
                startActivity(i)
                true
            }
            R.id.change_themes -> {
                val i = Intent(this@MainActivity, ChangeThemes::class.java)
                startActivity(i)
                true
            }
            else -> false
        }
    }

    override fun onLoading() {
        _binding.mainSearch.apply {
            searchIcon.visibility =invisible
            tvMessage.visibility = invisible
            progressBar.visibility = visible
            rvItemUser.visibility = invisible
            sib3.visibility = visible
        }

    }

    override fun onFailed(message: String?) {
        _binding.mainSearch.apply {
            if (message == null){
                searchIcon.apply {
                    setImageResource(R.drawable.ic_baseline_search_off_24)
                    visibility = visible
                }
                tvMessage.apply {
                    text = resources.getString(R.string.UserNotFound)
                    visibility = visible
                }

            }else{
                searchIcon.apply {
                    setImageResource(R.drawable.ic_baseline_error_outline_24)
                    visibility = visible
                }
                tvMessage.apply {
                    text = message
                    visibility = visible
                }
            }
            progressBar.visibility = invisible
            rvItemUser.visibility = invisible
            sib3.visibility = invisible
        }
    }

    override fun onSuccess(data: List<DataUser>) {
        _adapter.setAllData(data)
        _binding.mainSearch.apply {
            searchIcon.visibility = invisible
            tvMessage.visibility = invisible
            progressBar.visibility = invisible
            rvItemUser.visibility = visible
            sib3.visibility = invisible
        }
    }

}