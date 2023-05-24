package com.example.mvipattern.viewstates

sealed class APIViewState{
    object Loading : APIViewState()
    data class Success<out T>(val result: T) : APIViewState()
    data class Error(val error: String) : APIViewState()
}