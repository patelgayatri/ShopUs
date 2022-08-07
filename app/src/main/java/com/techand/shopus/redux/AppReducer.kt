package com.techand.shopus.redux
class AppReducer {
    fun reducer(action: Action, state: AppState): AppState {
        return when (action) {
            is ClearView -> state.copy(user = User("", null))
            is UserData -> state.copy(user = User(action.data, null))
            is UserError -> state.copy(user = User("", action.error))
            else -> state
        }
    }
}