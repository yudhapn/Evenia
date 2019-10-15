package com.gemastik.evenia.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.gemastik.evenia.databinding.ItemCategoryBinding
import com.gemastik.evenia.model.Category
import com.gemastik.evenia.ui.adapter.CategoryAdapter.ViewHolder.Companion.from

class CategoryAdapter :
    ListAdapter<Category, CategoryAdapter.ViewHolder>(CategoryDiffCallback ()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = from(parent)

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(getItem(position))

    class ViewHolder private constructor(val binding: ItemCategoryBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(category: Category) {
            binding.category = category
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup) = ViewHolder(
                ItemCategoryBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        }
    }
}

class CategoryDiffCallback : DiffUtil.ItemCallback<Category>() {
    override fun areItemsTheSame(oldItem: Category, newItem: Category) =
        oldItem.categoryId == newItem.categoryId

    override fun areContentsTheSame(oldItem: Category, newItem: Category) =
        oldItem == newItem
}

class CategoryListener(val clickListener: (category: Category) -> Unit) {
    fun onClick(category: Category) = clickListener(category)
}