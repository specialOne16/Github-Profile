package com.jundapp.githubprofile.adapter

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.jundapp.githubprofile.R
import com.jundapp.githubprofile.models.UserModel
import de.hdodenhof.circleimageview.CircleImageView

class UserListAdapter(private val context: Activity, var data: ArrayList<UserModel>)
    : RecyclerView.Adapter<UserListAdapter.ListViewHolder>() {

    interface OnItemClickCallback {
        fun onItemClicked(data: UserModel)
    }

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
            .into(holder.ivAvatar)

        holder.itemView.setOnClickListener { onItemClickCallback.onItemClicked(data[holder.adapterPosition]) }
    }

    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }
}