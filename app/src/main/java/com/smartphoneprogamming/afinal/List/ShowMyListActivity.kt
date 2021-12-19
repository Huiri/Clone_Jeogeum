package com.smartphoneprogamming.afinal.List

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.*
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.smartphoneprogamming.afinal.R

class ShowMyListActivity : AppCompatActivity() {
    private lateinit var adapter : Adapter
    private lateinit var recyclerView : RecyclerView
    private lateinit var Adapter : Adapter
    private lateinit var writingArrayList : ArrayList<Writing>
//    val db = Firebase.firestore

    private lateinit var db : FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_my_list)
        }
}