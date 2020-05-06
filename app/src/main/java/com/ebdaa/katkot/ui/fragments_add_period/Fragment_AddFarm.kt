package com.ebdaa.katkot.ui.fragments_add_period

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation

import com.ebdaa.katkot.R
import kotlinx.android.synthetic.main.fragment_add_farm.*

import java.util.ArrayList
import java.util.Arrays


class Fragment_AddFarm : Fragment() {

    //Vars
    private var navController: NavController? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_add_farm, container, false)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = Navigation.findNavController(activity!!, R.id.navHostFragment)
        //
        btnComplete.setOnClickListener { navController!!.navigate(R.id.action_fragment_AddFarm_to_fragment_AddKatakit) }
        SetAdapterofSpinnerFarm()
        btnChooseFarmListner()
    }

    private fun btnChooseFarmListner() {

        //...
//       btnChoose.setOnClickListener {
//            if (binding!!.spinnerFarmName.selectedItem != null && binding!!.spinnerFarmName.selectedItemPosition != 0) {
//
//                binding!!.layoutFarmInfo.visibility = View.VISIBLE
//                binding!!.layoutChoosefarm.visibility = View.GONE
//
//            } else if (binding!!.spinnerFarmName.selectedItemPosition == 0) {
//
//                binding!!.spinnerFarmName.performClick()
//
//            }
//        }


    }

    private fun SetAdapterofSpinnerFarm() {

        // set Data of farms in spinnner with Adapter
        val farms = resources.getStringArray(R.array.titles_FS)
        val farmsList = ArrayList<String>()

        farmsList.add("إختر مزرعة")
        farmsList.addAll(Arrays.asList(*farms))
        //


        val adapter = ArrayAdapter(activity!!, R.layout.spinner_item, farmsList)

        spinnerFarmName.adapter = adapter
        //....................

    }

}
