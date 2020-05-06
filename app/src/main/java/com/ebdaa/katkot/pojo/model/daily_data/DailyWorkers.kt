package com.ebdaa.katkot.pojo.model.daily_data

class DailyWorkers(var typeWorker: String?, var numWorkers: Double, var salaryWorker: Double, var total: Double) {

    var id: Int = 0         // Primary Key of This Table
    var idPeriod: Int = 0   // Primary Key
    var idFarm: Int = 0     // Primary Key
    var dateTime: String? = null // Date of Record
}
