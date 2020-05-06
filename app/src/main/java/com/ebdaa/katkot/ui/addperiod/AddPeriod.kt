package com.ebdaa.katkot.ui.addperiod

import androidx.appcompat.app.AppCompatActivity

import android.app.DatePickerDialog
import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.DatePicker
import android.widget.TextView

import com.ebdaa.katkot.R
import com.ebdaa.katkot.ui.mainactivity.MainActivity

import java.util.Calendar

class AddPeriod : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       setContentView(R.layout.activity_add_period)
        // change color of action bar
        supportActionBar!!.setBackgroundDrawable(ColorDrawable(Color.parseColor("#918c8f")))

        supportActionBar!!.setHomeButtonEnabled(true)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setHomeAsUpIndicator(R.drawable.close)


    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (item.itemId == android.R.id.home) {// app icon in action bar clicked; goto parent activity.
            onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        //super.onBackPressed();

        showDialogBackPressed()

    }

    private fun BacktoMain() {

        startActivity(Intent(applicationContext, MainActivity::class.java)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))

    }

    private fun showDialogBackPressed() {


        val dialog = Dialog(this@AddPeriod)
        dialog.setContentView(R.layout.dialog_exit_add_period)

        // Close App
        dialog.findViewById<View>(R.id.btn_Close).setOnClickListener {
            //
            BacktoMain()
        }


        // Complete App
        dialog.findViewById<View>(R.id.btn_Complete).setOnClickListener {
            //
            dialog.dismiss()
        }

        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.show()
    }


}
