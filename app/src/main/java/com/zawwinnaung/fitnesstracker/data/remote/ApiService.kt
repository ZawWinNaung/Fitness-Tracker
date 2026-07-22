package com.zawwinnaung.fitnesstracker.data.remote

import com.zawwinnaung.fitnesstracker.data.dto.BaseResponse
import com.zawwinnaung.fitnesstracker.data.dto.UserDto
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

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

    @FormUrlEncoded
    @POST("update_profile.php")
    suspend fun updateProfile(
        @Field("user_id") userId: Int,
        @Field("dob") dob: String,
        @Field("sex") sex: String
    ): BaseResponse<UserDto>

    @GET("get_user.php")
    suspend fun getUser(
        @Query("user_id") userId: Int,
    ): BaseResponse<UserDto>
}