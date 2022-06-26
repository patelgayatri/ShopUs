package com.techand.shopus.data.network

import com.techand.shopus.data.models.ProductResponse
import com.techand.shopus.util.Constants.END_POINT
import retrofit2.http.GET

interface ApiService {

    @GET(END_POINT)
    suspend fun getImages(): ProductResponse
}