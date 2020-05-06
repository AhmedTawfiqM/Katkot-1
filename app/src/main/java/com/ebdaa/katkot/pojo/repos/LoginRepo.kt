package com.ebada.embassy.pojo.repo

import android.util.Log
import androidx.lifecycle.LiveData
import com.ebada.embassy.pojo.networking.RetrofitBuilder
import com.ebdaasoft.doctorapp_doctors.pojo.model.UserLogged
import com.google.gson.JsonObject
import kotlinx.coroutines.*
import retrofit2.Response
import java.lang.Exception

object LoginRepo {


    var job: CompletableJob? = null

    fun loginUser(user: UserLogged): LiveData<Response<JsonObject>> {

        job = Job()

        return object : LiveData<Response<JsonObject>>() {

            override fun onActive() {
                super.onActive()

                job?.let { thejob ->

                    CoroutineScope(Dispatchers.IO + thejob).launch {

                        val response = RetrofitBuilder.apiService.login(user)
                        withContext(Dispatchers.Main) {

                            try {
                                value = response
                                thejob.complete()
                            }catch (ex: Exception){
                                ex.printStackTrace()
                                Log.d("debugL::", ex.message)
                            }
                        }
                    }
                }
            }
        }
    }

    fun cancelJob() {
        job!!.cancel()
    }
}