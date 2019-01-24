package com.haag.mlkit.imagelabeling.test

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.jonas.recipe_scanner_app.activity.CategoryActivity
import com.example.jonas.recipe_scanner_app.constant.Constant
import kotlinx.android.synthetic.main.word_item.view.*

class DetectedImageAdapter(private val list: List<String>, private val whichActivity: String) : RecyclerView.Adapter<DetectedImageAdapter.ItemHolder>() {

    lateinit var context: Context

    inner class ItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(currentItem: String) {
            itemView.wordItem.text = currentItem
        }
    }

    override fun onBindViewHolder(holder: DetectedImageAdapter.ItemHolder, position: Int) {
        val currentItem: String = list[position]
        holder.bind(currentItem)
        when(whichActivity) {
            Constant.MAIN -> {
                holder.itemView.setOnClickListener {
                    (context as ScanActivity).deleteWordToDetectedItems(position)
                }
            }
            Constant.CATEGORY -> {
                holder.itemView.setOnClickListener {
                    (context as CategoryActivity).deleteWordToDetectedItems(position)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetectedImageAdapter.ItemHolder {
        context = parent.context
        return ItemHolder(LayoutInflater.from(context).inflate(R.layout.word_item, parent, false))
    }

    override fun getItemCount() = list.size
}
