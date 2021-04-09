package com.jundapp.consumerapp.adapter

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.jundapp.consumerapp.R
import com.jundapp.consumerapp.favoritehelper.UserModel
import de.hdodenhof.circleimageview.CircleImageView

class UserListAdapter(private val context: Activity, var data: ArrayList<UserModel>)
    : RecyclerView.Adapter<UserListAdapter.ListViewHolder>() {

    class ListViewHolder(rowView: View) : RecyclerView.ViewHolder(rowView) {

        val ivAvatar: CircleImageView = rowView.findViewById(R.id.ivAvatar)

        val tvName: TextView = rowView.findViewById(R.id.tvName)
        val tvUName: TextView = rowView.findViewById(R.id.tvUName)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val rowView = inflater.inflate(R.layout.adapter_user_list, parent, false)
        return ListViewHolder(rowView)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val thisData = data[position]

        holder.tvName.text = thisData.login
        holder.tvUName.text = thisData.login

        Glide.with(context)
            .load(thisData.avatar_url)
            .placeholder(R.drawable.ic_avatar)
            .error(R.drawable.ic_avatar)
            .into(holder.ivAvatar)

    }

}