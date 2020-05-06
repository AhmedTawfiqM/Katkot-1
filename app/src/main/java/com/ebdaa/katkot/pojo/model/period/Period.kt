package com.ebdaa.katkot.pojo.model.period

class Period(var idPeriod: Int               // Primary Key of This Table
             , var idFarm: Int                 // Primary Key
             , var blanketCost: BlanketCost?, var cleaningCost: CleaningCost?, var farmInfo: FarmInfo?, var katkot: Katkot?, var warmingCost: WarmingCost?, var workingCost: WorkingCost?, var feedCost: FeedCost?, var otherCost: OtherCost?)
