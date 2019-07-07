package com.example.github_project.presenters

import com.example.github_project.contracts.IContractUserDetails
import com.example.github_project.models.Owner
import com.example.github_project.models.Repository
import com.example.github_project.network.ClientRetrofit
import com.example.github_project.services.IServiceUserDetails
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class PresenterUserDetails(_view: IContractUserDetails.View) : IContractUserDetails.Presenter {

    private val iServiceUserDetails = ClientRetrofit.getClient().create(IServiceUserDetails::class.java)
    private var view: IContractUserDetails.View = _view

    override fun onCreate() {
        view.init()
        view.initOnClickListeners()
    }

    override fun getUserDetails(user: Owner) {
        iServiceUserDetails
            .getUserDetails(user.username!!)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(object : Observer<Owner> {
                override fun onComplete() {
                }

                override fun onSubscribe(d: Disposable) {
                    view.showProgress()
                }

                override fun onNext(user: Owner) {
                    view.hideProgress()
                    view.setUserDetails(user)
                }

                override fun onError(e: Throwable) {
                    view.hideProgress()
                    view.showError(e.toString())
                }
            })
    }

    override fun getUserRepositories(user: Owner, per_page: Int, page: Int) {
        iServiceUserDetails
            .getUserRepositories(user.username!!, per_page, page)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(object : Observer<List<Repository>> {
                override fun onComplete() {
                }

                override fun onSubscribe(d: Disposable) {
                    view.showProgress()
                }

                override fun onNext(repositories: List<Repository>) {
                    view.hideProgress()
                    view.showItems(repositories)
                }

                override fun onError(e: Throwable) {
                    view.hideProgress()
                    view.showError(e.toString())
                }
            })
    }

    override fun setPagination(repositoriesSize: Int) {
        view.initPagination(repositoriesSize)
    }

    override fun btnPrevClicked() {
        view.getPrevRepositories()
    }

    override fun btnNextClicked() {
        view.getNextRepositories()
    }
}