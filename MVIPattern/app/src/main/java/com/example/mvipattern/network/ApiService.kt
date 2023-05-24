package com.example.mvipattern.network

import com.example.mvipattern.models.EntriesResponse
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {

    @GET("entries")
    suspend fun getEntries() : Response<EntriesResponse>
}