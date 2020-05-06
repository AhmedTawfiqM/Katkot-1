package com.ebdaa.katkot.utils

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Base64
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import androidx.core.app.ActivityCompat.requestPermissions
import androidx.core.content.ContextCompat
import com.ebdaa.katkot.ui.mainactivity.MainActivity
import java.io.ByteArrayOutputStream
import java.math.BigDecimal
import java.math.RoundingMode
import java.net.URL
import java.net.URLConnection

object Utils {

    fun isInternetAvailable(context: Context): Boolean {
        var result = false
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val networkCapabilities = connectivityManager.activeNetwork ?: return false
            val actNw =
                connectivityManager.getNetworkCapabilities(networkCapabilities) ?: return false
            result = when {
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                else -> false
            }
        } else {
            connectivityManager.run {
                connectivityManager.activeNetworkInfo?.run {
                    result = when (type) {
                        ConnectivityManager.TYPE_WIFI -> true
                        ConnectivityManager.TYPE_MOBILE -> true
                        ConnectivityManager.TYPE_ETHERNET -> true
                        else -> false
                    }

                }
            }
        }

        return result
    }

    fun isConnectedToServer(url: String?, timeout: Int): Boolean {

        return try {
            val myUrl = URL(url)
            val connection: URLConnection = myUrl.openConnection()
            connection.setConnectTimeout(timeout)
            connection.connect()
            true
        } catch (e: Exception) { // Handle your exceptions
            Log.d("servert::", "${e.message}")
            false
        }

    }

    fun saveInSHaredPrefs(context: Context, clientId: String, sessionID: String, fullName: String) {

        SharedPref.setDataByType(context, SharedPref.CLIENTID, clientId)
        SharedPref.setDataByType(context, SharedPref.SeSSIonID, sessionID)
        SharedPref.setDataByType(context, SharedPref.FULL_NAME, fullName)
        SharedPref.setIsUserLogginned(context) //set True

        Log.d(
            "debugP::",
            "getClientID pref : ${SharedPref.getDataByType(context, SharedPref.CLIENTID)}"
        )
        Log.d(
            "debugP::",
            "getSessionID pref : ${SharedPref.getDataByType(context, SharedPref.SeSSIonID)}"
        )
        Log.d(
            "debugP::",
            "getFullName pref : ${SharedPref.getDataByType(context, SharedPref.FULL_NAME)}"
        )
        Log.d("debugP::", "isUserLogginned pref : ${SharedPref.isUserLogginned(context)}")
    }

    fun openActivityMain(context: Context) {


        context.startActivity(
            Intent(context, MainActivity::class.java)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
        )
    }

    fun decodeImage46(context: Context, selectedImage: Uri): String {

        var bitmap: Bitmap? = null
        if (Build.VERSION.SDK_INT < 28) {

            bitmap = MediaStore.Images.Media.getBitmap(
                context.contentResolver,
                selectedImage
            )
        } else {
            val source = ImageDecoder.createSource(context.contentResolver, selectedImage)
            bitmap = ImageDecoder.decodeBitmap(source)
        }
        val boas = ByteArrayOutputStream();
        bitmap!!.compress(Bitmap.CompressFormat.JPEG, 100, boas);
        val b = boas.toByteArray()

        val encodeImage = android.util.Base64.encodeToString(b, android.util.Base64.DEFAULT);
        Log.e("imageBase64", encodeImage.toString())

        return encodeImage
    }

    fun decode64FromCamera(bitmap: Bitmap): String {

        //Resize
        //val resized = Bitmap.createScaledBitmap(bitmap, 600, 400, true)

        val boas = ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, boas);
        val b = boas.toByteArray()

        val encodeImage = android.util.Base64.encodeToString(b, android.util.Base64.DEFAULT);
        Log.e("imageBase64", encodeImage.toString())

        return encodeImage
    }

    fun setAdapterSpinner(context: Context, spinner: Spinner, dataList: ArrayList<String>) {

        //1- set Gravity in Center
        //2- change Font Size
        //3- set Custom layout for changing colors
        val adapter = object : ArrayAdapter<String>(
            context,
            android.R.layout.simple_list_item_1, dataList
        ) {

            override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

                val v = super.getView(position, convertView, parent)

                //(v as TextView).textSize = context.resources.getDimension(R.dimen._7sdp)

                return v
            }

            override fun getDropDownView(
                position: Int,
                convertView: View?,
                parent: ViewGroup
            ): View {

                val v = super.getDropDownView(position, convertView, parent)

                (v as TextView).gravity = Gravity.CENTER

                return v
            }


        }
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter
    }

    fun closeTheApp(context: Context) {
        val exitIntent = Intent(Intent.ACTION_MAIN)
        exitIntent.addCategory(Intent.CATEGORY_HOME)
        exitIntent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        context.startActivity(exitIntent)
    }

    fun checkFullName(text: String): Boolean {

        val ch = text.toCharArray()
        var letter = 0
        var space = 0
        var num = 0
        var other = 0
        for (i in 0 until text.length) {
            if (Character.isLetter(ch[i])) {
                letter++
            } else if (Character.isDigit(ch[i])) {
                num++
            } else if (Character.isSpaceChar(ch[i])) {
                space++
            } else {
                other++
            }
        }
        if (space < 3) {   //Space numbers of words  //4
            return false
        }
        if (num > 0) {
            return false
        }
        if (letter < 12) {  //total of all words  //12 minimum 'Full Name' contains 4 words
            return false
        }
        if (other > 0) {
            return false
        }
        Log.d("checkName", " letter : $letter")
        Log.d("checkName", " space : $space")
        Log.d("checkName", " num : $num")
        Log.d("checkName", " other : $other")
        return true
    }

    fun getDeviceInfo(): List<String> {

        return listOf(
            android.os.Build.TYPE,
            android.os.Build.VERSION_CODES().toString(),
            android.os.Build.DEVICE,
            android.os.Build.MODEL,
            android.os.Build.VERSION().toString(),
            android.os.Build.VERSION().toString(),
            android.os.Build.VERSION().toString()
        )
    }


    fun round(doubValue: Double): Double {
        try {
            return BigDecimal(doubValue).setScale(2, RoundingMode.HALF_UP).toDouble()
        } catch (ex: java.lang.Exception) {
            return doubValue
        }
    }

    fun resizeBase64Image(base64image: String): String {

        val encodeByte = Base64.decode(base64image, Base64.DEFAULT);
        val options = BitmapFactory.Options();
        options.inPurgeable = true;
        var image = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.size, options);


        if (image.getHeight() <= 400 && image.getWidth() <= 400) {
            return base64image;
        }
        image = Bitmap.createScaledBitmap(image, 400, 400, false);

        val baos = ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.PNG, 100, baos);

        val b = baos.toByteArray();
        System.gc();
        return Base64.encodeToString(b, Base64.NO_WRAP);

    }
}