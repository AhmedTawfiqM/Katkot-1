package com.ebdaasoft.doctorapp_doctors.pojo.utils

import android.content.Context
import com.ebdaa.katkot.pojo.utils.security.Encrypted

object Queries {

    val SELECT_Farms by lazy { "Select ID, TheName From vewKat_Farms Where ClientID=" }
    val SELECT_Blocks by lazy { "Select ID, WardNo From tblKat_Wards Where FarmID=" }

    fun getAllFarms(context: Context): String {
        return Encrypted.funEncryption(SELECT_Farms + SharedPref.getDataByType(context, SharedPref.CLIENTID))
    }

    fun getAllBlocks(farmID: String): String {
        return Encrypted.funEncryption(SELECT_Blocks + farmID)
    }
}