package com.ebdaa.katkot.pojo.model

import com.google.gson.annotations.SerializedName

data class SingleBlock (

		@SerializedName("FarmID") val farmID : String,
		@SerializedName("WardNo") val wardNo : String,
		@SerializedName("WardType") val wardType : String,
		//@SerializedName("WardSupervisorName") val wardSupervisorName : String,
		@SerializedName("WardCapacity") val wardCapacity : String,
		@SerializedName("WardSpace") val wardSpace : String
		//@SerializedName("WaterSource") val waterSource : String,
		//@SerializedName("GasSource") val gasSource : String,
		//@SerializedName("ElectricitySource") val electricitySource : String
)