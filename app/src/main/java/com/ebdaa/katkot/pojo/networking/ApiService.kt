package com.ebdaasoft.doctorapp_patients.pojo.networking

import com.ebdaa.katkot.pojo.model.QueryPost
import com.ebdaa.katkot.pojo.model.NewBlock
import com.ebdaa.katkot.pojo.model.RootBodyQeury
import com.ebdaa.katkot.pojo.model.User
import com.ebdaasoft.doctorapp_doctors.pojo.model.UserLogged
import com.google.gson.JsonObject
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {

    //http://23.111.143.250:6669/TraderService/GetList?
    @POST("UserLogin")
    suspend fun login(
        @Body login: UserLogged
    ): Response<JsonObject>

    @POST("RegisterUser")
    suspend fun Register(
            @Body user: User
    ): Response<JsonObject>

    @POST("CallMethod")
    suspend fun postData(
            @Body data: QueryPost
    ): Response<JsonObject>

    @POST("GetList")
    suspend fun getList
    (
       @Body rootBody: RootBodyQeury

    ): Response<JsonObject>

    @POST("CallMethod")
    suspend fun addBlock(
            @Body newBlock: NewBlock
    ): Response<JsonObject>

}