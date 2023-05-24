package com.example.mvipattern.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mvipattern.databinding.ItemCustomRowBinding
import com.example.mvipattern.models.EntryModel


class ItemAdapter: RecyclerView.Adapter<ItemAdapter.ViewHolder>() {

    private lateinit var context: Context
    private var items: List<EntryModel> = ArrayList()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        val binding =
            ItemCustomRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val movie = items[position]
        holder.onBind(movie)
    }

    override fun getItemCount(): Int = items.size

    fun setDataSource(movies: List<EntryModel>) {
        this.items = movies
        notifyDataSetChanged()
    }

    inner class ViewHolder(private var binding: ItemCustomRowBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun onBind(movie: EntryModel) {
            binding.apiName.text = movie.api
            binding.description.text = movie.description
            binding.category.text = movie.category

        }
    }
}