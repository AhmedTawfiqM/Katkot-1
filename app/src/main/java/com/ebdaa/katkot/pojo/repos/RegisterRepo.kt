package com.ebada.embassy.pojo.repo

import android.util.Log
import androidx.lifecycle.LiveData
import com.ebada.embassy.pojo.networking.RetrofitBuilder
import com.ebdaa.katkot.pojo.model.User
import com.google.gson.JsonObject
import kotlinx.coroutines.*
import retrofit2.Response
import java.lang.Exception

object RegisterRepo {

    var job: CompletableJob? = null

    fun registerUser(user: User): LiveData<Response<JsonObject>> {

        job = Job()

        return object : LiveData<Response<JsonObject>>() {

            override fun onActive() {
                super.onActive()

                job?.let { thejob ->
                    try {
                        CoroutineScope(Dispatchers.IO + thejob).launch {

                            val response = RetrofitBuilder.apiService.Register(user)
                            withContext(Dispatchers.Main) {


                                value = response
                                thejob.complete()
                            }
                        }
                        //
                    } catch (ex: Exception) {
                        ex.printStackTrace()
                        Log.d("debugR::", ex.message)
                    }

                }
            }
        }
    }

    fun cancelJob() {
        job!!.cancel()
    }
}