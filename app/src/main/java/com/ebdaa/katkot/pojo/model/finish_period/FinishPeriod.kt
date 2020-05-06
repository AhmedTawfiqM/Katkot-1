package com.ebdaa.katkot.pojo.model.finish_period

class FinishPeriod//
(var katkotsData: SelledKatkotsData?, var itemPrice: Double) {

    var id: Int = 0         // Primary Key of This Table
    var idPeriod: Int = 0   // Primary Key
    var idFarm: Int = 0     // Primary Key
    var remainingPrice: RemainingPrice? = null
    var dateTime: String? = null // Date of Record
}
