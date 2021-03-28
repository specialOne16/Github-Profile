package com.jundapp.githubprofile.fragments

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jundapp.githubprofile.R
import com.jundapp.githubprofile.activity.DetailActivity
import com.jundapp.githubprofile.adapter.UserListAdapter
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import org.json.JSONArray
import org.json.JSONObject

const val ARG_URL = "param_url"

class UserListFragment : Fragment() {
    private var url: String? = null
    private lateinit var listUser: RecyclerView
    private lateinit var progress: ProgressBar
    private lateinit var noData: TextView

    companion object {
        @JvmStatic
        fun newInstance(url: String) =
            UserListFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_URL, url)
                }
            }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            url = it.getString(ARG_URL)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_user_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        listUser = view.findViewById(R.id.listUser)
        listUser.layoutManager = LinearLayoutManager(context)
        progress = view.findViewById(R.id.progress_circular)
        noData = view.findViewById(R.id.no_data)

        url?.let { getUserList(it) }
    }

    private fun getUserList(url: String){
        progress.visibility = View.VISIBLE

        val asyncClient = AsyncHttpClient()
        asyncClient.addHeader("Authorization", "token 1a2e81eba671dc14aaab24ba3233e2b50ec83da7")
        asyncClient.addHeader("User-Agent", "request")

        asyncClient.get(url, object : AsyncHttpResponseHandler() {
            override fun onSuccess(
                statusCode: Int,
                headers: Array<out Header>,
                responseBody: ByteArray
            ) {
                progress.visibility = View.GONE

                val result = String(responseBody)


                try {
                    var userObject : JSONArray

                    try {userObject = JSONArray(result)}
                    catch (e: Exception){userObject = JSONObject(result).getJSONArray("items")}

                    if (userObject.length() == 0) noData.visibility = View.VISIBLE

                    val listUserAdapter = UserListAdapter(context as Activity, userObject)
                    listUser.adapter = listUserAdapter

                    listUserAdapter.setOnItemClickCallback(object : UserListAdapter.OnItemClickCallback {
                        override fun onItemClicked(data: JSONObject) {
                            val toDetail = Intent(context as Activity, DetailActivity::class.java)
                            toDetail.putExtra(DetailActivity.EXTRA_USER, data.getString("login"))
                            startActivity(toDetail)
                        }
                    })
                } catch (e: Exception) {
                    Toast.makeText(context, resources.getString(R.string.search_error), Toast.LENGTH_SHORT).show()
                    e.printStackTrace()
                    Log.d("follower", e.toString())
                }
            }

            override fun onFailure(
                statusCode: Int,
                headers: Array<out Header>,
                responseBody: ByteArray,
                error: Throwable
            ) {
                progress.visibility = View.GONE
                Toast.makeText(context, resources.getString(R.string.search_error), Toast.LENGTH_SHORT).show()
            }

        })
    }
}