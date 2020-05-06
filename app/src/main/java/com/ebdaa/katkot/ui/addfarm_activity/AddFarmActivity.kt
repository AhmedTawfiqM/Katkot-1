package com.ebdaa.katkot.ui.addfarm_activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.lifecycle.Observer
import com.ebdaa.katkot.R
import com.ebdaa.katkot.pojo.model.QueryPost
import com.ebdaa.katkot.pojo.model.Farm
import com.ebdaa.katkot.pojo.repos.PostDataRepo
import com.ebdaa.katkot.pojo.utils.Converts
import com.ebdaa.katkot.ui.mainactivity.MainActivity
import com.ebdaasoft.doctorapp_doctors.pojo.utils.Consts
import com.ebdaasoft.doctorapp_doctors.pojo.utils.Dialogs
import com.ebdaasoft.doctorapp_doctors.pojo.utils.SharedPref
import com.ebdaasoft.doctorapp_doctors.pojo.utils.Utils
import com.google.gson.Gson
import com.google.gson.JsonObject
import kotlinx.android.synthetic.main.activity_add_farm.*
import org.json.JSONObject
import retrofit2.Response
import java.lang.Exception
import java.util.ArrayList
import java.util.Objects

class AddFarmActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_farm)
        Objects.requireNonNull<ActionBar>(supportActionBar).setHomeButtonEnabled(true)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        //
        btnComplete.setOnClickListener {
            addFarmListner()
        }
    }

    private fun addFarmListner() {

        if (checkValidaty()) {
            //
            if (Utils.isInternetAvailable(this)) {
                //
                try {
                    val dialog = Dialogs.setProgressDialog(this@AddFarmActivity, getString(R.string.sending))
                    dialog.show()

                    PostDataRepo.postData(initFarm()).observe(this, Observer {
                        //
                        dialog.dismiss()
                        if (it.isSuccessful)
                            processResult(it)
                        else
                            Dialogs.showDialogFailTOConnect(this)
                    })
                    //

                } catch (ex: Exception) {
                    ex.printStackTrace()
                }
            } else {
                Dialogs.showDialog(this, "تأكد من اتصالك بالانترنت")
            }
            //
        }
    }

    private fun processResult(response: Response<JsonObject>?) {


        val obj = JSONObject(Gson().toJson(response!!.body()))
        //val jsonResult = jsonObject.getString("CallMethodResult")
        //val obj = JSONObject(jsonResult) //
        Log.d("debugL::", "success ResultCode -> ${obj.getString("ResultCode")}")

        val ResultCode = obj.getString("ResultCode")
        if (ResultCode.equals("0")) {
            val ResultDescription = obj.getString("ResultDescription")
            Dialogs.showDialog(this, ResultDescription)
            //return
        } else {
            //Dialogs.showDialog(this, "تم الاضافة بنجاح")
            Toast.makeText(this, getString(R.string.added), Toast.LENGTH_LONG).show()
            startActivity(Intent(this, MainActivity::class.java)
                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
        }
    }

    private fun initFarm(): QueryPost {

        val farm = Farm(
                et_FarmName.text.toString().trim(),
                spinnerGovern.selectedItem.toString(),
                //etCity.text.toString(),
                etAddress.text.toString()
        )
         //Converts.convertToJson(
        return QueryPost(
                SharedPref.getDataByType(this, SharedPref.SeSSIonID)!!,
                Consts.formNameProject,
                Consts.AddFarm,
                Gson().toJson(farm),
                ArrayList<String>()
        )
    }

    fun checkValidaty(): Boolean {

        var isValidate = true

        if (et_FarmName.text.toString().trim().isEmpty() ||
                et_FarmName.text.toString().trim().isBlank()
        ) {
            isValidate = false
            et_FarmName.setError("")
        }
        if (spinnerGovern.selectedItemPosition == 0) {
            isValidate = false
            spinnerGovern.performClick()
        }
//        if (spinnerCity.selectedItemPosition == 0) {
//            isValidate = false
//            spinnerCity.performClick()
//        }

        if (etAddress.text.toString().trim().isEmpty() ||
                etAddress.text.toString().trim().isBlank()
        ) {
            isValidate = false
            etAddress.setError("")
        }
        /*if (etCity.text.toString().trim().isEmpty() ||
                etCity.text.toString().trim().isBlank()
        ) {
            isValidate = false
            etCity.setError("")
        }*/


        return isValidate
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (item.itemId == android.R.id.home) {// app icon in action bar clicked; goto parent activity.
            onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)

    }
///
}

