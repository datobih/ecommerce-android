package com.example.e_commerceapp.ui

import android.app.Activity
import android.app.ActivityOptions
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.view.inputmethod.InputMethodManager
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityOptionsCompat
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.example.e_commerceapp.Constants
import com.example.e_commerceapp.R
import com.example.e_commerceapp.adapters.CategoryRecyclerAdapter
import com.example.e_commerceapp.adapters.LatestSalesViewPagerAdapter
import com.example.e_commerceapp.adapters.ProductRecyclerAdapter
import com.example.e_commerceapp.databinding.FragmentHomeBinding
import com.example.e_commerceapp.models.Category
import com.example.e_commerceapp.models.Product
import com.example.e_commerceapp.models.Sale
import com.example.e_commerceapp.utils.CategoryOnClickListener
import com.example.e_commerceapp.utils.DataState
import com.example.e_commerceapp.utils.ProductOnClickListener
import com.example.e_commerceapp.utils.main
import com.google.android.material.card.MaterialCardView


class HomeFragment : Fragment() {

    lateinit var binding: FragmentHomeBinding
    lateinit var mContext: Context
    lateinit var startActivityIntent: ActivityResultLauncher<Intent>
    private val mainViewModel: MainViewModel by activityViewModels()
    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
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
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val animZoomIn = AnimationUtils.loadAnimation(mContext, R.anim.zoom_in)
        val animZoomOut = AnimationUtils.loadAnimation(mContext, R.anim.zoom_out)
        var prevIndicator = binding.dotIndicator1


        startActivityIntent =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult(),
                object : ActivityResultCallback<ActivityResult> {
                    override fun onActivityResult(result: ActivityResult?) {
                        val parentActivity = (mContext as MainActivity)
                        if (result!!.resultCode == Constants.INTENT_SUCCESS_CART_RESULT_CODE) {
                            parentActivity.binding.bottomNavMain.selectedItemId = R.id.nav_cart
                        }
                    }
                })


        mainViewModel.productsLiveData.observe(viewLifecycleOwner, Observer { dataState ->
            when (dataState) {
                is DataState.Success -> {
                    val productList = dataState.data
                    setupProductsAdapter(productList!!)
                }

                is DataState.Error -> {
                    errorOccured()
                }

                else -> {
                    loadingFeed()
                }


            }


        })

        binding.btnTryAgain.setOnClickListener {
            mainViewModel.getProducts(mainViewModel.getUserTokenHeader()!!)
        }

        val categoryList = ArrayList<Category>()

        categoryList.add(Category("Phones", R.drawable.iphone13))
        categoryList.add(Category("Accessories", R.drawable.accessories))
        categoryList.add(Category("Fashion", R.drawable.fashion))
        categoryList.add(Category("Electronics", R.drawable.electronics))
        categoryList.add(Category("Laptops", R.drawable.laptops))


        val salesList = ArrayList<Sale>()
        for (i in 1..3)
            salesList.add(Sale("40% off", R.drawable.sales_discount_image))


        val categoryAdapter = CategoryRecyclerAdapter(categoryList)
        categoryAdapter.categoryOnClickListener=object :CategoryOnClickListener{
            override fun onClick(category: Category) {
                ((binding.rvProducts.adapter) as ProductRecyclerAdapter).filterCategory(category)
            }

        }

        binding.rvCategories.apply {
            layoutManager = LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false)
            this.adapter = categoryAdapter

        }



        binding.vpSales.apply {

            adapter = LatestSalesViewPagerAdapter(salesList)

        }

        binding.vpSales.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {


            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                when (position) {
                    0 -> {
                        prevIndicator.startAnimation(animZoomOut)
                        prevIndicator.setCardBackgroundColor(getColor(R.color.grey_indicator))
                        binding.dotIndicator1.setCardBackgroundColor(getColor(R.color.black))
                        binding.dotIndicator1.startAnimation(animZoomIn)
                        prevIndicator = binding.dotIndicator1
                    }
                    1 -> {
                        prevIndicator.startAnimation(animZoomOut)
                        prevIndicator.setCardBackgroundColor(getColor(R.color.grey_indicator))
                        binding.dotIndicator2.setCardBackgroundColor(getColor(R.color.black))
                        binding.dotIndicator2.startAnimation(animZoomIn)
                        prevIndicator = binding.dotIndicator2
                    }
                    2 -> {
                        prevIndicator.startAnimation(animZoomOut)
                        prevIndicator.setCardBackgroundColor(getColor(R.color.grey_indicator))
                        binding.dotIndicator3.setCardBackgroundColor(getColor(R.color.black))
                        binding.dotIndicator3.startAnimation(animZoomIn)
                        prevIndicator = binding.dotIndicator3
                    }


                }
            }

        })



        binding.etSearchItem.addTextChangedListener {

            val text=it.toString()

            Log.i("ON_SEARCH_CHANGED", it.toString())
            ((binding.rvProducts.adapter) as ProductRecyclerAdapter).let {
                it.searchFilter.filter(text)
            }
        }


        mainViewModel.getProducts(mainViewModel.getUserTokenHeader()!!)

    }


    fun loadingFeed() {
        binding.llNetworkError.visibility=View.GONE
        binding.llProductFeed.visibility = View.GONE
        binding.pbLoadingFeed.visibility = View.VISIBLE

    }

    fun doneLoading() {
        binding.pbLoadingFeed.visibility = View.GONE
        binding.llProductFeed.visibility = View.VISIBLE

    }

    fun errorOccured(){
        binding.pbLoadingFeed.visibility = View.GONE
        binding.llNetworkError.visibility=View.VISIBLE

    }


    fun setupProductsAdapter(productList: List<Product>) {
        doneLoading()
        val productAdapter = ProductRecyclerAdapter(mContext, ArrayList(productList))
        productAdapter.setProductOnClickListener(object : ProductOnClickListener {
            override fun onClick(productId: Int) {
                val parentActivity = (mContext as MainActivity)
                val intent = Intent(parentActivity, ProductDetailsActivity::class.java)
                intent.putExtra(Constants.INTENT_EXTRA_PRODUCT_ID, productId)
//                startActivity(intent,ActivityOptions.makeSceneTransitionAnimation(parentActivity).toBundle())
                startActivityIntent.launch(
                    intent,
                    ActivityOptionsCompat.makeSceneTransitionAnimation(parentActivity)
                )
            }


        })

        binding.rvProducts.apply {
            layoutManager = GridLayoutManager(mContext, 2)

            adapter = productAdapter
            
        }

    }

    fun getColor(color: Int): Int {
        return ContextCompat.getColor(mContext, color)
    }

}