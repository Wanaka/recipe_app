package com.example.jonas.recipe_scanner_app.helper

import android.content.Context
import android.widget.Toast

class Helper{

    companion object {

        fun queryIngredients(list: List<String>): String {
            val builder = StringBuilder()
            for (item in list) {
                builder.append(item + "+")
            }
            return builder.toString()
        }

        fun scanningFailedToastWarning(context: Context, message: String){
            //scanLoading.visibility = View.INVISIBLE
            Toast.makeText(context, message,
                    Toast.LENGTH_SHORT).show()
        }
    }
}