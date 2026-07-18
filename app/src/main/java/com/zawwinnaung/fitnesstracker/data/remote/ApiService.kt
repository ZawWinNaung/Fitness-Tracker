package com.zawwinnaung.fitnesstracker.data.remote

import com.zawwinnaung.fitnesstracker.data.dto.BaseResponse
import com.zawwinnaung.fitnesstracker.data.dto.UserDto
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface ApiService {
    @FormUrlEncoded
    @POST("login.php")
    suspend fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ): BaseResponse<UserDto>

    @FormUrlEncoded
    @POST("register.php")
    suspend fun register(
        @Field("user_name") userName: String,
        @Field("email") email: String,
        @Field("password") password: String
    ): BaseResponse<Unit>
}