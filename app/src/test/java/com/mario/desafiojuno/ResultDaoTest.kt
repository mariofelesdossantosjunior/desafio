package com.mario.desafiojuno

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.mario.desafiojuno.data.local.MyDataBase
import com.mario.desafiojuno.data.local.dao.ResultDao
import com.mario.desafiojuno.data.local.entity.Result
import org.hamcrest.core.IsEqual.equalTo
import org.junit.After
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule

@RunWith(AndroidJUnit4::class)
class ResultDaoTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var resultDao: ResultDao
    private lateinit var db: MyDataBase

    @Before
    fun createDataBase() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, MyDataBase::class.java
        ).build()
        resultDao = db.resultDao()
    }

    @After
    fun closeDataBase() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun saveResult() {
        val result = Result(1, false, listOf(), 10)

        resultDao.insertResult(result)

        val byCode = resultDao.getResultById("1").value

        assertThat(byCode, equalTo(result))
    }


}