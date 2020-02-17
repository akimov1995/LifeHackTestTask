package com.example.lifehacktesttask.api

import com.example.lifehacktesttask.mvp.model.Company
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface RestApiService {
    @GET("test.php")
    fun getCompanies(): Call<List<Company>>

    @GET("test.php")
    fun getCompanyById( @Query("id") id: Int): Call<ResponseBody>
}