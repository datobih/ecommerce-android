package com.example.e_commerceapp.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.e_commerceapp.databinding.ItemProductDetailImageBinding

class ProductDetailImagesVPAdapter(val context:Context,val images:List<String>):
RecyclerView.Adapter<ProductDetailImagesVPAdapter.ProductDetailViewHolder>(){

    inner class ProductDetailViewHolder(val binding:ItemProductDetailImageBinding):
            RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductDetailViewHolder {
        val binding=ItemProductDetailImageBinding.inflate(LayoutInflater.from(parent.context),
        parent,false)
        return ProductDetailViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductDetailViewHolder, position: Int) {
with(holder){


    Glide.with(context)
        .load("${images[position]}")
        .into(binding.categoryImage)


}
    }

    override fun getItemCount(): Int {
        return images.size
    }

}