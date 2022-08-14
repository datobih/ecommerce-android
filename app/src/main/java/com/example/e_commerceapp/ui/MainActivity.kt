package com.example.e_commerceapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.e_commerceapp.R
import com.example.e_commerceapp.adapters.MainViewPagerAdapter
import com.example.e_commerceapp.databinding.ActivityMainBinding
import com.google.android.material.navigation.NavigationBarView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    lateinit var binding:ActivityMainBinding

    val mainViewModel: MainViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("TESTOOOO",mainViewModel.i.toString())
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val mainAdapter=MainViewPagerAdapter(supportFragmentManager,lifecycle)

        binding.vpMain.adapter=mainAdapter
        binding.vpMain.isUserInputEnabled=false


        binding.bottomNavMain.setOnItemSelectedListener(object:NavigationBarView.OnItemSelectedListener{
            override fun onNavigationItemSelected(item: MenuItem): Boolean {
                when (item.itemId){
                    R.id.nav_home-> {
                        binding.vpMain.currentItem=0
                    return true
                    }
                    R.id.nav_cart->{
                        binding.vpMain.currentItem=1
                        return true
                    }
                }
            return false
            }


        })


    }
}