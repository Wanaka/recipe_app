package com.haag.mlkit.imagelabeling.test

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.scanned_word_item.view.*

class TextRecognitionAdapter(private val textRecognitionList: List<Any>) : RecyclerView.Adapter<TextRecognitionAdapter.ItemHolder>() {
    lateinit var context: Context

    inner class ItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindCloud(currentItem: String) {
            itemView.scannedWord.text = currentItem
        }
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        val currentItem = textRecognitionList[position]
        holder.bindCloud(currentItem.toString())
            holder.itemView.setOnClickListener {
                (context as ScanActivity).addWordToDetectedItems(currentItem.toString())
            }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        context = parent.context
            return ItemHolder(LayoutInflater.from(context).inflate(R.layout.scanned_word_item, parent, false))
    }

    override fun getItemCount() = textRecognitionList.size
}
