package com.example.jonas.recipe_scanner_app.repo

import com.example.jonas.recipe_scanner_app.model.Platform

class Repo(){

     fun getPlatformItems():List<Platform>{
         var platforms: ArrayList<Platform> = ArrayList()
         val bbcPlatform = Platform("BBC", "https://www.bbcgoodfood.com/search/recipes?query=")
         val allRecipesPlatform = Platform("All Recipes", "https://www.allrecipes.com/search/results/?wt=")
         platforms.add(bbcPlatform)
         platforms.add(allRecipesPlatform)
         return platforms
    }
}