package com.ebdaa.katkot.utils

import android.content.Context
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView

import com.ebdaa.katkot.R

import java.util.ArrayList

object Jobs {


    fun setAdapterSpinner(context: Context, spinner: Spinner, dataList: ArrayList<String>) {
        //1- set Gravity in Center
        //2- change Font Size
        //3- set Custom layout for changing colors
        val adapter = object : ArrayAdapter<String>(context,
                R.layout.spinner_item, dataList) {

            override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

                val v = super.getView(position, convertView, parent)
                (v as TextView).textSize = context.resources.getDimension(R.dimen._7sdp)
                return v
            }

            override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {

                val v = super.getDropDownView(position, convertView, parent)
                (v as TextView).gravity = Gravity.CENTER
                return v
            }
        }
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter
    }


}
