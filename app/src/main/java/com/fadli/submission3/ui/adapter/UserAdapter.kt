package com.fadli.submission3.ui.adapter

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.fadli.submission3.data.DataUser
import com.fadli.submission3.databinding.ItemUserBinding
import com.fadli.submission3.ui.detailUser.DetailUser
import com.fadli.submission3.util.Constanta.EXTRA_USER

class UserAdapter: RecyclerView.Adapter<UserAdapter.UserViewHolder>(){

    private val listDataUsers = ArrayList<DataUser>()

    inner class UserViewHolder(private val view: ItemUserBinding): RecyclerView.ViewHolder(view.root) {
        fun bind(dataUser: DataUser){
            view.apply {
                userName.text = dataUser.username
            }
            Glide.with(itemView.context)
                .load(dataUser.avatar)
                .apply(RequestOptions.circleCropTransform())
                .into(view.avatars)

            itemView.setOnClickListener {
                val i = Intent(itemView.context, DetailUser::class.java)
                i.putExtra(EXTRA_USER,dataUser.username)
                itemView.context.startActivity(i)
            }


        }
    }


    @SuppressLint("NotifyDataSetChanged")
    fun setAllData(data: List<DataUser>){
        listDataUsers.apply {
            clear()
            addAll(data)
        }
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(listDataUsers[position])
    }

    override fun getItemCount(): Int = listDataUsers.size
}