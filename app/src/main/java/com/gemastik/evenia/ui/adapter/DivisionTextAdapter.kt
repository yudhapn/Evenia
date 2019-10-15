package com.gemastik.evenia.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.gemastik.evenia.databinding.ItemTextDivisionBinding
import com.gemastik.evenia.model.Division
import com.gemastik.evenia.ui.adapter.DivisionTextAdapter.ViewHolder.Companion.from

class DivisionTextAdapter(val clickListener: DivisionListener) :
    ListAdapter<Division, DivisionTextAdapter.ViewHolder>(DivisionDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = from(parent)

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(getItem(position), clickListener)

    class ViewHolder private constructor(val binding: ItemTextDivisionBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(division: Division, clickListener: DivisionListener) {
            binding.division = division
            binding.clickListener = clickListener
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup) = ViewHolder(
                ItemTextDivisionBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        }
    }
}

class DivisionDiffCallback : DiffUtil.ItemCallback<Division>() {
    override fun areItemsTheSame(oldItem: Division, newItem: Division) =
        oldItem.divisionId == newItem.divisionId

    override fun areContentsTheSame(oldItem: Division, newItem: Division) =
        oldItem == newItem
}

class DivisionListener(val clickListener: (division: Division) -> Unit) {
    fun onClick(division: Division) = clickListener(division)
}