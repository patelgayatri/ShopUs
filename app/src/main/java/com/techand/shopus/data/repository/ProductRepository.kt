package com.techand.shopus.data.repository

import com.techand.daggerapp.data.repository.ProductBaseRepository
import com.techand.shopus.data.local.MyCart
import com.techand.shopus.data.local.MyCartDao
import com.techand.shopus.data.models.ProductResponse
import com.techand.shopus.data.network.ApiService
import com.techand.shopus.data.network.Resource
import com.techand.shopus.data.network.SafeApiCall
import javax.inject.Inject


open class ProductRepository @Inject constructor(
    private val apiService: ApiService,
    private val myCartDao: MyCartDao
) : ProductBaseRepository,SafeApiCall{

    override suspend fun insertToMyCart(product: MyCart) {
        myCartDao.insertToMyCart(product)
    }

    override suspend fun deleteProduct(id: Int) {
        myCartDao.deleteProduct(id)
    }

    override suspend fun getCartTotal(): Int? {
        return myCartDao.getCartTotal()
    }

    override suspend fun getTotalMoney(): Int? {
        return myCartDao.getTotalMoney()
    }

    override suspend fun getMyCart(): List<MyCart> {
       return myCartDao.getMyCart()
    }

    override suspend fun getQuantity(id: Int): Int? {
        return myCartDao.getQuantity(id)
    }

    override suspend fun addProduct(id: Int) {
        myCartDao.addProduct(id)
    }

    override suspend fun subProduct(id: Int) {
        myCartDao.subProduct(id)
    }

    override suspend fun getImages(): Resource<ProductResponse> = safeApiCall{
         apiService.getImages()
    }

//    suspend fun getImages() = safeApiCall {
//        apiService.getImages()
//    }
//
//    suspend fun getMyCart() =
//        withContext(Dispatchers.IO) {
//            myCartDao.getMyCart()
//        }
//    suspend fun getCartTotal() = withContext(Dispatchers.IO){
//        myCartDao.getCartTotal()
//    }
//    suspend fun getMoneyTotal() = withContext(Dispatchers.IO){
//        myCartDao.getTotalMoney()
//    }
//
//    suspend fun getQuantity(id: Int) =
//        withContext(Dispatchers.IO) {
//            myCartDao.getQuantity(id)
//        }
//
//    suspend fun insertToCart(myCart: MyCart) = withContext(Dispatchers.IO) {
//        myCartDao.insertToMyCart(myCart)
//    }
//
//    suspend fun addProduct(id: Int) = withContext(Dispatchers.IO) {
//        myCartDao.addProduct(id)
//    }
//
//    suspend fun subProduct(id: Int) = withContext(Dispatchers.IO) {
//        myCartDao.subProduct(id)
//    }
//
//    suspend fun deleteProduct(id: Int) = withContext(Dispatchers.IO) {
//        myCartDao.deleteProduct(id)
//    }
}