package com.example.websockettesting.data.ws

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tinder.scarlet.WebSocket
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WebSocketViewModel @Inject constructor(
    private val webSocketApi: WebSocketApi
):ViewModel() {
    private val connectionEventChannel = Channel<WebSocket.Event>()
    val connectionEvent = connectionEventChannel.receiveAsFlow().flowOn(Dispatchers.IO)

    private val socketEventChannel = Channel<SocketResponse>()
    val socketEvent = socketEventChannel.receiveAsFlow().flowOn(Dispatchers.IO)

    fun observeEvents() {
        viewModelScope.launch(Dispatchers.IO) {
            webSocketApi.observeEvent().collect { event ->
                connectionEventChannel.send(event)
            }
        }
    }

    fun observeBaseModels() {
        viewModelScope.launch(Dispatchers.IO) {
            webSocketApi.observeReceive().collect { data ->
                socketEventChannel.send(data)
            }
        }
    }

    fun sendBaseModel(data: SocketModel) {
        viewModelScope.launch(Dispatchers.IO) {
            webSocketApi.sendModel(data)
        }
    }

    init {
        observeEvents()
        observeBaseModels()
    }
}