package com.gemastik.evenia.ui


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.gemastik.evenia.databinding.FragmentProviderVacancyBinding
import com.gemastik.evenia.ui.adapter.ProviderVacancyAdapter
import com.gemastik.evenia.ui.adapter.VacancyAdapter
import com.gemastik.evenia.ui.adapter.VacancyListener
import com.gemastik.evenia.ui.adapter.VacancyPagerAdapter
import com.gemastik.evenia.viewmodel.VacancyViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProviderVacancyFragment : Fragment() {
    private lateinit var binding: FragmentProviderVacancyBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProviderVacancyBinding.inflate(inflater, container, false)
        binding.apply {
            val adapter = VacancyPagerAdapter(childFragmentManager)
            vpVacancy.adapter = adapter
            tlVacancy.setupWithViewPager(vpVacancy)

            fabVacancy.setOnClickListener {
                val action = ProviderVacancyFragmentDirections.actionRegisterVacancy()
                findNavController().navigate(action)

            }
            return binding.root
        }
    }

}
