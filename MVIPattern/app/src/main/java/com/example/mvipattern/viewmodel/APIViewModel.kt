package com.example.mvipattern.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mvipattern.intents.APIIntents
import com.example.mvipattern.models.EntriesResponse
import com.example.mvipattern.network.APiClient
import com.example.mvipattern.repository.MainRepository
import com.example.mvipattern.viewstates.APIViewState
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch
import retrofit2.Response

class APIViewModel : ViewModel() {
    private var repository = MainRepository.getInstance(APiClient.getInstance())

    val intentChannel = Channel<APIIntents>(Channel.UNLIMITED)

    private var _viewState = MutableStateFlow<APIViewState>(APIViewState.Loading)
    var viewState: StateFlow<APIViewState> = _viewState

   /* private var _viewState = MutableLiveData<APIViewState>()
    var viewState: LiveData<APIViewState> = _viewState*/

    init {
        getEntries()
    }

    //process
    private fun getEntries() {
        _viewState.value = APIViewState.Loading
        viewModelScope.launch {
            intentChannel.consumeAsFlow().collect { intents ->
                when (intents) {
                    is APIIntents.RequestData -> {
                        val response = repository.getData()
                        reduceData(response)
                    }

                    is APIIntents.Error -> {
                        _viewState.value = APIViewState.Error("Unknown Error")
                    }
                }
            }

        }
    }

    private fun reduceData(response: Response<EntriesResponse>) {
        viewModelScope.launch {
            if (response.code() == 200) {
                _viewState.value = APIViewState.Success(response.body())
            } else {
                _viewState.value = APIViewState.Error(response.errorBody().toString())
            }
        }
    }
}