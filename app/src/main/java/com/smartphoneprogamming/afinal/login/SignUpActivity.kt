package com.smartphoneprogamming.afinal.login

import android.content.ContentValues
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.smartphoneprogamming.afinal.R
import com.smartphoneprogamming.afinal.databinding.ActivitySignUpBinding

class SignUpActivity : AppCompatActivity() {

    private var _binding: ActivitySignUpBinding? = null
    private val binding get() = _binding!!
    val db = Firebase.firestore

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        binding.btnregister.setOnClickListener {
            val email = binding.email.text.toString().trim()
            val password = binding.password.text.toString().trim()
            val password2 = binding.password2.text.toString().trim()
            val nick = binding.nickname.text.toString().trim()

            // Validate...

            createUser(email, password, password2, nick)
        }
        binding.back.setOnClickListener{
            finish()
        }
    }

    private fun createUser(email: String, password: String, password2:String, nick : String) {
        if (email.length == 0) {
            binding.warningEmail.setText("이메일을 입력해주세요")
            Toast.makeText(this, "이메일을 입력해주세요", Toast.LENGTH_SHORT).show()
        } else if (password.length == 0){
            binding.warningEmail.setText("비밀번호를 입력해주세요")
            Toast.makeText(this, "비밀번호를 입력해주세요", Toast.LENGTH_SHORT).show()
        } else if (password.length < 6){
            binding.warningEmail.setText("비밀번호를 6자리 이상 입력해주세요")
            Toast.makeText(this, "비밀번호를 6자리 이상 입력해주세요", Toast.LENGTH_SHORT).show()
        } else if (password2 != password){
            binding.warningEmail.setText("비밀번호가 동일하지 않습니다.")
            Toast.makeText(this, "비밀번호가 동일하지 않습니다.", Toast.LENGTH_SHORT).show()
        } else if (nick.length == 0){
            binding.warningEmail.setText("닉네임을 작성해주세요.")
            Toast.makeText(this, "닉네임을 작성해주세요.", Toast.LENGTH_SHORT).show()
        } else {
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(this, "회원가입 성공", Toast.LENGTH_SHORT).show()
                        val user = auth.currentUser
                        updateUI(user)
                        val userdata = hashMapOf(
                            "nick" to nick
                        )
                        db.collection("users").document(email)
                            .set(userdata)
                            .addOnSuccessListener { documentReference ->
//                                Log.d(ContentValues.TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
                            }
                            .addOnFailureListener { e ->
                                Log.w(ContentValues.TAG, "Error adding document", e)
                            }
                        finish()
                    } else {
                        Toast.makeText(this, "회원가입 실패", Toast.LENGTH_SHORT).show()
                        updateUI(null)
                    }
                }
                .addOnFailureListener {
                    Toast.makeText(this, "회원가입 실패", Toast.LENGTH_SHORT).show()
                }
        }
    }

    private fun updateUI(user: FirebaseUser?) {
        //user?.let {
        //    binding.txtResult.text = "Email: ${user.email}\nUid: ${user.uid}"
        //}
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}