package com.ebdaa.katkot.pojo.model

data class QueryPost(
        val SessionID: String,
        val FormName: String,
        val Name: String,
        val Args: String,
        val ExtraInfo: ArrayList<String>
)