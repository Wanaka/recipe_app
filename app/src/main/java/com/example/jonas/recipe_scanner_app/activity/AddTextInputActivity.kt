package com.example.jonas.recipe_scanner_app.activity

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import com.example.jonas.recipe_scanner_app.constant.Constant
import com.haag.mlkit.imagelabeling.test.R
import com.haag.mlkit.imagelabeling.test.ScanActivity
import kotlinx.android.synthetic.main.activity_add_text_input.*

class AddTextInputActivity : AppCompatActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_text_input)
        addTextInput_button_save.setOnClickListener(this)
        addTextInput_button_close.setOnClickListener(this)

        addtextinput_input_food.setOnKeyListener(View.OnKeyListener { v, keyCode, event ->
                    if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_UP) {
                        if(!addtextinput_input_food.text.isEmpty()) {
                            val addNewFoodToList = addtextinput_input_food.text.toString()
                            val intent = Intent(this, ScanActivity::class.java)
                            intent.putExtra(Constant.PUT_EXTRA_KEY, addNewFoodToList)
                            setResult(RESULT_OK, intent)
                            finish()
                            return@OnKeyListener true
                        } else {
                            com.example.jonas.recipe_scanner_app.helper.Helper.scanningFailedToastWarning(baseContext, "You need to write something")
                        }
                    }
                    false
                })
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.addTextInput_button_save -> {
                if(!addtextinput_input_food.text.isEmpty()) {
                    val addNewFoodToList = addtextinput_input_food.text.toString()
                    val intent = Intent(this, ScanActivity::class.java)
                    intent.putExtra(Constant.PUT_EXTRA_KEY, addNewFoodToList)
                    setResult(RESULT_OK, intent)
                    finish()
                } else {
                    com.example.jonas.recipe_scanner_app.helper.Helper.scanningFailedToastWarning(baseContext, "You need to write something")
                }
            }

            R.id.addTextInput_button_close -> {
                val intent = Intent(this, ScanActivity::class.java)
                setResult(RESULT_OK, intent)
                finish()
            }
        }
    }

    override fun onBackPressed() {
        val intent = Intent(this, ScanActivity::class.java)
        setResult(RESULT_OK, intent)
        finish()
        super.onBackPressed()
    }
}
