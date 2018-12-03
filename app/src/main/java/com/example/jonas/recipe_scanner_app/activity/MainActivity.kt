package com.haag.mlkit.imagelabeling.test


import android.app.Activity
import android.arch.lifecycle.Observer
import android.graphics.Bitmap
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.example.jonas.recipe_scanner_app.constant.Constant
import com.example.jonas.recipe_scanner_app.viewmodel.ViewModel
import com.google.firebase.ml.vision.FirebaseVision
import com.google.firebase.ml.vision.common.FirebaseVisionImage
import kotlinx.android.synthetic.main.activity_main.*
import android.graphics.drawable.Drawable
import android.graphics.drawable.ScaleDrawable


class MainActivity : AppCompatActivity(), View.OnClickListener{

    //Scan RecyclerView
    private var itemsList: ArrayList<Any> = ArrayList()
    private lateinit var itemAdapter: ImageLabelAdapter

    //Items Detected RecyclerView
    private var detectedItemsList: ArrayList<String> = ArrayList()
    private lateinit var detectedItemAdapter: DetectedImageAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //Add adapter

        scannedItemRC.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        collectWordsRV.layoutManager = GridLayoutManager(this, Constant.RECYCLER_VIEW_SPAN_COUNT)
        //scanButton?.setOnClickListener(this)
        scanFAB?.setOnClickListener(this)
    }


    private fun getLabelsFromClod(bitmap: Bitmap) {
        val image = FirebaseVisionImage.fromBitmap(bitmap)
        val detector = FirebaseVision.getInstance().visionCloudLabelDetector
        itemsList.clear()
        detector.detectInImage(image)
                .addOnSuccessListener {
                    // Task completed successfully
                    itemsList.addAll(it)
                    itemAdapter = ImageLabelAdapter(itemsList, true)
                    scannedItemRC.adapter = itemAdapter
                }
                .addOnFailureListener {
                    // Task failed with an exception
                    Toast.makeText(baseContext,"Sorry, something went wrong!", Toast.LENGTH_SHORT).show()
                }
    }

    fun addWordToDetectedItems(word: String){
        ViewModel.getWord(word).observe(this, Observer {
            /*detectedItemsList.add("Hello")
            detectedItemsList.add("World")
            detectedItemsList.add("!")*/

            detectedItemsList.add(it.toString())
            detectedItemAdapter = DetectedImageAdapter(detectedItemsList)
            collectWordsRV.adapter = detectedItemAdapter
        })

    }

    fun deleteWordToDetectedItems(position: Int){
            detectedItemsList.removeAt(position)
            detectedItemAdapter = DetectedImageAdapter(detectedItemsList)
            collectWordsRV.adapter = detectedItemAdapter
    }

    override fun onClick(v: View?) {
        cameraView.captureImage { cameraKitImage ->
            // Get the Bitmap from the captured shot

            getLabelsFromClod(cameraKitImage.bitmap)
            runOnUiThread {
                //showPreview()
                //imagePreview.setImageBitmap(cameraKitImage.bitmap)
            }

            /*getLabelsFromDevice(cameraKitImage.bitmap)
            runOnUiThread {
                showPreview()
                imagePreview.setImageBitmap(cameraKitImage.bitmap)
            }*/

        }
    }



    override fun onResume() {
        super.onResume()
        cameraView.start()
    }

    override fun onPause() {
        cameraView.stop()
        super.onPause()
    }

     fun showPreview() {
        //framePreview.visibility = View.VISIBLE
        cameraView?.visibility = View.GONE
    }

    fun hidePreview() {
        //framePreview.visibility = View.GONE
        cameraView?.visibility = View.VISIBLE
    }
}
