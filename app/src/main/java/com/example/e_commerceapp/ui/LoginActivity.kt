package com.example.e_commerceapp.ui

import android.content.Intent
import android.os.Binder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.example.e_commerceapp.R
import com.example.e_commerceapp.databinding.ActivityLoginBinding
import com.example.e_commerceapp.retrofit.dto.LoginDTO
import com.example.e_commerceapp.utils.DataState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {
    lateinit var binding: ActivityLoginBinding
    private val loginViewModel: LoginViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)


        loginViewModel.loginLiveData.observe(this, Observer { dataState ->
            when (dataState) {
                is DataState.Success -> {
                    hideLoading()
                    val token = dataState.data
                    loginViewModel.cacheToken(token!!)

                    //Launch MainActivity after token caching
                    startActivity(Intent(this,MainActivity::class.java))
                    finish()

                }
                is DataState.Error -> {
                    hideLoading()
                    toastMessage("Ensure your details are correct and try again.")
                }
                else -> {
                    showLoading()
                }
            }
        })

        binding.btnSignIn.setOnClickListener {
            val email = binding.etSignInEmail.text.toString()
            val password = binding.etSignInPassword.text.toString()
            if (validateSignInForm(email, password)) loginViewModel.loginUser(
                LoginDTO(
                    email,
                    password
                )
            )
            else toastMessage("Input your email and password and try again.")

        }


    }

    private fun validateSignInForm(email: String, password: String): Boolean {
        return (!email.isNullOrEmpty() && !password.isNullOrEmpty())
    }

    private fun toastMessage(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    private fun showLoading() {
        binding.llSignInLoading.visibility = View.VISIBLE


    }

    private fun hideLoading() {
        binding.llSignInLoading.visibility = View.GONE
    }

}