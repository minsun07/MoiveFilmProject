package com.example.moviefilmproject

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.moviefilmproject.databinding.ActivityContentWriteBinding
import com.example.moviefilmproject.databinding.ActivityMainBinding
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class ContentWriteActivity : AppCompatActivity() {
    private lateinit var binding: ActivityContentWriteBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_content_write)

        binding = ActivityContentWriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val movieTitle = intent.getStringExtra("MOVIE_TITLE") ?: "Unknown"

        // 버튼 클릭 이벤트
        binding.writeBtn.setOnClickListener {
            val title = binding.titleArea.text.toString()
            val content = binding.contentArea.text.toString()
            val time = getTime()

            val editText = binding.contentArea.text.toString()
            val  titleText = binding.titleArea.text.toString()
            if(editText.isEmpty() || titleText.isEmpty()){
                Toast.makeText(applicationContext, "제목 또는 글을 작성해주세요.", Toast.LENGTH_SHORT).show()
            } else{
                val intent = Intent(applicationContext, ContentListActivity::class.java)
                intent.putExtra("MOVIE_TITLE", movieTitle)
                startActivity(intent)

                FBRef.contentFBRef
                    .child(movieTitle)      // child()는 해당 키 위치로 이동하는 메서드로 child()를 사용하여 key 값의 하위에 값을 저장한다.
                    .push()
                    .setValue(CommentModel(movieTitle,title, content, time)) // setValue() 메서드를 사용하여 값을 저장한다.

                Toast.makeText(this, "감상평 작성이 완료되었습니다.", Toast.LENGTH_SHORT).show()

            }

        }

        binding.returnBtn.setOnClickListener {
            val intent = Intent(applicationContext, SearchviewActivity::class.java)
            startActivity(intent)
        }
    }
    // 시간을 구하는 함수
    fun getTime(): String {
        val currentDateTime = Calendar.getInstance().time
        val dateFormat =
            SimpleDateFormat("yyyy.MM.dd HH:mm", Locale.KOREA).format(currentDateTime)

        return dateFormat
    }
}