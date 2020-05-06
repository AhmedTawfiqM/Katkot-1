package com.ebdaa.katkot.ui.fragments_add_period

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.ebdaa.katkot.R
import kotlinx.android.synthetic.main.fragment_cost_feed.*


class FragmentCostFeed : Fragment() {


    lateinit var navController: NavController

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
       val view = inflater.inflate( R.layout.fragment_cost_feed, container, false)
        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = Navigation.findNavController(activity!!, R.id.navHostFragment)
        //
      btnComplete.setOnClickListener {
          navController.navigate(R.id.action_fragmentCostFeed_to_fragmentCostsBlanket) }


    }
}
