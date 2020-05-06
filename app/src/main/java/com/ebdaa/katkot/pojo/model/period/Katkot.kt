package com.ebdaa.katkot.pojo.model.period

class Katkot(var nameImporter: String?, var company: String?, var nameDriver: String?, var phoneNumDriver: String?, var dateReciving: String?, var nameStrain: String?   // لسلالة
             , var totalNumKatakit: Int, var transCost: Double, var middleWeightOfKatkot: Double, var priceKatkot: Double, var totalPriceKatkot: Double  // ## Show for User
             , var nameDoctor: String?, var nameEngineer: String?) {

    var id: Int = 0         // Primary Key of This Table
    var idPeriod: Int = 0   // Primary Key
    var idFarm: Int = 0     // Primary Key
    var dateTime: String? = null // Date of Record
}
