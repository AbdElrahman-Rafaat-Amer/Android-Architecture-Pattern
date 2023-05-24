package com.example.mvipattern.network

import com.example.mvipattern.models.EntriesResponse
import retrofit2.Response

class APiClient {

    companion object {
        private var instance: APiClient? = null
        fun getInstance(): APiClient {
            return instance ?: APiClient()
        }
    }

    private val retrofitHelper = RetrofitHelper.getClient().create(ApiService::class.java)

    suspend fun getData(
    ): Response<EntriesResponse> {
        return retrofitHelper.getEntries()
    }

}