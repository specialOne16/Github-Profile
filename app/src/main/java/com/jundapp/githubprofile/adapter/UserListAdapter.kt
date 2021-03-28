package com.jundapp.githubprofile.adapter

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.jundapp.githubprofile.R
import com.jundapp.githubprofile.User
import de.hdodenhof.circleimageview.CircleImageView


class UserListAdapter(private val context: Activity, private val data: ArrayList<User>)
    : RecyclerView.Adapter<UserListAdapter.ListViewHolder>() {

    interface OnItemClickCallback {
        fun onItemClicked(data: User)
    }

    class ListViewHolder(rowView: View) : RecyclerView.ViewHolder(rowView) {

        val ivAvatar = rowView.findViewById<CircleImageView>(R.id.ivAvatar)

        val tvName = rowView.findViewById<TextView>(R.id.tvName)
        val tvUName = rowView.findViewById<TextView>(R.id.tvUName)
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
        val thisData = data.get(position)

        holder.tvName.text = thisData.name
        holder.tvUName.text = thisData.userName

        Glide.with(context)
            .load(thisData.avatar)
            .into(holder.ivAvatar)

        holder.itemView.setOnClickListener { onItemClickCallback.onItemClicked(data[holder.adapterPosition]) }
    }

    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }
}