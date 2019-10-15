package com.gemastik.evenia.ui


import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.gemastik.evenia.databinding.FragmentRegister2Binding
import com.gemastik.evenia.model.User
import com.gemastik.evenia.viewmodel.UserViewModel
import com.google.firebase.auth.FirebaseAuth
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class Register2Fragment : Fragment() {
    private lateinit var binding: FragmentRegister2Binding
    private lateinit var type: String

    private val auth by inject<FirebaseAuth>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRegister2Binding.inflate(inflater, container, false)
        setListener()
        arguments?.let { type = Register2FragmentArgs.fromBundle(it).userType }
        return binding.root
    }

    fun setListener() {
        binding.apply {
            btnRegister.setOnClickListener {
                btnRegister.isEnabled = false
                progressbar.visibility = View.VISIBLE
                auth.createUserWithEmailAndPassword(etEmail.text.toString(), etPassword.text.toString())
                    .addOnCompleteListener {
                        if (it.isSuccessful) {
                            val userVM: UserViewModel by viewModel { parametersOf("register") }
                            val uid = auth.currentUser?.uid.toString()
                            val intent = Intent(requireContext(), MainActivity::class.java)
                            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                            val user = User(
                                userId = uid,
                                name = etName.text.toString(),
                                type = type
                            )
                            userVM.createUserData(uid, user)
                            val userPref = requireContext().getSharedPreferences("user", AppCompatActivity.MODE_PRIVATE)
                            val edit = userPref.edit()
                            edit.putString("type", type)
                            edit.apply()
                            startActivity(intent)
                            progressbar.visibility = View.GONE
                            btnRegister.isEnabled = true
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("ERROR AUTHENTICATION","createUserWithEmail:failure", it.exception)
                        }
                    }
            }
        }
    }
}
