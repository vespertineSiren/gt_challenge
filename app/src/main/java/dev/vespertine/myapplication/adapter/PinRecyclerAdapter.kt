package dev.vespertine.myapplication.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import dev.vespertine.myapplication.R
import dev.vespertine.myapplication.model.PinData
import kotlinx.android.synthetic.main.pin_list_element_layout.view.*

class PinAdapter(
    pins: MutableList<PinData> = mutableListOf()
) : BaseRecyclerAdapter<PinData>(pins){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.pin_list_element_layout, parent, false))

    class ViewHolder(view :View) : BaseViewHolder<PinData>(view) {
        @SuppressLint("SetTextI18n")
        override fun onBind(data : PinData) {
            view.tv_pin_name_list.text = data.name
        }
    }

    fun setPins(data : List<PinData>) {
        masterList.addAll(data)
        notifyDataSetChanged()
    }
}