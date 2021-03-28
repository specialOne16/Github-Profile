package com.jundapp.githubprofile.adapter

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.jundapp.githubprofile.R
import de.hdodenhof.circleimageview.CircleImageView
import org.json.JSONArray
import org.json.JSONObject


class UserListAdapter(private val context: Activity, private val data: JSONArray)
    : RecyclerView.Adapter<UserListAdapter.ListViewHolder>() {

    interface OnItemClickCallback {
        fun onItemClicked(data: JSONObject)
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
        return data.length()
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val thisData = data.getJSONObject(position)

        holder.tvName.text = thisData.getString("login")
        holder.tvUName.text = thisData.getString("login")

        Glide.with(context)
            .load(thisData.getString("avatar_url"))
            .into(holder.ivAvatar)

        holder.itemView.setOnClickListener { onItemClickCallback.onItemClicked(data.getJSONObject(holder.adapterPosition)) }
    }

    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }
}