package com.mario.desafiojuno.data.repository

import io.reactivex.Flowable

interface IRepository<T> {

    fun findAll(): Flowable<List<T>>

    fun save(obj: T): Long

    fun delete(obj: T)
}