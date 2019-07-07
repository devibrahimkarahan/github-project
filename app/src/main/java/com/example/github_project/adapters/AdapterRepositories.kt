package com.example.github_project.adapters

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.github_project.R
import com.example.github_project.contracts.IContractAdapterRepositories
import com.example.github_project.models.Owner
import com.example.github_project.models.Repository
import com.example.github_project.presenters.PresenterAdapterRepositories
import com.example.github_project.utility.Const
import com.example.github_project.utility.GlideUtils
import com.example.github_project.views.ActivityRepoDetails
import com.example.github_project.views.ActivityUserDetails
import com.google.gson.Gson

class AdapterRepositories(
    _packageContext: Context,
    _reposList: List<Repository>
) :
    RecyclerView.Adapter<AdapterRepositories.ViewHolderRepositories>() {

    private val packageContext = _packageContext
    private var presenterAdapterRepositories =
        PresenterAdapterRepositories(null).apply { this.repositories = _reposList }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolderRepositories {
        val v: View = LayoutInflater.from(viewGroup.context).inflate(R.layout.item_repository, viewGroup, false)
        return ViewHolderRepositories(packageContext, v, presenterAdapterRepositories)
    }

    override fun getItemCount(): Int {
        return presenterAdapterRepositories.getRepositoriesRowsCount()
    }

    override fun onBindViewHolder(viewHolderRepositories: ViewHolderRepositories, position: Int) {
        presenterAdapterRepositories.onBindViewHolder(position, viewHolderRepositories)
    }


    class ViewHolderRepositories(
        _packageContext: Context,
        _itemView: View,
        _presenterAdapterRepositories: PresenterAdapterRepositories
    ) :
        RecyclerView.ViewHolder(_itemView),
        View.OnClickListener,
        IContractAdapterRepositories.View {
        private val ivAvatar: ImageView = itemView.findViewById(R.id.iv_avatar)
        private val tvUsername: TextView = itemView.findViewById(R.id.tv_username)
        private val tvRepoName: TextView = itemView.findViewById(R.id.tv_repoName)
        private val presenterAdapterRepositories =
            _presenterAdapterRepositories.apply { view = this@ViewHolderRepositories }
        private val packageContext = _packageContext

        init {
            presenterAdapterRepositories.start()
        }

        override fun initClickListeners() {
            ivAvatar.setOnClickListener(this)
            itemView.setOnClickListener(this)
        }

        override fun setAvatar(avatarUrl: String) {
            GlideUtils.imageLoad(itemView.context, avatarUrl, ivAvatar)
        }

        override fun setUsername(username: String) {
            tvUsername.text = username
        }

        override fun setRepoName(repoName: String) {
            tvRepoName.text = repoName
        }

        override fun runUserDetails(owner: Owner) {
            val intent = Intent(packageContext, ActivityUserDetails::class.java)
            intent.putExtra(Const.TAG_OWNER, Gson().toJson(owner))
            packageContext.startActivity(intent)
        }

        override fun runRepoDetails(repo: Repository) {
            val intent = Intent(packageContext, ActivityRepoDetails::class.java)
            intent.putExtra(Const.TAG_REPOSITORY, Gson().toJson(repo))
            packageContext.startActivity(intent)
        }

        override fun onClick(v: View?) {
            if (v?.id == R.id.iv_avatar)
                presenterAdapterRepositories.clickedAvatar(adapterPosition)
            else
                presenterAdapterRepositories.clickedRepo(adapterPosition)
        }
    }
}