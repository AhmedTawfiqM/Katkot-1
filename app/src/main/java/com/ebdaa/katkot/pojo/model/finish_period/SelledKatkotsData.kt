package com.ebdaa.katkot.pojo.model.finish_period

class SelledKatkotsData(var type: String?, var date: String?, var totalWeight: Double, var totalNumbKatkots: Double, var middleWeightKatkots: Double   // ## show For User
                        , var totalPrice: Double) {

    var id: Int = 0         // Primary Key of This Table
    var idPeriod: Int = 0   // Primary Key
    var idFarm: Int = 0     // Primary Key
    var dateTime: String? = null // Date of Record
}
