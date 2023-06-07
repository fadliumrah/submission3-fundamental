package com.fadli.submission3.ui.following

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.fadli.submission3.R
import com.fadli.submission3.data.DataUser
import com.fadli.submission3.ui.adapter.UserAdapter
import com.fadli.submission3.util.ViewStateCallback
import by.kirich1409.viewbindingdelegate.viewBinding
import com.fadli.submission3.databinding.FragmentFollowingBinding
import com.fadli.submission3.util.Resource

class FollowingFragment : Fragment(), ViewStateCallback<List<DataUser>> {

    private lateinit var viewModel: FollowingViewModel
    private lateinit var userAdapter: UserAdapter
    private val _binding: FragmentFollowingBinding by viewBinding()
    private var username : String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            username = it.getString(KEY_BUNDLE)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_following, container, false)
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this)[FollowingViewModel::class.java]
        userAdapter = UserAdapter()
        _binding.rvFollowing.apply {
            adapter = userAdapter
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false)
        }

        viewModel.getFollowing(username.toString()).observe(viewLifecycleOwner){
            when(it){
                is Resource.Error -> onFailed(it.message)
                is Resource.Loading -> onLoading()
                is Resource.Success -> it.data?.let { it1 -> onSuccess(it1) }
            }
        }
    }

    override fun onSuccess(data: List<DataUser>) {
        userAdapter.setAllData(data)
        _binding.apply {
            tvMessage.visibility = invisible
            progressBar.visibility = invisible
            rvFollowing.visibility = visible
        }
    }

    override fun onLoading() {
        _binding.apply {
            tvMessage.visibility = invisible
            progressBar.visibility = visible
            rvFollowing.visibility = invisible
        }
    }

    override fun onFailed(message: String?) {
        _binding.apply {
            if (message == null){
                tvMessage.text = resources.getString(R.string.followers_not_found, username)
                tvMessage.visibility = visible
            }else{
                tvMessage.text = message
                tvMessage.visibility = visible
            }
            progressBar.visibility = invisible
            rvFollowing.visibility = invisible
        }
    }

    companion object {
        private const val KEY_BUNDLE = "USERNAME"
        fun newInstance(username: String): Fragment{
            return FollowingFragment().apply {
                arguments = Bundle().apply {
                    putString(KEY_BUNDLE, username)
                }
            }
        }
    }

}