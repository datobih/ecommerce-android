package com.example.e_commerceapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.transition.Explode
import android.transition.Fade
import android.view.Window
import com.example.e_commerceapp.R
import com.example.e_commerceapp.databinding.ActivityPaymentCompletedBinding

class PaymentCompletedActivity : AppCompatActivity() {
    lateinit var binding:ActivityPaymentCompletedBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        // Set Activity transition
        with(window) {

            requestFeature(Window.FEATURE_ACTIVITY_TRANSITIONS)

            enterTransition = Explode()

        }
        super.onCreate(savedInstanceState)
        binding=ActivityPaymentCompletedBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.btnContinueShopping.setOnClickListener {
            onBackPressed()
        }

    }
}