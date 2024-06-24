package com.movies.streamy.view.home

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.movies.streamy.R
import com.movies.streamy.databinding.AllMovieSlideItemBinding
import com.movies.streamy.model.dataSource.network.data.response.homeData.HomeResult

class HomeAdapter : RecyclerView.Adapter<HomeAdapter.TransactionsViewHolder> (){
    private lateinit var context: Context

    override fun onBindViewHolder(holder: TransactionsViewHolder, position: Int) {
        val data = asyncList.currentList[position]
        holder.bind(data)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionsViewHolder {
        context = parent.context
        return TransactionsViewHolder(
            AllMovieSlideItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun getItemCount() = asyncList.currentList.size

    inner class TransactionsViewHolder(
        private val binding: AllMovieSlideItemBinding,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: HomeResult) {
            binding.MovieName.text = data.title
            binding.Type.text = data.media_type
            //binding.genre.text =

            data.poster_path?.let { posterPath ->
                if (posterPath.isNotEmpty()) {
                    Glide.with(binding.image.context)
                        .load(posterPath)
//                        .placeholder(R.drawable.logo) // Optional placeholder image
                        .into(binding.image)
                } else {
                    binding.image.setImageResource(R.drawable.logo) // Placeholder for empty path
                }
            }
            binding.root.setOnClickListener {
                //data?.let { clicked.invoke(it) }
            }

        }
    }
    private val TransactionsDiffCallback = object : DiffUtil.ItemCallback<HomeResult>() {
        override fun areItemsTheSame(oldItem: HomeResult, newItem: HomeResult): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: HomeResult, newItem: HomeResult): Boolean {
            return oldItem == newItem
        }
    }
    val asyncList = AsyncListDiffer(this, TransactionsDiffCallback)
}

