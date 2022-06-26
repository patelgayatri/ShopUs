package com.techand.shopus.util

import android.accounts.NetworkErrorException
import android.app.Activity
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.techand.shopus.data.network.Resource

fun Fragment.handleApiError(
    failure: Resource.Error,
    retry: (() -> Unit)? = null
) {
    when(failure.exception) {
        is NetworkErrorException -> requireView().snackbar(
            "Please check your internet connection",
            retry
        )

        else -> {
            println("=========${failure.exception}")
            val error = failure.exception.message.toString()
            requireView().snackbar(error)
        }
    }
}

fun View.snackbar(message: String, action: (() -> Unit)? = null) {
    val snackbar = Snackbar.make(this, message, Snackbar.LENGTH_LONG)
    action?.let {
        snackbar.setAction("Retry") {
            it()
        }
    }
    snackbar.show()
}

fun Activity.showBack(show: Boolean, name: String) {
    (this as AppCompatActivity).supportActionBar
        ?.setDisplayHomeAsUpEnabled(show)
    this.supportActionBar?.title = name

}