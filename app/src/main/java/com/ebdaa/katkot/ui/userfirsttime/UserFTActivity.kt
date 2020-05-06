package com.ebdaa.katkot.ui.userfirsttime

import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.text.Html
import android.text.Spanned
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView

import com.ebdaa.katkot.R
import com.ebdaa.katkot.pojo.adapters.SliderAdapter
import com.ebdaa.katkot.ui.login.LoginActivity
import com.ebdaa.katkot.ui.signup.SignUpActivity
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_user_ft.*

class UserFTActivity : AppCompatActivity() {

    lateinit var mDOts: Array<TextView>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar!!.hide() //<< this
        setContentView( R.layout.activity_user_ft)

        initUi()
        AddDotsIndicator(0)
        setOnPageChangeListner()
    }

    fun removeBarAndTitle() {


        //Remove title bar
        //        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //        //Remove notification bar
        //        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

    }

    private fun setOnPageChangeListner() {

        viewpager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

            }

            override fun onPageSelected(position: Int) {

                AddDotsIndicator(position)
            }
            override fun onPageScrollStateChanged(state: Int) {

            }
        })
    }

    private fun AddDotsIndicator(position: Int) {

        mDOts = Array(3){TextView(this)}
        mDotsLayout.removeAllViews()

        for (i in mDOts.indices) {

            mDOts[i] = TextView(this)
            mDOts[i].text = fromHtml("&#8226")  // "\u2022 Bullet"
            mDOts[i].textSize = 35f

            mDOts[i].setTextColor(Color.parseColor("#8d8d8d"))

            mDotsLayout.addView(mDOts[i])
        }

        if (mDOts.size > 0) {

            mDOts[position].setTextColor(Color.parseColor("#ffffff"))
        }
    }

    private fun initUi()
    {
        viewpager.adapter = SliderAdapter(this)
        btn_Register.setOnClickListener { startActivity(Intent(applicationContext, SignUpActivity::class.java)) }
        rl_LoginIn.setOnClickListener { startActivity(Intent(applicationContext, LoginActivity::class.java)) }
    }

    companion object {

        fun fromHtml(html: String): Spanned {

            return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                Html.fromHtml(html, Html.FROM_HTML_MODE_LEGACY)
            } else {
                Html.fromHtml(html)
            }
        }
    }
}
