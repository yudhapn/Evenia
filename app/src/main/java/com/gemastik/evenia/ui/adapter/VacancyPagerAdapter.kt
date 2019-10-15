package com.gemastik.evenia.ui.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.gemastik.evenia.ui.CurrentVacancyFragment
import com.gemastik.evenia.ui.HistoryVacancyFragment

@Suppress("DEPRECATION")
class VacancyPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {
    override fun getItem(position: Int): Fragment =
        when (position) {
            0 -> CurrentVacancyFragment()
            1 -> HistoryVacancyFragment()
            else -> CurrentVacancyFragment()
        }

    override fun getCount(): Int = 2

    override fun getPageTitle(position: Int): CharSequence? =
        when (position) {
            0 -> "Berlangsung"
            1 -> "Riwayat"
            else -> "Berlangsung"
        }
}