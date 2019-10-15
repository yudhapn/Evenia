package com.gemastik.evenia.ui


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.gemastik.evenia.databinding.FragmentRegisterBinding

class RegisterFragment : Fragment() {
    private lateinit var binding: FragmentRegisterBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRegisterBinding.inflate(inflater, container, false)
        setListener()
        return binding.root
    }

    fun setListener() {
        binding.apply {
            providerMenu.setOnClickListener {
                val action = RegisterFragmentDirections.actionRegister2("provider")
                findNavController().navigate(action)
            }
            seekerMenu.setOnClickListener {
                val action = RegisterFragmentDirections.actionRegister2("seeker")
                findNavController().navigate(action)
            }
        }
    }
}
