package com.example.jonas.recipe_scanner_app.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.jonas.recipe_scanner_app.constant.Constant
import com.example.jonas.recipe_scanner_app.helper.Helper
import com.example.jonas.recipe_scanner_app.model.Platform
import com.haag.mlkit.imagelabeling.test.R
import kotlinx.android.synthetic.main.activity_web.*

class WebActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web)
        searchRecipeOnWeb()
    }

    private fun searchRecipeOnWeb(){
        web_webview_web.loadUrl("${intent.getParcelableExtra<Platform>(Constant.PLATFORM_URL)._url}" +
                "${Helper.queryIngredients(intent.getStringArrayListExtra(Constant.PUT_EXTRA_KEY))}")
    }
}
