package com.example.jonas.recipe_scanner_app.activity

import android.app.ActionBar
import android.app.Activity
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.Toolbar
import com.haag.mlkit.imagelabeling.test.R
import kotlinx.android.synthetic.main.custom_toolbar.*
import android.content.Intent
import android.view.MenuItem


class CategoryActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category)
        setSupportActionBar(custom_toolbar)
        supportActionBar?.setTitle(R.string.category_title)
        /*
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        */

        val s:String = intent.getStringExtra("keyIdentifier")
Log.d("TAG", "$s")
    }

}
