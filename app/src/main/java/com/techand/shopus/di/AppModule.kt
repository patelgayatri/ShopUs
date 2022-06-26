package com.techand.shopus.di

import android.content.Context
import com.techand.shopus.data.local.AppDatabase
import com.techand.shopus.data.local.MyCartDao
import com.techand.shopus.data.network.ApiService
import com.techand.shopus.data.network.NetworkConnectionInterCeptor
import com.techand.shopus.util.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
object AppModule {


    @Provides
    @Singleton
    fun provideRetrofitInstance(networkConnectionInterCeptor: NetworkConnectionInterCeptor): ApiService {
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(networkConnectionInterCeptor)
            .build()

        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(
                ApiService::
                class.java
            )
    }

    @Singleton
    @Provides
    fun provideMyCartDao(appDatabase: AppDatabase): MyCartDao {
        return appDatabase.getCartDao()
    }

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext appContext: Context) = AppDatabase.invoke(appContext)

}