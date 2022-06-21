package com.example.android.cw1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView

class PopUpWindow : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pop_up_window)

        val okBtn = findViewById<Button>(R.id.ok_btn)
        okBtn.setOnClickListener{this.finish()}
    }


}