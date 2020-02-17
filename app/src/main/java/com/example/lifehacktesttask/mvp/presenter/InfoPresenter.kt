package com.example.lifehacktesttask.mvp.presenter

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.example.lifehacktesttask.R
import com.example.lifehacktesttask.api.RestApiService
import com.example.lifehacktesttask.application.Application
import com.example.lifehacktesttask.mvp.model.Company
import com.example.lifehacktesttask.mvp.view.InfoView
import com.example.lifehacktesttask.utils.NetworkStatusChecker
import com.google.gson.Gson
import com.google.gson.JsonElement
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import java.lang.StringBuilder
import javax.inject.Inject


@InjectViewState
class InfoPresenter(_id: Int) : MvpPresenter<InfoView>() {

    @Inject
    lateinit var retrofit: Retrofit
    private var id: Int

    init {
        Application.graph.inject(this)
        id = _id
    }

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        loadData()
    }

    private fun loadData() {
        if (NetworkStatusChecker.isNetworkAvailable(Application.context)) {
            val service = retrofit.create(RestApiService::class.java)
            val call = service.getCompanyById(id = id)

            call.enqueue(object : Callback<ResponseBody> {
                override fun onResponse(
                    call: Call<ResponseBody>,
                    response: Response<ResponseBody>
                ) {
                    if (response.code() == 200) {
                        val text: String = response.body()!!.charStream().readText()
                            .removePrefix("[").removeSuffix("]")

                        val subString = "\"description\":\""
                        val startIndex: Int = text.indexOf(subString)
                        val endIndex: Int = text.indexOf("\",\"lat\"")

                        var builder = StringBuilder(text)
                        val string = builder.substring(startIndex + subString.length, endIndex)
                            .replace("\"", "\\\"")
                        builder = builder.replace(startIndex + subString.length, endIndex, string)

                        val gson = Gson()
                        val element: JsonElement =
                            gson.fromJson(builder.toString(), JsonElement::class.java)
                        val jsonObject = element.asJsonObject
                        val company = gson.fromJson(jsonObject, Company::class.java)

                        viewState.showCompanyInfo(company = company)
                    }
                }

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    viewState.showError(t.message.toString())
                }
            })
        } else {
            viewState.showError(Application.context.resources.getString(R.string.not_connection_error_message))
        }
    }
}