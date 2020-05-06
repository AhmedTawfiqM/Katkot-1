package com.ebdaa.katkot.ui.addblock_activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.ebdaa.katkot.R
import com.ebdaa.katkot.pojo.model.*
import com.ebdaa.katkot.pojo.repos.BlockRepo
import com.ebdaa.katkot.pojo.repos.GetListRepo
import com.ebdaa.katkot.pojo.repos.PostDataRepo
import com.ebdaa.katkot.pojo.utils.Converts
import com.ebdaa.katkot.pojo.utils.security.Encrypted
import com.ebdaa.katkot.ui.mainactivity.MainActivity
import com.ebdaa.katkot.utils.SharedPref
import com.ebdaa.katkot.utils.Utils
import com.ebdaasoft.doctorapp_doctors.pojo.utils.Consts
import com.ebdaasoft.doctorapp_doctors.pojo.utils.Dialogs
import com.ebdaasoft.doctorapp_doctors.pojo.utils.Queries
import com.google.gson.Gson
import com.google.gson.JsonObject
import kotlinx.android.synthetic.main.activity_add_block.*
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.Response


class AddWardActivity : AppCompatActivity() {

    var allFarms: ArrayList<FarmResponse>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_block)

        supportActionBar!!.setHomeButtonEnabled(true)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        getAllFarms()
        addNewBlockListner()
    }

    private fun addNewBlockListner() {

        btnComplete.setOnClickListener {

            if (checkValidity()) {
                if (Utils.isInternetAvailable(this)) {

                    val dialog = Dialogs.setProgressDialog(this@AddWardActivity, getString(R.string.sending))
                    dialog.show()

                    PostDataRepo.postData(initNewBlock()).observe(this, Observer {

                        dialog.dismiss()
                        if (it.isSuccessful)
                            processResultAddBlock(it)
                    })

                }//
            }
        }
    }

    private fun processResultAddBlock(response: Response<JsonObject>?) {

        //val jsonResult = JSONObject(JSONObject(Gson().toJson(response!!.body())).getString("CallMethodResult"))
        val jsonResult = JSONObject(Gson().toJson(response!!.body()))
        Log.d("debugL::", "success ResultCode -> ${jsonResult.getString("ResultCode")}")

        val ResultCode = jsonResult.getString("ResultCode")

        if (ResultCode.equals("0")) {
            val ResultDescription = jsonResult.getString("ResultDescription")
            Dialogs.showDialog(this, ResultDescription)
            //return
        } else {
            Toast.makeText(this, "تم الإضافة بنجاح", Toast.LENGTH_LONG).show()
            startActivity(Intent(this, MainActivity::class.java)
                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))

        }
    }

    private fun initNewBlock(): QueryPost {

        val farmName: String = allFarms!!.get(spinnerFarms.selectedItemPosition).ID
        val block = SingleBlock(
                farmName,
                //spinnerNumbBlock.selectedItem.toString(),
                etWardName.text.toString(),
                spinnBlockType.selectedItem.toString(),
                //etSupervisorName.text.toString(),
                etBlockSize.text.toString(),
                etBlockSpace.text.toString()
                /*spinnWaterSource.selectedItem.toString(),
                spinnGasSource.selectedItem.toString(),
                spinnElectricitySource.selectedItem.toString()*/

        )
        //Converts.convertToJson(
        return QueryPost(
                SharedPref.getDataByType(this, SharedPref.SeSSIonID)!!,
                Consts.formNameProject,
                Consts.AddBlock,
                Gson().toJson(block),
                ArrayList<String>()
        )
    }

    private fun checkValidity(): Boolean {


        var isValidate = true

        if (spinnerFarms.selectedItemPosition == 0
        ) {
            isValidate = false
            spinnerFarms.performClick()
        }
        if (etWardName.text.toString().trim().isEmpty() ||
                etWardName.text.toString().trim().isBlank()
        ) {
            isValidate = false
            etWardName.setError("")
        }
        /*if (spinnerNumbBlock.selectedItemPosition == 0
        ) {
            isValidate = false
            spinnerNumbBlock.performClick()
        }*/
        if (spinnBlockType.selectedItemPosition == 0
        ) {
            isValidate = false
            spinnBlockType.performClick()
        }

        /*if (etSupervisorName.text.toString().trim().isEmpty() ||
                etSupervisorName.text.toString().trim().isBlank()
        ) {
            isValidate = false
            etSupervisorName.setError("")
        }*/
        if (etBlockSize.text.toString().trim().isEmpty() ||
                etBlockSize.text.toString().trim().isBlank()
        ) {
            isValidate = false
            etBlockSize.setError("")
        }

        if (etBlockSpace.text.toString().trim().isEmpty() ||
                etBlockSpace.text.toString().trim().isBlank()
        ) {
            isValidate = false
            etBlockSpace.setError("")
        }

        /*if (spinnWaterSource.selectedItemPosition == 0
        ) {
            isValidate = false
            spinnWaterSource.performClick()
        }
        if (spinnGasSource.selectedItemPosition == 0
        ) {
            isValidate = false
            spinnGasSource.performClick()
        }
        if (spinnElectricitySource.selectedItemPosition == 0
        ) {
            isValidate = false
            spinnElectricitySource.performClick()
        }*/

        return isValidate
    }

    private fun getAllFarms() {

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

            allFarms = ArrayList()
            allFarms!!.add(FarmResponse("#CHOOSE#", "إختر المزرعة"))
            for (i in 0..farmsArray.length() - 1)
                allFarms!!.add(
                        Gson().fromJson(
                                farmsArray.get(i).toString(),
                                FarmResponse::class.java
                        )
                )
            //End Loop
            initSpinnerFarms(allFarms!!)

        }

    }

    private fun initSpinnerFarms(allFarms: java.util.ArrayList<FarmResponse>) {

        val names: ArrayList<String> = ArrayList()
        for (pos in 0..allFarms.size - 1) {
            names.add(allFarms.get(pos).TheName)
            Log.d("name1:", allFarms.get(pos).TheName)
        }

        val adapter: ArrayAdapter<String> = ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, names)

        spinnerFarms.adapter = adapter

    }

    fun initQuery(): RootBodyQeury {

        return RootBodyQeury(
                Encrypted.funEncryption(SharedPref.getDataByType(this, SharedPref.SeSSIonID)),
                Queries.getAllFarms(this)
        )
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (item.itemId == android.R.id.home) {// app icon in action bar clicked; goto parent activity.
            onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

}
