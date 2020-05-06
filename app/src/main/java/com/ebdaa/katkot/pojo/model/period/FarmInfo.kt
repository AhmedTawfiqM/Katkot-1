package com.ebdaa.katkot.pojo.model.period

class FarmInfo(var numRoom: Int, var typeRoom: String?, var nameAdminRoom: String?) {

    var id: Int = 0         // Primary Key of This Table
    var idPeriod: Int = 0   // Primary Key
    var idFarm: Int = 0     // Primary Key
    var dateTime: String? = null // Date of Record
}
