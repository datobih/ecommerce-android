package com.example.e_commerceapp.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.transition.ChangeBounds
import android.transition.Explode
import android.transition.Fade
import android.transition.Slide
import android.util.Log
import android.view.MenuItem
import android.view.Window
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.e_commerceapp.R
import com.example.e_commerceapp.adapters.MainViewPagerAdapter
import com.example.e_commerceapp.databinding.ActivityMainBinding
import com.example.e_commerceapp.utils.DataState
import com.example.e_commerceapp.utils.UserDataState
import com.google.android.material.navigation.NavigationBarView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    lateinit var accessToken:String
    val mainViewModel: MainViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        with(window){

            requestFeature(Window.FEATURE_ACTIVITY_TRANSITIONS)

            exitTransition=Fade()

        }


        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)


        setContentView(binding.root)





        mainViewModel.userLiveData.observe(this, Observer { userDataState ->
            when (userDataState) {

                is UserDataState.UserAvailable -> {
                    accessToken=userDataState.data
                    val tokenHeader="Bearer ${accessToken}"
                    mainViewModel.getUserProfile(tokenHeader)

                }

                is UserDataState.AnonymousUser -> {
                    startActivity(Intent(this,LoginActivity::class.java))
                    finish()

                }

                else -> {

                }
            }


        })

        mainViewModel.profileLiveData.observe(this, Observer {
            dataState->

            when(dataState){
                is DataState.Success->{
                    val user=dataState.data!!
                    //Cache User data when available
                    mainViewModel.cacheUserData(user)
                }
                is DataState.Error->{

                }
                else->{}
            }

        })


        mainViewModel.isUserLoggedIn()


        val mainAdapter = MainViewPagerAdapter(supportFragmentManager, lifecycle)

        binding.vpMain.adapter = mainAdapter
        binding.vpMain.isUserInputEnabled = false


        binding.bottomNavMain.setOnItemSelectedListener(object :
            NavigationBarView.OnItemSelectedListener {
            override fun onNavigationItemSelected(item: MenuItem): Boolean {
                when (item.itemId) {
                    R.id.nav_home -> {
                        binding.vpMain.currentItem = 0
                        return true
                    }
                    R.id.nav_cart -> {
                        binding.vpMain.currentItem = 1
                        return true
                    }
                }
                return false
            }


        })


    }


fun toastMessage(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }
}