package com.haag.mlkit.imagelabeling.test


import android.app.Activity
import android.arch.lifecycle.Observer
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.jonas.recipe_scanner_app.activity.CategoryActivity
import com.example.jonas.recipe_scanner_app.constant.Constant
import com.example.jonas.recipe_scanner_app.viewmodel.ViewModel
import com.google.firebase.ml.vision.FirebaseVision
import com.google.firebase.ml.vision.common.FirebaseVisionImage
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(), View.OnClickListener{

    private var itemsList: ArrayList<Any> = ArrayList()
    private lateinit var itemAdapter: ImageLabelAdapter

    private var detectedItemsList: ArrayList<String> = ArrayList()
    private lateinit var detectedItemAdapter: DetectedImageAdapter

    private var recognizedTextList: ArrayList<Any> = ArrayList()
    private lateinit var textRecognitionAdapter: TextRecognitionAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        scannedItemRC.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        collectWordsRV.layoutManager = GridLayoutManager(this, Constant.RECYCLER_VIEW_SPAN_COUNT)
        scanFAB.setOnClickListener(this)
        doneButton.setOnClickListener(this)
        scanLoading.visibility = View.INVISIBLE
        doneButton.visibility = View.INVISIBLE
    }

    //set in other class
    private fun getLabelsFromClod(bitmap: Bitmap) {
        ViewModel.getLabelsFromClod(bitmap).observe(this, Observer {
            scanLoading.visibility = View.INVISIBLE
            itemAdapter = ImageLabelAdapter(it!!)
            scannedItemRC.adapter = itemAdapter
        })
    }

    private fun recognizeText(bitmap: Bitmap) {
        ViewModel.getTextRecognitionFromDevice(bitmap).observe(this, Observer {
            scanLoading.visibility = View.INVISIBLE
            textRecognitionAdapter = TextRecognitionAdapter(it!!)
            scannedItemRC.adapter = textRecognitionAdapter
        })
    }

    fun addWordToDetectedItems(word: String){
        ViewModel.getWord(word).observe(this, Observer {
            detectedItemsList.add(it.toString())
            detectedItemAdapter = DetectedImageAdapter(detectedItemsList, Constant.MAIN)
            collectWordsRV.adapter = detectedItemAdapter
            showDoneButton()
        })
    }

    fun deleteWordToDetectedItems(position: Int){
        detectedItemsList.removeAt(position)
        detectedItemAdapter = DetectedImageAdapter(detectedItemsList, Constant.MAIN)
        collectWordsRV.adapter = detectedItemAdapter
        showDoneButton()
    }

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                val getPutExtraList = data.getStringArrayListExtra(Constant.PUT_EXTRA_KEY)
                detectedItemsList.clear()
                for (item in getPutExtraList) detectedItemsList.add(item)
                detectedItemAdapter = DetectedImageAdapter(detectedItemsList, Constant.MAIN)
                collectWordsRV.adapter = detectedItemAdapter
            }
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.scanFAB -> {
                scanLoading.visibility = View.VISIBLE
                cameraView.captureImage { cameraKitImage ->
                    //getLabelsFromClod(cameraKitImage.bitmap)
                    recognizeText(cameraKitImage.bitmap)
                }
            }

            R.id.doneButton -> {
                val intent = Intent(this, CategoryActivity::class.java)
                intent.putStringArrayListExtra(Constant.PUT_EXTRA_KEY, detectedItemsList)
                startActivityForResult(intent, 1)
            }
        }
    }

    // maybe set in another class ??
    private fun showDoneButton(){
        when {
            detectedItemsList.size >= 1 -> doneButton.visibility = View.VISIBLE
            else -> doneButton.visibility = View.INVISIBLE
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
}


/* getLabelsFromClod function!

        val image = FirebaseVisionImage.fromBitmap(bitmap)
        val detector = FirebaseVision.getInstance()
                .visionCloudLabelDetector
        itemsList.clear()
        detector.detectInImage(image)
                .addOnSuccessListener {
                    scanLoading.visibility = View.INVISIBLE
                    itemsList.addAll(it)
                    itemAdapter = ImageLabelAdapter(itemsList)
                    scannedItemRC.adapter = itemAdapter
                }
                .addOnFailureListener {
                    scanLoading.visibility = View.INVISIBLE
                    Toast.makeText(baseContext, getString(R.string.something_went_wrong),
                            Toast.LENGTH_SHORT).show()
                }
                */

/* recognizeText function!
        val image = FirebaseVisionImage.fromBitmap(bitmap)
        val detector = FirebaseVision.getInstance()
                .onDeviceTextRecognizer
        recognizedTextList.clear()
        itemsList.clear()
        detector.processImage(image)
                .addOnSuccessListener { firebaseVisionText ->
                    for (block in firebaseVisionText.textBlocks) {
                        for (line in block.lines) {
                            for (element in line.elements) {
                                scanLoading.visibility = View.INVISIBLE
                                recognizedTextList.add(element.text)
                            }
                        }
                    }
                    itemsList.addAll(recognizedTextList)
                    textRecognitionAdapter = TextRecognitionAdapter(itemsList)
                    scannedItemRC.adapter = textRecognitionAdapter
                }
                .addOnFailureListener {
                    scanLoading.visibility = View.INVISIBLE
                    Toast.makeText(baseContext, getString(R.string.something_went_wrong),
                            Toast.LENGTH_SHORT).show()
                }
                */