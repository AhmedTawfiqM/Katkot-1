package com.ebdaa.katkot.ui.blockhouses_activity

import androidx.appcompat.app.AppCompatActivity

import android.os.Bundle
import android.view.MenuItem

import com.ebdaa.katkot.R

class AddBlockHousesActicity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_block_houses_acticity)

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
