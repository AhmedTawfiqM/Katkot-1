package com.ebdaa.katkot.pojo.model.period

class FeedCost(var feedTypes: Array<String>?, var companyName: String?, var quantityByKilo: Double, var kiloPrice: Double, var total: Double) {

    var id: Int = 0         // Primary Key of This Table
    var idPeriod: Int = 0   // Primary Key
    var idFarm: Int = 0     // Primary Key
    var dateTime: String? = null // Date of Record
}
