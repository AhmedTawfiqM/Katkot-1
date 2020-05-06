package com.ebdaa.katkot.pojo.model.daily_data

class Medicines(var name: String?, var companyName: String?, var itemType: String?, var numberItems: Int, var priceItem: Double, var total: Double) {

    var id: Int = 0         // Primary Key of This Table
    var idPeriod: Int = 0   // Primary Key
    var idFarm: Int = 0     // Primary Key
    var dateTime: String? = null // Date of Record
}
