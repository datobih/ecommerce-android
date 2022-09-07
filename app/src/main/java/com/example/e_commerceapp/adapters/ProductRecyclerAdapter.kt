package com.example.e_commerceapp.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.compose.ui.text.toLowerCase
import androidx.compose.ui.unit.Constraints
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.e_commerceapp.Constants
import com.example.e_commerceapp.databinding.ItemProductBinding
import com.example.e_commerceapp.models.Product
import com.example.e_commerceapp.utils.ProductOnClickListener

class ProductRecyclerAdapter(val context: Context,val productList: ArrayList<Product>):
RecyclerView.Adapter<ProductRecyclerAdapter.ProductViewHolder>(),Filterable{
private var productOnClickListener:ProductOnClickListener?= null
    var completeProductList=ArrayList<Product>()


    init {
        completeProductList.addAll(productList)

    }


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


    public val filterObj=object :Filter(){
        override fun performFiltering(input: CharSequence?): FilterResults {
            val filterResults=FilterResults()
            val query=input.toString().lowercase().trim()

            if(query.isNullOrEmpty()){
                filterResults.values=completeProductList
                return filterResults
            }
            else{

                val filteredList=completeProductList.filter {
                    filter-> filter.title.lowercase().contains(query)

                }
                filterResults.values=filteredList
                return filterResults
            }

        }

        override fun publishResults(p0: CharSequence?, result: FilterResults?) {
            productList.clear()

            productList.addAll(result?.values as ArrayList<Product>)

            notifyDataSetChanged()


        }


    }



fun setProductOnClickListener(productOnClickListener: ProductOnClickListener){
    this.productOnClickListener=productOnClickListener
}

    override fun getItemCount(): Int {
        return productList.size
    }

    override fun getFilter(): Filter {
        TODO("Not yet implemented")
    }

}