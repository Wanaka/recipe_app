package com.haag.mlkit.imagelabeling.test


import android.app.Activity
import android.arch.lifecycle.Observer
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.example.jonas.recipe_scanner_app.activity.CategoryActivity
import com.example.jonas.recipe_scanner_app.constant.Constant
import com.example.jonas.recipe_scanner_app.helper.Helper
import com.example.jonas.recipe_scanner_app.viewmodel.ViewModel
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(), View.OnClickListener{

    private lateinit var itemAdapter: ImageLabelAdapter
    private lateinit var textRecognitionAdapter: TextRecognitionAdapter
    private lateinit var detectedItemAdapter: DetectedImageAdapter
    private var detectedItemsList: ArrayList<String> = ArrayList()
    var toggleBetweenImageAndTextRecognitionStates: Boolean = Constant.TRUE

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        scannedItemRC.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, Constant.FALSE)
        collectWordsRV.layoutManager = GridLayoutManager(this, Constant.RECYCLER_VIEW_SPAN_COUNT)
        scanFAB.setOnClickListener(this)
        doneButton.setOnClickListener(this)
        main_button_imagebutton.setOnClickListener(this)
        main_button_textbutton.setOnClickListener(this)
        scanLoading.visibility = View.INVISIBLE
        doneButton.visibility = View.INVISIBLE
        activateLabelRecognition()
    }

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

    private fun showDoneButton(){
        when {
            detectedItemsList.size >= 1 -> doneButton.visibility = View.VISIBLE
            else -> doneButton.visibility = View.INVISIBLE
        }
    }

    private fun activateLabelRecognition(){
        toggleBetweenImageAndTextRecognitionStates = Constant.TRUE
        setToggleButtonsBackgroundColors(Constant.TRUE)
        setToggleScanText(Constant.TRUE)
    }

    private fun activateTextRecognition(){
        toggleBetweenImageAndTextRecognitionStates = Constant.FALSE
        setToggleButtonsBackgroundColors(Constant.FALSE)
        setToggleScanText(Constant.FALSE)
    }

    private fun setToggleButtonsBackgroundColors(toggle: Boolean){
        when (toggle) {
            true -> {
                main_button_imagebutton.setBackgroundResource(R.color.colorLightBlue)
                main_button_textbutton.setBackgroundResource(R.color.colorGrey)
            }
            else -> {
                main_button_imagebutton.setBackgroundResource(R.color.colorGrey)
                main_button_textbutton.setBackgroundResource(R.color.colorLightBlue)
            }
        }
    }

    private fun setToggleScanText(toggle: Boolean){
        when (toggle) {
            true -> {
                main_text_toggleScanState.text = getString(R.string.image_recognition)
            }
            else -> {
                main_text_toggleScanState.text = getString(R.string.text_recognition)
            }
        }
    }

    fun runToastErrorMessageSomethingWentWrong(){
        Helper.scanningFailedToastWarning(baseContext, getString(R.string.something_went_wrong))
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
                    when(toggleBetweenImageAndTextRecognitionStates){
                       true -> getLabelsFromClod(cameraKitImage.bitmap)
                        else -> recognizeText(cameraKitImage.bitmap)
                    }
                }
            }

            R.id.doneButton -> {
                val intent = Intent(this, CategoryActivity::class.java)
                intent.putStringArrayListExtra(Constant.PUT_EXTRA_KEY, detectedItemsList)
                startActivityForResult(intent, 1)
            }

            R.id.main_button_imagebutton -> {activateLabelRecognition()}
            R.id.main_button_textbutton -> {activateTextRecognition()}
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
