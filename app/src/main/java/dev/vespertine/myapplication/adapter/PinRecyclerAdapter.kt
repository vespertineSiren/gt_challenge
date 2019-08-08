package dev.vespertine.myapplication.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import dev.vespertine.myapplication.R
import dev.vespertine.myapplication.model.PinData
import kotlinx.android.synthetic.main.pin_list_element_layout.view.*

class PinAdapter(
    private val pins: MutableList<PinData> = mutableListOf(),
    private val listener: (PinData) -> Unit
) : RecyclerView.Adapter<PinAdapter.ViewHolder>() {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(pins[position], listener)
    }


    override fun getItemCount(): Int = pins.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.pin_list_element_layout, parent, false))
    }

    class ViewHolder(view : View) : RecyclerView.ViewHolder(view) {

        private val pinName :TextView = view.tv_pin_name_list

        fun bind(data : PinData, listener: (PinData) -> Unit) = with(itemView) {
            pinName.text = data.name
            setOnClickListener { listener(data) }
        }
    }

    fun setPins(data : List<PinData>) {
        pins.addAll(data)
        notifyDataSetChanged()
    }
}