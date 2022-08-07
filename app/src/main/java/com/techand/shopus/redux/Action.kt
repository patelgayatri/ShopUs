package com.techand.shopus.redux

interface Action

object ClearView: Action

data class UserData(val data:String) : Action

data class UserError(val error:Int) : Action