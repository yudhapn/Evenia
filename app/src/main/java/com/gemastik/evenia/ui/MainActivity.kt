package com.gemastik.evenia.ui

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.NavGraph
import androidx.navigation.NavOptions
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupWithNavController
import com.gemastik.evenia.R
import com.gemastik.evenia.databinding.ActivityMainBinding
import com.gemastik.evenia.viewmodel.UserViewModel
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class MainActivity : AppCompatActivity(), DrawerLayout.DrawerListener {
    private val userVM: UserViewModel by viewModel { parametersOf("") }
    private lateinit var binding: ActivityMainBinding
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var navController: NavController
    private val auth = FirebaseAuth.getInstance()
    private var cookieType: String? = ""
    private lateinit var graph: NavGraph

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        navController = findNavController(R.id.fragment_nav_main)
        setNavigationView()
    }

    private fun setNavigationView() {
        binding.navigationView.setupWithNavController(navController)
        val headerView = binding.navigationView.getHeaderView(0)
        val profileMenu = headerView.findViewById<ImageView>(R.id.iv_avatar)
        val applicantMenu = headerView.findViewById<ImageView>(R.id.iv_application)

        val user = getSharedPreferences("user", MODE_PRIVATE)
        cookieType = user.getString("type", "missing")
        val navHostFragment = fragment_nav_main as NavHostFragment
        val inflater = navHostFragment.navController.navInflater

        var idProfileMenu = R.id.seeker_profile_dest
        var idVacancyMenu = R.id.seeker_vacancy_dest
        if (cookieType.equals("provider")) {
            graph = inflater.inflate(R.navigation.provider_nav)
            val tvVacancy = headerView.findViewById<TextView>(R.id.tv_application)
            tvVacancy.text = "Lowongan"
            idProfileMenu = R.id.provider_profile_dest
            idVacancyMenu = R.id.provider_vacancy_dest
        } else {
            graph = inflater.inflate(R.navigation.seeker_nav)
        }

        navHostFragment.navController.graph = graph

        profileMenu.setOnClickListener {
            navController.navigate(
                idProfileMenu,
                null,
                NavOptions.Builder().setEnterAnim(R.anim.slide_in_right)
                    .setExitAnim(R.anim.slide_out_left)
                    .setPopEnterAnim(R.anim.slide_in_left)
                    .setPopExitAnim(R.anim.slide_out_right).build()
            )
            binding.drawerLayout.closeDrawer(GravityCompat.START)
        }

        applicantMenu.setOnClickListener {
            navController.navigate(
                idVacancyMenu,
                null,
                NavOptions.Builder().setEnterAnim(R.anim.slide_in_right)
                    .setExitAnim(R.anim.slide_out_left)
                    .setPopEnterAnim(R.anim.slide_in_left)
                    .setPopExitAnim(R.anim.slide_out_right).build()
            )
            binding.drawerLayout.closeDrawer(GravityCompat.START)
        }
        binding.ivSignout.setOnClickListener {
            userVM.signout()
            binding.progressbar.visibility = View.VISIBLE
            binding.drawerLayout.closeDrawer(GravityCompat.START)
        }
        binding.drawerLayout.addDrawerListener(this)
        binding.drawerLayout.setScrimColor(Color.TRANSPARENT)
    }

    override fun onSupportNavigateUp() =
        navController.navigateUp(appBarConfiguration)

    override fun onBackPressed() =
        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START))
            binding.drawerLayout.closeDrawer(GravityCompat.START)
        else
            super.onBackPressed()

    override fun onDrawerStateChanged(newState: Int) {}

    override fun onDrawerSlide(drawerView: View, slideOffset: Float) {
        var moveFactor: Float
        moveFactor = (drawerView.getWidth() * slideOffset)
        binding.parentLayout.translationX = moveFactor
    }

    override fun onDrawerClosed(drawerView: View) {}

    override fun onDrawerOpened(drawerView: View) {}

    override fun onStart() {
        super.onStart()
        auth.addAuthStateListener(authListener)
    }

    override fun onStop() {
        super.onStop()
        auth.removeAuthStateListener(authListener)
    }

    private val authListener = FirebaseAuth.AuthStateListener {
        if (it.currentUser == null) {
            binding.progressbar.visibility = View.GONE
            val intent = Intent(this, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }
    }
}
