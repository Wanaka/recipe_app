package com.example.jonas.recipe_scanner_app.repo

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.graphics.Bitmap
import android.util.Log
import com.example.jonas.recipe_scanner_app.constant.Constant
import com.example.jonas.recipe_scanner_app.model.Platform
import com.google.firebase.ml.vision.FirebaseVision
import com.google.firebase.ml.vision.common.FirebaseVisionImage
import com.haag.mlkit.imagelabeling.test.ScanActivity

class Repo{
    var instanceOfScanActivity: ScanActivity = ScanActivity()

    fun getPlatformList(): LiveData<List<Platform>> {
         var mutablePlatform: MutableLiveData<List<Platform>> = MutableLiveData()
         var platforms: ArrayList<Platform> = ArrayList()
         platforms.add(Platform(Constant.BBC, Constant.BBC_URL))
         platforms.add(Platform(Constant.DELISH, Constant.DELISH_URL))
         platforms.add(Platform(Constant.JAMIE_OLIVER, Constant.JAMIE_OLIVER_URL))
         platforms.add(Platform(Constant.TASTY, Constant.TASTY_URL))
         mutablePlatform.value = platforms
         return mutablePlatform
    }

    fun getLabelsFromClod(bitmap: Bitmap): LiveData<List<Any>> {
        var mutableLabelRecognitionList: MutableLiveData<List<Any>> = MutableLiveData()
        val image = FirebaseVisionImage.fromBitmap(bitmap)
        val detector = FirebaseVision.getInstance()
                .visionCloudLabelDetector
        detector.detectInImage(image)
                .addOnSuccessListener {
                    mutableLabelRecognitionList.value = it
                }
                .addOnFailureListener {
                    instanceOfScanActivity.runToastErrorMessageSomethingWentWrong()
                }
        return mutableLabelRecognitionList
    }

    fun getTextRecognitionFromDevice(bitmap: Bitmap): LiveData<List<Any>> {
        var recognizedTextList: ArrayList<Any> = ArrayList()
        var mutableTextRecognitionList: MutableLiveData<List<Any>> = MutableLiveData()
        val image = FirebaseVisionImage.fromBitmap(bitmap)
        val detector = FirebaseVision.getInstance()
                .onDeviceTextRecognizer
        detector.processImage(image)
                .addOnSuccessListener { firebaseVisionText ->
                    for (block in firebaseVisionText.textBlocks) {
                        for (line in block.lines) {
                            for (element in line.elements) {
                                recognizedTextList.add(element.text)
                            }
                        }
                    }
                    mutableTextRecognitionList.value = recognizedTextList
                }
                .addOnFailureListener {
                    instanceOfScanActivity.runToastErrorMessageSomethingWentWrong()
                }
        return mutableTextRecognitionList
    }

}
