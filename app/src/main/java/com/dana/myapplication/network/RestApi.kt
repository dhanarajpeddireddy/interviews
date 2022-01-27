package com.pivincii.livedata_retrofit.model

import androidx.lifecycle.LiveData
import com.pivincii.livedata_retrofit.network.ApiResponse
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

interface RestApi {

    @GET("panValidate")
    fun panValidate(@Query("panNumber") panNumber: String?): Call<Boolean>


}