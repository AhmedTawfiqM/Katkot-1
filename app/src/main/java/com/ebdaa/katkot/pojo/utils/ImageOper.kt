package com.ebdaasoft.doctorapp_doctors.pojo.utils

import android.content.Context
import android.graphics.Bitmap
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.*


object ImageOper {

    fun saveImageToGallery(context: Context, bitmapIMG: Bitmap) {

        try {
            MediaStore.Images.Media.insertImage(
                context.getContentResolver(),
                bitmapIMG, "nameofimage", "description"
            );

        } catch (ex: Exception) {
            ex.printStackTrace()
            Log.d("SaveI::", ex.message);
        }
    }

    fun saveImageToAppFolder(context: Context, bitmapIMG: Bitmap) {

        var out: FileOutputStream? = null
        try {
            val myDir = createImageFile(context)
            if (!myDir.exists()) myDir.mkdirs()

            val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
            val fname = "JPEG_${timeStamp}_.jpg"
            val file = File(myDir, fname)

            //if (!file.exists()) file.mkdirs()
            Log.i("SaveI", file.absolutePath)

            out = FileOutputStream(file)
            bitmapIMG.compress(Bitmap.CompressFormat.JPEG, 90, out)

        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            out!!.flush()
            out.close()
        }
    }

    fun createImageFile(context: Context): File {
        // Create an image file name
        //val storageDir: String = context.getDir("EbassyApp",Context.MODE_APPEND).toString()
        //val storageDir = File(context.filesDir.toString() + File.separator +"EmbasssyApp","gfgd")

        val storageDir = Environment.getExternalStorageDirectory().toString() + "/EmbasssyApp/"

        return File(storageDir)
    }
}