package com.ebdaa.katkot.ui.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.Observer
import com.ebada.embassy.pojo.repo.LoginRepo
import com.ebada.embassy.pojo.repo.RegisterRepo
import com.ebdaa.katkot.R
import com.ebdaa.katkot.pojo.utils.security.DeviceID
import com.ebdaa.katkot.pojo.utils.security.Encrypted
import com.ebdaa.katkot.ui.signup.SignUpActivity
import com.ebdaasoft.doctorapp_doctors.pojo.model.UserLogged
import com.ebdaasoft.doctorapp_doctors.pojo.utils.Dialogs
import com.ebdaasoft.doctorapp_doctors.pojo.utils.SharedPref
import com.ebdaasoft.doctorapp_doctors.pojo.utils.Utils
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.iid.InstanceIdResult
import com.google.gson.Gson
import com.google.gson.JsonObject
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_sign_up.*
import org.json.JSONObject
import retrofit2.Response
import java.lang.Exception

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar!!.hide() //<< this
        setContentView(R.layout.activity_login)

        setTokenID()
        btnLogin.setOnClickListener {

            if (checkValidate()) {
                if (Utils.isInternetAvailable(this)) {
                    try {
                        val dialog = Dialogs.setProgressDialog(this@LoginActivity, " جاري التسجيل.. ")
                        dialog.show()

                        LoginRepo.loginUser(getUser()).observe(this, Observer {

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
        }
        registerListner()
    }

    private fun processResult(response: Response<JsonObject>?) {

        val jsonObject = JSONObject(Gson().toJson(response!!.body()))
        val jsonResult = jsonObject.getString("UserLoginResult")
        val obj = JSONObject(jsonResult) //
        Log.d("debugL::", "success ResultCode -> ${obj.getString("ResultCode")}")

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
            Log.d("debugL::", "ID before: ${clientID}")

            Log.d("debugL::", "ResultCode : ${ResultCode}")

            Utils.saveInSHaredPrefs(
                    this@LoginActivity,
                    clientID.toString(),
                    sessionID.toString(),
                    fullName
            )
            Utils.openActivityMain(this@LoginActivity)
            Toast.makeText(this@LoginActivity, "تم التسجيل بنجاح", Toast.LENGTH_LONG).show()
            finish()
        }
    }

    private fun getUser(): UserLogged {

        return UserLogged("2",
                etPhoneNumb.text.toString().trim(),
                "",
                Encrypted.funEncryption(etPassword.text.toString().trim()),
                DeviceID.getUniqueDeviceIdentifier(this),
                "",
                Utils.getDeviceInfo(),
                SharedPref.getTokenID(this)!!)
    }

    private fun checkValidate(): Boolean {

        var isValidate = true

        if (etPhoneNumb.text.toString().trim().isEmpty() ||
                etPhoneNumb.text.toString().trim().isBlank()
        ) {
            isValidate = false
            etPhoneNumb.setError("")
        }

        if (etPassword.text.toString().trim().isEmpty() ||
                etPassword.text.toString().trim().isBlank()
        ) {
            isValidate = false
            etPassword.setError("")
        }

        return isValidate
    }

    private fun registerListner() {

        btnRegister.setOnClickListener{
            startActivity(Intent(this, SignUpActivity::class.java))
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


}
