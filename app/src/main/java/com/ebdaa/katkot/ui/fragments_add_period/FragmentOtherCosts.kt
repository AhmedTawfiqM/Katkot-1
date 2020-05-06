package com.ebdaa.katkot.ui.fragments_add_period


import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.ebdaa.katkot.R


class FragmentOtherCosts : Fragment() {




    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
       val view = inflater.inflate( R.layout.fragment_other_costs, container, false)
        return view
    }


}
