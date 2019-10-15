package com.gemastik.evenia.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.gemastik.evenia.databinding.ItemVacancyBinding
import com.gemastik.evenia.model.Vacancy
import com.gemastik.evenia.ui.adapter.VacancyAdapter.ViewHolder.Companion.from

class VacancyAdapter(val clickListener: VacancyListener) :
    ListAdapter<Vacancy, VacancyAdapter.ViewHolder>(VacancyDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = from(parent)

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(getItem(position), clickListener)

    class ViewHolder private constructor(val binding: ItemVacancyBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(vacancy: Vacancy, clickListener: VacancyListener) {
            binding.vacancy = vacancy
            binding.clickListener = clickListener
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup) = ViewHolder(
                ItemVacancyBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        }
    }
}

class VacancyDiffCallback : DiffUtil.ItemCallback<Vacancy>() {
    override fun areItemsTheSame(oldItem: Vacancy, newItem: Vacancy) =
        oldItem.vacancyId == newItem.vacancyId

    override fun areContentsTheSame(oldItem: Vacancy, newItem: Vacancy) =
        oldItem == newItem
}

class VacancyListener(val clickListener: (vacancy: Vacancy) -> Unit) {
    fun onClick(vacancy: Vacancy) = clickListener(vacancy)
}