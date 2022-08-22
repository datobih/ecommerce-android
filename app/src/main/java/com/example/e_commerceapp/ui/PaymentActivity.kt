package com.example.e_commerceapp.ui

import android.app.ActivityOptions
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.example.e_commerceapp.Constants
import com.example.e_commerceapp.R
import com.example.e_commerceapp.databinding.ActivityPaymentBinding
import com.example.e_commerceapp.utils.DataState
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PaymentActivity : AppCompatActivity() {
    lateinit var binding: ActivityPaymentBinding
    private val paymentViewModel: PaymentViewModel by viewModels()
    var isPaymentLoading = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPaymentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val transitionUp = AnimationUtils.loadAnimation(this, R.anim.transition_up)
        val transitionDown = AnimationUtils.loadAnimation(this, R.anim.transition_down)
        val totalPrice = intent.getIntExtra(Constants.INTENT_EXTRA_TOTAL_PRICE, -1)

        paymentViewModel.makePaymentLiveData.observe(this, Observer { dataState ->

            when (dataState) {

                is DataState.Success -> {
                    isPaymentLoading=false
                    binding.btnPayWithProfile.isClickable=true
                    binding.llConfirmPaymentScene.visibility=View.GONE

                    when(dataState.data!!.message){
                        Constants.MAKE_PAYEMENT_RESPONSE_SUCCESS->{

                            startActivity(
                                Intent(this,PaymentCompletedActivity::class.java),
                            ActivityOptions.makeSceneTransitionAnimation(this).toBundle())
                            finish()
                        }

                        Constants.MAKE_PAYMENT_RESPONSE_INSUFFICIENT_BALANCE->{
                            showSnackBar("You have insufficient balance").show()

                        }

                        Constants.MAKE_PAYMENT_RESPONSE_INVALID_PASSWORD->{
                            showSnackBar("Invalid password provided").show()

                        }
                    }

                }


                is DataState.Error -> {
                    isPaymentLoading=false
                    binding.btnPayWithProfile.isClickable=true
                    binding.llConfirmPaymentScene.visibility=View.GONE

                }

                else -> {
                    isPaymentLoading = true
                    binding.llConfirmPayment.startAnimation(transitionDown)
                }


            }


        })



        binding.tvCartTotalPrice.text = "NGN ${totalPrice.toString()}"




        transitionUp.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(p0: Animation?) {
                binding.btnPayWithProfile.isClickable = false
            }

            override fun onAnimationEnd(p0: Animation?) {


            }

            override fun onAnimationRepeat(p0: Animation?) {

            }

        })


        transitionDown.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(p0: Animation?) {
                binding.greyFilter.isClickable = false
            }

            override fun onAnimationEnd(p0: Animation?) {
                binding.llConfirmPayment.clearAnimation()
                binding.greyFilter.isClickable = true
                binding.btnPayWithProfile.isClickable = true


                if (isPaymentLoading) {
                    disableListeners()

                    binding.pbLoading.visibility = View.VISIBLE
                    binding.llConfirmPayment.visibility = View.GONE

                } else binding.llConfirmPaymentScene.visibility = View.GONE


            }

            override fun onAnimationRepeat(p0: Animation?) {

            }

        })



        binding.btnPayWithProfile.setOnClickListener {
            binding.llConfirmPaymentScene.visibility = View.VISIBLE
            binding.llConfirmPayment.visibility = View.VISIBLE
            binding.llConfirmPayment.startAnimation(transitionUp)
        }
        binding.greyFilter.setOnClickListener {

            binding.llConfirmPayment.startAnimation(transitionDown)

        }
        binding.btnPaymentContinue.setOnClickListener {
            val password=binding.etEnterPassword.text.toString()
            if(!password.isNullOrEmpty()) paymentViewModel.makePayment(paymentViewModel.getUserTokenHeader()!!,password)

            else showSnackBar("Make sure you have inputted your password").show()

        }


    }

    fun showSnackBar(message:String): Snackbar {
        return Snackbar.make(binding.root,message, Snackbar.LENGTH_SHORT)

    }


    fun disableListeners() {

        binding.btnPayWithProfile.isClickable = false
        binding.llConfirmPayment.isClickable = false
    }


}