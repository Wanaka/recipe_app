package com.example.jonas.recipe_scanner_app.repo

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.example.jonas.recipe_scanner_app.constant.Constant
import com.example.jonas.recipe_scanner_app.model.Platform

class Repo{
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
}
