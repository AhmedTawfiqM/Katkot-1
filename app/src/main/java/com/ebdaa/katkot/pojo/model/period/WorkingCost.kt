package com.ebdaa.katkot.pojo.model.period

class WorkingCost(var workersDataList: List<WorkersData>?, var numMonthsOfPeriod: Int, var numCleaningWorkers: Int, var salaryWorkerCleaner: Double, var total: Double) {


    var id: Int = 0         // Primary Key of This Table
    var idPeriod: Int = 0   // Primary Key
    var idFarm: Int = 0     // Primary Key
    var dateTime: String? = null // Date of Record
}
