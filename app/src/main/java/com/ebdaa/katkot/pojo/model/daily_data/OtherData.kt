package com.ebdaa.katkot.pojo.model.daily_data

class OtherData(var nasriat: Double, var dialyCost: Double, var total: Double               // ## for User
                , var percentTotal: Double        // ## for User
                , var weeklyWeight: Double, //
                var totalByKatotOrKilo: String?  // ## for User
                , var totalForUser: Double        // ## for User
) {

    var id: Int = 0         // Primary Key of This Table
    var idPeriod: Int = 0   // Primary Key
    var idFarm: Int = 0     // Primary Key
    var dateTime: String? = null // Date of Record
}
