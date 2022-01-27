package com.dana.myapplication

import androidx.lifecycle.ViewModel
import com.dana.myapplication.network.RestRepo


class MainActivityViewModel : ViewModel() {
    private val appRepo: AppRepo? = AppRepo.instance
    fun validate(callback: RestRepo.IRestRepo?, panNumber: String?) {
        appRepo!!.validate(callback, panNumber)
    }
}