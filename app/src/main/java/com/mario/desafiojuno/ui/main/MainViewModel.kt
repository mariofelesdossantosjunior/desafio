package com.mario.desafiojuno.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mario.desafiojuno.data.local.entity.Result
import com.mario.desafiojuno.data.repository.RepositoryResults
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class MainViewModel(
    private val repositoryResults: RepositoryResults
) : ViewModel() {

    private val results: MutableLiveData<Result> = MutableLiveData()
    private val loading: MutableLiveData<Boolean> = MutableLiveData()
    private val error: MutableLiveData<String> = MutableLiveData()

    fun results() = results as LiveData<Result>
    fun loading() = loading as LiveData<Boolean>
    fun error() = error as LiveData<String>

    /**
     * Fun load results in API
     */
    fun searchResult(query: String): Disposable {
        loading.postValue(true)

        return repositoryResults.searchResults(query)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    loading.postValue(false)
                    results.postValue(it)
                },
                {
                    loading.postValue(false)
                    error.postValue(it.message)
                })

    }

    fun isCached() = repositoryResults.isCache()

    fun findAllResult() = repositoryResults.findAllResult()
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .toObservable()
        .flatMapIterable { it }
        .subscribe(
            {
                loading.postValue(false)
                results.postValue(it)
            },
            {
                loading.postValue(false)
                error.postValue(it.message)
            })

}
