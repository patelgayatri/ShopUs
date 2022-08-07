package com.techand.shopus.redux

data class AppState(val user : User)

data class User(val data:String, val error: Int?)

//data class Error(val error:Int)
