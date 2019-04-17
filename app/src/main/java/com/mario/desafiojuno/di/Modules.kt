package com.mario.desafiojuno.di

import androidx.room.Room
import com.google.gson.GsonBuilder
import com.mario.desafiojuno.data.local.MyDataBase
import com.mario.desafiojuno.data.remote.GitHubAPI
import com.mario.desafiojuno.data.repository.RepositoryResults
import com.mario.desafiojuno.ui.detail.DetailViewModel
import com.mario.desafiojuno.ui.main.MainViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Koin main module
 */
val module = module {

    single {
        Room.databaseBuilder(
            androidApplication(),
            MyDataBase::class.java,
            "juno-db"
        )
            .allowMainThreadQueries()
            .build()
    }


    single {
        Retrofit.Builder()
            .baseUrl("https://api.github.com/")//Base Url Default
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(
                GsonConverterFactory.create(
                    GsonBuilder()
                        .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ")
                        .create()
                )
            )
            .build()
    }

    single { get<Retrofit>().create(GitHubAPI::class.java) }

    single { get<MyDataBase>().resultDao() }

    viewModel { MainViewModel(get()) }

    viewModel { DetailViewModel() }

    single { RepositoryResults(get(), get()) }

}

/**
 * module list
 */
val appModules = listOf(module)
