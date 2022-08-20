package com.example.e_commerceapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import com.example.e_commerceapp.R
import com.example.e_commerceapp.databinding.ActivityPaymentBinding

class PaymentActivity : AppCompatActivity() {
    lateinit var binding:ActivityPaymentBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityPaymentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val transitionUp= AnimationUtils.loadAnimation(this, R.anim.transition_up)
        val transitionDown= AnimationUtils.loadAnimation(this, R.anim.transition_down)

        transitionUp.setAnimationListener(object : Animation.AnimationListener{
            override fun onAnimationStart(p0: Animation?) {
                binding.btnPayWithProfile.isClickable=false
            }

            override fun onAnimationEnd(p0: Animation?) {


            }

            override fun onAnimationRepeat(p0: Animation?) {

            }

        })


        transitionDown.setAnimationListener(object : Animation.AnimationListener{
            override fun onAnimationStart(p0: Animation?) {
                binding.greyFilter.isClickable=false
            }

            override fun onAnimationEnd(p0: Animation?) {
                binding.greyFilter.isClickable=true
                binding.btnPayWithProfile.isClickable=true
                binding.llConfirmPaymentScene.visibility= View.GONE
            }

            override fun onAnimationRepeat(p0: Animation?) {

            }

        })

val changeBounds= android.transition.ChangeBounds()



        binding.btnPayWithProfile.setOnClickListener {


            binding.llConfirmPaymentScene.visibility= View.VISIBLE

            binding.llConfirmPayment.startAnimation(transitionUp)
        }


        binding.greyFilter.setOnClickListener {
            binding.llConfirmPayment.startAnimation(transitionDown)

        }

    }
}