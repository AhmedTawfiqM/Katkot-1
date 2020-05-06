package com.ebdaa.katkot.pojo.model.daily_data

data class Blocks(var age: String?, var name: String?, var companyName: String?, var place: String?, var blockType: String?, var way: String?,
             var discoverName: String?, var discoverCompany: String?, var notesBefore: String?, var notesAfter: String?, var itemType: String?,
             var priceItem: Double, var total: Double) {

    var id: Int = 0         // Primary Key of This Table
    var idPeriod: Int = 0   // Primary Key
    var idFarm: Int = 0     // Primary Key
    var dateTime: String? = null // Date of Record
}

