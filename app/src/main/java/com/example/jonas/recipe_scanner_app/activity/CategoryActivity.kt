package com.example.jonas.recipe_scanner_app.activity

import android.arch.lifecycle.Observer
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.haag.mlkit.imagelabeling.test.R
import kotlinx.android.synthetic.main.custom_toolbar.*
import android.support.v7.widget.GridLayoutManager
import com.example.jonas.recipe_scanner_app.constant.Constant
import com.haag.mlkit.imagelabeling.test.DetectedImageAdapter
import kotlinx.android.synthetic.main.card_view_detected_items.*
import com.haag.mlkit.imagelabeling.test.MainActivity


class CategoryActivity : AppCompatActivity() {

    //CardDetected RecyclerView
    private lateinit var detectedItemAdapter: DetectedImageAdapter
    private var detectedItemsList: ArrayList<String> = ArrayList()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category)
        setSupportActionBar(custom_toolbar)
        supportActionBar?.setTitle(R.string.category_title)
        /*supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)*/

        val getPutExtraList = intent.getStringArrayListExtra(Constant.PUT_EXTRA_KEY)
        cardDetectedItemsRV.layoutManager = GridLayoutManager(this, Constant.RECYCLER_VIEW_SPAN_COUNT)
        addListToRecyclerView(getPutExtraList)
    }

    private fun addListToRecyclerView(getPutExtraList: List<String>){
        com.example.jonas.recipe_scanner_app.viewmodel.ViewModel.getListDetectedItems(getPutExtraList).observe(this, Observer {
            for(item in it!!) detectedItemsList.add(item)
            detectedItemAdapter = DetectedImageAdapter(detectedItemsList, Constant.CATEGORY)
            cardDetectedItemsRV.adapter = detectedItemAdapter
        })
    }

    fun deleteWordToDetectedItems(position: Int){
        detectedItemsList.removeAt(position)
        detectedItemAdapter = DetectedImageAdapter(detectedItemsList, Constant.CATEGORY)
        cardDetectedItemsRV.adapter = detectedItemAdapter
    }

    override fun onBackPressed() {
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra(Constant.PUT_EXTRA_KEY, detectedItemsList)
        setResult(RESULT_OK, intent)
        finish()
        super.onBackPressed()
    }
}
