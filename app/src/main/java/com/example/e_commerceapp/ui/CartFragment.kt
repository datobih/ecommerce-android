package com.example.e_commerceapp.ui

import android.app.ActivityOptions
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityOptionsCompat
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.e_commerceapp.Constants
import com.example.e_commerceapp.R
import com.example.e_commerceapp.adapters.CartRecyclerAdapter
import com.example.e_commerceapp.databinding.FragmentCartBinding
import com.example.e_commerceapp.databinding.FragmentHomeBinding
import com.example.e_commerceapp.retrofit.dto.OrderItemDTO
import com.example.e_commerceapp.utils.CartItemOnClickListener
import com.example.e_commerceapp.utils.DataState
import com.example.e_commerceapp.utils.main


class CartFragment : Fragment() {
lateinit var binding: FragmentCartBinding
lateinit var mContext: Context
private val mainViewModel:MainViewModel by activityViewModels()
var totalPrice=0
    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext=context
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding=FragmentCartBinding.inflate(LayoutInflater.from(mContext),container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainViewModel.cartItemsLiveData.observe(viewLifecycleOwner, Observer {
            dataState->

            when(dataState){
                is DataState.Success->{
                    doneLoadingFeed()
                    setupCartAdapter(dataState.data!!)

                }

                is DataState.Error->{
                    doneLoadingFeed()
                }

                else->{
                    loadingFeed()
                }

            }

        })

        mainViewModel.removeOrderLiveData.observe(viewLifecycleOwner, Observer {
            dataState->

            when(dataState){
                is DataState.Success->{
                    doneLoadingFeed()
                    val removePosition=dataState.data
                    val adapter= binding.rvCart.adapter as CartRecyclerAdapter
                    adapter.orderItemDTOList.removeAt(removePosition!!)
                    adapter.notifyItemRemoved(removePosition)
                    evaluateTotalPrice(adapter.orderItemDTOList)

                }

                is DataState.Error->{
                    doneLoadingFeed()
                }

                else->{
                    loadingFeed()
                }
            }


        })

        binding.btnCheckout.setOnClickListener {
            val parentActivity=(mContext as MainActivity)
            val intent=Intent(parentActivity,PaymentActivity::class.java)
            intent.putExtra(Constants.INTENT_EXTRA_TOTAL_PRICE,totalPrice)
            startActivity(intent,
                ActivityOptions.makeSceneTransitionAnimation(parentActivity).toBundle())
        }

    }

    override fun onResume() {
        mainViewModel.getCart(mainViewModel.getUserTokenHeader()!!)
        super.onResume()
    }




    fun setupCartAdapter(orderListItemDTO: List<OrderItemDTO>){

        val adapter=CartRecyclerAdapter(mContext,ArrayList(orderListItemDTO))
        adapter.setCartItemOnClickListener(object : CartItemOnClickListener{
            override fun onClick(orderID: Int,itemPosition:Int) {
                mainViewModel.removeOrder(mainViewModel.getUserTokenHeader()!!,orderID,itemPosition)
            }

        })
        binding.rvCart.layoutManager=LinearLayoutManager(mContext)
        binding.rvCart.adapter=adapter
        evaluateTotalPrice(orderListItemDTO)

    }

    fun evaluateTotalPrice(orderListItemDTO: List<OrderItemDTO>){

        totalPrice=0
        for(orderItemDTO in orderListItemDTO){
            totalPrice+=(orderItemDTO.price.toInt()*orderItemDTO.quantity)
        }
        binding.tvCartTotalPrice.text="NGN ${totalPrice.toString()}"

    }

    fun loadingFeed(){
        binding.llCartFeed.visibility=View.GONE
        binding.pbLoadingFeed.visibility=View.VISIBLE

    }

    fun doneLoadingFeed(){
        binding.pbLoadingFeed.visibility=View.GONE
        binding.llCartFeed.visibility=View.VISIBLE
    }


}