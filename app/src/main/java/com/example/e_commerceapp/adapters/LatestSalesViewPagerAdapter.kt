package com.example.e_commerceapp.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.e_commerceapp.databinding.ItemSalesBinding
import com.example.e_commerceapp.models.Sale

class LatestSalesViewPagerAdapter(val salesList:ArrayList<Sale>):
    RecyclerView.Adapter<LatestSalesViewPagerAdapter.SaleViewHolder>() {



    inner class SaleViewHolder(val binding: ItemSalesBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SaleViewHolder {
        val binding=ItemSalesBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return SaleViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SaleViewHolder, position: Int) {
        with(holder){
            with(salesList[position]){

                binding.tvSalesCondition.text=condition
                binding.imvSale.setImageResource(image)

            }

        }
    }

    override fun getItemCount(): Int {
        return salesList.size
    }

}