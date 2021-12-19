package com.smartphoneprogamming.afinal.List

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Button
import android.widget.Spinner
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import com.smartphoneprogamming.afinal.R
import kotlinx.android.synthetic.main.activity_show_my_list.*
import kotlinx.android.synthetic.main.recycler_item.view.*

class ShowWritingListActivity : AppCompatActivity() {
    private lateinit var recyclerView : RecyclerView
    var firestore : FirebaseFirestore? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_writing_list)
        val close : Button = findViewById(R.id.close_btn)
        close.setOnClickListener{
            finish()
        }

        firestore = FirebaseFirestore.getInstance()
        recyclerView = findViewById(R.id.recycler_view)
        recyclerView.adapter = RecyclerViewAdapter()
        recyclerView?.layoutManager = LinearLayoutManager(this)
        recyclerView?.itemAnimator = DefaultItemAnimator()
            var searchOption = "name"

        // 스피너 옵션에 따른 동작
        spinner.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                when (spinner.getItemAtPosition(position)) {
                    "질문" -> {
                        searchOption = "question"
                    }
                    "닉네임" -> {
                        searchOption = "nick"
                    }
                }
            }
        }

        // 검색 옵션에 따라 검색
        searchBtn.setOnClickListener {
            (recyclerView.adapter as RecyclerViewAdapter).search(searchWord.text.toString(), searchOption)
        }
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
    fun search(searchWord : String, option : String) {
        firestore?.collection("post")?.addSnapshotListener { querySnapshot, firebaseFirestoreException ->
            // ArrayList 비워줌
            writingArrayList.clear()

            for (snapshot in querySnapshot!!.documents) {
                if (snapshot.getString(option)!!.contains(searchWord)) {
                    var item = snapshot.toObject(Writing::class.java)
                    writingArrayList.add(item!!)
                }
            }
            notifyDataSetChanged()
        }
    }
    }

}