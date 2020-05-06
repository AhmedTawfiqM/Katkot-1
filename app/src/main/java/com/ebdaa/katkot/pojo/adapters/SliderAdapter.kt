package com.ebdaa.katkot.pojo.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.viewpager.widget.PagerAdapter

import com.ebdaa.katkot.R


class SliderAdapter(private val context: Context) : PagerAdapter() {

    private val titles: Array<String>


    init {
        titles = context.resources.getStringArray(R.array.titles_FS)
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {

        val inf = LayoutInflater.from(context).inflate(R.layout.slide_layout, container, false)
        return inf
    }

    override fun getCount(): Int {
        return titles.size
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object`
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {

        container.removeView(`object` as LinearLayout)
    }
}
