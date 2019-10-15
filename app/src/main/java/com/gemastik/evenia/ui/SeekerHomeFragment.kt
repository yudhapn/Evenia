package com.gemastik.evenia.ui


import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.gemastik.evenia.R
import com.gemastik.evenia.databinding.FragmentSeekerHomeBinding
import com.gemastik.evenia.model.Category
import com.gemastik.evenia.model.Vacancy
import com.gemastik.evenia.ui.adapter.CategoryAdapter
import com.gemastik.evenia.ui.adapter.VacancyAdapter
import com.gemastik.evenia.ui.adapter.VacancyListener
import com.gemastik.evenia.viewmodel.RegisterViewModel
import com.gemastik.evenia.viewmodel.VacancyViewModel
import com.google.android.material.appbar.AppBarLayout
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class SeekerHomeFragment : Fragment(), AppBarLayout.OnOffsetChangedListener, View.OnClickListener {
    private lateinit var binding: FragmentSeekerHomeBinding
    private lateinit var drawerLayout: DrawerLayout
    private val vacancyVM: VacancyViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSeekerHomeBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.vacancyVM = vacancyVM
        val adapterCategory = CategoryAdapter()
        binding.rvCategory.adapter = adapterCategory
        val categories = listOf(
            Category("1", R.drawable.tech_category, "Teknologi"),
            Category("2", R.drawable.art_category, "Seni"),
            Category("3", R.drawable.social_category, "Sosial"),
            Category("4", R.drawable.tech_category, "Teknologi"),
            Category("5", R.drawable.art_category, "Seni"),
            Category("6", R.drawable.social_category, "Sosial"),
            Category("7", R.drawable.tech_category, "Teknologi"),
            Category("8", R.drawable.art_category, "Seni"),
            Category("9", R.drawable.social_category, "Sosial")
        )
        adapterCategory.submitList(categories)

        binding.rvVacancy.adapter = VacancyAdapter(VacancyListener { vacancy ->
            val action = SeekerHomeFragmentDirections.actionDetailEvent(vacancy)
            findNavController().navigate(action)
        })

//        val vacancies = listOf(
//            Vacancy("1",
//                image = R.drawable.indonesia_book_fair,
//                name = "Indonesia Book Fair 2019",
//                category = "Pendidikan"),
//            Vacancy("1",
//                image = R.drawable.indonesia_book_fair,
//                name = "Indonesia Book Fair 2019",
//                category = "Pendidikan"),
//            Vacancy("1",
//                image = R.drawable.indonesia_book_fair,
//                name = "Indonesia Book Fair 2019",
//                category = "Pendidikan"),
//            Vacancy("1",
//                image = R.drawable.indonesia_book_fair,
//                name = "Indonesia Book Fair 2019",
//                category = "Pendidikan")
//        )
//        adapterVacancy.submitList(vacancies)

        binding.appbar.addOnOffsetChangedListener(this)
        drawerLayout = requireActivity().findViewById(R.id.drawer_layout)
        binding.ivMenu.setOnClickListener(this)
        binding.ivMenuToolbar.setOnClickListener(this)
        return binding.root
    }

    override fun onOffsetChanged(appBarLayout: AppBarLayout, verticalOffset: Int) {
        if (Math.abs(verticalOffset) - appBarLayout.getTotalScrollRange() == 0) {
            // collapse
            binding.toolbar.visibility = View.VISIBLE

        } else {
            //Expanded
            binding.toolbar.visibility = View.GONE

        }
    }

    override fun onClick(p0: View?) {
        if (drawerLayout.isDrawerOpen(GravityCompat.START))
            drawerLayout.closeDrawer(GravityCompat.START)
        else
            drawerLayout.openDrawer(GravityCompat.START)
    }
}
