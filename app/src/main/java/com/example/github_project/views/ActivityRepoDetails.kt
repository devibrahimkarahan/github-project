package com.example.github_project.views

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.ImageView
import android.widget.TextView
import com.example.github_project.R
import com.example.github_project.contracts.IContractRepositoryDetails
import com.example.github_project.models.Owner
import com.example.github_project.models.Repository
import com.example.github_project.presenters.PresenterRepositoryDetails
import com.example.github_project.utility.Const
import com.example.github_project.utility.GlideUtils
import com.google.gson.Gson
import java.lang.StringBuilder

class ActivityRepoDetails : AppCompatActivity(), IContractRepositoryDetails.View {

    private lateinit var repository: Repository
    private lateinit var owner: Owner

    private lateinit var presenterRepositoryDetails: PresenterRepositoryDetails

    private lateinit var tvRepoName: TextView
    private lateinit var tvDescription: TextView
    private lateinit var tvForksCount: TextView
    private lateinit var tvLanguage: TextView
    private lateinit var tvDefaultBranch: TextView
    private lateinit var ivOwnerAvatar: ImageView
    private lateinit var tvOwnerUsername: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_repo_details)

        presenterRepositoryDetails = PresenterRepositoryDetails(this)
        presenterRepositoryDetails.onCreate()
    }

    override fun init() {
        repository = Gson().fromJson(intent.getStringExtra(Const.TAG_REPOSITORY), Repository::class.java)
        owner = repository.owner!!

        tvRepoName = findViewById<TextView>(R.id.tv_repoName).apply { text = repository.name }
        tvDescription = findViewById<TextView>(R.id.tv_description).apply { text = repository.description }
        tvForksCount = findViewById<TextView>(R.id.tv_forks_count).apply {
            text = StringBuilder(getString(R.string.forks_count)).append(" ").append(repository.forksCount).toString()
        }
        tvLanguage = findViewById<TextView>(R.id.tv_language).apply {
            text = StringBuilder(getString(R.string.language)).append(" ").append(repository.language).toString()
        }
        tvDefaultBranch = findViewById<TextView>(R.id.tv_default_branch).apply {
            text = StringBuilder(getString(R.string.default_branch)).append(" ").append(repository.defaultBranch)
                .toString()
        }
        ivOwnerAvatar = findViewById<ImageView>(R.id.iv_avatar).apply {
            GlideUtils.imageLoad(context, owner.avatarUrl.toString(), this)
        }
        tvOwnerUsername = findViewById<TextView>(R.id.tv_username).apply { text = owner.username }
    }

    override fun initOnClickListeners() {
        ivOwnerAvatar.setOnClickListener { presenterRepositoryDetails.avatarClicked() }
    }

    override fun runUserDetails() {
        val owner = repository.owner
        val intent = Intent(this, ActivityUserDetails::class.java)
        intent.putExtra(Const.TAG_OWNER, Gson().toJson(owner))
        startActivity(intent)
    }

}