package com.ebdaa.katkot.ui.fragments_add_period

import android.app.DatePickerDialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.ebdaa.katkot.R
import com.ebdaa.katkot.ui.finish_period.FinishPeriodActivity
import kotlinx.android.synthetic.main.fragment_add_kakatit.*

import java.util.Calendar

class Fragment_AddKatakit : Fragment() {

    //Vars
    lateinit var navController: NavController

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view  = inflater.inflate(R.layout.fragment_add_kakatit, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = Navigation.findNavController(activity!!, R.id.navHostFragment)
        //
        btnComplete.setOnClickListener { navController.navigate(R.id.action_fragment_AddKatakit_to_fragment_CostWorkers) }


        //
        SetDateTimeofDAY()

    }//..


    private fun setClickDateListner(year: Int, month: Int, day: Int) {

        val onDateSetListener_TODate = DatePickerDialog.OnDateSetListener { datePicker, year, month, day ->
            tvDate.text = year.toString() + "-" + (month + 1) + "-" + day }


        tvDate.setOnClickListener {
            val datePickerDialog = DatePickerDialog(activity!!,
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

        tvDate.text = year.toString() + "-" + (month + 1) + "-" + day

        setClickDateListner(year, month, day)
    }


}
