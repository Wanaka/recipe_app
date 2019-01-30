package com.haag.mlkit.imagelabeling.test

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.jonas.recipe_scanner_app.constant.Constant
import com.example.jonas.recipe_scanner_app.model.Platform
import kotlinx.android.synthetic.main.platform_item.view.*


class PlatformAdapter(private val list: List<Platform>, private val whichActivity: String) : RecyclerView.Adapter<PlatformAdapter.ItemHolder>() {

    private lateinit var context: Context
    private var selectedRowPositionWhenClicked = 0

    var mOnItemClickListener: OnItemClickListener? = null

    inner class ItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(currentItem: Platform, position: Int) {
            defaultSelectedPlatform(itemView, currentItem)
            itemView.platformWord.text = currentItem._title
            itemView.platformWord.setOnClickListener {
                selectedRowPositionWhenClicked = position
                mOnItemClickListener?.onItemClick(currentItem)
                notifyDataSetChanged()
            }
            changeBackgroundColorForSelectedPlatform(itemView, position)
        }
    }

    override fun onBindViewHolder(holder: PlatformAdapter.ItemHolder, position: Int) {
        val currentItem: Platform = list[position]
        holder.bind(currentItem, position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlatformAdapter.ItemHolder {
        context = parent.context
        return ItemHolder(LayoutInflater.from(context).inflate(R.layout.platform_item, parent, false))
    }

    override fun getItemCount() = list.size

    fun defaultSelectedPlatform(itemView: View, currentItem: Platform){
        if(selectedRowPositionWhenClicked == 0) {
            itemView.platformWord.setBackgroundResource(R.drawable.platform_rounded_corners_textview_colored)
            mOnItemClickListener?.onItemClick(Platform(Constant.BBC, Constant.BBC_URL))
        }
    }
    fun changeBackgroundColorForSelectedPlatform(itemView: View, position: Int){
        when (selectedRowPositionWhenClicked) {
            position -> itemView.platformWord.setBackgroundResource(R.drawable.platform_rounded_corners_textview_colored)
            else -> itemView.platformWord.setBackgroundResource(R.drawable.platform_rounded_corners_textview)
        }
    }

    interface  OnItemClickListener {
        fun onItemClick(platform: Platform)
    }
}
