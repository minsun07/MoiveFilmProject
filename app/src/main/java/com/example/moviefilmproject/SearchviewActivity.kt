package com.example.moviefilmproject

import android.content.Intent
import android.os.Bundle
//import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.appcompat.widget.SearchView
import com.example.moviefilmproject.databinding.ActivityMainBinding
import com.example.moviefilmproject.databinding.ActivitySearchviewBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth


class SearchviewActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySearchviewBinding
    private lateinit var adapter: MyAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_searchview)

        binding = ActivitySearchviewBinding.inflate(layoutInflater)
        setContentView(binding.root)


        // 데이터 준비 (영화 제목, 영화 포스터 이미지 리소스 ID, 감독 이름)
        val dataList = listOf(
            DataModel("30일", R.drawable.m1, "남대중"),
            DataModel("ET", R.drawable.m2, "스티븐 스필버그"),
            DataModel("건축학개론", R.drawable.m8, "이용주"),
            DataModel("검사외전", R.drawable.m9, "이일형"),
            DataModel("Get out", R.drawable.m4, "조던 필"),
            DataModel("곡성", R.drawable.m3, "홍진나"),
            DataModel("국가대표", R.drawable.m6, "김용화"),
            DataModel("국제시장", R.drawable.m7, "윤제균"),
            DataModel("극한직업", R.drawable.m5, "이병헌")
//            DataModel("나니아연대기"),
//            DataModel("너와 100번째 사랑"),
//            DataModel("너의 결혼식"),
//            DataModel("노트북"),
//            DataModel("Den skyldige"),
//            DataModel("The covenant"),
//            DataModel("더 킬러"),
//            DataModel("더 플랜"),
//            DataModel("Hangover"),
//            DataModel("도어락"),
//            DataModel("독전"),
//            DataModel("동네사람들"),
//            DataModel("라라랜드"),
//            DataModel("라이어"),
//            DataModel("럭키"),
//            DataModel("래미제라블"),
//            DataModel("리바운드"),
//            DataModel("마션"),
//            DataModel("말아톤"),
//            DataModel("맘마미아"),
//            DataModel("Memento"),
//            DataModel("명량"),
//            DataModel("반지의 제왕"),
//            DataModel("범죄도시"),
//            DataModel("보헤미안 랩소디"),
//            DataModel("부산행"),
//            DataModel("The blind side"),
//            DataModel("The blind side"),
//            DataModel("비상선언"),
//            DataModel("뺑반"),
//            DataModel("사운드 오브 뮤직"),
//            DataModel("순바꼭질"),
//            DataModel("스타이즈본"),
//            DataModel("승리호"),
//            DataModel("신과 함께"),
//            DataModel("싱글 인 서울"),
//            DataModel("아바타"),
//            DataModel("23아이덴티티"),
//            DataModel("알라딘"),
//            DataModel("GP506"),
//            DataModel("어바웃 타임"),
//            DataModel("어스"),
//            DataModel("어쩌다 로맨스"),
//            DataModel("원더랜드"),
//            DataModel("원더랜드"),
//            DataModel("유열의 음악앨범"),
//            DataModel("인터스텔라"),
//            DataModel("인셉션"),
//            DataModel("정직한 후보"),
//            DataModel("챌린저스"),
//            DataModel("치어댄스"),
//            DataModel("타이타닉"),
//            DataModel("태극기 휘날리며"),
//            DataModel("트랩"),
//            DataModel("트로이"),
//            DataModel("파묘"),
//            DataModel("파이브피트"),
//            DataModel("헌트"),
//            DataModel("호빗")

        )
        adapter = MyAdapter(dataList, object : MyAdapter.OnItemClickListener {
            override fun onItemClick(data: DataModel) {
                val intent = Intent(applicationContext, OptionActivity::class.java)
                intent.putExtra("MOVIE_NAME", data.name)
                intent.putExtra("MOVIE_DIRECTOR", data.director)
                intent.putExtra("MOVIE_IMAGE", data.img)
                startActivity(intent)
            }
        })
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean = false
            override fun onQueryTextChange(newText: String?): Boolean {
                adapter.filter.filter(newText)
                return true
            }
        })

    }
}