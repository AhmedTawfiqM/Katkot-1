package com.ebdaa.katkot.pojo.model

data class NewBlock(
        val SessionID: String,
        val FormName: String,
        val Name: String,
        val Args: String,
        val ExtraInfo: ArrayList<String>
)