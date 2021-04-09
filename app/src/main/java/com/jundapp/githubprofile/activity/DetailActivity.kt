package com.jundapp.githubprofile.activity

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayoutMediator
import com.google.gson.Gson
import com.jundapp.githubprofile.BuildConfig
import com.jundapp.githubprofile.R
import com.jundapp.githubprofile.adapter.DetailPagerAdapter
import com.jundapp.githubprofile.databinding.ActivityDetailBinding
import com.jundapp.githubprofile.db.DatabaseContract
import com.jundapp.githubprofile.db.FavoriteHelper
import com.jundapp.githubprofile.helper.MappingHelper
import com.jundapp.githubprofile.models.UserModel
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import org.json.JSONObject

class DetailActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_USER = "extra_user"
        const val EXTRA_USER_ID = "extra_user_id"

        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.follower,
            R.string.following
        )
    }

    private lateinit var binding: ActivityDetailBinding
    private lateinit var favoriteHelper: FavoriteHelper
    private var userModel: UserModel? = null
    private var isFavorite = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = ""

        val userName = intent.getStringExtra(EXTRA_USER) ?: "specialOne16"
        val userID = intent.getIntExtra(EXTRA_USER_ID, 0)
        favoriteHelper = FavoriteHelper.getInstance(this)
        favoriteHelper.open()

        getIsFavorite(userID)
        getDetailData(userName)

        setUpTabLayout(userName)

        binding.fabFavorite.setOnClickListener {
            if (isFavorite) {
                val result = favoriteHelper.deleteById(userID.toString())
                if (result > 0) {
                    Snackbar.make(
                        binding.root,
                        resources.getString(R.string.remove_favorites, userModel?.login ?: ""),
                        Snackbar.LENGTH_SHORT
                    ).show()
                    isFavorite = false
                    binding.fabFavorite.setImageDrawable(
                        ContextCompat.getDrawable(
                            this,
                            R.drawable.ic_favorite
                        )
                    )
                } else {
                    Snackbar.make(
                        binding.root,
                        resources.getString(R.string.failed_remove_favorites),
                        Snackbar.LENGTH_SHORT
                    ).show()
                }
            } else {
                val values = ContentValues()
                values.put(DatabaseContract.FavoriteColumns.LOGIN, userModel?.login)
                values.put(DatabaseContract.FavoriteColumns.ID, userModel?.id)
                values.put(DatabaseContract.FavoriteColumns.AVATAR_URL, userModel?.avatar_url)

                val result = favoriteHelper.insert(values)
                if (result > 0) {
                    Snackbar.make(
                        binding.root,
                        resources.getString(R.string.add_favorites, userModel?.login ?: ""),
                        Snackbar.LENGTH_SHORT
                    ).show()
                    isFavorite = true
                    binding.fabFavorite.setImageDrawable(
                        ContextCompat.getDrawable(
                            this,
                            R.drawable.ic_un_favorite
                        )
                    )
                } else {
                    Snackbar.make(
                        binding.root,
                        resources.getString(R.string.failed_add_favorites),
                        Snackbar.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    private fun getIsFavorite(userID: Int) {
        GlobalScope.launch(Dispatchers.Main) {
            val favHelper = FavoriteHelper.getInstance(applicationContext)
            favHelper.open()
            val deferredFavorites = async(Dispatchers.IO) {
                val cursor = favHelper.queryById(userID.toString())
                MappingHelper.mapCursorToArrayList(cursor)
            }
            val favorites = deferredFavorites.await()
            Log.d("favorites size", favorites.size.toString())
            if (favorites.size > 0) {
                isFavorite = true
                binding.fabFavorite.setImageDrawable(
                    ContextCompat.getDrawable(
                        this@DetailActivity,
                        R.drawable.ic_un_favorite
                    )
                )
            }
        }
    }

    private fun setUpTabLayout(username: String) {
        val detailPagerAdapter = DetailPagerAdapter(this, username)
        binding.viewPager.adapter = detailPagerAdapter
        TabLayoutMediator(binding.tabs, binding.viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()

        supportActionBar?.elevation = 0f
    }

    private fun getDetailData(username: String) {

        val asyncClient = AsyncHttpClient()
        asyncClient.addHeader("Authorization", "token ${BuildConfig.GITHUB_API_KEY}")
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
                    userModel = Gson().fromJson(userObject.toString(), UserModel::class.java)
                    Log.d("fav", userModel.toString())

                    val name = userObject.getString("name")
                    val uname = userObject.getString("login")
                    val company = userObject.getString("company")
                    val location = userObject.getString("location")

                    binding.tvName.text =
                        if (name.equals("null")) resources.getString(R.string.name) else name
                    binding.tvUName.text =
                        if (uname.equals("null")) resources.getString(R.string.uname) else uname

                    binding.tvCompany.text =
                        if (company.equals("null")) resources.getString(R.string.company) else company
                    binding.tvLocation.text =
                        if (location.equals("null")) resources.getString(R.string.location) else location
                    binding.tvHyphen.text = "-"

                    Glide.with(this@DetailActivity)
                        .load(userObject.getString("avatar_url"))
                        .into(binding.ivAvatar)
                } catch (e: Exception) {
                    Toast.makeText(
                        this@DetailActivity,
                        resources.getString(R.string.search_error),
                        Toast.LENGTH_SHORT
                    ).show()
                    e.printStackTrace()
                }

            }

            override fun onFailure(
                statusCode: Int,
                headers: Array<out Header>,
                responseBody: ByteArray,
                error: Throwable
            ) {
                Toast.makeText(
                    this@DetailActivity,
                    resources.getString(R.string.search_error),
                    Toast.LENGTH_SHORT
                ).show()
            }

        })
    }

}