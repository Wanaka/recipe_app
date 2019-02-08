package com.example.jonas.recipe_scanner_app.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.example.jonas.recipe_scanner_app.constant.Constant
import com.example.jonas.recipe_scanner_app.helper.Helper
import com.example.jonas.recipe_scanner_app.model.Platform
import com.haag.mlkit.imagelabeling.test.R
import kotlinx.android.synthetic.main.activity_web.*
import kotlinx.android.synthetic.main.custom_toolbar.*

class WebActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var setTitle = intent.getParcelableExtra<Platform>(Constant.PLATFORM_URL)._title
        setContentView(R.layout.activity_web)
        setSupportActionBar(custom_toolbar)
        supportActionBar!!.title = setTitle
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        searchRecipeOnWeb()
    }

    private fun searchRecipeOnWeb(){
        var setUrl = intent.getParcelableExtra<Platform>(Constant.PLATFORM_URL)._url
        var setFoodsInQuery = intent.getStringArrayListExtra(Constant.PUT_EXTRA_KEY)
        web_webview_web.loadUrl("$setUrl" + "${Helper.queryIngredients(setFoodsInQuery)}")
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item!!.itemId) {
            android.R.id.home -> {
                goBack()
            }
            else -> return super.onOptionsItemSelected(item)
        }
        return true
    }

    private fun goBack(){
        finish()
    }
}
