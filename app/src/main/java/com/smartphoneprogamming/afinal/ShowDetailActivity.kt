package com.smartphoneprogamming.afinal

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class ShowDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_detail)
        val close : Button = findViewById(R.id.close)
        close.setOnClickListener {
            finish()
        }
        val text = intent.getStringExtra("text")
        val question = intent.getStringExtra("question")
        val text_main : TextView = findViewById(R.id.text_main)
        val sub_main : TextView = findViewById(R.id.sub_main)
        text_main.setText(text)
        sub_main.setText(question)
    }
}