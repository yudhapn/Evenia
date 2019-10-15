package com.gemastik.evenia.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.gemastik.evenia.databinding.ItemFollowedEventBinding
import com.gemastik.evenia.model.FollowedEvent
import com.gemastik.evenia.ui.adapter.FollowedEventAdapter.ViewHolder.Companion.from

class FollowedEventAdapter :
    ListAdapter<FollowedEvent, FollowedEventAdapter.ViewHolder>(FollowedEventDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = from(parent)

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(getItem(position))

    class ViewHolder private constructor(val binding: ItemFollowedEventBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(followedEvent: FollowedEvent) {
            binding.followedEvent = followedEvent
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup) = ViewHolder(
                ItemFollowedEventBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        }
    }
}

class FollowedEventDiffCallback : DiffUtil.ItemCallback<FollowedEvent>() {
    override fun areItemsTheSame(oldItem: FollowedEvent, newItem: FollowedEvent) =
        oldItem.followedEventId == newItem.followedEventId

    override fun areContentsTheSame(oldItem: FollowedEvent, newItem: FollowedEvent) =
        oldItem == newItem
}