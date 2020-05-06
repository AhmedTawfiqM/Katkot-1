package com.ebdaa.katkot.ui.signup

import androidx.appcompat.app.AppCompatActivity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.lifecycle.Observer
import com.ebada.embassy.pojo.repo.RegisterRepo

import com.ebdaa.katkot.R
import com.ebdaa.katkot.pojo.model.User
import com.ebdaa.katkot.pojo.utils.security.DeviceID
import com.ebdaa.katkot.pojo.utils.security.Encrypted
import com.ebdaasoft.doctorapp_doctors.pojo.utils.Dialogs
import com.ebdaasoft.doctorapp_doctors.pojo.utils.SharedPref
import com.ebdaasoft.doctorapp_doctors.pojo.utils.Utils
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.iid.InstanceIdResult
import com.google.gson.Gson
import com.google.gson.JsonObject
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_sign_up.*
import kotlinx.android.synthetic.main.activity_sign_up.et_Pass
import org.json.JSONObject
import retrofit2.Response
import java.lang.Exception

import java.util.Objects

class SignUpActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Objects.requireNonNull<ActionBar>(supportActionBar).hide() //<< this
        setContentView(R.layout.activity_sign_up)

        setTokenID()
        //
        btn_Register.setOnClickListener {

            if (checkValidate()) {
                if (Utils.isInternetAvailable(this)) {

                    try {
                        val dialog = Dialogs.setProgressDialog(this@SignUpActivity, " جاري التسجيل.. ")
                        dialog.show()

                        RegisterRepo.registerUser(getUser()).observe(this, Observer {

                            dialog.dismiss()
                            if (it.isSuccessful)
                                processResult(it)
                            else
                                Dialogs.showDialog(this, "فشل الاتصال !")
                        })

                    } catch (ex: Exception) {
                        ex.printStackTrace()
                    }
                } else {
                    Dialogs.showDialog(this, "تأكد من اتصالك بالانترنت")
                }
            }
            //
        }
    }
    //

    private fun processResult(response: Response<JsonObject>?) {

        val jsonObject = JSONObject(Gson().toJson(response!!.body()))
        val jsonResult = jsonObject.getString("RegisterUserResult")
        val obj = JSONObject(jsonResult) //
        Log.d("debugR::", "success ResultCode -> ${obj.getString("ResultCode")}")

        val ResultCode = obj.getString("ResultCode")


        if (ResultCode.equals("0")) {
            val ResultDescription = obj.getString("ResultDescription")
            Dialogs.showDialog(this, ResultDescription)
            //return
        } else {
            //
            //save Client ID , Session Id in Shared Prefres
            //convert ResultBody
            val ResultBody = obj.getString("ResultBody")  //ResultBody
            val objResultBody = JSONObject(ResultBody) //
            val clientID = objResultBody.getInt("ClientID")
            val sessionID = objResultBody.getInt("SessionID")
            val fullName = objResultBody.getString("UserName")
            Log.d("debugR::", "ID before: ${clientID}")

            Log.d("debugR::", "ResultCode : ${ResultCode}")

            Utils.saveInSHaredPrefs(
                    this@SignUpActivity,
                    clientID.toString(),
                    sessionID.toString(),
                    fullName
            )
            Utils.openActivityMain(this@SignUpActivity)
            Toast.makeText(this@SignUpActivity, "تم التسجيل بنجاح", Toast.LENGTH_LONG).show()
            finish()
        }

    }

    private fun setTokenID() {

        FirebaseInstanceId.getInstance().instanceId.addOnSuccessListener(
                this
        ) { instanceIdResult: InstanceIdResult ->
            val newToken = instanceIdResult.token
            Log.d("newToken:", newToken)
            //
            SharedPref.setDataByType(this, SharedPref.TOKEN_ID, newToken)
        }
    }

    private fun getUser(): User {

        return User("2",
                et_UserName.text.toString().trim(),
                etPhoneNumber.text.toString().trim(),
                Encrypted.funEncryption(et_Pass.text.toString().trim()),
                DeviceID.getUniqueDeviceIdentifier(this),
                SharedPref.getTokenID(this)!!
        )
    }

    private fun checkValidate(): Boolean {

        var isValidate = true

        if (et_UserName.text.toString().trim().isEmpty() ||
                et_UserName.text.toString().trim().isBlank()
        ) {
            isValidate = false
            et_UserName.setError("")
        }

        if (etPhoneNumber.text.toString().trim().isEmpty() ||
                etPhoneNumber.text.toString().trim().isBlank()
        ) {
            isValidate = false
            etPhoneNumber.setError("")
        }

        if (et_Pass.text.toString().trim().isEmpty() ||
                et_Pass.text.toString().trim().isBlank()
        ) {
            isValidate = false
            et_Pass.setError("")
        }
        return isValidate
    }
}
