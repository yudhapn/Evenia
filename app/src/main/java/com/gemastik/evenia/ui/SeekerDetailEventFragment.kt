package com.gemastik.evenia.ui


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.gemastik.evenia.R
import com.gemastik.evenia.databinding.FragmentSeekerDetailEventBinding
import com.gemastik.evenia.model.Vacancy

class SeekerDetailEventFragment : Fragment() {
    private lateinit var binding: FragmentSeekerDetailEventBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSeekerDetailEventBinding.inflate(inflater, container, false)
        binding.apply {
            var vacancy = Vacancy()
            arguments?.let { vacancy = SeekerDetailEventFragmentArgs.fromBundle(it).vacancy }
            vacancyArg = vacancy
        }
        return binding.root
    }


}
