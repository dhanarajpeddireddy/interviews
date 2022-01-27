package com.dana.myapplication.network

import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.pivincii.livedata_retrofit.model.RestApi
import com.pivincii.livedata_retrofit.network.ApiResponse
import com.pivincii.livedata_retrofit.network.ApiSuccessResponse
import com.pivincii.livedata_retrofit.network.RetrofitClient
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class RestRepo  {
    interface IRestRepo {
        fun success(status: Boolean)
        fun fail()
    }

    fun validate(callback: IRestRepo?, panNumber: String?) {
        val retrofit: Retrofit = RetrofitClient().getRetrofit()
        val api: RestApi = retrofit.create(RestApi::class.java)


        GlobalScope.launch {
            val call: Call<Boolean> = api.panValidate(panNumber)


            call.enqueue(object : Callback<Boolean> {

                override fun onFailure(call: Call<Boolean>, t: Throwable) {
                    callback?.fail()
                }

                override fun onResponse(call: Call<Boolean>, response: Response<Boolean>) {
                    callback?.success((response.body()!!))
                }
            })

        }


        }





}