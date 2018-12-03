package com.example.jonas.recipe_scanner_app.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel

class ViewModel: ViewModel(){

    companion object {

        fun getWord(word: String): LiveData<String>{
            val text = MutableLiveData<String>()
            text.value = word
            return text
        }
    }

}