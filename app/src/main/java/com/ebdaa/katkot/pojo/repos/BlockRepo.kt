package com.ebdaa.katkot.pojo.repos

import android.util.Log
import androidx.lifecycle.LiveData
import com.ebada.embassy.pojo.networking.RetrofitBuilder
import com.ebdaa.katkot.pojo.model.NewBlock
import com.google.gson.JsonObject
import kotlinx.coroutines.*
import retrofit2.Response
import java.lang.Exception

object BlockRepo {


    var job: CompletableJob? = null

    fun addBLock(block: NewBlock): LiveData<Response<JsonObject>> {

        GetListRepo.job = Job()

        return object : LiveData<Response<JsonObject>>() {

            override fun onActive() {
                super.onActive()

                GetListRepo.job?.let { thejob ->

                    CoroutineScope(Dispatchers.IO + thejob).launch {

                        val response = RetrofitBuilder.apiService.addBlock(block)
                        withContext(Dispatchers.Main) {

                            try {
                                value = response
                                thejob.complete()
                            } catch (ex: Exception) {
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
        GetListRepo.job!!.cancel()
    }
}