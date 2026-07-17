package com.zawwinnaung.fitnesstracker.data.remote

import com.zawwinnaung.fitnesstracker.data.dto.LoginResponseDto
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface ApiService {
    @FormUrlEncoded
    @POST("login.php")
    suspend fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ): LoginResponseDto
}