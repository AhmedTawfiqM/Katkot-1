package com.ebdaa.katkot.pojo.repos

import android.util.Log
import androidx.lifecycle.LiveData
import com.ebada.embassy.pojo.networking.RetrofitBuilder
import com.ebdaa.katkot.pojo.model.RootBodyQeury
import com.google.gson.JsonObject
import kotlinx.coroutines.*
import retrofit2.Response
import java.lang.Exception

object GetListRepo {


    var job: CompletableJob? = null

    fun getList(rootBody: RootBodyQeury): LiveData<Response<JsonObject>> {

        job = Job()
        return object : LiveData<Response<JsonObject>>() {

            override fun onActive() {
                super.onActive()
                job?.let { thejob ->

                    CoroutineScope(Dispatchers.IO + thejob).launch {

                        val response = RetrofitBuilder.apiService.getList(rootBody)
                        withContext(Dispatchers.Main) {
                            try {
                                value = response
                                thejob.complete()
                            } catch (ex: Exception) {
                                ex.printStackTrace()
                                //Log.d("debugL::", ex.message)
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