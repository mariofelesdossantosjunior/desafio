package com.mario.desafiojuno.data.repository

import com.mario.desafiojuno.data.local.MyDataBase
import com.mario.desafiojuno.data.local.entity.Result
import com.mario.desafiojuno.data.remote.GitHubAPI
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers

class RepositoryResults(
    private val db: MyDataBase,
    private val gitHubAPI: GitHubAPI
) {

    /**
     * fun responsible for make bind API with Room
     */

    fun searchResults(query: String) =
        Observable.concatArray(
            getResultFromDb(),
            getResultFromApi(query)
        )

    private fun getResultFromApi(query: String): Observable<Result> {
        return gitHubAPI.searchResults(query)
            .doOnNext {
                storeResultInDb(it)
            }
    }

    private fun storeResultInDb(it: Result) {
        Observable.fromCallable {
            removeAllResults()
            insertResult(it)
        }
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())
            .subscribe()
    }

    private fun getResultFromDb(): Observable<Result> {
        return db.resultDao().loadResults()
            .filter { it.isNotEmpty() }
            .toObservable()
            .flatMapIterable { it }
            .doOnNext {}
    }

    /**
     * Fun responsible for find all registers into database Room
     */
    fun findAllResult() = db.resultDao().loadResults()

    fun isCache() = db.query("SELECT id from result", null).count > 0

    fun insertResult(result: Result) = db.resultDao().insertResult(result)

    fun removeAllResults() = db.resultDao().removeAll()

}