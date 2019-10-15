package com.gemastik.evenia.ui


import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.gemastik.evenia.databinding.FragmentLoginBinding
import com.gemastik.evenia.viewmodel.UserViewModel
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class LoginFragment : Fragment(), TextWatcher {
    private lateinit var binding: FragmentLoginBinding
    private val auth by inject<FirebaseAuth>()
    private lateinit var intent: Intent
    private val userVM: UserViewModel by viewModel { parametersOf("") }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        binding.apply {
            val user = requireContext().getSharedPreferences("user", AppCompatActivity.MODE_PRIVATE)
            etEmail.addTextChangedListener(this@LoginFragment)
            etPassword.addTextChangedListener(this@LoginFragment)
            intent = Intent(requireContext(), MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            if (auth.currentUser != null) {
                startActivity(intent)
            } else {
                setupListener(user)
            }
        }
        return binding.root
    }

    private fun setupListener(user: SharedPreferences) {
        binding.apply {
            tvRegister.setOnClickListener {
                val action = LoginFragmentDirections.actionRegister1()
                findNavController().navigate(action)
            }
            btnLogin.setOnClickListener {
                btnLogin.isEnabled = false
                progressbar.visibility = View.VISIBLE
                val email = etEmail.text.toString()
                val password = etPassword.text.toString()

                auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        if (!task.isSuccessful) {
                            Snackbar.make(it,"Kata sandi / email tidak valid", Snackbar.LENGTH_SHORT).show()
                            progressbar.visibility = View.GONE
                            btnLogin.isEnabled = true
                        } else {
                            userVM.user.observe(viewLifecycleOwner, Observer {
                                val edit = user.edit()
                                edit.putString("type", it.type)
                                Log.d("testing", "type: ${it.type}")
                                edit.apply()
                                startActivity(intent)
                                progressbar.visibility = View.GONE
                                btnLogin.isEnabled = true
                            })
                        }
                    }
            }
        }
    }

    override fun afterTextChanged(s: Editable?) {
        binding.apply {
            btnLogin.isEnabled =
                !(etEmail.text.toString().isEmpty() || etPassword.text.toString().isEmpty())
        }
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
}
