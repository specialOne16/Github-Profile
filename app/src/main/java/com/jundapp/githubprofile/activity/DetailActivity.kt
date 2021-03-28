package com.jundapp.githubprofile.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.StringRes
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayoutMediator
import com.jundapp.githubprofile.R
import com.jundapp.githubprofile.adapter.DetailPagerAdapter
import com.jundapp.githubprofile.databinding.ActivityDetailBinding
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import org.json.JSONObject

class DetailActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_USER = "extra_user"


        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.follower,
            R.string.following
        )
    }

    lateinit var binding : ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val userName = intent.getStringExtra(EXTRA_USER) ?: "specialOne16"
        getDetailData(userName)

        setUpTabLayout(userName)
    }

    private fun setUpTabLayout(username: String) {
        val detailPagerAdapter = DetailPagerAdapter(this, username)
        binding.
        viewPager.adapter = detailPagerAdapter
        TabLayoutMediator(binding.tabs, binding.viewPager){
            tab, position -> tab.text = resources.getString(TAB_TITLES[position])
        }.attach()

        supportActionBar?.elevation = 0f
    }

    private fun getDetailData(username: String){
//        binding.progressCircular.visibility = View.VISIBLE

        val asyncClient = AsyncHttpClient()
        asyncClient.addHeader("Authorization", "token 1a2e81eba671dc14aaab24ba3233e2b50ec83da7")
        asyncClient.addHeader("User-Agent", "request")

        val url = "https://api.github.com/users/${username}"
        asyncClient.get(url, object : AsyncHttpResponseHandler() {
            override fun onSuccess(
                statusCode: Int,
                headers: Array<out Header>,
                responseBody: ByteArray
            ) {
                val result = String(responseBody)

                try {
                    val userObject = JSONObject(result)

                    val name = userObject.getString("name")
                    val uname = userObject.getString("login")
                    val company = userObject.getString("company")
                    val location = userObject.getString("location")

                    binding.tvName.text = if(name.equals("null")) resources.getString(R.string.name) else name
                    binding.tvUName.text = if(uname.equals("null")) resources.getString(R.string.uname) else uname

                    binding.tvCompany.text = if(company.equals("null")) resources.getString(R.string.company) else company
                    binding.tvLocation.text = if(location.equals("null")) resources.getString(R.string.location) else location
                    binding.tvHyphen.text = "-"

                    Glide.with(this@DetailActivity)
                        .load(userObject.getString("avatar_url"))
                        .into(binding.ivAvatar)
                } catch (e: Exception) {
                    Toast.makeText(this@DetailActivity, resources.getString(R.string.search_error), Toast.LENGTH_SHORT).show()
                    e.printStackTrace()
                }

//                binding.progressCircular.visibility = View.GONE
            }

            override fun onFailure(
                statusCode: Int,
                headers: Array<out Header>,
                responseBody: ByteArray,
                error: Throwable
            ) {
                Toast.makeText(this@DetailActivity, resources.getString(R.string.search_error), Toast.LENGTH_SHORT).show()
//                binding.progressCircular.visibility = View.GONE
            }

        })
    }

}