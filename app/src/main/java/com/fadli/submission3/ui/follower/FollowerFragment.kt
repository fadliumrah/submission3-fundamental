package com.fadli.submission3.ui.follower

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.fadli.submission3.R
import com.fadli.submission3.data.DataUser
import com.fadli.submission3.ui.adapter.UserAdapter
import com.fadli.submission3.util.ViewStateCallback
import com.fadli.submission3.databinding.FragmentFollowerBinding
import com.fadli.submission3.util.Resource


class FollowerFragment : Fragment(), ViewStateCallback<List<DataUser>> {

    private lateinit var _viewModel: FollowerViewModel
    private lateinit var _adapter: UserAdapter
    private val _binding: FragmentFollowerBinding by viewBinding()
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
        return inflater.inflate(R.layout.fragment_follower, container, false)
    }


    @Deprecated("Deprecated in Java")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        _viewModel = ViewModelProvider(this)[FollowerViewModel::class.java]
        _adapter = UserAdapter()

        _binding.rvFollower.apply {
            adapter = _adapter
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false)
        }

        _viewModel.getFollowers(username.toString()).observe(viewLifecycleOwner){
            when(it){
                is Resource.Error -> onFailed(it.message)
                is Resource.Loading -> onLoading()
                is Resource.Success -> it.data?.let { it1 -> onSuccess(it1) }
            }
        }
    }

    override fun onSuccess(data: List<DataUser>) {
        _adapter.setAllData(data)

        _binding.apply {
            tvMessage.visibility = invisible
            progressBar.visibility = invisible
            rvFollower.visibility = visible
        }
    }

    override fun onLoading() {
        _binding.apply {
            tvMessage.visibility = invisible
            progressBar.visibility = visible
            rvFollower.visibility = invisible
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
            rvFollower.visibility = invisible
        }
    }

    companion object {
        private const val KEY_BUNDLE = "USERNAME"

        fun newInstance(username: String): Fragment{
            return FollowerFragment().apply {
                arguments = Bundle().apply {
                    putString(KEY_BUNDLE, username)
                }
            }
        }
    }

}