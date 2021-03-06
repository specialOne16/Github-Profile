package com.jundapp.githubprofile.fragments

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.jundapp.githubprofile.BuildConfig
import com.jundapp.githubprofile.R
import com.jundapp.githubprofile.activity.DetailActivity
import com.jundapp.githubprofile.adapter.UserListAdapter
import com.jundapp.githubprofile.models.UserModel
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

    private fun getUserList(url: String) {
        progress.visibility = View.VISIBLE

        val asyncClient = AsyncHttpClient()
        asyncClient.addHeader("Authorization", "token ${BuildConfig.GITHUB_API_KEY}")
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
                    val userObject: JSONArray = try {
                        JSONArray(result)
                    } catch (e: Exception) {
                        JSONObject(result).getJSONArray("items")
                    }

                    val gson = Gson()
                    val listDataUser = ArrayList<UserModel>()

                    for (i in 0 until userObject.length()) {
                        listDataUser.add(
                            gson.fromJson(
                                userObject.getJSONObject(i).toString(),
                                UserModel::class.java
                            )
                        )
                    }

                    if (listDataUser.size == 0) noData.visibility = View.VISIBLE

                    val listUserAdapter = UserListAdapter(context as Activity, listDataUser)
                    listUser.adapter = listUserAdapter

                    listUserAdapter.setOnItemClickCallback(object :
                        UserListAdapter.OnItemClickCallback {
                        override fun onItemClicked(data: UserModel) {
                            val toDetail = Intent(
                                context as Activity,
                                DetailActivity::class.java
                            )
                            toDetail.putExtra(DetailActivity.EXTRA_USER, data.login)
                            toDetail.putExtra(DetailActivity.EXTRA_USER_ID, data.id)
                            startActivity(toDetail)
                        }
                    })
                } catch (e: Exception) {
                    Toast.makeText(
                        context,
                        resources.getString(R.string.search_error),
                        Toast.LENGTH_SHORT
                    ).show()
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
                Toast.makeText(
                    context,
                    resources.getString(R.string.search_error),
                    Toast.LENGTH_SHORT
                ).show()
            }

        })
    }
}