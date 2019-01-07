package com.example.jonas.recipe_scanner_app.activity

import android.arch.lifecycle.Observer
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.haag.mlkit.imagelabeling.test.R
import kotlinx.android.synthetic.main.custom_toolbar.*
import android.support.v7.widget.GridLayoutManager
import android.view.View
import com.example.jonas.recipe_scanner_app.constant.Constant
import com.example.jonas.recipe_scanner_app.model.Platform
import com.haag.mlkit.imagelabeling.test.DetectedImageAdapter
import kotlinx.android.synthetic.main.card_view_detected_items.*
import com.haag.mlkit.imagelabeling.test.MainActivity
import com.haag.mlkit.imagelabeling.test.PlatformAdapter
import kotlinx.android.synthetic.main.activity_category.*
import kotlinx.android.synthetic.main.card_layout_platform.*


class CategoryActivity : AppCompatActivity(), View.OnClickListener {

    //CardDetected RecyclerView
    private lateinit var detectedItemAdapter: DetectedImageAdapter
    private var detectedItemsList: ArrayList<String> = ArrayList()

    //Platform RecyclerView
    private lateinit var platformItemAdapter: PlatformAdapter
    var getPlatformsList: MutableList<Platform> = ArrayList()

    private val platformBBC = Platform(Constant.BBC, Constant.BBC_URL) // keep for now

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category)
        setSupportActionBar(custom_toolbar)
        supportActionBar?.setTitle(R.string.category_title)
        category_fab_addMoreItems.setOnClickListener(this)
        category_button_findRecipe.setOnClickListener(this)
        cardDetectedItemsRV.layoutManager = GridLayoutManager(this, Constant.RECYCLER_VIEW_SPAN_COUNT)
        card_rv_platform_rv.layoutManager = GridLayoutManager(this, Constant.RECYCLER_VIEW_SPAN_COUNT)

        //Get list from MainActivity and put it in RecyclerView
        addDetectedItemsListRecyclerView(intent.getStringArrayListExtra(Constant.PUT_EXTRA_KEY))
        addPlatformsRecyclerView() //Platform recyclerview
    }

    private fun addPlatformsRecyclerView(){
        getPlatformsList.add(platformBBC)
        platformItemAdapter = PlatformAdapter(getPlatformsList, Constant.CATEGORY)
        card_rv_platform_rv.adapter = platformItemAdapter
    }

    private fun addDetectedItemsListRecyclerView(list: List<String>){
        com.example.jonas.recipe_scanner_app.viewmodel.ViewModel.getListDetectedItems(list).observe(this, Observer {
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

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.category_fab_addMoreItems -> { //Send list back to MainActivity
                // TODO: create an helper intent class to re-use intent code this one and on onBackPressed() ??
                val intent = Intent(this, MainActivity::class.java)
                intent.putExtra(Constant.PUT_EXTRA_KEY, detectedItemsList)
                setResult(RESULT_OK, intent)
                finish()
            }
            R.id.category_button_findRecipe -> {
                val intent = Intent(this, WebActivity::class.java)
                intent.putExtra(Constant.PUT_EXTRA_KEY, detectedItemsList)
                intent.putExtra(Constant.PLATFORM_URL, platformBBC)
                startActivity(intent)
            }
        }
    }

    override fun onBackPressed() { //Send list back to MainActivity
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra(Constant.PUT_EXTRA_KEY, detectedItemsList)
        setResult(RESULT_OK, intent)
        finish()
        super.onBackPressed()
    }
}
