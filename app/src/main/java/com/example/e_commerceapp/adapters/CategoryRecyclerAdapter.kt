package com.example.e_commerceapp.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.e_commerceapp.R
import com.example.e_commerceapp.databinding.ItemCategoryBinding
import com.example.e_commerceapp.models.Category
import com.example.e_commerceapp.ui.MainActivity
import com.example.e_commerceapp.utils.CategoryOnClickListener

class CategoryRecyclerAdapter(val categoryList: ArrayList<Category>):RecyclerView.Adapter<CategoryRecyclerAdapter.CategoryViewHolder>(){
    lateinit var categoryOnClickListener: CategoryOnClickListener
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val binding=ItemCategoryBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return CategoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
       with(holder){
           with(categoryList[position]){
               binding.tvCategoryName.text=categoryList[position].name
               binding.tvCategoryImage.setImageResource(image)
           }

           binding.root.setOnClickListener {
               categoryOnClickListener?.onClick(categoryList[position])
           }
       }



    }

    override fun getItemCount(): Int {
       return categoryList.size
    }

    class CategoryViewHolder(val binding:ItemCategoryBinding):RecyclerView.ViewHolder(binding.root)


}




