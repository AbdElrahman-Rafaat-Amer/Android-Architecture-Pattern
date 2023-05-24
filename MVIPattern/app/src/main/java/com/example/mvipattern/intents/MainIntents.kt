package com.example.mvipattern.intents

sealed class MainIntents {

    object AddNumber : MainIntents()
    data class AddNumbers(val firstNumber: Int) : MainIntents()
}