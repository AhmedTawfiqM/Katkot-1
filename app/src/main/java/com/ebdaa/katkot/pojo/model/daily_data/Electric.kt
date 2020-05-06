package com.ebdaa.katkot.pojo.model.daily_data

class Electric(var month: String?, var cost: Double) {

    var id: Int = 0         // Primary Key of This Table
    var idPeriod: Int = 0   // Primary Key
    var idFarm: Int = 0     // Primary Key
    var dateTime: String? = null // Date of Record
}
