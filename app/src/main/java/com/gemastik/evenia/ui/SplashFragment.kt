package com.gemastik.evenia.ui


import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.gemastik.evenia.databinding.FragmentSplashBinding

class SplashFragment : Fragment() {
    private val splashScreenTimeOut = 2000L
    private lateinit var binding: FragmentSplashBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSplashBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Handler().postDelayed({
            val action = SplashFragmentDirections.actionLogin()
            findNavController().navigate(action)
        }, splashScreenTimeOut)
    }
}
