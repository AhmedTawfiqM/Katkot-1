package com.ebdaa.katkot.pojo.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ebdaa.katkot.R
import com.ebdaa.katkot.pojo.adapters.viewholders.ViewHolderPeriods
import com.ebdaa.katkot.pojo.model.FarmResponse

class AdapterFarms(val mContext: Context,
                   val list: ArrayList<FarmResponse>) : RecyclerView.Adapter<ViewHolderFarm>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderFarm {

        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.single_farm, parent, false)

        return ViewHolderFarm(view)
    }

    override fun getItemCount(): Int {

        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolderFarm, position: Int) {

        holder.tvName.text = list.get(position).TheName
    }

}

class ViewHolderFarm(val view: View) : RecyclerView.ViewHolder(view) {

    val tvName: TextView
    init {
        tvName = view.findViewById(R.id.tvName)
    }
}