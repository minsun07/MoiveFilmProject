package com.example.moviefilmproject

import android.app.Person
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.moviefilmproject.databinding.ActivityMainBinding
import com.example.moviefilmproject.databinding.ItemRecyclerviewBinding
import com.google.firebase.auth.FirebaseAuth

// RecyclerView.Adapter를 구현하여 데이터를 리스트 형태로 화면에 표시한다.
class MyAdapter(
    private var itemList: List<DataModel>,
    private val onItemClickListener: OnItemClickListener
) : RecyclerView.Adapter<MyAdapter.MyViewHolder>(), Filterable {

    private var filteredList: List<DataModel> = itemList

     //ViewHolder 정의
    class MyViewHolder(val binding: ItemRecyclerviewBinding) : RecyclerView.ViewHolder(binding.root)

    interface OnItemClickListener{
        fun onItemClick(data: DataModel)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        // ViewBinding을 통해 레이아웃 인플레이트
        val binding = ItemRecyclerviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = filteredList[position]

        // Binding을 활용하여 뷰에 데이터 설정
        with(holder.binding) {
            imgPost.setImageResource(item.img)
            movieName.text = item.name
            directorName.text = "감독  |  ${item.director}"

            // 아이템 클릭 리스너
            root.setOnClickListener {
                onItemClickListener.onItemClick(item)
            }
        }
    }

    // 아이템 개수 반환
    override fun getItemCount(): Int = filteredList.size

    // 필터 구현
    override fun getFilter(): Filter {
        return object : Filter() {
            // 필터링 로직
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charString = constraint.toString().trim()  // 입력된 검색어를 가져옴
                filteredList = if (charString.isEmpty()) {            // 검색어가 비어 있으면
                    itemList                                          // 원래 데이터 전체를 사용
                } else {
                    itemList.filter { it.name.contains(charString, true) } // 검색어를 포함하는 데이터만 필터링
                }
                return FilterResults().apply { values = filteredList } // 필터링 결과 반환
            }

            // 필터링 결과를 RecyclerView에 반영
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                filteredList = results?.values as? List<DataModel> ?: emptyList()
                notifyDataSetChanged()
            }
        }
    }
}
