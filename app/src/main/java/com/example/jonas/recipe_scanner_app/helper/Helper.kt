package com.example.jonas.recipe_scanner_app.helper

import android.app.Activity
import android.content.Context

class Helper{

    companion object {

        fun queryIngredients(list: List<String>): String {
            val builder = StringBuilder()
            for (item in list) {
                builder.append(item + "+")
            }
            return builder.toString()
        }
        /*fun createIntent(context: Context, thisClass: Class<CategoryActivity>, detectedItemsList: ArrayList<String>) {
            val intent = Intent(context, thisClass)
            intent.putStringArrayListExtra("keyIdentifier", detectedItemsList)
            startActivity(intent)
        }
        */
        fun createIntent(context: Context, list: ArrayList<String>, activity: Activity){
        }
    }
}