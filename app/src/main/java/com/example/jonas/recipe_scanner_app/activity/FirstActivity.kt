package com.example.jonas.recipe_scanner_app.activity

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.jonas.recipe_scanner_app.constant.Constant
import com.haag.mlkit.imagelabeling.test.R
import com.haag.mlkit.imagelabeling.test.ScanActivity
import kotlinx.android.synthetic.main.activity_first.*
import kotlinx.android.synthetic.main.custom_toolbar.*

class FirstActivity : AppCompatActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_first)
        setSupportActionBar(custom_toolbar)
        supportActionBar?.setTitle(R.string.scan_app_title)
        first_button_startScan.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.first_button_startScan -> {
                startIntent()
            }
        }
    }

    private fun startIntent(){
        val intent = Intent(this, ScanActivity::class.java)
        startActivityForResult(intent, 0)
    }
}
