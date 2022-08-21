package com.example.e_commerceapp.ui

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.transition.ChangeBounds
import android.transition.Explode
import android.transition.Slide
import android.view.View
import android.view.Window
import android.view.animation.AnimationUtils
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.example.e_commerceapp.Constants
import com.example.e_commerceapp.R
import com.example.e_commerceapp.adapters.ProductDetailImagesVPAdapter
import com.example.e_commerceapp.databinding.ActivityProductDetailsBinding
import com.example.e_commerceapp.retrofit.dto.AddToCartDTO
import com.example.e_commerceapp.retrofit.dto.ProductDetailDTO
import com.example.e_commerceapp.utils.DataState
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProductDetailsActivity : AppCompatActivity() {
    lateinit var binding: ActivityProductDetailsBinding
    val productDetailViewModel: ProductDetailsViewModel by viewModels()
    var productId:Int=-1
    var quantityCount=0
    override fun onCreate(savedInstanceState: Bundle?) {
// Set Activity transition
        with(window) {

            requestFeature(Window.FEATURE_ACTIVITY_TRANSITIONS)

            enterTransition = Explode()

        }
        super.onCreate(savedInstanceState)
        binding = ActivityProductDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val shakeAnim = AnimationUtils.loadAnimation(this, R.anim.shake)


        productId = intent.getIntExtra(Constants.INTENT_EXTRA_PRODUCT_ID, -1)

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
                    setupStars(productDetail.averageRating)
                    val productImageAdapter = ProductDetailImagesVPAdapter(
                        this,
                        productDetail.images
                    )
                    binding.vpProductImage.adapter = productImageAdapter


                }

                is DataState.Error -> {

                }

                else -> {
                    loadingDetail()
                }


            }

        })

        productDetailViewModel.productAddCartLiveData.observe(this, Observer {
            dataState->

            when(dataState){
                is DataState.Success->{
                    binding.llAddingLoading.visibility=View.GONE
                    val snackbar=showSnackBar("Successfully added to cart")
                    snackbar.addCallback(object:BaseTransientBottomBar.BaseCallback<Snackbar>(){
                        override fun onDismissed(transientBottomBar: Snackbar?, event: Int) {
                          if(event==Snackbar.Callback.DISMISS_EVENT_TIMEOUT){
                              setResult(Constants.INTENT_SUCCESS_CART_RESULT_CODE)
                              finish()
                          }
                            super.onDismissed(transientBottomBar, event)

                        }
                    })
                    snackbar.show()

                }

                is DataState.Error->{

                    binding.llAddingLoading.visibility=View.GONE
                    val snackbar=showSnackBar("Something happened,try again")
                    snackbar.show()
                }

                else->{
                    binding.llAddingLoading.visibility=View.VISIBLE
                }



            }

        })

        binding.btnBack.setOnClickListener {
            onBackPressed()
        }
        binding.btnAddQuantity.setOnClickListener {
            binding.btnAddQuantity.startAnimation(shakeAnim)
            quantityCount = binding.tvQuantityCount.text.toString().toInt()
            if (quantityCount < 100) quantityCount++

            binding.tvQuantityCount.text = quantityCount.toString()

        }
        binding.btnReduceQuantity.setOnClickListener {
            binding.btnReduceQuantity.startAnimation(shakeAnim)
            quantityCount = binding.tvQuantityCount.text.toString().toInt()
            if (quantityCount > 0) quantityCount--
            binding.tvQuantityCount.text = quantityCount.toString()
        }

        binding.btnAddToCart.setOnClickListener {

            if (quantityCount > 0) {
            productDetailViewModel.addProductToCart(productDetailViewModel.getUserTokenHeader()!!,
                AddToCartDTO(productId,quantityCount)
            )

            }
        }



        productDetailViewModel.getProductDetail(
            productDetailViewModel.getUserTokenHeader()!!,
            productId
        )

    }

    fun setupStars(stars: Int) {
        val starsList = arrayOf(
            binding.imvStar1,
            binding.imvStar2, binding.imvStar3, binding.imvStar4,
            binding.imvStar5
        )
        for (i in 0 until stars) {

            val star = starsList[i]

            star.background = ContextCompat.getDrawable(this, R.drawable.ic_round_star_yellow_24)

        }
    }


    fun showSnackBar(message:String):Snackbar{
        return Snackbar.make(binding.root,message,Snackbar.LENGTH_SHORT)

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