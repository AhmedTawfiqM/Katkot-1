package com.ebdaa.katkot.ui.finish_period

import androidx.appcompat.app.AppCompatActivity

import android.app.DatePickerDialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.DatePicker
import android.widget.TextView

import com.ebdaa.katkot.R
import com.ebdaa.katkot.ui.addperiod.AddPeriod
import kotlinx.android.synthetic.main.dialog_message.*

import java.text.SimpleDateFormat
import java.util.Calendar

class FinishPeriodActivity : AppCompatActivity() {

    //
    private val farmID: String? = null
    private val IdPeriod: String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       setContentView( R.layout.activity_finish_period)


        supportActionBar!!.setHomeButtonEnabled(true)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        //
        SetDateTimeofDAY()

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (item.itemId == android.R.id.home) {// app icon in action bar clicked; goto parent activity.
            onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setClickDateListner(year: Int, month: Int, day: Int) {

        val onDateSetListener_TODate = DatePickerDialog.OnDateSetListener { datePicker, year, month, day ->
            tvDate.text = year.toString() + "-" + (month + 1) + "-" + day }


       tvDate.setOnClickListener {
            val datePickerDialog = DatePickerDialog(this@FinishPeriodActivity,
                    android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                    onDateSetListener_TODate, year, month, day)

            datePickerDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            datePickerDialog.show()
        }


    }

    //
    private fun SetDateTimeofDAY() {

        //final SimpleDateFormat format = new SimpleDateFormat("EEE, dd MMM yyyy hh:mm:ss Z");

        //Get CUrrent date
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        tvDate.text = year.toString() + "-" + (month + 1) + "-" + day

        setClickDateListner(year, month, day)
    }


}
