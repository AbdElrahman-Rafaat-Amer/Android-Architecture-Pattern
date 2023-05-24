package com.example.mvipattern.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mvipattern.intents.MainIntents
import com.example.mvipattern.viewstates.MainViewState
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    val intentChannel = Channel<MainIntents>(Channel.UNLIMITED)

    private val _viewState = MutableStateFlow<MainViewState>(MainViewState.Idle)
    val viewState: StateFlow<MainViewState> = _viewState

    private var number = 0

    init {
        processIntent()
    }

    //process
    private fun processIntent() {
        viewModelScope.launch {
            intentChannel.consumeAsFlow().collect {
                when (it) {
                    is MainIntents.AddNumber -> {
                        reduceResult(++number)
                    }

                    is MainIntents.AddNumbers -> {
                        number += it.firstNumber
                        reduceResult(number)
                    }
                }
            }
        }
    }

    //reduce
    private fun reduceResult(number : Int) {
        viewModelScope.launch {
            _viewState.value = try {
                MainViewState.Number(number)
            } catch (exception: java.lang.Exception) {
                MainViewState.Error(exception.message ?: "Unknown Error")
            }
        }
    }

}