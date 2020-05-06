package com.ebdaa.katkot.ui.report_activity

import androidx.appcompat.app.AppCompatActivity

import android.app.DatePickerDialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.DatePicker
import android.widget.Spinner
import androidx.appcompat.app.ActionBar

import com.ebdaa.katkot.R
import com.ebdaa.katkot.ui.finish_period.FinishPeriodActivity
import com.ebdaa.katkot.ui.mainactivity.MainActivity
import com.ebdaa.katkot.utils.Jobs
import kotlinx.android.synthetic.main.activity_report.*

import java.util.ArrayList
import java.util.Arrays
import java.util.Calendar
import java.util.Objects

class ReportActivity : AppCompatActivity() {

    //Vars
   //

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView( R.layout.activity_report)

        Objects.requireNonNull<ActionBar>(supportActionBar).setHomeButtonEnabled(true)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        //

        //Set Adapters of QueryPost And Periods
        SetAdapterofSpinnerFarm()
        SetAdapterofSpinnerPeriod()
        //

        //
        setSpinnerReportListner()

        //
        SetDateTimeofDAY()

        //
        setSpinnerFarmListner()
        //

    }

    private fun setSpinnerFarmListner() {

        spinnerFarm.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {

                if (position == 1) {

                    spinnerPeriod.visibility = View.GONE
                } else {
                    spinnerPeriod.visibility = View.VISIBLE

                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {

            }
        }
    }

    private fun setSpinnerReportListner() {

       spinnerReport.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {

                if (position == 4) {
                    layoutDATE.visibility = View.VISIBLE
                    spinnerPeriod.visibility = View.GONE

                } else {
                   layoutDATE.visibility = View.GONE

                    if (spinnerFarm.selectedItemPosition != 1)
                      spinnerPeriod.visibility = View.VISIBLE
                    else
                       spinnerPeriod.visibility = View.GONE

                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {

            }
        }
    }

    private fun SetAdapterofSpinnerFarm() {

        // set Data of farms in spinnner with Adapter
        val farms = resources.getStringArray(R.array.titles_FS)
        val farmsList = ArrayList<String>()
        //
        farmsList.add("إختر مزرعة")
        farmsList.add("جميع المزارع")
        farmsList.addAll(Arrays.asList(*farms))
        //

        Jobs.setAdapterSpinner(this@ReportActivity, spinnerFarm, farmsList)
        //....................

    }


    private fun SetAdapterofSpinnerPeriod() {


        // set Data of farms in spinnner with Adapter
        val farms = resources.getStringArray(R.array.titles_FS)
        val farmsList = ArrayList<String>()
        farmsList.add("إختر دورة")
        farmsList.add("جميع الدورات")
        farmsList.addAll(Arrays.asList(*farms))
        //


        Jobs.setAdapterSpinner(this@ReportActivity,spinnerPeriod, farmsList)
        //....................
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
            tvDateFrom.text = year.toString() + "-" + (month + 1) + "-" + day
            tvDateTo.text = year.toString() + "-" + (month + 1) + "-" + day
        }


       tvDateFrom.setOnClickListener {
            val datePickerDialog = DatePickerDialog(this@ReportActivity,
                    android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                    onDateSetListener_TODate, year, month, day)

            datePickerDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            datePickerDialog.show()
        }

        tvDateTo.setOnClickListener {
            val datePickerDialog = DatePickerDialog(this@ReportActivity,
                    android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                    onDateSetListener_TODate, year, month, day)

            datePickerDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            datePickerDialog.show()
        }


    }

    private fun SetDateTimeofDAY() {

        //final SimpleDateFormat format = new SimpleDateFormat("EEE, dd MMM yyyy hh:mm:ss Z");

        //Get CUrrent date
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        tvDateTo.text = year.toString() + "-" + (month + 1) + "-" + day
        tvDateFrom.text = year.toString() + "-" + (month + 1) + "-" + day

        setClickDateListner(year, month, day)
    }

}
