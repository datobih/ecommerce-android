package com.example.e_commerceapp.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.e_commerceapp.databinding.ItemProductBinding
import com.example.e_commerceapp.models.Product

class ProductRecyclerAdapter(val productList: ArrayList<Product>):
RecyclerView.Adapter<ProductRecyclerAdapter.ProductViewHolder>(){


    class ProductViewHolder(val binding:ItemProductBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val binding=ItemProductBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ProductViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        with(holder){
            with(productList[position]){
                binding.tvProductName.text=title
                binding.tvProductPrice.text=price
                binding.imvProduct.setImageResource(image)


            }


        }



    }

    override fun getItemCount(): Int {
        return productList.size
    }

}