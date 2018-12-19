package com.example.jonas.recipe_scanner_app.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.jonas.recipe_scanner_app.constant.Constant
import com.example.jonas.recipe_scanner_app.helper.Helper
import com.haag.mlkit.imagelabeling.test.R
import kotlinx.android.synthetic.main.activity_web.*

class WebActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web)

        val getList = intent.getStringArrayListExtra(Constant.PUT_EXTRA_KEY)
       /* var getList: ArrayList<String> = ArrayList()
        getList.add("Sausage")
        getList.add("onion")
        getList.add("bacon")*/
        web_webview_web.loadUrl("https://www.bbcgoodfood.com/search/recipes?query=${Helper.queryIngredients(getList)}")
    }
}
