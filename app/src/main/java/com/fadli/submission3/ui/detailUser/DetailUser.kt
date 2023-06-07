package com.fadli.submission3.ui.detailUser

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.fadli.submission3.R
import com.fadli.submission3.data.DataUser
import com.fadli.submission3.databinding.ActivityDetailUserBinding
import com.fadli.submission3.ui.adapter.FragmentAdapter
import com.fadli.submission3.ui.themes.ThemesViewModel
import com.fadli.submission3.util.Constanta.EXTRA_USER
import com.fadli.submission3.util.Constanta.TAB_TITLES
import com.fadli.submission3.util.Resource
import com.fadli.submission3.util.ViewStateCallback
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetailUser : AppCompatActivity(), ViewStateCallback<DataUser?> {

    private lateinit var _binding : ActivityDetailUserBinding
    private lateinit var  _viewModel: DetailUserViewModel
    private lateinit var viewModelThemes: ThemesViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(_binding.root)

        _viewModel = DetailUserViewModel(application)
        viewModelThemes = ThemesViewModel(application)

        val username = intent.getStringExtra(EXTRA_USER)

        CoroutineScope(Dispatchers.Main).launch {
            _viewModel.getDetail(username.toString()).observe(this@DetailUser){
                when(it){
                    is Resource.Loading -> onLoading()
                    is Resource.Success -> onSuccess(it.data)
                    is Resource.Error -> onFailed(it.message)
                }
            }
        }

        val fragmentAdapter = FragmentAdapter(this, username.toString())
        _binding.apply {
            viewPager2.adapter = fragmentAdapter
            TabLayoutMediator(tabs, viewPager2){tabs, position ->
                tabs.text = resources.getString(TAB_TITLES[position])
            }.attach()
        }

        _binding.apply {
            viewModelThemes.getChangeTheme().observe(this@DetailUser) { isDarkModeActive ->
                if (isDarkModeActive) {
                    imageView3.apply { setImageResource(R.drawable.ic_baseline_person_white_24) }
                    imageView4.apply { setImageResource(R.drawable.ic_baseline_work_white_24) }
                    imageView5.apply { setImageResource(R.drawable.ic_baseline_location_on_white_24) }
                    imageView6.apply { setImageResource(R.drawable.ic_baseline_folder_white_24) }
                } else {
                    imageView3.apply { setImageResource(R.drawable.ic_baseline_person_24) }
                    imageView4.apply { setImageResource(R.drawable.ic_baseline_work_24) }
                    imageView5.apply { setImageResource(R.drawable.ic_baseline_location_on_24) }
                    imageView6.apply { setImageResource(R.drawable.ic_baseline_folder_24) }
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

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onSuccess(data: DataUser?) {

        _binding.apply {

            if (data?.isFavorite == true){
                ivFavorite.apply { setImageResource(R.drawable.ic_baseline_bookmark_24) }
            }else{
                ivFavorite.apply { setImageResource(R.drawable.ic_baseline_bookmark_border_24) }
            }

            supportActionBar?.title = data?.username

            ivFavorite.setOnClickListener {
                if (data?.isFavorite == true) {
                    data.isFavorite = false
                    _viewModel.deleteFavorite(data)
                    ivFavorite.apply { setImageResource(R.drawable.ic_baseline_bookmark_border_24) }
                } else {
                    data?.isFavorite = true
                    data?.let { it1 -> _viewModel.insertFavorite(it1) }
                    ivFavorite.apply { setImageResource(R.drawable.ic_baseline_bookmark_24) }
                }
            }

            tvNameDetail.text =data?.name ?: "-"
            tvCompany.text = data?.company ?: "-"
            tvLocation.text = data?.location ?: "-"
            tvRepository.text = (data?.repository?:0).toString()
            tvFollowers.text = (data?.follower?:0).toString()
            tvFollowing.text = (data?.following?:0).toString()

            Glide.with(this@DetailUser)
                .load(data?.avatar)
                .apply(RequestOptions.circleCropTransform())
                .into(avatarDetail)

            tvMessage?.visibility = invisible
            progressBar?.visibility = invisible

        }
    }

    override fun onLoading() {
        _binding.apply {
            tvMessage?.visibility = invisible
            progressBar?.visibility = visible
        }
    }

    override fun onFailed(message: String?) {
        _binding.apply {
            if (message == null){
                tvMessage?.text = getString(R.string.detailError)
                tvMessage?.visibility = visible
            }else{
                tvMessage?.text = message
                tvMessage?.visibility = visible
            }
            progressBar?.visibility = invisible
        }
    }

}