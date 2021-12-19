package com.smartphoneprogamming.afinal

import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.*
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.util.*
import com.smartphoneprogamming.afinal.List.ShowMyListActivity
import android.widget.TextView

import android.view.LayoutInflater

import com.google.android.gms.tasks.OnFailureListener

import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.auth.ktx.auth

import com.google.firebase.firestore.DocumentReference
import com.smartphoneprogamming.afinal.List.QuestionListActivity
import com.smartphoneprogamming.afinal.List.ShowWritingListActivity
import java.text.SimpleDateFormat


class MainContentActivity : AppCompatActivity() {

    val db = Firebase.firestore
    lateinit var toggle : ActionBarDrawerToggle
//    val email : String = intent.getStringExtra("email")!!
    val email : String = "qqq@naver.com"
    lateinit var nick : String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_content)
        set_nick()
        checkquestion_used()
        nav_bar()
        val complete : Button = findViewById(R.id.completebtn)
        complete.setOnClickListener{
            save_text()
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
                R.id.write_texts -> Toast.makeText(applicationContext,"현재 페이지입니다",Toast.LENGTH_SHORT).show()
                R.id.show_yours -> intent = Intent(this, ShowWritingListActivity::class.java)
                R.id.show_mine -> intent = Intent(this, ShowMyListActivity::class.java)
                R.id.question -> intent = Intent(this, QuestionListActivity::class.java)
//            R.id.setting -> intent = Intent(this, ::class.java)
                R.id.logout -> Firebase.auth.signOut()

            }
            drawerLayout.closeDrawer(GravityCompat.START);
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
    fun checkcheckbox(): Boolean {
        val checkBox = findViewById<CheckBox>(R.id.checkBox)
        return checkBox.isChecked
    }
    fun save_text() {
        val dateAndtime: Date = Calendar.getInstance().getTime()
        val write : EditText = findViewById(R.id. write)
        val text: String = write.getText().toString()
        val question : String = findViewById<TextView>(R.id.question).text.toString()
        val checked = checkcheckbox()
        if (text.isEmpty()) {
            Toast.makeText(this, "공백X", Toast.LENGTH_SHORT).show()
        } else {
            val posts = hashMapOf(
                "text" to write.text.toString(),
                "nick" to nick,
                "lock" to checked,
                "date" to dateAndtime,
                "question" to question
            )
            db.collection("post")
                .add(posts)
                .addOnSuccessListener { documentReference ->
                    Log.d(TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
                    Toast.makeText(this, "저장 완료", Toast.LENGTH_SHORT).show()
//                    val bundle = Bundle()
//                    bundle.putString("text", text)
//                    bundle.putString("question", question)
//                    bundle.putString("date", dateAndtime.toString())
                    intent = Intent(this, ShowDetailActivity::class.java)
                    intent.putExtra("text", text)
                    intent.putExtra("question", question)
                    write.setText("")
//                    val fragment = ShowDetailFragment()
//                    fragment.arguments = bundle
//                    setFragment()
                    startActivity(intent)

                }
                .addOnFailureListener { e ->
                    Log.w(TAG, "Error adding document", e)
                }
            }
        }
    fun set_nick(){
        val UserRef = db.collection("users").document(email)
        UserRef.get().addOnSuccessListener { documentSnapshot ->
            val inflater =
                getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            inflater.inflate(R.layout.nav_header, null, false)
            val user = findViewById<TextView>(R.id.user)
            if (documentSnapshot.exists()) {
                nick = documentSnapshot.getString("nick").toString()
                user.text = "$nick 님"
            }
        }
    }
    fun checkquestion_used(){
        val today = SimpleDateFormat("yyyyMMdd")
        val date: String = today.format(Date())

        val WordRef = db.collection("question").document(date)

        WordRef.get().addOnSuccessListener { documentSnapshot ->
            if (documentSnapshot.exists()) {
                val question = documentSnapshot.getString("question")
                val main_word : TextView = findViewById(R.id.question)
                    main_word.setText(question)
            }
        }

        //사용한 글감 used => true로 바꾸기

        //사용한 글감 used => true로 바꾸기
        WordRef.update("used", true)
            .addOnSuccessListener(OnSuccessListener<Void?> {
                Log.d(
                    TAG,
                    "DocumentSnapshot successfully updated!"
                )
            })
            .addOnFailureListener(OnFailureListener { e ->
                Log.w(
                    TAG,
                    "Error updating document",
                    e
                )
            })
    }
}

