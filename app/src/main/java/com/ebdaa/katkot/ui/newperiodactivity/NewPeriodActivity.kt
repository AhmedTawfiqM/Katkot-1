package com.ebdaa.katkot.ui.newperiodactivity

import android.app.DatePickerDialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
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
import kotlinx.android.synthetic.main.activity_new_period.*
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.Response
import java.util.*
import kotlin.collections.ArrayList

class NewPeriodActivity : AppCompatActivity() {

    var allFarms: ArrayList<FarmResponse>? = null
    var allBlocks: ArrayList<WardResponse>? = null
    //
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_period)

        supportActionBar!!.setHomeButtonEnabled(true)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        //
        getAllFarms()
        listnerFarmChoosen()
        addCycleListner()
        listenerDateStartEnd()
    }

    private fun listenerDateStartEnd() {

        //Get CUrrent date
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        //
        val onDateSetListener_TODate = DatePickerDialog.OnDateSetListener { datePicker, year, month, day ->
            tvStartDate.text = year.toString() + "-" + (month + 1) + "-" + day
        }

        tvStartDate.setOnClickListener {
            val datePickerDialog = DatePickerDialog(this,
                    android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                    onDateSetListener_TODate, year, month, day)

            datePickerDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            datePickerDialog.show()
        }
        //
        val onDateSetListener_EndDAte = DatePickerDialog.OnDateSetListener { datePicker, year, month, day ->
            tvEndDate.text = year.toString() + "-" + (month + 1) + "-" + day
        }

        tvEndDate.setOnClickListener {
            val datePickerDialog = DatePickerDialog(this,
                    android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                    onDateSetListener_EndDAte, year, month, day)

            datePickerDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            datePickerDialog.show()
        }
    }

    private fun addCycleListner() {

        btnComplete.setOnClickListener {

            if (checkValidity()) {
                if (Utils.isInternetAvailable(this)) {

                    val dialog = Dialogs.setProgressDialog(this@NewPeriodActivity, " جاري التسجيل.. ")
                    dialog.show()

                    PostDataRepo.postData(initNewPeriod()).observe(this, Observer {

                        dialog.dismiss()
                        if (it.isSuccessful)
                            processResultAddPeriod(it)
                    })

                }//
            }
        }
    }

    private fun processResultAddPeriod(response: Response<JsonObject>?) {

        val jsonResult =
                JSONObject(JSONObject(Gson().toJson(response!!.body())).getString("CallMethodResult"))
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

    private fun initNewPeriod(): QueryPost {

        val cycle = Cycle(
                etPeriodName.text.toString().trim(),
                allFarms!!.get(spinnerFarms.selectedItemPosition).ID,
                allBlocks!!.get(spinnerFarms.selectedItemPosition).ID,
                tvStartDate.text.toString(),
                tvEndDate.text.toString()
        ) //
        return QueryPost(
                SharedPref.getDataByType(this, SharedPref.SeSSIonID)!!,
                Consts.formNameProject,
                Consts.AddCycle,
                Converts.convertToJson(Gson().toJson(cycle)),
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
        if (spinnerNumbBlock.selectedItemPosition == 0
        ) {
            isValidate = false
            spinnerNumbBlock.performClick()
        }

        if (etPeriodName.text.toString().trim().isEmpty() ||
                etPeriodName.text.toString().trim().isBlank()
        ) {
            isValidate = false
            etPeriodName.setError("")
        }

        if (tvStartDate.text.toString().trim().isEmpty() ||
                tvStartDate.text.trim().equals("تاريخ البداية")
        ) {
            isValidate = false
            tvStartDate.setError("")
        }

        if (tvEndDate.text.toString().trim().isEmpty() ||
                tvEndDate.text.trim().equals("تاريخ الإنتهاء")
        ) {
            isValidate = false
            tvEndDate.setError("")
        }

        return isValidate
    }

    private fun listnerFarmChoosen() {

        spinnerFarms.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

                if (position != 0) {
                    getAllPeriods()
                }
            }

        }
    }

    private fun getAllPeriods() {

        GetListRepo.getList(queryWards()).observe(this, Observer {

            if (it.isSuccessful)
                processResultBlocks(it)
        })

    }

    private fun processResultBlocks(response: Response<JsonObject>?) {

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
            val blocksArray = JSONArray(jsonResult.getString("ResultBody"))

            allBlocks = ArrayList()
            allBlocks!!.add(WardResponse("#CHOOSE#", "إختر العنبر"))
            for (i in 0..blocksArray.length() - 1)
                allBlocks!!.add(
                        Gson().fromJson(
                                blocksArray.get(i).toString(),
                                WardResponse::class.java
                        )
                )
            //End Loop
            initSpinnerBlocks()

        }
    }

    private fun initSpinnerBlocks() {

        val names: ArrayList<String> = ArrayList()
        for (pos in 0..allBlocks!!.size - 1) {
            names.add(allBlocks!!.get(pos).WardNo)
            Log.d("name1:", allBlocks!!.get(pos).WardNo)
        }

        val adapter: ArrayAdapter<String> = ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, names)

        spinnerNumbBlock.adapter = adapter

    }

    fun queryWards(): RootBodyQeury {

        return RootBodyQeury(
                Encrypted.funEncryption(SharedPref.getDataByType(this, SharedPref.SeSSIonID)),
                Queries.getAllBlocks(allFarms!!.get(spinnerFarms.selectedItemPosition).ID)
        )
    }

    fun getAllFarms() {

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
    }//..

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
