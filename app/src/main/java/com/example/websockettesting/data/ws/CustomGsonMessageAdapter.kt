package com.example.websockettesting.data.ws

import com.google.gson.Gson
import com.tinder.scarlet.Message
import com.tinder.scarlet.MessageAdapter
import java.lang.reflect.Type

@Suppress("UNCHECKED_CAST")
class CustomGsonMessageAdapter<T> private constructor(
    val gson: Gson
):MessageAdapter<T>{
    override fun fromMessage(message: Message): T {
        val value = when(message){
            is Message.Text -> message.value
            is Message.Bytes -> message.value.toString()
        }

        val obj = gson.fromJson(value, SocketResponse::class.java)
        return obj as T
    }

    override fun toMessage(data: T): Message {
        val convertedData = data as SocketModel
        return Message.Text(gson.toJson(convertedData))
    }

    class Factory(private val gson: Gson):MessageAdapter.Factory{
        override fun create(type: Type, annotations: Array<Annotation>): MessageAdapter<*> {
            return CustomGsonMessageAdapter<Any>(gson)
        }


    }
}
