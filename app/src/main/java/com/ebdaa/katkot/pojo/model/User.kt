package com.ebdaa.katkot.pojo.model

data class User(
    val UserType: String, // 1-Admin  2-Doctor  3-User
    val FullName: String,
    val PhoneNumber: String,
    val Password: String,
    val DeviceID: String,
    val TokenID: String
)