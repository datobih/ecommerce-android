package com.example.e_commerceapp.ui

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.example.e_commerceapp.R
import com.example.e_commerceapp.databinding.FragmentWalletBinding
import com.example.e_commerceapp.databinding.TopUpDialogBinding
import com.example.e_commerceapp.retrofit.dto.TopupDTO
import com.example.e_commerceapp.utils.DataState
import com.google.android.material.snackbar.Snackbar

class WalletFragment : Fragment() {
    lateinit var binding: FragmentWalletBinding
    private val mainViewModel: MainViewModel by activityViewModels()
    lateinit var mContext: Context
    var accountBalance: Int = 0
    var topupAmount = 0


    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentWalletBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainViewModel.profileLiveData.observe(viewLifecycleOwner, Observer { dataState ->

            when (dataState) {
                is DataState.Success -> {
                    doneLoading()
                    val user = dataState.data
                    val accountBalanceStr = user!!.accountBalance
                    accountBalance = accountBalanceStr.toInt()
                    binding.tvAccountBalance.text = "NGN $accountBalance"


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



        mainViewModel.topUpLiveData.observe(viewLifecycleOwner, Observer { dataState ->
            when (dataState) {
                is DataState.Success -> {
                    doneLoading()
                    accountBalance+=topupAmount
                    topupAmount=0
                    binding.tvAccountBalance.text = "NGN $accountBalance"

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

        binding.btnTopupOnCredit.setOnClickListener {
            val dialog = Dialog(mContext)
            with(dialog) {
                setContentView(R.layout.top_up_dialog)
                val btnTopup = findViewById<TextView>(R.id.btn_topup_dialog)
                val btnCancel = findViewById<TextView>(R.id.btn_cancel_dialog)
                val etTopUpBalance = findViewById<EditText>(R.id.et_topup_balance)
                btnTopup.setOnClickListener {

                    if (!etTopUpBalance.text.toString().isNullOrEmpty()) {
                        topupAmount = etTopUpBalance.text.toString().toInt()
                        mainViewModel.topUpBalance(mainViewModel.getUserTokenHeader()!!,
                            TopupDTO(topupAmount)
                        )
                    }
                }

                btnCancel.setOnClickListener {
                    dismiss()
                }


                show()
            }


        }


    }

    override fun onResume() {
        mainViewModel.getUserProfile(mainViewModel.getUserTokenHeader()!!)
        super.onResume()
    }

    fun startLoading() {
        binding.llWalletFeed.visibility = View.GONE
        binding.pbLoadingFeed.visibility = View.VISIBLE

    }


    fun doneLoading() {
        binding.llWalletFeed.visibility = View.VISIBLE
        binding.pbLoadingFeed.visibility = View.GONE
    }

    fun showSnackBar(message: String): Snackbar {
        return Snackbar.make(binding.root, message, Snackbar.LENGTH_SHORT)

    }


}