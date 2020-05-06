package com.ebdaa.katkot.pojo.model.period

class CleaningCost(var cleanerName: String?, var companyName: String?, var numPlates: Double, var quantityOfPlates: Double, var priceOfPlate: Double, var total: Double) {

    var id: Int = 0         // Primary Key of This Table
    var idPeriod: Int = 0   // Primary Key
    var idFarm: Int = 0     // Primary Key
    var dateTime: String? = null // Date of Record
}
