package com.haag.mlkit.imagelabeling.test

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.ml.vision.cloud.label.FirebaseVisionCloudLabel
import kotlinx.android.synthetic.main.scanned_word_item.view.*

class ImageLabelAdapter(private val firebaseVisionList: List<Any>) : RecyclerView.Adapter<ImageLabelAdapter.ItemHolder>() {
    lateinit var context: Context

    inner class ItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindCloud(currentItem: FirebaseVisionCloudLabel) {
            itemView.scannedWord.text = currentItem.label
        }
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        val currentItem = firebaseVisionList[position]
        holder.bindCloud(currentItem as FirebaseVisionCloudLabel)
            holder.itemView.setOnClickListener {
                (context as ScanActivity).addWordToDetectedItems(currentItem.label)
            }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        context = parent.context
            return ItemHolder(LayoutInflater.from(context).inflate(R.layout.scanned_word_item, parent, false))
    }

    override fun getItemCount() = firebaseVisionList.size
}
