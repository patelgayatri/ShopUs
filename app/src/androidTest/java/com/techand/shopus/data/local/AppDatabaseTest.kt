package com.techand.shopus.data.local

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.espresso.matcher.ViewMatchers.assertThat
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.runBlocking
import org.hamcrest.Matchers.*
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class AppDatabaseTest {

    private lateinit var productDao: MyCartDao
    private lateinit var appDatabase: AppDatabase
    val product = MyCart(1, "KIWI", "abc", 2, 1)

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun createDb() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext

        appDatabase = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java)
            .allowMainThreadQueries()
            .build()

        productDao = appDatabase.getCartDao()

    }

    @After
    fun closeDb() {
        appDatabase.clearAllTables()
        appDatabase.close()
    }

    @Test
    fun noData_returnsNull() {
        runBlocking {
            val result = productDao.getQuantity(1)
            assertThat(result, `is`(nullValue()))
        }
    }

    @Test
    fun insertAndGetProduct() {
        runBlocking {
            productDao.insertToMyCart(product)
            val result = productDao.getMyCart()
            assertEquals(true, result.contains(product))
        }
    }

    @Test
    fun deleteProduct_getNoProduct() {
        runBlocking {
            productDao.insertToMyCart(product)
            productDao.deleteProduct(product.id)
            val result = productDao.getMyCart()
            assertEquals(false, result.contains(product))
        }
    }

    @Test
    fun noProduct_getZeroTotal() {
        runBlocking {
            val result = productDao.getCartTotal()
            assertThat(result, `is`(nullValue()))
        }
    }

    @Test
    fun getCartTotal() {
        runBlocking {
            productDao.insertToMyCart(product)
            val result = productDao.getCartTotal()
            assertThat(result, `is`(notNullValue()))
        }
    }


    @Test
    fun noProduct_returnTotalMoney_null() {
        runBlocking {
            val result = productDao.getTotalMoney()
            assertThat(result, `is`(nullValue()))
        }
    }

    @Test
    fun getTotalMoney() {
        runBlocking {
            productDao.insertToMyCart(product)
            val result = productDao.getTotalMoney()
            assertThat(result, `is`(notNullValue()))
        }
    }

    @Test
    fun noQuantity_returnQuantity_null() {
        runBlocking {
            val result = productDao.getQuantity(1)
            assertThat(result, `is`(nullValue()))
        }
    }

    @Test
    fun getQuantity() {
        runBlocking {
            productDao.insertToMyCart(product)
            val result = productDao.getQuantity(1)
            assertThat(result, `is`(notNullValue()))
        }
    }

    @Test
    fun addProduct(){
        runBlocking {
            productDao.insertToMyCart(product)
            productDao.addProduct(product.id)
            val result = productDao.getQuantity(product.id)
            assertThat(result, `is`(2))
        }
    }
    @Test
    fun subProduct(){
        runBlocking {
            productDao.insertToMyCart(product)
            productDao.subProduct(product.id)
            val result = productDao.getQuantity(product.id)
            assertThat(result, `is`(0))
        }
    }

    @Test
    fun noProducts_returnsEmptyList() = runBlocking {
        val result = productDao.getMyCart()
        assertThat(result.size, `is`(0))
    }
}