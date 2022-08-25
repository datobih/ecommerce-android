package com.example.e_commerceapp.ui

import android.content.Intent
import android.os.Binder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.example.e_commerceapp.R
import com.example.e_commerceapp.databinding.ActivitySignupBinding
import com.example.e_commerceapp.retrofit.dto.SignupDTO
import com.example.e_commerceapp.utils.DataState
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignupActivity : AppCompatActivity() {
    lateinit var binding: ActivitySignupBinding
    private val signupViewModel:SignupViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)



        signupViewModel.createUserLiveData.observe(this, Observer {
            dataState->

            when(dataState){
                is DataState.Success->{
                    doneLoading()
                    val snackbar=showSnackBar("Successfully created your account,a verification link has been sent to your email.")
                        snackbar.addCallback(object : BaseTransientBottomBar.BaseCallback<Snackbar>(){
                            override fun onDismissed(transientBottomBar: Snackbar?, event: Int) {
                                super.onDismissed(transientBottomBar, event)
                                startActivity(Intent(this@SignupActivity,OTPVerificationActivity::class.java))
                            finish()
                            }

                        })
                    snackbar.show()
                }
                is DataState.Error->{
                    doneLoading()
                    showSnackBar("Something")
                }
                is DataState.Loading->{
                    startLoading()
                }



            }


        })

        binding.btnRegister.setOnClickListener {
            val name=binding.etName.text.toString()
            val email=binding.etEmail.text.toString()
            val password=binding.etPassword.text.toString()
            val confirm_password=binding.etConfirmPassword.text.toString()

            if(!(name.isNullOrEmpty())&&!(email.isNullOrEmpty())&&
                    !(password.isNullOrEmpty())&&!(confirm_password.isNullOrEmpty())){
                signupViewModel.createUser(SignupDTO(name,email,password,confirm_password))
            }
            else{
                showSnackBar("Make sure you have filled all the required input fields").show()
            }



        }

    }
    fun startLoading(){

        binding.llSignUpLoading.visibility=View.VISIBLE
    }
    fun doneLoading(){
        binding.llSignUpLoading.visibility=View.GONE
    }

    fun showSnackBar(message: String): Snackbar {
        return Snackbar.make(binding.root, message, Snackbar.LENGTH_SHORT)

    }

}