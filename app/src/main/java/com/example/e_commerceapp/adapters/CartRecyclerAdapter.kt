package com.example.e_commerceapp.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.compose.ui.unit.Constraints
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.e_commerceapp.Constants
import com.example.e_commerceapp.databinding.ItemCartBinding
import com.example.e_commerceapp.retrofit.dto.OrderItemDTO
import com.example.e_commerceapp.utils.CartItemOnClickListener

class CartRecyclerAdapter(val context: Context,val orderItemDTOList: ArrayList<OrderItemDTO>):RecyclerView.Adapter<CartRecyclerAdapter.CartViewHolder>() {

    lateinit var cartItemOnClickListener: CartItemOnClickListener
    inner class CartViewHolder(val binding:ItemCartBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val binding=ItemCartBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return  CartViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        with(holder){
            with(orderItemDTOList[position]){
                binding.tvProductName.text=title
                val sumPrice=price.toInt()*quantity
                binding.tvProductPrice.text="NGN ${Constants.formatPrice(sumPrice.toString())}"
                binding.tvProductCount.text=quantity.toString()

                Glide.with(context)
                    .load("http://10.0.2.2:8000${images[0]}")
                    .into(binding.imvProduct)


                binding.imbProductRemove.setOnClickListener {
                    cartItemOnClickListener.onClick(id,position)
                }
            }






        }
    }

    @JvmName("setCartItemOnClickListener1")
    fun setCartItemOnClickListener(cartItemOnClickListener: CartItemOnClickListener){

        this.cartItemOnClickListener=cartItemOnClickListener

    }

    override fun getItemCount(): Int {
        return orderItemDTOList.size
    }

}