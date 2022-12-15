package com.kgk.task1.network

import com.kgk.task1.ui.ListData
import io.reactivex.Observable
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url

interface APIMethods {

    @GET
    fun getData(@Url url: String): Observable<Response<List<ListData>>>

}