package com.example.e_commerceapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.transition.ChangeBounds
import android.transition.Explode
import android.transition.Slide
import android.view.View
import android.view.Window
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.example.e_commerceapp.Constants
import com.example.e_commerceapp.R
import com.example.e_commerceapp.adapters.ProductDetailImagesVPAdapter
import com.example.e_commerceapp.databinding.ActivityProductDetailsBinding
import com.example.e_commerceapp.utils.DataState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProductDetailsActivity : AppCompatActivity() {
    lateinit var binding: ActivityProductDetailsBinding
    val productDetailViewModel: ProductDetailsViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
// Set Activity transition
        with(window) {

            requestFeature(Window.FEATURE_ACTIVITY_TRANSITIONS)

            enterTransition = Explode()

        }
        super.onCreate(savedInstanceState)
        binding = ActivityProductDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val productId = intent.getIntExtra(Constants.INTENT_EXTRA_PRODUCT_ID, -1)

        productDetailViewModel.productdetailLiveData.observe(this, Observer { dataState ->
            when (dataState) {

                is DataState.Success -> {
                    doneLoading()
                    val productDetail = dataState.data
                    binding.tvProductName.text = productDetail!!.title
                    binding.tvProductDesc.text = productDetail!!.description
                    binding.tvRatingValue.text = productDetail!!.averageRating.toString()
                    binding.tvRatingReview.text =
                        "(${productDetail!!.ratingCount.toString()} reviews)"
                    binding.tvProductPrice.text = "NGN " + productDetail!!.price

                    val productImageAdapter = ProductDetailImagesVPAdapter(this,
                    productDetail.images)
                    binding.vpProductImage.adapter=productImageAdapter


                }

                is DataState.Error -> {

                }

                else -> {
                    loadingDetail()
                }


            }

        })

        binding.btnBack.setOnClickListener {
            onBackPressed()
        }

        productDetailViewModel.getProductDetail(productDetailViewModel.getUserTokenHeader()!!,productId)

    }


    fun loadingDetail() {
        binding.llProductDetails.visibility = View.GONE
        binding.pbLoadingDetails.visibility = View.VISIBLE
    }

    fun doneLoading() {
        binding.pbLoadingDetails.visibility = View.GONE
        binding.llProductDetails.visibility = View.VISIBLE
    }

}