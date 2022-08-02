package com.example.e_commerceapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.transition.Scene
import android.transition.*
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.transition.ChangeBounds
import androidx.transition.Transition
import androidx.transition.TransitionInflater
import androidx.transition.TransitionManager
import com.example.e_commerceapp.databinding.ActivityPaymentBinding

class PaymentActivity : AppCompatActivity() {
    lateinit var binding:ActivityPaymentBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityPaymentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val transitionUp= AnimationUtils.loadAnimation(this,R.anim.transition_up)
        val transitionDown= AnimationUtils.loadAnimation(this,R.anim.transition_down)

        transitionDown.setAnimationListener(object : Animation.AnimationListener{
            override fun onAnimationStart(p0: Animation?) {
                binding.greyFilter.isClickable=false
            }

            override fun onAnimationEnd(p0: Animation?) {
                binding.greyFilter.isClickable=true
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