package com.techand.shopus.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.techand.shopus.data.local.MyCart
import com.techand.shopus.data.models.ProductResponse
import com.techand.shopus.data.network.Resource
import com.techand.shopus.data.repository.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val repository: ProductRepository) : ViewModel() {

    private val _products: MutableLiveData<Resource<ProductResponse>> = MutableLiveData()
    val products: LiveData<Resource<ProductResponse>> = _products

    private var _cartData: MutableLiveData<List<MyCart>> = MutableLiveData()
    val cartLiveData: LiveData<List<MyCart>> = _cartData

    private var _quantity: MutableLiveData<Int> = MutableLiveData()
    val quantity: LiveData<Int> = _quantity

    private var _cartTotal: MutableLiveData<Int> = MutableLiveData()
    val cartTotal: LiveData<Int> = _cartTotal

    private var _totalAmount: MutableLiveData<Int> = MutableLiveData()
    val totalAmount: LiveData<Int> = _totalAmount

    init {
        getImages()
        getMyCart()
    }

     fun getImages() = viewModelScope.launch {
        _products.value = Resource.Loading
        _products.value = repository.getImages()
    }

    fun getMyCart() = viewModelScope.launch {
        _cartData.value = repository.getMyCart()
    }

    fun getQuantity(id: Int) = viewModelScope.launch {
        _quantity.value = repository.getQuantity(id)
    }

    fun insetToCart(myCart: MyCart) = viewModelScope.launch {
        repository.insertToMyCart(myCart)
        getQuantity(myCart.id)
    }

    fun addToCart(id: Int) = viewModelScope.launch {
        repository.addProduct(id)
        getQuantity(id)
        _cartData.value = repository.getMyCart()
        _totalAmount.value = repository.getTotalMoney()

    }

    fun subCart(id: Int) = viewModelScope.launch {
        repository.subProduct(id)
        getQuantity(id)
        _cartData.value = repository.getMyCart()
        _totalAmount.value = repository.getTotalMoney()

    }

    fun deleteProduct(id: Int) = viewModelScope.launch {
        repository.deleteProduct(id)
        getQuantity(id)
        _cartData.value = repository.getMyCart()
        _totalAmount.value = repository.getTotalMoney()
    }

    fun getCartTotal() = viewModelScope.launch {
        _cartTotal.value = repository.getCartTotal()
    }
    fun getTotalMoney() = viewModelScope.launch {
        _totalAmount.value = repository.getTotalMoney()
    }

}