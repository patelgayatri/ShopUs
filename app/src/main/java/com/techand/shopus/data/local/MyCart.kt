package com.techand.shopus.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "my_cart")
data class MyCart(
    @PrimaryKey(autoGenerate = false)
    var id: Int,
    var name: String,
    var path: String,
    var price: Int,
    var quantity: Int
)