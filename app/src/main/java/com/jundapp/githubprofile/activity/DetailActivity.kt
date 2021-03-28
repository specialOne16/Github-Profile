package com.jundapp.githubprofile.activity

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.jundapp.githubprofile.R
import com.jundapp.githubprofile.User
import de.hdodenhof.circleimageview.CircleImageView

class DetailActivity : AppCompatActivity(), View.OnClickListener {

    companion object {
        const val EXTRA_USER = "extra_user"
    }

    var isFav: Boolean = false
    lateinit var user: User

    lateinit var share: ImageView
    lateinit var fav: ImageView

    lateinit var ivAvatar: CircleImageView
    lateinit var tvName: TextView
    lateinit var tvUName: TextView

    lateinit var tvCompany: TextView
    lateinit var tvLocation: TextView

    lateinit var tvFollower: TextView
    lateinit var tvFollowing: TextView
    lateinit var tvRepository: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        user = intent.getParcelableExtra<User>(EXTRA_USER) ?: User()

        bindView()

        tvName.text = user.name
        tvUName.text = user.userName

        tvCompany.text = user.company
        tvLocation.text = user.location

        tvFollower.text = user.follower.toString()
        tvFollowing.text = user.following.toString()
        tvRepository.text = user.repository.toString()

        Glide.with(this)
            .load(ContextCompat.getDrawable(this, user.avatar))
            .into(ivAvatar)

        fav.setOnClickListener(this)
        share.setOnClickListener(this)

    }

    private fun bindView() {
        ivAvatar = findViewById(R.id.ivAvatar)
        tvName = findViewById(R.id.tvName)
        tvUName = findViewById(R.id.tvUName)

        share = findViewById(R.id.share)
        fav = findViewById(R.id.favourite)

        tvCompany = findViewById(R.id.tvCompany)
        tvLocation = findViewById(R.id.tvLocation)

        tvFollower = findViewById(R.id.tvFollower)
        tvFollowing = findViewById(R.id.tvFollowing)
        tvRepository = findViewById(R.id.tvRepository)
    }

    fun composeEmail(subject: String, attachment: String?) {
        val intent = Intent(Intent.ACTION_SENDTO).apply {
            data = Uri.parse("mailto:")
            putExtra(Intent.EXTRA_SUBJECT, subject)
            putExtra(Intent.EXTRA_TEXT, attachment)
        }
        if (intent.resolveActivity(packageManager) != null) {
            startActivity(intent)
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.favourite -> {
                if (isFav)
                    fav.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.star_outline))
                else
                    fav.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.star_fill))
                isFav = !isFav
            }
            R.id.share -> {
                composeEmail(
                    "View ${user.userName} on github!",
                    "Name : ${user.name}\nUsername : ${user.userName}\n"
                )
            }
        }
    }

}