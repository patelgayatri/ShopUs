package com.techand.daggerapp.data.repository

import com.techand.shopus.data.local.MyCart
import com.techand.shopus.data.models.ProductResponse
import com.techand.shopus.data.network.Resource

interface ProductBaseRepository {


    suspend fun insertToMyCart(product: MyCart)

    suspend fun deleteProduct(id: Int)

    suspend fun getCartTotal(): Int?

    suspend fun getTotalMoney(): Int?

    suspend fun getMyCart(): List<MyCart>


    suspend fun getQuantity(id: Int): Int?

    suspend fun addProduct(id: Int)

    suspend fun subProduct(id: Int)

    suspend fun getImages() : Resource<ProductResponse>

}