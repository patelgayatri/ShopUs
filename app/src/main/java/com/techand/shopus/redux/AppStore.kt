package com.techand.shopus.redux

interface Store {
    fun getState(): AppState
    fun dispatch(action: Action)
    fun subscribe(listener: StateChangeListener)
    fun unSubscribe(listener: StateChangeListener)
}

interface StateChangeListener {
    fun onUpdate(state: AppState)
}

class AppStore(val initialState: AppState, val reducers: List<AppReducer>) : Store {
   lateinit var currentState : AppState
    //    override fun dispatch(action: Action) {
//        val newState = applyReducers(action)
//        currentState = newState
//        // .. notify listeners of the state change.
//    }
//
    private fun applyReducers(action: Action): AppState {
        var state = currentState
        for (reducer in reducers) {
            state = reducer.reducer(action, state)
        }

        return state
    }

    override fun dispatch(action: Action) {
        val newState = applyReducers(action)
        currentState = newState
    }

    override fun getState(): AppState {
        TODO("Not yet implemented")
    }


    override fun subscribe(listener: StateChangeListener) {
        TODO("Not yet implemented")
    }

    override fun unSubscribe(listener: StateChangeListener) {
        TODO("Not yet implemented")
    }
}