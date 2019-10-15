package com.gemastik.evenia.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.gemastik.evenia.databinding.ItemProviderVacancyBinding
import com.gemastik.evenia.model.Vacancy
import com.gemastik.evenia.ui.adapter.ProviderVacancyAdapter.ViewHolder.Companion.from

class ProviderVacancyAdapter(val clickListener: VacancyListener) :
    ListAdapter<Vacancy, ProviderVacancyAdapter.ViewHolder>(VacancyDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = from(parent)

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(getItem(position), clickListener)

    class ViewHolder private constructor(val binding: ItemProviderVacancyBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(vacancy: Vacancy, clickListener: VacancyListener) {
            binding.vacancy = vacancy
            binding.clickListener = clickListener
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup) = ViewHolder(
                ItemProviderVacancyBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        }
    }
}