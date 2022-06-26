package com.techand.shopus.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface MyCartDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertToMyCart(product: MyCart)

    @Query("DELETE FROM my_cart WHERE id = :id")
    suspend fun deleteProduct(id: Int)

    @Query("Select  sum(quantity) from my_cart")
    suspend fun getCartTotal(): Int?

    @Query("Select sum(quantity * price) from my_cart")
    suspend fun getTotalMoney(): Int?

    @Query("Select * from my_cart")
    suspend fun getMyCart(): List<MyCart>


    @Query("Select quantity from my_cart where id=:id ")
    suspend fun getQuantity(id: Int): Int?

    @Query("Update my_cart SET quantity = quantity + 1 where id=:id ")
    suspend fun addProduct(id: Int)

    @Query("Update my_cart SET quantity = quantity - 1 where id=:id ")
    suspend fun subProduct(id: Int)

}