package com.example.android.cw1

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class ResultWindow : AppCompatActivity() {
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result_window)

        val correctAns = findViewById<TextView>(R.id.noOfCorrects)
        val wrongAns = findViewById<TextView>(R.id.noOfWrongs)
        var c = intent.getIntegerArrayListExtra("answers")  //get the final result from game screen

        //display final result
        if (c != null) {
            correctAns.text = "Correct : " + c.first()
            wrongAns.text = "Wrong : " + c.last()
        }
    }
}