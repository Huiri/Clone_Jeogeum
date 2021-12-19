package com.smartphoneprogamming.afinal.List

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.smartphoneprogamming.afinal.List.dataModel.Writing
import com.smartphoneprogamming.afinal.MainContentActivity
import com.smartphoneprogamming.afinal.R
import com.smartphoneprogamming.afinal.setting.SettingActivity
import kotlinx.android.synthetic.main.recycler_item.view.*

class QuestionListActivity : AppCompatActivity() {
    private lateinit var recyclerView : RecyclerView
    var firestore : FirebaseFirestore? = null
    lateinit var toggle : ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_question_list)
        val close : Button = findViewById(R.id.close_btn)
        close.setOnClickListener{
            finish()
        }
        nav_bar()
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
            firestore?.collection("question")?.addSnapshotListener { querySnapshot, firebaseFirestoreException ->
                    // ArrayList 비워줌
                    writingArrayList.clear()

                    for (snapshot in querySnapshot!!.documents) {
                        val item = snapshot.toObject(Writing::class.java)
                        if (item == null) {
                            Toast.makeText(applicationContext, "작성한 글이 존재하지 않습니다", Toast.LENGTH_SHORT).show()
                        } else {
                            writingArrayList.add(item)
                        }
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
            viewHolder.text.text = writingArrayList[position].date.toString()
        }

        // 리사이클러뷰의 아이템 총 개수 반환
        override fun getItemCount(): Int {
            return writingArrayList.size
        }

    }
    fun nav_bar(){
        val drawerLayout : DrawerLayout = findViewById(R.id.drawer_layout)
        val navView : NavigationView = findViewById(R.id.nav_view)
        toggle = ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        setSupportActionBar(findViewById(R.id.toolbar))
//
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_baseline_dehaze_24)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        navView.setNavigationItemSelectedListener {
            when(it.itemId){
                R.id.write_texts -> {
                    intent = Intent(this, MainContentActivity::class.java)
                    finish()
                }
                R.id.show_yours -> {
                    intent = Intent(this, ShowWritingListActivity::class.java)
                    finish()
                }
                R.id.show_mine -> {
                    intent = Intent(this, ShowMyListActivity::class.java)
                    finish()
                }
                R.id.question -> Toast.makeText(applicationContext,"현재 페이지입니다", Toast.LENGTH_SHORT).show()
                R.id.setting -> {
                    intent = Intent(this, SettingActivity::class.java)
                    finish()
                }
                R.id.logout -> {
                    Firebase.auth.signOut()
                    finish()
                }
            }
            drawerLayout.closeDrawer(GravityCompat.START)
            val nick = intent.getStringExtra("nick")
            intent.putExtra("nick", nick)
            startActivity(intent)

            true
        }
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(toggle.onOptionsItemSelected(item)){
            return true
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        val drawerLayout : DrawerLayout = findViewById(R.id.drawer_layout)
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}