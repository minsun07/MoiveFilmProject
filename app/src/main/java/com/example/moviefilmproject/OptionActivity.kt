package com.example.moviefilmproject

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class OptionActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_option)

        val movieName = intent.getStringExtra("MOVIE_NAME") ?: run {
            Toast.makeText(this, "영화 제목을 확인할 수 없습니다.", Toast.LENGTH_SHORT).show()
            //Log.d("OptionActivity", "Received MOVIE_NAME: $movieName")
            finish()
            return
        }
        val movieDirector = intent.getStringExtra("MOVIE_DIRECTOR") ?: "감독 정보 없음"
        val movieImage = intent.getIntExtra("MOVIE_IMAGE", 0) // 기본 이미지


        val imageView: ImageView = findViewById(R.id.detailImageView)
        val nameTextView: TextView = findViewById(R.id.detailNameTextView)
        val directorTextView: TextView = findViewById(R.id.detailDirectorTextView)
        val writeBtn : Button = findViewById(R.id.writeBtn)
        val contentListBtn : Button = findViewById(R.id.contentListBtn)

        imageView.setImageResource(movieImage)
        nameTextView.text = movieName
        directorTextView.text = "감독| $movieDirector"

        writeBtn.setOnClickListener {
            var intent = Intent(applicationContext, ContentWriteActivity::class.java)
            intent.putExtra("MOVIE_TITLE", movieName)
            intent.putExtra("MOVIE_DIRECTOR", movieDirector)
            intent.putExtra("MOVIE_IMAGE", movieImage)
            startActivity(intent)
        }

        contentListBtn.setOnClickListener {
            val intent = Intent(applicationContext, ContentListActivity::class.java)
            intent.putExtra("MOVIE_TITLE", movieName)
            intent.putExtra("MOVIE_DIRECTOR", movieDirector)
            intent.putExtra("MOVIE_IMAGE", movieImage)
            startActivity(intent)
            Log.d("OptionActivity", "MOVIE_TITLE before sending Intent: $movieName")

        }
    }
}