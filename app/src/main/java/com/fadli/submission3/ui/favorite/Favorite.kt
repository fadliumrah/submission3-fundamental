package com.fadli.submission3.ui.favorite

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.fadli.submission3.R
import com.fadli.submission3.data.DataUser
import com.fadli.submission3.databinding.ActivityFavoriteBinding
import com.fadli.submission3.databinding.ItemUserBinding
import com.fadli.submission3.ui.adapter.UserAdapter
import com.fadli.submission3.util.Resource
import com.fadli.submission3.util.ViewStateCallback
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class Favorite : AppCompatActivity(), ViewStateCallback<List<DataUser>> {

    private lateinit var _binding: ActivityFavoriteBinding
    private lateinit var _viewModel: FavoriteViewModel
    private lateinit var _adapter: UserAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(_binding.root)

        _adapter = UserAdapter()
        _viewModel = FavoriteViewModel(application)

        _binding.rvFavorite.apply {
            adapter = _adapter
            layoutManager = LinearLayoutManager(this@Favorite, LinearLayoutManager.VERTICAL, false)
        }

        CoroutineScope(Dispatchers.Main).launch {
            _viewModel.getFavoriteList().observe(this@Favorite){
                when(it){
                    is Resource.Error -> onFailed(it.message)
                    is Resource.Loading -> onLoading()
                    is Resource.Success -> it.data?.let { it1 -> onSuccess(it1) }
                }
            }
        }
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            elevation = 0f
        }

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    override fun onSuccess(data: List<DataUser>) {
        _adapter.setAllData(data)
        _binding.apply {
            favoriteProgressBar.visibility = invisible
        }
    }

    override fun onLoading() {
        _binding.apply {
            favoriteProgressBar.visibility = visible
        }
    }

    override fun onFailed(message: String?) {
        if (message == null){
            _binding.apply {
                favoriteProgressBar.visibility = invisible
                tvFavoriteError.text = getString(R.string.tv_favorite_error)
                rvFavorite.visibility = View.GONE
            }
        }
    }

    override fun onResume() {
        super.onResume()
        CoroutineScope(Dispatchers.Main).launch {
            _viewModel.getFavoriteList().observe(this@Favorite){
                when(it){
                    is Resource.Error -> onFailed(it.message)
                    is Resource.Loading -> onLoading()
                    is Resource.Success -> it.data?.let { it1 -> onSuccess(it1) }
                }
            }
        }
    }
}