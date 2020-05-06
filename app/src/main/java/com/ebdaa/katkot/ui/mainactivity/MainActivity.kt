package com.ebdaa.katkot.ui.mainactivity

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.LinearLayout
import android.widget.ListView
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBar

import com.ebdaa.katkot.R
import com.ebdaa.katkot.ui.addblock_activity.AddWardActivity
import com.ebdaa.katkot.ui.addfarm_activity.AddFarmActivity
import com.ebdaa.katkot.ui.addmedicines_activity.AddMedicinesActicity
import com.ebdaa.katkot.ui.anotherdata_activity.AnotherDataActivity
import com.ebdaa.katkot.ui.blockhouses_activity.AddBlockHousesActicity
import com.ebdaa.katkot.ui.dialy_workers.AddDailyWorkers
import com.ebdaa.katkot.ui.electrics_cost.ElectricsCost
import com.ebdaa.katkot.ui.farms.AllFarms
import com.ebdaa.katkot.ui.main_fragments.FragmentMainPage
import com.ebdaa.katkot.ui.main_fragments.FragmentPeriod
import com.ebdaa.katkot.ui.main_fragments.FragmentReports
import com.ebdaa.katkot.ui.newperiodactivity.NewPeriodActivity
import com.ebdaa.katkot.ui.userfirsttime.UserFTActivity
import com.ebdaa.katkot.ui.water_cost.WaterCostActivity
import com.ebdaa.katkot.utils.Cons
import com.ebdaa.katkot.utils.Jobs
import com.ebdaa.katkot.utils.SharedPref
import com.etebarian.meowbottomnavigation.MeowBottomNavigation
import com.google.android.gms.ads.MobileAds
import com.google.android.material.bottomnavigation.LabelVisibilityMode
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.messaging.FirebaseMessaging
import com.luseen.spacenavigation.SpaceItem
import com.luseen.spacenavigation.SpaceOnClickListener
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.Exception

import java.util.ArrayList
import java.util.Arrays
import java.util.Objects

class MainActivity : AppCompatActivity() {

    //Vars
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        checkUserLogin()
        Objects.requireNonNull<ActionBar>(supportActionBar).hide() //<< this
        setContentView(R.layout.activity_main)
        setTitle(R.string.app_name)

        initUI()
        SetUpBottomNavigationListner()
        //setUpBottomBar()
        // Space Naivigation Init
        // setUpSpaceNavigation(savedInstanceState)
        //clickListnerForSpaceNaviagtion()
        setUpNavigationView()
        // Begin Main Page fragment
        navigatetoFragment(FragmentMainPage())
        //
        initMenuDrawer()
        //
        FirebaseNotifiy()
        //..
    }

    private fun checkUserLogin() {

        val isUserloggin = SharedPref.isUserLogginned(this@MainActivity)

        if (!isUserloggin!!) {
            startActivity(
                    Intent(this, UserFTActivity::class.java)
                            .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            )
            finish() //finish to not request permission now //You have set android:noHistory = "true" for MainActivity inside AndroidManifest.xml which causes MainActivity to finish automatically on pressing the back key.
        }
    }

    override fun onResume() {
        super.onResume()
        //Cause the app Slower...,
        //NotificationHelper.showNotiifcation(this@MainActivity)
        //Init Ads
        InitAds()

    }


    private fun InitAds() {

        MobileAds.initialize(this) { initializationStatus -> Toast.makeText(this@MainActivity, "Ads $initializationStatus", Toast.LENGTH_SHORT).show() }
    }

    private fun FirebaseNotifiy() {

        //Send Notfication to single or some user
        //By Categories in Firebase
        //From Youtube : https://www.youtube.com/watch?v=aG2JC8c9EK0
        FirebaseMessaging.getInstance().subscribeToTopic(Cons.TOPIC_All_Users)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {

                        Log.d("FNotifi", "Successful subscribeToTopic")
                    }//..
                }
        //

        FirebaseInstanceId.getInstance().instanceId
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {

                        Log.d("FNotifi", "token: " + task.result!!.token)
                    }
                }
    }

    private fun initMenuDrawer() {

        iv_Menu.setOnClickListener { drawerLayout.openDrawer(Gravity.RIGHT) }

        //RTL
        window.decorView.layoutDirection = View.LAYOUT_DIRECTION_RTL

    }


    private fun clickListnerForSpaceNaviagtion() {


        space.setSpaceOnClickListener(object : SpaceOnClickListener {

            override fun onCentreButtonClick() {
                //Toast.makeText(MainActivity.this, "onCentreButtonClick", Toast.LENGTH_SHORT).show();

                openDialog_AddDailyData()
            }

            override fun onItemClick(itemIndex: Int, itemName: String) {
                //Toast.makeText(MainActivity.this, itemIndex + " " + itemName, Toast.LENGTH_SHORT).show();

                when (itemIndex) {

                    0 -> navigatetoFragment(FragmentReports())
                    1 -> {
                    }
                    2 -> navigatetoFragment(FragmentPeriod())
                    3 -> navigatetoFragment(FragmentMainPage())
                }
            }

            override fun onItemReselected(itemIndex: Int, itemName: String) {
                //Toast.makeText(MainActivity.this, itemIndex + " " + itemName, Toast.LENGTH_SHORT).show();
            }
        })
    }

    //.....
    private fun setUpSpaceNavigation(savedInstanceState: Bundle) {

        try {

            space.initWithSaveInstanceState(savedInstanceState)

            space.addSpaceItem(SpaceItem("تقارير", R.drawable.finishperiod))
            space.addSpaceItem(SpaceItem(null, android.R.drawable.ic_menu_search))

            space.addSpaceItem(SpaceItem("الدورات", R.drawable.home))
            space.addSpaceItem(SpaceItem(" الرئيسية", R.drawable.home))

            // Go to First Page on The Right
            space.changeCurrentItem(3)

        } catch (ex: Exception) {
            ex.printStackTrace()
            println("bug: ${ex.message}")
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        space.onSaveInstanceState(outState)
    }

    private fun navigatetoFragment(mainPage: Fragment) {

        //..
        supportFragmentManager.beginTransaction().replace(R.id.fragemntContainer,
                mainPage, "Tag")
                .commit()
        //..

    }

    private fun setUpNavigationView() {

        //...
        navigationviuew.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.itemFarms -> startActivity(Intent(applicationContext, AllFarms::class.java))
                R.id.item_AddPeriod -> startActivity(Intent(applicationContext, NewPeriodActivity::class.java))
                R.id.itemAddFarm -> startActivity(Intent(applicationContext, AddFarmActivity::class.java))
                //R.id.item_FinishPeriod -> startActivity(Intent(applicationContext, FinishPeriodActivity::class.java))
                R.id.itemAddBlock -> startActivity(Intent(applicationContext, AddWardActivity::class.java))
            }
            drawerLayout.closeDrawers()
            true
        }
    }

    companion object {
        private const val ID_HOME = 1
        private const val ID_EXPLORE = 2
        private const val ID_MESSAGE = 3
        private const val ID_NOTIFICATION = 4
        private const val ID_ACCOUNT = 5
    }

    private fun setUpBottomBar(){
        bottomNavigation1.apply {
            add(MeowBottomNavigation.Model(ID_EXPLORE, R.drawable.home))
            add(MeowBottomNavigation.Model(ID_MESSAGE, R.drawable.home))
            add(MeowBottomNavigation.Model(ID_HOME, R.drawable.plus))
            add(MeowBottomNavigation.Model(ID_NOTIFICATION, R.drawable.home))
            add(MeowBottomNavigation.Model(ID_ACCOUNT, R.drawable.home))
            show(ID_HOME)
            //setCount(ID_NOTIFICATION, "115")
            setOnShowListener {
                val name = when (it.id) {
                    ID_EXPLORE -> "EXPLORE"
                    ID_MESSAGE -> "MESSAGE"
                    ID_HOME -> "HOME"
                    ID_NOTIFICATION -> "NOTIFICATION"
                    ID_ACCOUNT -> "ACCOUNT"
                    else -> ""
                }
                //tvSelected.text = getString(R.string.main_page_selected, name)
            }
            setOnClickMenuListener {
                val name = when (it.id) {
                    ID_EXPLORE -> "EXPLORE"
                    ID_MESSAGE -> "MESSAGE"
                    ID_HOME -> "HOME"
                    ID_NOTIFICATION -> "NOTIFICATION"
                    ID_ACCOUNT -> "ACCOUNT"
                    else -> ""
                }
            }
            /*setOnReselectListener {
                Toast.makeText(context, "item ${it.id} is reselected.", Toast.LENGTH_LONG).show()
            }*/
        }
    }

    private fun SetUpBottomNavigationListner() {


        bottomNavigation.labelVisibilityMode = LabelVisibilityMode.LABEL_VISIBILITY_LABELED
        bottomNavigation.selectedItemId = R.id.itemMainPage
        //

        bottomNavigation.setOnNavigationItemSelectedListener {

            when (it.itemId) {

                R.id.itemMainPage -> navigatetoFragment(FragmentMainPage())
                R.id.itemPeriods -> navigatetoFragment(FragmentPeriod());
                R.id.itemReports -> navigatetoFragment(FragmentReports());
                R.id.plusId -> print("") //openDialog_AddDailyData()
                else -> print("")//openDialog_AddDailyData()

            }
            true

        }

        //Float Button Plus
        fabPlus.setOnClickListener {
            openDialog_AddDailyData()
        }

    }

    //
    private fun openDialog_AddDailyData() {

        val dialog = Dialog(this@MainActivity)
        dialog.setContentView(R.layout.dialog_add_data)


        (dialog.findViewById<View>(R.id.list_add) as ListView).adapter = ArrayAdapter(this,
                R.layout.single_row_ls, R.id.textItem,
                resources.getStringArray(R.array.add_daily_data))

        //.....
        (dialog.findViewById<View>(R.id.list_add) as ListView).onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
            when (position) {

                0 -> startActivity(Intent(applicationContext, AddMedicinesActicity::class.java))
                1 -> startActivity(Intent(applicationContext, AddBlockHousesActicity::class.java))
                2 -> startActivity(Intent(applicationContext, AddDailyWorkers::class.java))
                3 -> startActivity(Intent(applicationContext, ElectricsCost::class.java))
                4 -> startActivity(Intent(applicationContext, WaterCostActivity::class.java))
                5 -> startActivity(Intent(applicationContext, AnotherDataActivity::class.java))
            }
            dialog.dismiss()
        }
        //.....


        // Two Layouts for 1- Choose QueryPost and Period
        // 2- Add Actually data to those QueryPost and Period
        val lLChooseFarm = dialog.findViewById<LinearLayout>(R.id.lLChooseFarm)
        val lLAddData = dialog.findViewById<LinearLayout>(R.id.lLAddData)
        //...
        // Spinners QueryPost And Period
        val spinnerFarm = dialog.findViewById<Spinner>(R.id.spinnerFarm)
        val spinnerPeriod = dialog.findViewById<Spinner>(R.id.spinnerPeriod)
        //

        //..
        val btn_Choose = dialog.findViewById<Button>(R.id.btn_Choose)

        btn_Choose.setOnClickListener {
            if (spinnerFarm.selectedItem != null && spinnerPeriod.selectedItem != null
                    && spinnerFarm.selectedItemPosition != 0 && spinnerPeriod.selectedItemPosition != 0) {

                // Navigate to 'Add Layout'
                lLAddData.visibility = View.VISIBLE
                lLChooseFarm.visibility = View.GONE
                //
                //Set Title to Choosen QueryPost And Period
                val farm = spinnerFarm.selectedItem.toString()
                val period = spinnerPeriod.selectedItem.toString()
                //
                (dialog.findViewById<View>(R.id.tv_FarmAndPeriod) as TextView).text = "$farm : المزرعة  \n$period :   الدورة  "
                //

            } else if (spinnerFarm.selectedItemPosition == 0) {

                spinnerFarm.performClick()

            } else if (spinnerPeriod.selectedItemPosition == 0) {

                spinnerPeriod.performClick()

            }
            //..
        }

        dialog.findViewById<View>(R.id.close).setOnClickListener { dialog.dismiss() }
        //..
        SetAdapterofSpinnerFarm(spinnerFarm)
        SetAdapterofSpinnerPeriod(spinnerPeriod)

        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.show()
    }

    private fun SetAdapterofSpinnerPeriod(spinnerPeriod: Spinner) {

        // set Data of farms in spinnner with Adapter
        val farms = resources.getStringArray(R.array.titles_FS)
        val farmsList = ArrayList<String>()
        farmsList.add("إختر دورة")
        farmsList.addAll(Arrays.asList(*farms))
        //
        Jobs.setAdapterSpinner(this@MainActivity, spinnerPeriod, farmsList)
        //....................
    }

    private fun SetAdapterofSpinnerFarm(spinnerFarm: Spinner) {

        // set Data of farms in spinnner with Adapter
        val farms = resources.getStringArray(R.array.titles_FS)
        val farmsList = ArrayList<String>()
        //
        farmsList.add("إختر مزرعة")
        farmsList.addAll(Arrays.asList(*farms))
        //
        Jobs.setAdapterSpinner(this@MainActivity, spinnerFarm, farmsList)
        //....................
    }

    private fun initUI() {


    }


}
