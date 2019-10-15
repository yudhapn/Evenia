package com.gemastik.evenia.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.gemastik.evenia.databinding.ItemParticipantBinding
import com.gemastik.evenia.model.Participant
import com.gemastik.evenia.ui.adapter.ParticipantAdapter.ViewHolder.Companion.from

class ParticipantAdapter(val clickListener: ParticipantListener) :
    ListAdapter<Participant, ParticipantAdapter.ViewHolder>(ParticipantDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = from(parent)

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(getItem(position), clickListener)

    class ViewHolder private constructor(val binding: ItemParticipantBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(participant: Participant, clickListener: ParticipantListener) {
            binding.participant = participant
            binding.clickListener = clickListener
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup) = ViewHolder(
                ItemParticipantBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        }
    }
}

class ParticipantDiffCallback : DiffUtil.ItemCallback<Participant>() {
    override fun areItemsTheSame(oldItem: Participant, newItem: Participant) =
        oldItem.participantId == newItem.participantId

    override fun areContentsTheSame(oldItem: Participant, newItem: Participant) =
        oldItem == newItem
}

class ParticipantListener(val clickListener: (participant: Participant) -> Unit) {
    fun onClick(participant: Participant) = clickListener(participant)
}