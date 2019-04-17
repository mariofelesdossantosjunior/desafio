package com.mario.desafiojuno.data.remote


import com.mario.desafiojuno.data.local.entity.Result
import io.reactivex.Flowable
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query


interface GitHubAPI {
    @GET("search/repositories")
    fun searchResults(@Query("q") query: String): Observable<Result>
}