package com.ebdaa.katkot.ui.farms

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.ebdaa.katkot.R
import com.ebdaa.katkot.pojo.adapters.AdapterFarms
import com.ebdaa.katkot.pojo.model.FarmResponse
import com.ebdaa.katkot.pojo.model.RootBodyQeury
import com.ebdaa.katkot.pojo.repos.GetListRepo
import com.ebdaa.katkot.pojo.utils.security.Encrypted
import com.ebdaa.katkot.utils.SharedPref
import com.ebdaasoft.doctorapp_doctors.pojo.utils.Dialogs
import com.ebdaasoft.doctorapp_doctors.pojo.utils.Queries
import com.google.gson.Gson
import com.google.gson.JsonObject
import kotlinx.android.synthetic.main.activity_all_farms.*
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.Response

class AllFarms : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_all_farms)

        supportActionBar!!.setHomeButtonEnabled(true)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        getFarms()
    }

    private fun getFarms() {

        GetListRepo.getList(initQuery()).observe(this, Observer {

            if (it.isSuccessful)
                processResult(it)
        })
    }

    private fun processResult(response: Response<JsonObject>?) {

        Log.d("okhttp", response!!.body().toString())

        val jsonResult =
                JSONObject(JSONObject(Gson().toJson(response.body())).getString("GetListResult"))
        Log.d("debugL::", "success ResultCode -> ${jsonResult.getString("ResultCode")}")

        val ResultCode = jsonResult.getString("ResultCode")

        if (ResultCode.equals("0")) {
            val ResultDescription = jsonResult.getString("ResultDescription")
            Dialogs.showDialog(this, ResultDescription)
            //return
        } else {
            val farmsArray = JSONArray(jsonResult.getString("ResultBody"))

            val allFarms: ArrayList<FarmResponse> = ArrayList()
            for (i in 0..farmsArray.length() - 1)
                allFarms.add(
                        Gson().fromJson(
                                farmsArray.get(i).toString(),
                                FarmResponse::class.java
                        )
                )
            //End Loop
            setAdapterFarms(allFarms)

        }

    }

    private fun setAdapterFarms(allFarms: java.util.ArrayList<FarmResponse>) {

        rvFarms.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        rvFarms.adapter = AdapterFarms(this, allFarms)
    }

    private fun initQuery(): RootBodyQeury {

        return RootBodyQeury(
                Encrypted.funEncryption(SharedPref.getDataByType(this, SharedPref.SeSSIonID)),
                Queries.getAllFarms(this)
        )
    }//...........

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (item.itemId == android.R.id.home) {// app icon in action bar clicked; goto parent activity.
            onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}
