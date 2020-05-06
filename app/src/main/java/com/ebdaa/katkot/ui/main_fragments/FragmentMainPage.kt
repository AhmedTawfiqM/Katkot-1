package com.ebdaa.katkot.ui.main_fragments

import android.os.Bundle
import androidx.fragment.app.Fragment

import android.util.DisplayMetrics
import android.view.Display
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.FragmentActivity

import com.ebdaa.katkot.R
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView

import java.util.Objects

class FragmentMainPage : Fragment() {

    //......
    //Vars
    private var mAdView: AdView? = null


    private// Step 2 - Determine the screen width (less decorations) to use for the ad width.
    // Step 3 - Get adaptive ad size and return for setting on the ad view.
    val adSize: AdSize
        get() {
            val display = Objects.requireNonNull<FragmentActivity>(activity).getWindowManager().getDefaultDisplay()
            val outMetrics = DisplayMetrics()
            display.getMetrics(outMetrics)

            val widthPixels = outMetrics.widthPixels.toFloat()
            val density = outMetrics.density

            val adWidth = (widthPixels / density).toInt()
            return AdSize.getCurrentOrientationAnchoredAdaptiveBannerAdSize(activity, adWidth)
        }
    //
    //...

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val view = inflater.inflate( R.layout.fragment_main_page, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        LoadAdaptiveBannerAds()

    }


    private fun LoadAdaptiveBannerAds() {

        // Step 1 - Create an AdView and set the ad unit ID on it.
        mAdView = AdView(activity!!)
        mAdView!!.adUnitId = getString(R.string.ID_BAnner)
        //binding!!.bannerFrame.addView(mAdView)
        loadBanner()
    }


    private fun loadBanner() {

        // Create an ad request. Check your logcat output for the hashed device ID
        // to get test ads on a physical device, e.g.,
        // "Use AdRequest.Builder.addTestDevice("ABCDE0123") to get test ads on this
        // device."
        val adRequest = AdRequest.Builder()
                .addTestDevice("DFA2E616549C8FDA2211193E3F09FF41")
                .build()

        //...

        val adSize = adSize
        // Step 4 - Set the adaptive ad size on the ad view.
        mAdView!!.adSize = adSize

        // Step 5 - Start loading the ad in the background.
        mAdView!!.loadAd(adRequest)


        mAdView!!.adListener = object : AdListener() {

            override fun onAdLoaded() {
                // Code to be executed when an ad finishes loading.
                Toast.makeText(context, "Loaded Successfully", Toast.LENGTH_SHORT).show()
                //..
            }

            override fun onAdFailedToLoad(errorCode: Int) {
                // Code to be executed when an ad request fails.
                Toast.makeText(context, "Loaded FAilled $errorCode", Toast.LENGTH_SHORT).show()
            }

            override fun onAdOpened() {
                // Code to be executed when an ad opens an overlay that
                // covers the screen.
                // Toast.makeText(getBaseContext(), "Ad Opended", Toast.LENGTH_SHORT).show();
            }

            override fun onAdLeftApplication() {
                // Code to be executed when the user has left the app.
                // Toast.makeText(getBaseContext(), "User Left The APp", Toast.LENGTH_SHORT).show();
            }

            override fun onAdClosed() {
                // Code to be executed when when the user is about to return
                // to the app after tapping on an ad.
                // Toast.makeText(getBaseContext(), "USer Close Ad", Toast.LENGTH_SHORT).show();
            }
        }


    }
}
