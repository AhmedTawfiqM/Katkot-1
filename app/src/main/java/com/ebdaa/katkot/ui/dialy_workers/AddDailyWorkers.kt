package com.ebdaa.katkot.ui.dialy_workers

import androidx.appcompat.app.AppCompatActivity

import android.os.Bundle
import android.view.MenuItem

import com.ebdaa.katkot.R

class AddDailyWorkers : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_daily_workers)


        supportActionBar!!.setHomeButtonEnabled(true)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (item.itemId == android.R.id.home) {// app icon in action bar clicked; goto parent activity.
            onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}
