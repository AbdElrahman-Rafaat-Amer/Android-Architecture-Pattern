package com.example.mvipattern.repository

import com.example.mvipattern.models.EntriesResponse
import com.example.mvipattern.network.APiClient
import retrofit2.Response

class MainRepository constructor(private val apiService: APiClient) {

    companion object {
        private var weatherRepo: MainRepository? = null
        fun getInstance(
            remoteSource: APiClient
        ): MainRepository {

            return weatherRepo ?: MainRepository(remoteSource)
        }
    }

    suspend fun getData(): Response<EntriesResponse> {
        return apiService.getData()
    }
}