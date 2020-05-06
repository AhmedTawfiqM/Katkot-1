package com.ebdaa.katkot.ui.main_fragments


import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.ebdaa.katkot.R
import com.ebdaa.katkot.ui.report_activity.ReportActivity
import kotlinx.android.synthetic.main.fragment_reports.*


class FragmentReports : Fragment() {

    //Vars
    //..

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
       val view = inflater.inflate( R.layout.fragment_reports, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lLChooseFarm.setOnClickListener { startActivity(Intent(context, ReportActivity::class.java)) }
    }
}
