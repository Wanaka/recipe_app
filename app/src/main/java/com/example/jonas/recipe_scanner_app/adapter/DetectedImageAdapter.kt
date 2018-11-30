package com.haag.mlkit.imagelabeling.test

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.ml.vision.cloud.label.FirebaseVisionCloudLabel
import com.haag.mlkit.imagelabeling.test.ImageLabelAdapter.ItemHolder
import kotlinx.android.synthetic.main.scanned_word_item.view.*
import kotlinx.android.synthetic.main.word_item.view.*

class DetectedImageAdapter(private val list: List<String>) : RecyclerView.Adapter<DetectedImageAdapter.ItemHolder>() {


    lateinit var context: Context

    inner class ItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(currentItem: String) {
            itemView.detectedWord.text = currentItem
            //itemView.itemAccuracy.text = "Probability : ${(currentItem.confidence * 100).toInt()}%"
        }
    }

    override fun onBindViewHolder(holder: DetectedImageAdapter.ItemHolder, position: Int) {
        val currentItem: String = list[position]
        holder.bind(currentItem)
        holder.itemView.setOnClickListener {
            //notifyItemRemoved(position)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetectedImageAdapter.ItemHolder {
        context = parent.context
        return ItemHolder(LayoutInflater.from(context).inflate(R.layout.word_item, parent, false))
    }

    override fun getItemCount() = list.size
}
