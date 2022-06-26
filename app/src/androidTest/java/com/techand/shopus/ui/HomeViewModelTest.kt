package com.techand.shopus.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.techand.shopus.data.local.MyCart
import com.techand.shopus.data.models.ProductResponse
import com.techand.shopus.data.network.Resource
import com.techand.shopus.data.repository.ProductRepository
import com.techand.shopus.getOrAwaitValue
import io.mockk.coEvery
import junit.framework.TestCase.assertNotNull
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import org.junit.*
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import org.mockito.MockitoAnnotations


@RunWith(AndroidJUnit4::class)
@ExperimentalCoroutinesApi
class HomeViewModelTest {
    private lateinit var viewModel: HomeViewModel

    @Mock
    private lateinit var repo: ProductRepository
    private val totalMoney = 0

    @Mock
    private lateinit var observer: Observer<in LiveData<List<MyCart>>>

    val product = MyCart(1, "KIWI", "abc", 2, 1)

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        repo = mock(ProductRepository::class.java)
        viewModel = HomeViewModel(repo)
    }

    @Test
    fun getTotalMoneyVal() {
        runBlocking {
            val observer = Observer<Int> {}
            viewModel.totalAmount.observeForever { observer }
            `when`(repo.getTotalMoney()).thenReturn(10)
            viewModel.getTotalMoney()
            assertNotNull(viewModel.totalAmount)
            assertTrue(viewModel.totalAmount.hasObservers())


        }
    }

    @Test
    fun getMyCart() {
        runBlocking {
            val res = Resource.Success(
                data = ProductResponse()
            )
            coEvery { repo.getImages() } returns res
            viewModel.getImages()
            Assert.assertEquals(viewModel.products, res)
        }
    }

    @Test
    fun getTotalProducts_returnValue() =
        mainCoroutineRule.runBlockingTest {
            var res = Resource.Success(ProductResponse())
            `when`(repo.getImages()).thenReturn(Resource.Success(ProductResponse()))
            viewModel.getImages()
            Assert.assertEquals(viewModel.products.getOrAwaitValue(), res)
        }

    @Test
    fun getTotalMoney_returnValue() =
        mainCoroutineRule.runBlockingTest {
            `when`(repo.getTotalMoney()).thenReturn(10)
            viewModel.getTotalMoney()
            Assert.assertEquals(viewModel.totalAmount.getOrAwaitValue(), totalMoney)
        }


    @After
    fun tearDown() {
    }
}

