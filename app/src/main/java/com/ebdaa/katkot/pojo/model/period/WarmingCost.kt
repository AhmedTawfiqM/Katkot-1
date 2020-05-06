package com.ebdaa.katkot.pojo.model.period

class WarmingCost(var warningType: String?, var quantity: String?, var price: Double, var total: Double) {

    var id: Int = 0         // Primary Key of This Table
    var idPeriod: Int = 0   // Primary Key
    var idFarm: Int = 0     // Primary Key
    var dateTime: String? = null // Date of Record
}
