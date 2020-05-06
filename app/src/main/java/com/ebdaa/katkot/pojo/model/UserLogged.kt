package com.ebdaasoft.doctorapp_doctors.pojo.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class UserLogged(

    val UserType: String,  // 1-Admin 2-Doctor 3- User
    @SerializedName("PhoneNumber")
    @Expose
    val phoneNumber: String,
    @SerializedName("UserName")
    @Expose
    val userName: String,
    @SerializedName("Password")
    @Expose
    val password: String,
    @SerializedName("DeviceID")
    @Expose
    val deviceID: String,
    @SerializedName("BranchName")
    @Expose
    val branchName: String,
    @SerializedName("PCInfo")
    @Expose
    val pCInfo: List<String>,

    val TokenID: String

)

