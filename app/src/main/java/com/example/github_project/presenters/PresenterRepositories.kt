package com.example.github_project.presenters

import com.example.github_project.contracts.IContractRepositories
import com.example.github_project.models.Repositories
import com.example.github_project.network.ClientRetrofit
import com.example.github_project.services.IServiceRepositories
import com.example.github_project.utility.Const
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class PresenterRepositories(_view: IContractRepositories.View) : IContractRepositories.Presenter {

    private var view: IContractRepositories.View = _view

    override fun onCreate() {
        view.init()
        view.initOnClickListeners()
    }

    override fun btnSearchClicked() {
        view.runSearch()
    }

    override fun getRepositories(text: String, page: Int) {
        val iServiceRepositories = ClientRetrofit.getClient().create(IServiceRepositories::class.java)
        iServiceRepositories
            .getRepositories(text, Const.PER_PAGE_ITEM_COUNTS, page)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(object : Observer<Repositories> {
                override fun onComplete() {
                }

                override fun onSubscribe(d: Disposable) {
                    view.showProgress()
                }

                override fun onNext(repositories: Repositories) {
                    view.hideProgress()
                    view.showRepositories(repositories)
                }

                override fun onError(err: Throwable) {
                    view.showError(err.toString())
                }
            })
    }

    override fun setPagination(totalCount: Int, itemsSize: Int) {
        view.initPagination(totalCount, itemsSize)
    }

    override fun btnPrevClicked() {
        view.getPreviousRepositories()
    }

    override fun btnNextClicked() {
        view.getNextRepositories()
    }
}