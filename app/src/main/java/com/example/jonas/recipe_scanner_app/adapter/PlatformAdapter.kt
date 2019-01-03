package com.haag.mlkit.imagelabeling.test

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.platform_item.view.*


class PlatformAdapter(private val list: List<String>, private val whichActivity: String) : RecyclerView.Adapter<PlatformAdapter.ItemHolder>() {

    lateinit var context: Context
    private var mSelectedItem = -1

    inner class ItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(currentItem: String) {
            itemView.platformWord.text = currentItem

            itemView.platformWord.setOnClickListener {
                itemView.platformWord.setBackgroundResource(R.drawable.platform_rounded_corners_textview_blue)
                //holder.itemView.platformWord.setBackgroundResource(R.drawable.platform_rounded_corners_textview)
            }
        }
    }

    override fun onBindViewHolder(holder: PlatformAdapter.ItemHolder, position: Int) {
        val currentItem: String = list[position]
        holder.bind(currentItem)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlatformAdapter.ItemHolder {
        context = parent.context
        return ItemHolder(LayoutInflater.from(context).inflate(R.layout.platform_item, parent, false))
    }

    override fun getItemCount() = list.size
}
