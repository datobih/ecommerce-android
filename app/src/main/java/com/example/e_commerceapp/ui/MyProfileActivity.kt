package com.example.e_commerceapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import com.example.e_commerceapp.R
import com.example.e_commerceapp.databinding.ActivityMyProfileBinding
import com.example.e_commerceapp.utils.DataState
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyProfileActivity : AppCompatActivity() {
lateinit var binding:ActivityMyProfileBinding
private val profileViewModel:MyProfileViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMyProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.actionBar)
        supportActionBar!!.title="My Profile"
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setHomeAsUpIndicator(ContextCompat.getDrawable(this,R.drawable.ic_baseline_arrow_back_24))

        binding.actionBar.setNavigationOnClickListener {
            onBackPressed()
        }

        profileViewModel.profileLiveData.observe(this, Observer {
            dataState->
            when(dataState){
                is DataState.Success->{
                    doneLoading()
                    val user=dataState.data!!
                    binding.etName.setText(user.fullname)
                    binding.etAddress.setText(user.address)
                    binding.etEmail.setText(user.email)
                }
                is DataState.Error->{
                doneLoading()
                }
                else->{
                    startLoading()

                }
            }



        })

        profileViewModel.updateUserLiveData.observe(this, Observer {
                dataState->
            when(dataState){
                is DataState.Success->{
                    doneLoading()
                    showSnackBar("Details updated").show()

                }
                is DataState.Error->{
                    doneLoading()
                    showSnackBar("Something went wrong")
                }
                else->{
                    startLoading()

                }
            }


        })


        binding.btnSave.setOnClickListener {
            val fullname=binding.etName.text.toString()
            val email=binding.etEmail.text.toString()
            val address=binding.etAddress.text.toString()
            if(!(fullname.isNullOrEmpty()) && !(email.isNullOrEmpty()))
            profileViewModel.updateUserDetails(profileViewModel.getUserTokenHeader()!!,
            fullname = fullname, email = email, address = address
                )

        }

profileViewModel.getUserProfile(profileViewModel.getUserTokenHeader()!!)


    }

    fun startLoading(){
        binding.llProfileDetails.visibility= View.GONE
        binding.pbLoadingFeed.visibility=View.VISIBLE

    }

    fun doneLoading(){


        binding.llProfileDetails.visibility= View.VISIBLE
        binding.pbLoadingFeed.visibility=View.GONE
    }

    fun toastMessage(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    fun showSnackBar(message: String): Snackbar {
        return Snackbar.make(binding.root, message, Snackbar.LENGTH_SHORT)

    }
}