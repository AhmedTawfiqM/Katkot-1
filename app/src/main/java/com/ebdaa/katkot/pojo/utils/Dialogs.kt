package com.ebdaasoft.doctorapp_doctors.pojo.utils

import android.app.AlertDialog
import android.app.Dialog
import android.app.ProgressDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.text.InputType
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import com.ebdaa.katkot.R
import com.ebdaa.katkot.ui.mainactivity.MainActivity
import kotlinx.android.synthetic.main.dialog_message.*
import kotlinx.android.synthetic.main.dialog_message.btnDone
import kotlinx.android.synthetic.main.dialog_message.code
import kotlinx.android.synthetic.main.dialog_message.tvDesc
import kotlinx.android.synthetic.main.dialog_save_data.*


object Dialogs {
    fun Context.toast(message: String) = Toast.makeText(this, message, Toast.LENGTH_LONG).show()

    //
    fun showDialog(context: Context, message: String) {

        val dialog = Dialog(context)
        dialog.setContentView(R.layout.dialog_message)

        dialog.tvDesc.text = message
        dialog.btnDone.setOnClickListener {
            dialog.dismiss()
        }

        dialog.code.visibility = View.GONE
        dialog.date.visibility = View.GONE

        //dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.show()
    }

    fun showDialogSuccess(context: Context, message: String, code: String, date: String) {

        val dialog = Dialog(context)
        dialog.setContentView(R.layout.dialog_message)

        dialog.tvDesc.text = message
        dialog.tvCode.text = code
        dialog.tvDate.text = date

        dialog.btnDone.setOnClickListener {
            dialog.dismiss()

            //Start Activity Requests
            //Go to All Requests
            //context.startActivity(Intent(context, AllRequestsActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK))
        }

        //context.toast("تم الارسال بنجاح")
        //dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setCancelable(false)
        dialog.show()

        dialog.setCancelable(false)

    }


    fun setProgressDialog(context: Context, message: String): AlertDialog {

        val llPadding = 30
        val ll = LinearLayout(context)
        ll.orientation = LinearLayout.HORIZONTAL
        ll.setPadding(llPadding, llPadding, llPadding, llPadding)
        ll.gravity = Gravity.CENTER
        var llParam = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        llParam.gravity = Gravity.CENTER
        ll.layoutParams = llParam

        val progressBar = ProgressBar(context)
        progressBar.isIndeterminate = true
        progressBar.setPadding(0, 0, llPadding, 0)
        progressBar.layoutParams = llParam

        llParam = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        llParam.gravity = Gravity.CENTER
        val tvText = TextView(context)
        tvText.text = message
        tvText.setTextColor(Color.parseColor("#000000"))
        tvText.textSize = 20.toFloat()
        tvText.layoutParams = llParam

        ll.addView(progressBar)
        ll.addView(tvText)

        val builder = AlertDialog.Builder(context)
        builder.setCancelable(true)
        builder.setView(ll)

        val dialog = builder.create()
        val window = dialog.window
        if (window != null) {
            val layoutParams = WindowManager.LayoutParams()
            layoutParams.copyFrom(dialog.window?.attributes)
            layoutParams.width = LinearLayout.LayoutParams.WRAP_CONTENT
            layoutParams.height = LinearLayout.LayoutParams.WRAP_CONTENT
            dialog.window?.attributes = layoutParams
        }
        dialog.setCancelable(false)
        return dialog
    }


    fun showDIalogSaveData(context: Context, TYPE: String) {

        val dialog = Dialog(context)
        dialog.setContentView(R.layout.dialog_save_data)

        dialog.btnDone.setOnClickListener {

            val text = dialog.tvData.text.toString().trim()
            if (text.isNotEmpty() && text.isNotBlank()) {


            } else {
                dialog.tvData.setError("")
            }
        }

//        dialog.setOnDismissListener {
//
//            (context as MainActivity).callFragmentProfile()
//        }
        dialog.show()
    }

    fun showDialogFailTOConnect(context: Context){
        Dialogs.showDialog(context, "فشل الاتصال !")
    }

}