package com.example.websockettesting.data.ws

import com.google.gson.annotations.SerializedName

data class SocketModel(

	@field:SerializedName("data")
	val data: Data? = null,

	@field:SerializedName("event")
	val event: String? = null
)

data class Data(

	@field:SerializedName("channel")
	val channel: String? = null
)
