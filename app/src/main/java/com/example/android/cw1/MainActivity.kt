package com.example.android.cw1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //create about button
        val aboutBtn = findViewById<Button>(R.id.about_btn)
        aboutBtn.setOnClickListener{
            val aboutIntent = Intent(this,PopUpWindow::class.java)
            startActivity(aboutIntent)
        }

        //create new game button
        val newGameBtn = findViewById<Button>(R.id.new_game)
        newGameBtn.setOnClickListener {
            val newGameIntent = Intent(this,GameScreen::class.java)
            startActivity(newGameIntent)
        }
    }
}