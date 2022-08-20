package com.example.e_commerceapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.transition.ChangeBounds
import android.transition.Explode
import android.transition.Slide
import android.view.Window
import com.example.e_commerceapp.Constants
import com.example.e_commerceapp.R

class ProductDetailsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
// Set Activity transition
        with(window){

            requestFeature(Window.FEATURE_ACTIVITY_TRANSITIONS)

            enterTransition=Explode()

        }
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_details)

        val productId=intent.getIntExtra(Constants.INTENT_EXTRA_PRODUCT_ID,-1)










    }
}