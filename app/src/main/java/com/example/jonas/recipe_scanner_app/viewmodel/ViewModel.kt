package com.example.jonas.recipe_scanner_app.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.graphics.Bitmap
import com.example.jonas.recipe_scanner_app.model.Platform
import com.example.jonas.recipe_scanner_app.repo.Repo


class ViewModel: ViewModel(){

    companion object {

        fun getWord(word: String): LiveData<String>{
            val text = MutableLiveData<String>()
            text.value = word
            return text
        }

        fun getListDetectedItems(list: List<String>): LiveData<List<String>>{
            val mutableList = MutableLiveData<List<String>>()
            mutableList.value = list
            return mutableList
        }

        fun getPlatformList(): LiveData<List<Platform>>{
            return Repo().getPlatformList()
        }

        fun getLabelsFromClod(bitmap: Bitmap): LiveData<List<Any>>{
            return Repo().getLabelsFromClod(bitmap)
        }

        fun getTextRecognitionFromDevice(bitmap: Bitmap): LiveData<List<Any>>{
            return Repo().getTextRecognitionFromDevice(bitmap)
        }
    }

}
