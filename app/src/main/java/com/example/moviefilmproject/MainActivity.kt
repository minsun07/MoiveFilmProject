package com.example.moviefilmproject

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.moviefilmproject.databinding.ActivityMainBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

class MainActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        auth = Firebase.auth
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // binding 초기화
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = Firebase.auth

        Toast.makeText(this, auth.currentUser?.uid.toString(), Toast.LENGTH_SHORT).show()

        // 회원가입
        binding.joinBtn.setOnClickListener {
            val email = binding.EmailArea.text.toString()
            val pw = binding.pwArea.text.toString()

            auth.createUserWithEmailAndPassword(email, pw)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(this, "ok", Toast.LENGTH_SHORT).show()
                    } else {
                        // If sign in fails, display a message to the user.
                        Toast.makeText(this, "no", Toast.LENGTH_SHORT).show()
                    }
                }
        }

        // 4. 로그아웃
//        binding.logoutBtn.setOnClickListener{
//            binding.EmailArea.text.clear()
//            binding.pwArea.text.clear()
//            auth.signOut()
//            Toast.makeText(this,"로그아웃", Toast.LENGTH_SHORT).show()
//        }

        // 5. 로그인
        binding.loginBtn.setOnClickListener{
            val email = binding.EmailArea.text.toString()
            val pw = binding.pwArea.text.toString()

            auth.signInWithEmailAndPassword(email, pw)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(this, "ok", Toast.LENGTH_SHORT).show()
                        Toast.makeText(this, auth.currentUser?.uid.toString(), Toast.LENGTH_SHORT).show()

                        //6. 정상 로그인 글쓰기로 이동
                        val intent = Intent(this, SearchviewActivity::class.java)
                        startActivity(intent)
                    } else {
                        Toast.makeText(this, "no", Toast.LENGTH_SHORT).show()
                    }
                }
        }
    }
}