package com.ebdaa.katkot.pojo.adapters

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

import com.ebdaa.katkot.R
import com.ebdaa.katkot.pojo.adapters.viewholders.ViewHolderPeriods
import com.ebdaa.katkot.Test
import com.ebdaa.katkot.ui.finish_period.FinishPeriodActivity
import com.ebdaa.katkot.utils.RowListener


class PeriodsAdapter(val context: Context) : RecyclerView.Adapter<ViewHolderPeriods>(), RowListener {

    // Vars
    private var testPeriods: List<Test.TestPeriod>? = null

    fun SetPeriods(testPeriods: List<Test.TestPeriod>) {

        this.testPeriods = testPeriods
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderPeriods {

        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate( R.layout.single_finish_period, parent, false)

        return ViewHolderPeriods(view)
    }

    override fun onBindViewHolder(holder: ViewHolderPeriods, position: Int) {

        val test = testPeriods!![position]

        Log.d("TestPR", "onBindViewHolder: " + test.date)
        //...



    }

    override fun getItemCount(): Int {
        return testPeriods!!.size
    }

    override fun onRowClick() {

        context.startActivity(Intent(context, FinishPeriodActivity::class.java))

    }

    // use ANonyMous Interface
    //        val myListner = object : RowListener {
//
//            override fun onRowClick() {
//
//                context.startActivity(Intent(context, FinishPeriodActivity::class.java))
//
//            }
//        }
//        binding.listener = myListner
}
