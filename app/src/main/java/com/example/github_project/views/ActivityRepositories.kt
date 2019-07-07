package com.example.github_project.views

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.*
import com.example.github_project.R
import com.example.github_project.adapters.AdapterRepositories
import com.example.github_project.contracts.IContractRepositories
import com.example.github_project.models.Repositories
import com.example.github_project.presenters.PresenterRepositories
import com.example.github_project.utility.Const
import com.example.github_project.utility.ToastMessages
import kotlin.math.ceil

class ActivityRepositories : AppCompatActivity(), IContractRepositories.View {

    private lateinit var adapterRepositories: AdapterRepositories

    private lateinit var presenterRepositories: PresenterRepositories

    private var currentPage: Int = 1
    private var lastText: String = ""

    private lateinit var etInput: EditText
    private lateinit var btnSearch: Button
    private lateinit var progressBar: ProgressBar
    private lateinit var rvRepositories: RecyclerView
    private lateinit var rlPagination: RelativeLayout
    private lateinit var btnPrev: Button
    private lateinit var btnNext: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_repositories)

        presenterRepositories = PresenterRepositories(this)
        presenterRepositories.onCreate()
    }

    override fun init() {
        currentPage = 1
        lastText = ""

        etInput = findViewById(R.id.et_input)
        btnSearch = findViewById(R.id.btn_search)
        progressBar = findViewById(R.id.progressBar)
        rvRepositories = findViewById<RecyclerView>(R.id.rv_repositories).apply {
            val llManager = LinearLayoutManager(this.context)
            llManager.orientation = LinearLayoutManager.VERTICAL
            this.layoutManager = llManager
        }
        rlPagination = findViewById(R.id.rl_pagination)
        btnPrev = findViewById(R.id.btn_prev)
        btnNext = findViewById(R.id.btn_next)
    }

    override fun initOnClickListeners() {
        btnSearch.setOnClickListener { presenterRepositories.btnSearchClicked() }
        btnPrev.setOnClickListener { presenterRepositories.btnPrevClicked() }
        btnNext.setOnClickListener { presenterRepositories.btnNextClicked() }
    }

    override fun runSearch() {
        val text = etInput.text.toString().trim()
        if (text.isBlank()) {
            showError(getString(R.string.warning_empty))
            return
        }
        currentPage = 1
        lastText = text
        getRepositories()
    }

    private fun getRepositories() {
        presenterRepositories.getRepositories(lastText, currentPage)
    }

    override fun showProgress() {
        rvRepositories.visibility = View.GONE
        rlPagination.visibility = View.GONE
        progressBar.visibility = View.VISIBLE
    }

    override fun hideProgress() {
        progressBar.visibility = View.GONE
        rvRepositories.visibility = View.VISIBLE
    }

    override fun showError(err: String) {
        ToastMessages.showToast(this, err, Toast.LENGTH_LONG)
    }

    override fun showRepositories(repositories: Repositories) {
        val items = repositories.items!!
        if (items.isEmpty()) {
            showError(getString(R.string.warning_no_results))
        }
        presenterRepositories.setPagination(repositories.totalCount!!, items.size)
        adapterRepositories = AdapterRepositories(this, items)
        rvRepositories.adapter = adapterRepositories
    }

    override fun initPagination(totalCount: Int, itemsSize: Int) {
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
        if (itemsSize < Const.PER_PAGE_ITEM_COUNTS || itemsSize + (currentPage - 1) * Const.PER_PAGE_ITEM_COUNTS == totalCount)
            btnNext.visibility = View.GONE
        rlPagination.visibility = View.VISIBLE
    }

    override fun getPreviousRepositories() {
        currentPage--
        getRepositories()
    }

    override fun getNextRepositories() {
        currentPage++
        getRepositories()
    }
}
