package com.example.e_commerceapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.example.e_commerceapp.R
import com.example.e_commerceapp.databinding.ActivityLoginBinding
import com.example.e_commerceapp.databinding.ActivityOtpverificationBinding
import com.example.e_commerceapp.utils.DataState
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OTPVerificationActivity : AppCompatActivity() {
    lateinit var binding:ActivityOtpverificationBinding
    val otpViewModel:OTPViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityOtpverificationBinding.inflate(layoutInflater)
        setContentView(binding.root)


        otpViewModel.verifyOTPLiveData.observe(this, Observer {
            dataState->

            when(dataState){
                is DataState.Success->{
                    doneLoading()
                    val snackbar=showSnackBar("Your account is activated,you can now sign in")

                        snackbar.addCallback(object : BaseTransientBottomBar.BaseCallback<Snackbar>(){
                            override fun onDismissed(transientBottomBar: Snackbar?, event: Int) {
                                super.onDismissed(transientBottomBar, event)
                                finish()
                            }

                        })
                    snackbar.show()

                }
                is DataState.Error->{
                    doneLoading()
                    showSnackBar("Something went wrong,try again").show()
                }
                is DataState.Loading->{
                    startLoading()
                }
        }
        })


        binding.btnVerify.setOnClickListener {
            val otpValue=binding.etOtp.text.toString()

            if(!otpValue.isNullOrEmpty()) {
                otpViewModel.verifyOTP(otpValue)
            }

            else showSnackBar("Make sure you have provided an input").show()

        }





    }
    fun startLoading(){

        binding.llOtpLoading.visibility=View.VISIBLE

    }
    fun doneLoading(){

        binding.llOtpLoading.visibility=View.GONE
    }

    fun showSnackBar(message: String): Snackbar {
        return Snackbar.make(binding.root, message, Snackbar.LENGTH_SHORT)

    }
}