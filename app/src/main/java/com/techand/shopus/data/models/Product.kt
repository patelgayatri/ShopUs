package com.techand.shopus.data.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Product(
    val category: String,
    val id: Int,
    val name: String,
    val path: String,
    val price: Int
): Parcelable