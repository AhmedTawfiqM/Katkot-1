package com.ebdaa.katkot.utils

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.util.Log

class SharedPref {

    companion object {

        val PREF_FILE_NAME = "SharedPref"
        //
        val CLIENTID = "CLIENTID"
        val SeSSIonID = "SeSSIonID"
        val TOKEN_ID = "TOKEN_ID"
        val FULL_NAME = "FULL_NAME"
        val ISUSER_LOGGINNED = "ISUSER_LOGGINNED"
        //
        fun setDataByType(context: Context, TYPE: String, data: String) {

            val prefs = context.getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE)
            prefs.edit().putString(TYPE, data).apply()
        }

        fun getDataByType(context: Context, TYPE: String): String? {
            val prefs = context.getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE)
            return prefs.getString(TYPE, "")
        }
        // fullName
        fun isUserLogginned(context: Context): Boolean? {

            val prefs = context.getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE)
            return prefs.getBoolean(ISUSER_LOGGINNED, false)
        }
        fun setIsUserLogginned(context: Context) {

            val prefs = context.getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE)
            prefs.edit().putBoolean(ISUSER_LOGGINNED, true).apply()

        }

        fun getTokenID(context: Context): String? {
            return context.getSharedPreferences("_", Context.MODE_PRIVATE)
                .getString(TOKEN_ID, "empty")
        }
    }

}