package com.example.moviefilmproject

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.moviefilmproject.databinding.ActivityContentListBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class ContentListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityContentListBinding
    private lateinit var contentAdapter: ContentAdapter
    private val contentList = mutableListOf<CommentModel>()
    private lateinit var movieTitle: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 뷰 바인딩 초기화
        binding = ActivityContentListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Intent로부터 영화 제목 가져오기
        movieTitle = intent.getStringExtra("MOVIE_TITLE") ?: ""

        // 영화 제목 유효성 검사
        if (movieTitle.isEmpty()) {
            Log.d("ContentListActivity", "Received MOVIE_TITLE is empty.")
            Toast.makeText(this, "영화 제목을 확인할 수 없습니다.", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        // RecyclerView 설정
        contentAdapter = ContentAdapter(contentList)
        binding.recyclerview.apply {
            adapter = contentAdapter
            layoutManager = LinearLayoutManager(this@ContentListActivity)
        }

        // Firebase 데이터 로드
        getFBContentData()
    }

    // Firebase에서 데이터 읽어오는 메서드
    private fun getFBContentData() {
        if (movieTitle.isEmpty()) {
            Log.e("ContentListActivity", "Cannot load data: movieTitle is empty")
            Toast.makeText(this, "영화 제목 정보가 누락되었습니다.", Toast.LENGTH_SHORT).show()
            return
        }

        val database = FirebaseDatabase.getInstance()
        val contentRef = database.getReference("content").child(movieTitle)

        val postListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                contentList.clear()

                if (!snapshot.exists()) { // 데이터가 없는 경우 처리
                    Toast.makeText(this@ContentListActivity, "관련 작성글이 없습니다.", Toast.LENGTH_SHORT).show()
                    Log.d("ContentListActivity", "No data found for movieTitle: $movieTitle")
                    return
                }

                for (data in snapshot.children) {
                    val item = data.getValue(CommentModel::class.java)
                    if (item != null) {
                        Log.d("ContentListActivity", "Loaded Item: $item")
                        contentList.add(item)
                    } else {
                        Log.e("ContentListActivity", "Failed to parse data: ${data.value}")
                    }
                }

                contentList.reverse() // 최신 글이 상단에 표시되도록 역순 정렬
                contentAdapter.notifyDataSetChanged() // RecyclerView 업데이트
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("ContentListActivity", "Failed to load data: ${error.message}")
                Toast.makeText(this@ContentListActivity, "데이터를 가져오는 중 오류가 발생했습니다.", Toast.LENGTH_SHORT).show()
            }
        }
        contentRef.addValueEventListener(postListener)
    }
}

