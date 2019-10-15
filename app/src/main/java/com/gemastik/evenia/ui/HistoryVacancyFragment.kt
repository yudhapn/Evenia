package com.gemastik.evenia.ui


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.gemastik.evenia.R
import com.gemastik.evenia.databinding.FragmentCurrentVacancyBinding
import com.gemastik.evenia.databinding.FragmentHistoryVacancyBinding
import com.gemastik.evenia.ui.adapter.ProviderVacancyAdapter
import com.gemastik.evenia.ui.adapter.VacancyListener
import com.gemastik.evenia.viewmodel.VacancyViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class HistoryVacancyFragment : Fragment() {
    private lateinit var binding: FragmentHistoryVacancyBinding
    private val vacVM: VacancyViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHistoryVacancyBinding.inflate(inflater, container, false)
        binding.apply {
            lifecycleOwner = this@HistoryVacancyFragment
            vacancyVM = vacVM
            rvVacancy.adapter = ProviderVacancyAdapter(VacancyListener { vacancy ->
//                val action = SeekerHomeFragmentDirections.actionDetailEvent(vacancy)
//                findNavController().navigate(action)
            })

        }
        return binding.root

    }


}
