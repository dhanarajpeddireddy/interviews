package com.dana.myapplication

import com.dana.myapplication.network.RestRepo
import kotlinx.coroutines.DelicateCoroutinesApi

class AppRepo private constructor() {
    private var restRepo: RestRepo = RestRepo()
    @DelicateCoroutinesApi
    fun validate(callback: RestRepo.IRestRepo?, panNumber: String?) {
        restRepo.validate(callback, panNumber)
    }

    companion object {
        var appRepo: AppRepo? = null
        val instance: AppRepo?
            get() {
                if (appRepo == null) {
                    appRepo = AppRepo()
                }
                return appRepo
            }
    }
}