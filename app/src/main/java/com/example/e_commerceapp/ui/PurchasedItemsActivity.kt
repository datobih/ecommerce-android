package com.example.e_commerceapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.compose.ui.unit.Constraints
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.e_commerceapp.R
import com.example.e_commerceapp.adapters.CartRecyclerAdapter
import com.example.e_commerceapp.adapters.PurchasedProductRecyclerAdapter
import com.example.e_commerceapp.databinding.ActivityPurchasedItemsBinding
import com.example.e_commerceapp.retrofit.dto.OrderItemDTO
import com.example.e_commerceapp.utils.CartItemOnClickListener
import com.example.e_commerceapp.utils.DataState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PurchasedItemsActivity : AppCompatActivity() {
    lateinit var binding: ActivityPurchasedItemsBinding
    private val purchasedItemsViewModel: PurchasedItemsViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPurchasedItemsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.actionBar)
        supportActionBar!!.setTitle("Purchased items")
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setHomeAsUpIndicator(
            ContextCompat.getDrawable(
                this,
                R.drawable.ic_baseline_arrow_back_24
            )
        )

        binding.actionBar.setNavigationOnClickListener {
            onBackPressed()
        }


        purchasedItemsViewModel.purchasedItemsLiveData.observe(this, Observer {
                dataState ->
            when (dataState) {
                is DataState.Success -> {
                    doneLoading()
                    setupItemsAdapter(dataState.data!!)

                }
                is DataState.Error -> {
                    doneLoading()
                }
                else -> {
                    startLoading()
                }

            }})


purchasedItemsViewModel.getPurchasedProducts(purchasedItemsViewModel.getUserTokenHeader()!!)

    }
    fun startLoading(){
        binding.rvPurchasedItems.visibility= View.GONE
        binding.pbLoadingFeed.visibility=View.VISIBLE
    }

    fun doneLoading(){
        binding.rvPurchasedItems.visibility= View.VISIBLE
        binding.pbLoadingFeed.visibility=View.GONE

    }


    fun setupItemsAdapter(orderListItemDTO: List<OrderItemDTO>) {

        val adapter = PurchasedProductRecyclerAdapter(this, orderListItemDTO)

        binding.rvPurchasedItems.layoutManager = LinearLayoutManager(this)
        binding.rvPurchasedItems.adapter = adapter


    }

}

