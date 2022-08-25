package com.example.e_commerceapp.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.compose.ui.unit.Constraints
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.e_commerceapp.Constants
import com.example.e_commerceapp.databinding.ItemProductBinding
import com.example.e_commerceapp.models.Product
import com.example.e_commerceapp.utils.ProductOnClickListener

class ProductRecyclerAdapter(val context: Context,val productList: ArrayList<Product>):
RecyclerView.Adapter<ProductRecyclerAdapter.ProductViewHolder>(){
private var productOnClickListener:ProductOnClickListener?= null

    class ProductViewHolder(val binding:ItemProductBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val binding=ItemProductBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ProductViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        with(holder){
            with(productList[position]){
                binding.tvProductName.text=title
                binding.tvProductPrice.text="NGN ${Constants.formatPrice(price)}"

                Glide.with(context)
                    .load("${images[0]}")
                    .into(binding.imvProduct)


            }

holder.binding.root.setOnClickListener {

    productOnClickListener?.onClick(productList[position].id)

}
        }



    }

fun setProductOnClickListener(productOnClickListener: ProductOnClickListener){
    this.productOnClickListener=productOnClickListener
}

    override fun getItemCount(): Int {
        return productList.size
    }

}