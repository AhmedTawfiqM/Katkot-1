package com.ebdaa.katkot.ui.main_fragments


import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider

import com.ebdaa.katkot.R
import com.ebdaa.katkot.pojo.adapters.PeriodsAdapter
import com.ebdaa.katkot.Test
import kotlinx.android.synthetic.main.fragment_period.*


class FragmentPeriod : Fragment() {

    lateinit var viewModel: PeriodsViewModel
    lateinit var periodsAdapter: PeriodsAdapter
    //

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_period, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        init()

        initAdapter()
    }

    private fun initAdapter() {


        // Set Adapter
        //binding.recyclerMain.adapter = periodsAdapter
    }

    //...
    private fun init() {

        //
        viewModel = ViewModelProvider(this).get(PeriodsViewModel::class.java)
        viewModel.init()
        periodsAdapter = PeriodsAdapter(activity as Context)
        periodsAdapter.SetPeriods(Test.periods)

        //
        recyclerMain.layoutManager = LinearLayoutManager(activity)
        recyclerMain.adapter = PeriodsAdapter(activity as Context)
        //
    }


}
