package com.example.e_commerceapp.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.unit.Constraints
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.example.e_commerceapp.Constants
import com.example.e_commerceapp.R
import com.example.e_commerceapp.databinding.FragmentAccountBinding
import com.example.e_commerceapp.databinding.FragmentCartBinding
import com.example.e_commerceapp.utils.DataState
import com.google.android.material.snackbar.Snackbar


class AccountFragment : Fragment() {
    lateinit var binding:FragmentAccountBinding
    lateinit var mContext: Context
    private val mainViewModel: MainViewModel by activityViewModels()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext=context
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding=FragmentAccountBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mainViewModel.profileLiveData.observe(viewLifecycleOwner, Observer { dataState ->

            when (dataState) {
                is DataState.Success -> {
                    doneLoading()
                    val user = dataState.data
                    val accountBalance = user!!.accountBalance
                    val fullname= user!!.fullname
                    binding.tvAccountBalance.text = "Your balance: NGN " +
                            "${Constants.formatPrice(accountBalance)}"
                    binding.tvlWelcome.text="Welcome ${getFirstName(fullname)}!"

                }

                is DataState.Error -> {
                    doneLoading()
                    showSnackBar("Something went wrong").show()
                }

                is DataState.Loading -> {
                    startLoading()

                }

            }


        })


        binding.llAccount.setOnClickListener {
            val parentActivity=(mContext as MainActivity)
            startActivity(Intent(parentActivity,MyProfileActivity::class.java))
        }

        binding.llPurchasedItems.setOnClickListener {
            val parentActivity=(mContext as MainActivity)
            startActivity(Intent(parentActivity,PurchasedItemsActivity::class.java))
        }

    }

    override fun onResume() {
        mainViewModel.getUserProfile(mainViewModel.getUserTokenHeader()!!)

        super.onResume()
    }


    fun startLoading(){
        binding.llAccountFeed.visibility=View.GONE
        binding.pbLoadingFeed.visibility=View.VISIBLE
    }

    fun doneLoading(){
        binding.llAccountFeed.visibility=View.VISIBLE
        binding.pbLoadingFeed.visibility=View.GONE
    }



    fun showSnackBar(message: String): Snackbar {
        return Snackbar.make(binding.root, message, Snackbar.LENGTH_SHORT)

    }

    fun getFirstName(name:String):String{
        val names=name.split(" ")
        return names[0]
    }



}


