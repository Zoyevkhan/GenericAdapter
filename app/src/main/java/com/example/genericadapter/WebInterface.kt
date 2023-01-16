package com.example.genericadapter


import com.example.genericadapter.model.LoginResponse
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface WebInterface {


    @FormUrlEncoded
    @POST(ApiConstants.login)
  suspend  fun API_USER_LOGIN_AUTHENTICATION(
        @Field("email") EMAIL: String,
        @Field("password") PASSWORD: String,
        @Field("is_social") IS_SOCIAL: String,
        @Field("social_type") SOCIAL_TYPE: String,
        @Field("social_tokken") SOCIAL_TOKEN: String,
        @Field("device_type") DEVICE_TYPE: String,
        @Field("location") Location: String,
        @Field("device_id") DEVICE_id: String,
        @Field("device_tokken") DEVICE_TOKEN: String
    ): Response<LoginResponse>
}