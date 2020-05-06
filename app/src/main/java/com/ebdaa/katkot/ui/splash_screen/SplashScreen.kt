package com.ebdaa.katkot.ui.splash_screen

import androidx.appcompat.app.AppCompatActivity

import android.content.Intent
import android.os.Bundle

import com.ebdaa.katkot.R
import com.ebdaa.katkot.ui.userfirsttime.UserFTActivity

import java.sql.Time
import java.util.Timer
import java.util.TimerTask

class SplashScreen : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar!!.hide()
        setContentView(R.layout.activity_splash_screen)


        val timer = Timer()
        timer.schedule(object : TimerTask() {

            override fun run() {
                this@SplashScreen.runOnUiThread {
                    try {

                        startActivity(Intent(applicationContext, UserFTActivity::class.java))
                        finish()

                    } catch (ex: Exception) {
                        ex.printStackTrace()
                    }
                }
            }
        }, WAITING_TIME.toLong())
    }

    companion object {

        private val WAITING_TIME = 2000
    }
}
