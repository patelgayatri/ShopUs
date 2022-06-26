package com.techand.shopus.data.repository

import androidx.lifecycle.MutableLiveData
import com.techand.daggerapp.data.repository.ProductBaseRepository
import com.techand.shopus.data.local.MyCart
import com.techand.shopus.data.models.ProductResponse
import com.techand.shopus.data.network.Resource

class FakeProductRepository : ProductBaseRepository {

    var productsServiceData: LinkedHashMap<String, MyCart> = LinkedHashMap()

    private val _cartData = mutableListOf<MyCart>()
    private val observableProducts = MutableLiveData<Resource<List<MyCart>>>()


    private var shouldReturnNetworkError = false

    private suspend fun refreshData() {
        observableProducts.value = Resource.Success(productsServiceData.values.toList())
    }

    override suspend fun insertToMyCart(product: MyCart) {
        _cartData.add(product)
        refreshData()
    }

    override suspend fun deleteProduct(id: Int) {
        _cartData.remove(_cartData[id])
        refreshData()
    }

    override suspend fun getCartTotal(): Int? {
        return _cartData.size
    }

    override suspend fun getTotalMoney(): Int? {
        return _cartData.sumBy { it.price * it.quantity }
    }

    override suspend fun getMyCart(): List<MyCart> {
        return _cartData
    }

    override suspend fun getQuantity(id: Int): Int? {
        productsServiceData[id]?.let {
            return it.quantity
        }
        return 0
    }

    override suspend fun addProduct(id: Int) {
        productsServiceData[id]?.let {
            it.quantity + 1
            refreshData()
        }
    }

    override suspend fun subProduct(id: Int) {
        productsServiceData[id]?.let {
            it.quantity - 1
            refreshData()
        }
    }

    override suspend fun getImages(): Resource<ProductResponse> {
        return Resource.Success(ProductResponse())
    }

}