package com.gemastik.evenia.ui


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.gemastik.evenia.R
import com.gemastik.evenia.databinding.FragmentProviderHomeBinding
import com.gemastik.evenia.databinding.FragmentProviderRecruitmentBinding
import com.gemastik.evenia.model.FollowedEvent
import com.gemastik.evenia.ui.adapter.FollowedEventAdapter

class ProviderRecruitmentFragment : Fragment() {
    private lateinit var binding: FragmentProviderRecruitmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProviderRecruitmentBinding.inflate(inflater, container, false)
        val adapterFollowed = FollowedEventAdapter()
        binding.rvFollowedEvent.adapter = adapterFollowed
        val division = listOf(
            FollowedEvent(followedEventId = "1", name = "Teknologi"),
            FollowedEvent(followedEventId = "2", name = "Teknologi"),
            FollowedEvent(followedEventId = "3", name = "Teknologi"),
            FollowedEvent(followedEventId = "4", name = "Teknologi"),
            FollowedEvent(followedEventId = "5", name = "Teknologi"),
            FollowedEvent(followedEventId = "6", name = "Teknologi"),
            FollowedEvent(followedEventId = "7", name = "Teknologi"),
            FollowedEvent(followedEventId = "8", name = "Teknologi")
        )
        adapterFollowed.submitList(division)
        return binding.root
    }


}
