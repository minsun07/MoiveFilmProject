package com.example.moviefilmproject


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.moviefilmproject.databinding.ItemContentListBinding

class ContentAdapter(val items : MutableList<CommentModel>) : RecyclerView.Adapter<ContentAdapter.ViewHolder>(){
        // View Holder를 생성하고 View를 붙여주는 메서드
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val binding = ItemContentListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return ViewHolder(binding)
        }
        // 생성된 View Holder에 데이터를 바인딩 해주는 메서드
        override fun onBindViewHolder(holder: ContentAdapter.ViewHolder, position: Int) {
            holder.bindItems(items[position])
        }

        // 데이터의 개수를 반환하는 메서드
        override fun getItemCount(): Int = items.size

        // 화면에 표시 될 뷰를 저장하는 역할의 메서드
        // View를 재활용 하기 위해 각 요소를 저장해두고 사용한다.
        // ViewHolder 클래스
        inner class ViewHolder(private val binding: ItemContentListBinding) : RecyclerView.ViewHolder(binding.root) {
            fun bindItems(item: CommentModel) {
                binding.titleArea.text = item.title ?: "제목 없음"
                binding.contentArea.text = item.content ?: "내용 없음"
                binding.timeArea.text = item.time ?: "시간 없음"
            }
        }
}