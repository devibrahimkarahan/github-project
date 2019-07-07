package com.example.github_project.views

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.RelativeLayout
import android.widget.Toast
import com.example.github_project.R
import com.example.github_project.adapters.AdapterUserDetails
import com.example.github_project.contracts.IContractUserDetails
import com.example.github_project.models.Owner
import com.example.github_project.models.Repository
import com.example.github_project.models.UserDetailsItem
import com.example.github_project.presenters.PresenterUserDetails
import com.example.github_project.utility.Const
import com.example.github_project.utility.ToastMessages
import com.google.gson.Gson
import kotlin.math.ceil

class ActivityUserDetails : AppCompatActivity(), IContractUserDetails.View {

    private lateinit var presenterUserDetails: PresenterUserDetails

    private lateinit var user: Owner

    private lateinit var itemsList: List<UserDetailsItem>

    private var currentPage: Int = 1

    private lateinit var progressBar: ProgressBar
    private lateinit var rvUserDetails: RecyclerView
    private lateinit var rlPagination: RelativeLayout
    private lateinit var btnPrev: Button
    private lateinit var btnNext: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_details)

        presenterUserDetails = PresenterUserDetails(this)
        presenterUserDetails.onCreate()
        presenterUserDetails.getUserDetails(user)
    }

    override fun init() {
        currentPage = 1

        user = Gson().fromJson(intent.getStringExtra(Const.TAG_OWNER), Owner::class.java)

        progressBar = findViewById(R.id.progressBar)
        rvUserDetails = findViewById<RecyclerView>(R.id.rv_user_details).apply {
            val llManager = LinearLayoutManager(this.context)
            llManager.orientation = LinearLayoutManager.VERTICAL
            this.layoutManager = llManager
        }
        rlPagination = findViewById(R.id.rl_pagination)
        btnPrev = findViewById(R.id.btn_prev)
        btnNext = findViewById(R.id.btn_next)
    }

    override fun initOnClickListeners() {
        btnPrev.setOnClickListener { presenterUserDetails.btnPrevClicked() }
        btnNext.setOnClickListener { presenterUserDetails.btnNextClicked() }
    }

    override fun showProgress() {
        rvUserDetails.visibility = View.GONE
        rlPagination.visibility = View.GONE
        progressBar.visibility = View.VISIBLE
    }

    override fun hideProgress() {
        progressBar.visibility = View.GONE
        rvUserDetails.visibility = View.VISIBLE
    }

    override fun showError(err: String) {
        ToastMessages.showToast(this, err, Toast.LENGTH_LONG)
    }

    override fun setUserDetails(user: Owner) {
        this.user = user
        presenterUserDetails.getUserRepositories(user, Const.PER_PAGE_ITEM_COUNTS, currentPage)
    }

    override fun showItems(repositories: List<Repository>) {
        itemsList = mutableListOf()
        itemsList = itemsList + UserDetailsItem(AdapterUserDetails.TYPE_USER_DETAILS, this.user, null)
        for (repository in repositories) {
            itemsList = itemsList + UserDetailsItem(AdapterUserDetails.TYPE_REPOSITORY, this.user, repository)
        }
        presenterUserDetails.setPagination(itemsList.size - 1)
        val adapterUserDetails = AdapterUserDetails(this, itemsList)
        rvUserDetails.adapter = adapterUserDetails
    }

    override fun initPagination(repositoriesSize: Int) {
        val totalCount: Int = user.reposCount!!
        if (totalCount <= Const.PER_PAGE_ITEM_COUNTS) {
            rlPagination.visibility = View.GONE
            return
        }
        btnPrev.visibility = View.VISIBLE
        btnNext.visibility = View.VISIBLE
        // First page
        if (currentPage == 1)
            btnPrev.visibility = View.GONE
        // Last page
        if (currentPage == ceil(totalCount.toFloat() / Const.PER_PAGE_ITEM_COUNTS.toFloat()).toInt())
            btnNext.visibility = View.GONE
        if (repositoriesSize < Const.PER_PAGE_ITEM_COUNTS || repositoriesSize + (currentPage - 1) * Const.PER_PAGE_ITEM_COUNTS == totalCount)
            btnNext.visibility = View.GONE
        rlPagination.visibility = View.VISIBLE
    }

    override fun getPrevRepositories() {
        currentPage--
        setUserDetails(user)
    }

    override fun getNextRepositories() {
        currentPage++
        setUserDetails(user)
    }
}