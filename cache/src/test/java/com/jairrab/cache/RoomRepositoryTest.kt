package com.jairrab.cache

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.jairrab.cache.dao.CachedDao
import com.jairrab.cache.db.AppDatabase
import com.jairrab.cache.entities.CachedExchangeRate
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config

@Config(manifest = Config.NONE)
@RunWith(AndroidJUnit4::class)
class RoomRepositoryTest {

    @Rule
    @JvmField var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var cachedDao: CachedDao
    private lateinit var db: AppDatabase

    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()

        db = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java)
            .allowMainThreadQueries().build()

        cachedDao = db.cachedDao()
    }

    @After
    fun tearDown() {
        db.close()
    }

    @Test
    fun getCategories() {

        val source = Rndom.string()

        val timestamp = Rndom.long()

        val list = CachedExchangeRate(
            source = source,
            quotes = Rndom.string(),
            timestamp = timestamp
        )

        cachedDao.saveExchangeRates(list).test()

        val observer = cachedDao.getCachedTimeStamp(source).test()

        observer.assertValue(listOf(timestamp))
    }

    @Test
    fun getExchangeRate() {

        val source = Rndom.string()

        val timestamp = Rndom.long()

        val list = CachedExchangeRate(
            source = source,
            quotes = Rndom.string(),
            timestamp = timestamp
        )

        cachedDao.saveExchangeRates(list).test()

        val observer = cachedDao.getExchangeRate(source).test()

        observer.assertValue(list)
    }
}
