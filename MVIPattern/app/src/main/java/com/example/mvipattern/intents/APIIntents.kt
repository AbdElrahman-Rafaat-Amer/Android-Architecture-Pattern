package com.example.mvipattern.intents

sealed class APIIntents {

    object RequestData : APIIntents()
    object Error : APIIntents()

}