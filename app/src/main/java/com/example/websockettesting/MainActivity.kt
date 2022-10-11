package com.example.websockettesting

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.websockettesting.data.ws.Data
import com.example.websockettesting.data.ws.SocketModel
import com.example.websockettesting.data.ws.WebSocketViewModel
import com.tinder.scarlet.WebSocket
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel: WebSocketViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        lifecycleScope.launchWhenStarted {
            viewModel.connectionEvent.collectLatest{
                when(it)
                {
                    is WebSocket.Event.OnConnectionOpened<*> -> {
                        Log.d("TAG", "onCreate: OnConnectionOpened")

                        viewModel.sendBaseModel(SocketModel(Data("live_trades_btcusd"),"bts:subscribe"))
                    }
                    is WebSocket.Event.OnConnectionClosing -> {
                        Log.d("TAG", "onCreate: OnConnectionClosing")

                    }
                    is WebSocket.Event.OnConnectionClosed -> {
                        Log.d("TAG", "onCreate: OnConnectionClosed")

                    }
                    is WebSocket.Event.OnConnectionFailed -> {
                        Log.d("TAG", "onCreate: OnConnectionFailed")

                    }
                }
            }
        }

        lifecycleScope.launchWhenStarted {
            viewModel.socketEvent.collectLatest{
                Log.d("TAG", "onCreate: $it")
            }
        }
    }
}