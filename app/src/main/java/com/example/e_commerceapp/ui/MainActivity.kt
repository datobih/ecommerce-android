package com.example.e_commerceapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.e_commerceapp.R
import com.example.e_commerceapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding:ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

supportFragmentManager.beginTransaction().apply {

    replace(R.id.fragment_home,HomeFragment())
    commit()

}



    }
}