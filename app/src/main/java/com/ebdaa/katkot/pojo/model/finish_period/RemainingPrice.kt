package com.ebdaa.katkot.pojo.model.finish_period

class RemainingPrice(var remainingType: String?, var quantity: Double, var totalPrice: Double) {

    var id: Int = 0         // Primary Key of This Table
    var idPeriod: Int = 0   // Primary Key
    var idFarm: Int = 0     // Primary Key
    var dateTime: String? = null // Date of Record
}
