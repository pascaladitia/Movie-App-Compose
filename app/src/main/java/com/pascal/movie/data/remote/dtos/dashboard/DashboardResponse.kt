package com.pascal.movie.data.remote.dtos.dashboard

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DashboardResponse(

	@SerialName("code")
	val code: Int? = null,

	@SerialName("success")
	val success: Boolean? = null,

	@SerialName("message")
	val message: String? = null
)