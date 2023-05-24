package com.example.mvipattern.viewstates

sealed class MainViewState {

    object Idle : MainViewState()
    data class Number(val number: Int) : MainViewState()
    data class Error(val error: String) : MainViewState()
}