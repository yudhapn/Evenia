package com.gemastik.evenia.ui


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.gemastik.evenia.R
import com.gemastik.evenia.databinding.FragmentProviderHomeBinding
import com.gemastik.evenia.model.Division
import com.gemastik.evenia.model.Participant
import com.gemastik.evenia.ui.adapter.DivisionListener
import com.gemastik.evenia.ui.adapter.DivisionTextAdapter
import com.gemastik.evenia.ui.adapter.ParticipantAdapter
import com.gemastik.evenia.ui.adapter.ParticipantListener
import com.google.android.material.appbar.AppBarLayout

class ProviderHomeFragment : Fragment(), AppBarLayout.OnOffsetChangedListener,
    View.OnClickListener {
    private lateinit var binding: FragmentProviderHomeBinding
    private lateinit var drawerLayout: DrawerLayout

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProviderHomeBinding.inflate(inflater, container, false)
        val adapterDivision = DivisionTextAdapter(DivisionListener { division ->
            //            val action = SeekerHomeFragmentDirections.actionDetailEvent(participant)
            //            findNavController().navigate(action)
        })
        binding.rvCategory.adapter = adapterDivision
        val division = listOf(
            Division(divisionId = "1", name = "Teknologi"),
            Division(divisionId = "2", name = "Teknologi"),
            Division(divisionId = "3", name = "Teknologi"),
            Division(divisionId = "4", name = "Teknologi"),
            Division(divisionId = "5", name = "Teknologi")
        )
        adapterDivision.submitList(division)

        val adapterParticipant = ParticipantAdapter(ParticipantListener { participant ->
            val action = ProviderHomeFragmentDirections.actionRecruitment(participant)
            findNavController().navigate(action)
        })
        binding.rvParticipant.adapter = adapterParticipant
        val participant = listOf(
            Participant(participantId = "1"),
            Participant(participantId = "2"),
            Participant(participantId = "3"),
            Participant(participantId = "4"),
            Participant(participantId = "5")
        )
        adapterParticipant.submitList(participant)

        binding.appbar.addOnOffsetChangedListener(this)
        drawerLayout = requireActivity().findViewById(R.id.drawer_layout)
        binding.ivMenu.setOnClickListener(this)
        return binding.root
    }

    override fun onOffsetChanged(appBarLayout: AppBarLayout, verticalOffset: Int) {
        if (Math.abs(verticalOffset) - appBarLayout.getTotalScrollRange() == 0) {
            // collapse
//            binding.toolbar.visibility = View.VISIBLE

        } else {
            //Expanded
//            binding.toolbar.visibility = View.GONE

        }
    }

    override fun onClick(p0: View?) {
        if (drawerLayout.isDrawerOpen(GravityCompat.START))
            drawerLayout.closeDrawer(GravityCompat.START)
        else
            drawerLayout.openDrawer(GravityCompat.START)
    }
}