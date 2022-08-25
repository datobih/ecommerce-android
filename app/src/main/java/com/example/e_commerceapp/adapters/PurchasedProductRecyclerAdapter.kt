package com.example.e_commerceapp.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.e_commerceapp.Constants
import com.example.e_commerceapp.databinding.ItemPurchasedProductsBinding
import com.example.e_commerceapp.retrofit.dto.OrderItemDTO

class PurchasedProductRecyclerAdapter(val context: Context,val orderItemDTOList: List<OrderItemDTO>):RecyclerView.Adapter
<PurchasedProductRecyclerAdapter.ProductViewHolder>() {



    inner class ProductViewHolder(val binding:ItemPurchasedProductsBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val binding=ItemPurchasedProductsBinding.inflate(LayoutInflater.from(parent.context),
        parent,false)
        return ProductViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        with(holder){
            with(orderItemDTOList[position]){
                binding.tvProductName.text=title
                val sumPrice=price.toInt()*quantity
                binding.tvProductPrice.text="NGN ${com.example.e_commerceapp.Constants.formatPrice(sumPrice.toString())}"
                binding.tvProductCount.text=quantity.toString()

                com.bumptech.glide.Glide.with(context)
                    .load("${images[0]}")
                    .into(binding.imvProduct)


            }
        }
    }

    override fun getItemCount(): Int {
        return orderItemDTOList.size
    }
}