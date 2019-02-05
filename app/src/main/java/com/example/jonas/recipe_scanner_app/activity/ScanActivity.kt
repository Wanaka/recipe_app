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
import com.example.jonas.recipe_scanner_app.activity.AddTextInputActivity
import com.example.jonas.recipe_scanner_app.activity.CategoryActivity
import com.example.jonas.recipe_scanner_app.constant.Constant
import com.example.jonas.recipe_scanner_app.helper.Helper
import com.example.jonas.recipe_scanner_app.viewmodel.ViewModel
import kotlinx.android.synthetic.main.activity_main.*


class ScanActivity : AppCompatActivity(), View.OnClickListener{

    private lateinit var itemAdapter: ImageLabelAdapter
    private lateinit var textRecognitionAdapter: TextRecognitionAdapter
    private lateinit var detectedItemAdapter: DetectedImageAdapter
    private var detectedItemsList: ArrayList<String> = ArrayList()
    private var toggleBetweenImageAndTextRecognitionStates: Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        scannedItemRC.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, Constant.FALSE)
        collectWordsRV.layoutManager = GridLayoutManager(this, Constant.RECYCLER_VIEW_SPAN_COUNT)
        scanFAB.setOnClickListener(this)
        scan_button_findRecipes.setOnClickListener(this)
        scan_button_imageScanning.setOnClickListener(this)
        scan_button_textScanning.setOnClickListener(this)
        main_background_green.setOnClickListener(this)
        scan_text_clickToSelectFood.visibility = View.INVISIBLE
        scanLoading.visibility = View.INVISIBLE
        scan_button_findRecipes.visibility = View.INVISIBLE
        activateLabelRecognition()
    }

    private fun getLabelsFromClod(bitmap: Bitmap) {
        ViewModel.getLabelsFromClod(bitmap).observe(this, Observer {
            scanLoading.visibility = View.INVISIBLE
            if (it != null) {
                if (!it.isEmpty()) {
                    setRecyclerViewContentVisible()
                    itemAdapter = ImageLabelAdapter(it)
                    scannedItemRC.adapter = itemAdapter
                } else {
                    Helper.scanningFailedToastWarning(baseContext, getString(R.string.toast_noImageWasFound))
                }
            }
        })
    }

    private fun recognizeText(bitmap: Bitmap) {
        ViewModel.getTextRecognitionFromDevice(bitmap).observe(this, Observer {
            scanLoading.visibility = View.INVISIBLE
            if (it != null) {
                if(!it.isEmpty()) {
                    setRecyclerViewContentVisible()
                    textRecognitionAdapter = TextRecognitionAdapter(it)
                    scannedItemRC.adapter = textRecognitionAdapter
                } else {
                Helper.scanningFailedToastWarning(baseContext, getString(R.string.toast_noTextWasFound))
                }
            }
        })
    }

    fun setRecyclerViewContentVisible(){
        scannedItemRC.visibility = View.VISIBLE
        scan_text_clickToSelectFood.visibility = View.VISIBLE
    }

    fun setRecyclerViewInvisibleWhenLoadingNewContent(){
        scannedItemRC.visibility = View.INVISIBLE
        scan_text_clickToSelectFood.visibility = View.INVISIBLE
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

    private fun showDoneButton(){
        when {
            detectedItemsList.size >= 1 -> scan_button_findRecipes.visibility = View.VISIBLE
            else -> scan_button_findRecipes.visibility = View.INVISIBLE
        }
    }

    private fun activateLabelRecognition(){
        toggleBetweenImageAndTextRecognitionStates = true
        setToggleButtonsBackgroundColors(true)
        setToggleScanText(true)
    }

    private fun activateTextRecognition(){
        toggleBetweenImageAndTextRecognitionStates = false
        setToggleButtonsBackgroundColors(false)
        setToggleScanText(false)
    }

    private fun setToggleButtonsBackgroundColors(toggle: Boolean){
        when (toggle) {
            true -> {
                scan_button_imageScanning.setBackgroundResource(R.drawable.green_image_text_toggle_background)
                scan_button_textScanning.setBackgroundResource(R.drawable.black_opacity_background)
            }
            else -> {
                scan_button_imageScanning.setBackgroundResource(R.drawable.black_opacity_background)
                scan_button_textScanning.setBackgroundResource(R.drawable.green_image_text_toggle_background)
            }
        }
    }

    private fun setToggleScanText(toggle: Boolean){
        when (toggle) {
            true -> {
                scan_text_modeText.text = getString(R.string.image_recognition)
            }
            else -> {
                scan_text_modeText.text = getString(R.string.text_recognition)
            }
        }
    }

        public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                val getPutExtraList = data.getStringArrayListExtra(Constant.PUT_EXTRA_KEY)

                //should be in a func
                detectedItemsList.clear()
                for (item in getPutExtraList) detectedItemsList.add(item)
                detectedItemAdapter = DetectedImageAdapter(detectedItemsList, Constant.MAIN)
                collectWordsRV.adapter = detectedItemAdapter
            }
        }
            if (requestCode == 2) {
                if (resultCode == Activity.RESULT_OK) {
                    if(data.getStringExtra(Constant.PUT_EXTRA_KEY) != null){
                        val getNewItemFromAddTextInputActivity = data.getStringExtra(Constant.PUT_EXTRA_KEY)
                        detectedItemsList.add(getNewItemFromAddTextInputActivity)
                        detectedItemAdapter = DetectedImageAdapter(detectedItemsList, Constant.MAIN)
                        collectWordsRV.adapter = detectedItemAdapter
                        showDoneButton()
                    }
                }
            }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.scanFAB -> {
                scanLoading.visibility = View.VISIBLE
                setRecyclerViewInvisibleWhenLoadingNewContent()
                cameraView.captureImage { cameraKitImage ->
                    when(toggleBetweenImageAndTextRecognitionStates){
                        true -> getLabelsFromClod(cameraKitImage.bitmap)
                        else -> recognizeText(cameraKitImage.bitmap)
                    }
                }
            }

            R.id.scan_button_findRecipes -> {
                val intent = Intent(this, CategoryActivity::class.java)
                intent.putStringArrayListExtra(Constant.PUT_EXTRA_KEY, detectedItemsList)
                startActivityForResult(intent, 1)
            }

            R.id.scan_button_imageScanning -> {activateLabelRecognition()}

            R.id.scan_button_textScanning -> {activateTextRecognition()}

            R.id.main_background_green -> {
                val intent = Intent(this, AddTextInputActivity::class.java)
                startActivityForResult(intent, 2)
            }
        }
    }

    fun runToastErrorMessageSomethingWentWrong(){
        Helper.scanningFailedToastWarning(baseContext, getString(R.string.something_went_wrong))
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
