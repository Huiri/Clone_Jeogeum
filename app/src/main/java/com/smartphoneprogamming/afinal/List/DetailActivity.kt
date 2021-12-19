package com.smartphoneprogamming.afinal.List

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.smartphoneprogamming.afinal.R

class DetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        val text = intent.getStringExtra("text")
        val question = intent.getStringExtra("question")
        val nick = intent.getStringExtra("nick")

        val close: Button = findViewById(R.id.close)
        close.setOnClickListener {
            finish()
        }
        val title: TextView = findViewById(R.id.title)
        val sub_main: TextView = findViewById(R.id.sub_main)
        val text_main: TextView = findViewById(R.id.text_main)

        title.setText(question)
        if(nick == null){
            sub_main.visibility = View.GONE
        }else{
            sub_main.setText(nick)
        }
        text_main.setText(text)
    }
}