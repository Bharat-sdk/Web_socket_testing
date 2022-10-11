package com.example.websockettesting.data.ws

import com.tinder.scarlet.WebSocket
import com.tinder.scarlet.ws.Receive
import com.tinder.scarlet.ws.Send
import kotlinx.coroutines.flow.Flow

interface WebSocketApi {

    @Receive
    fun observeEvent(): Flow<WebSocket.Event>

    @Send
    fun sendModel(socketModel: SocketModel):Boolean

    @Receive
    fun observeReceive():Flow<SocketResponse>
}