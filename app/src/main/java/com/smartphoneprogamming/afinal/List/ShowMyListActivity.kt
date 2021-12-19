package com.smartphoneprogamming.afinal.List

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.*
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.smartphoneprogamming.afinal.R
import kotlinx.android.synthetic.main.recycler_item.view.*

class ShowMyListActivity : AppCompatActivity() {
    private lateinit var recyclerView : RecyclerView
    var firestore : FirebaseFirestore? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_my_list)

        val close : Button = findViewById(R.id.close_btn)
        close.setOnClickListener{
            finish()
        }

        firestore = FirebaseFirestore.getInstance()
        recyclerView = findViewById(R.id.recycler_view)
        recyclerView.adapter = RecyclerViewAdapter()
        recyclerView?.layoutManager = LinearLayoutManager(this)
        recyclerView?.itemAnimator = DefaultItemAnimator()

    }
    inner class RecyclerViewAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
        // Person 클래스 ArrayList 생성성
        var writingArrayList : ArrayList<Writing> = arrayListOf()

        init {  // telephoneBook의 문서를 불러온 뒤 Person으로 변환해 ArrayList에 담음
            firestore?.collection("post")?.addSnapshotListener { querySnapshot, firebaseFirestoreException ->
                // ArrayList 비워줌
                writingArrayList.clear()

                for (snapshot in querySnapshot!!.documents) {
                    val item = snapshot.toObject(Writing::class.java)
                    writingArrayList.add(item!!)
                }
                notifyDataSetChanged()
            }
        }

        // xml파일을 inflate하여 ViewHolder를 생성
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            var view = LayoutInflater.from(parent.context).inflate(R.layout.recycler_item, parent, false)
            return ViewHolder(view)
        }

        inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        }

        // onCreateViewHolder에서 만든 view와 실제 데이터를 연결
        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            var viewHolder = (holder as ViewHolder).itemView

            viewHolder.question.text = writingArrayList[position].question
            viewHolder.text.text = writingArrayList[position].text
//            viewHolder.nick.text = writingArrayList[position].nick
        }

        // 리사이클러뷰의 아이템 총 개수 반환
        override fun getItemCount(): Int {
            return writingArrayList.size
        }

    }
}