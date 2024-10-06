package com.softylur.productapi

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.softylur.productapi.adapter.CategoryAdapter
import com.softylur.productapi.adapter.ProductAdapter
import com.softylur.productapi.databinding.ActivityHomepageBinding
import com.softylur.productapi.model.ProductModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.DecimalFormat
import java.util.Locale
import java.util.Random


class HomePageActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomepageBinding
    private lateinit var apiInterface: ApiInterface
    private val random = Random()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomepageBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        // Transparent Status Bar
        window.setFlags( WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS )


        binding.rvProduct.layoutManager = GridLayoutManager(this, 2)
        binding.rvProduct.hasFixedSize()

        getApiInterface()
        getCategoryData()
        getProductData(apiInterface.getAllProducts(), "All")
        getSingleProduct()

        binding.btnAll.setOnClickListener {
            shimmerInProductToCategory()
            binding.btnAll.setCardBackgroundColor(ContextCompat.getColor(this@HomePageActivity, R.color.catActive))
            getProductData(apiInterface.getAllProducts(), "All")
        }

    }


    private fun getApiInterface() {
        // Retrofit instance is created and ApiInterface object is created to fetch data from the API.
        apiInterface = RetrofitInstance.getInstance().create(ApiInterface::class.java)
    }

    private fun getSingleProduct() {
        val singleItemCall = apiInterface.getAllProducts()
        singleItemCall.enqueue(object : Callback<List<ProductModel>> {
            @SuppressLint("SetTextI18n")
            override fun onResponse(
                call: Call<List<ProductModel>>,
                response: Response<List<ProductModel>>
            ) {
                if (response.isSuccessful && response.body() != null) {
                    if (response.isSuccessful && response.body() != null) {

                        // If data is available then Shimmer Effect will be gone
                        binding.shimmerSingleProduct.stopShimmer()
                        binding.shimmerSingleProduct.visibility = View.GONE
                        binding.cvSingleProduct.visibility = View.VISIBLE

                        val productList = response.body()

                        val randNumber = productList?.let { rand(0, it.size) }

                        for (i in 0..randNumber!!) {
                            binding.tvTitle.text = productList[i].getTitle()
                            binding.tvCategory.text = productList[i].getCategory().replaceFirstChar {
                                if (it.isLowerCase()) it.titlecase(Locale.getDefault())
                                else it.toString()
                            }

                            val priceValue = productList[i].getPrice().toFloat().times(119.99)
                            val price = priceValue.let { java.lang.Double.valueOf(it) }
                            val dec = DecimalFormat("#,###.##")
                            val bdtPrice = dec.format(price)
                            binding.tvPrice.text = "৳ $bdtPrice"

                            val rating = "${productList[i].getRating().getRate()} (${
                                productList[i].getRating().getCount()
                            })"
                            binding.tvRating.text = rating
                            // Load image using Glide
                            Glide
                                .with(this@HomePageActivity)
                                .load(productList[i].getImage())
                                .into(binding.ivProductImage)
                        }

                        binding.cvSingleProduct.setOnClickListener {
                            val intent = Intent(this@HomePageActivity, ProductActivity::class.java)
                            intent.putExtra("productId", productList[randNumber].getId())
                            intent.putExtra("title", productList[randNumber].getTitle())
                            intent.putExtra("category", productList[randNumber].getCategory())
                            intent.putExtra("price", productList[randNumber].getPrice())
                            intent.putExtra("description", productList[randNumber].getDescription())
                            intent.putExtra("image", productList[randNumber].getImage())
                            intent.putExtra("rate", productList[randNumber].getRating().getRate())
                            intent.putExtra("count", productList[randNumber].getRating().getCount())
                            startActivity(intent)
                        }

                    }
                }
            }

            override fun onFailure(call: Call<List<ProductModel>>, t: Throwable) {
                Toast.makeText(this@HomePageActivity, t.toString(), Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun getProductData(call: Call<List<ProductModel>>, categoryName: String) {
        call.enqueue(object : Callback<List<ProductModel>> {
            override fun onResponse(
                call: Call<List<ProductModel>>,
                response: Response<List<ProductModel>>
            ) {
                if (response.isSuccessful && response.body() != null) {

                    // If data is available then Shimmer Effect will be gone
                    binding.shimmerProductList.stopShimmer()
                    binding.shimmerProductList.visibility = View.GONE
                    binding.rvProduct.visibility = View.VISIBLE

                    Toast.makeText(this@HomePageActivity, "$categoryName Products", Toast.LENGTH_SHORT).show()

                    val productList = response.body()
                    val adapter = ProductAdapter(productList!!)
                    binding.rvProduct.adapter = adapter
                    adapter.setOnItemClickListener(object : ProductAdapter.onItemClickListener {
                        override fun onItemClick(position: Int) {

                            val intent = Intent(this@HomePageActivity, ProductActivity::class.java)
                            intent.putExtra("productId", productList[position].getId())
                            intent.putExtra("title", productList[position].getTitle())
                            intent.putExtra("category", productList[position].getCategory())
                            intent.putExtra("price", productList[position].getPrice())
                            intent.putExtra("description", productList[position].getDescription())
                            intent.putExtra("image", productList[position].getImage())
                            intent.putExtra("rate", productList[position].getRating().getRate())
                            intent.putExtra("count", productList[position].getRating().getCount())
                            startActivity(intent)

                        }
                    })

                }
            }

            override fun onFailure(call: Call<List<ProductModel>>, t: Throwable) {
                Toast.makeText(this@HomePageActivity, t.toString(), Toast.LENGTH_SHORT).show()
            }
        })
    }


    fun rand(from: Int, to: Int): Int {
        return random.nextInt(to - from) + from
    }

    private fun getCategoryData() {
        val call = apiInterface.getCategories()
        call.enqueue(object : Callback<Array<String>> {
            override fun onResponse(
                call: Call<Array<String>>,
                response: Response<Array<String>>
            ) {
                if (response.isSuccessful && response.body() != null) {

                    // If data is available then Shimmer Effect will be gone
                    binding.shimmerCategoryList.stopShimmer()
                    binding.shimmerCategoryList.visibility = View.GONE
                    binding.nsvCategory.visibility = View.VISIBLE

                    val categoryList = response.body()
                    val adapter = CategoryAdapter(categoryList)
                    binding.rvCategory.adapter = adapter

                    adapter.setOnItemClickListener(object: CategoryAdapter.onItemClickListener{
                        override fun onItemClick(position: Int) {

                            when(position){
                                0 -> {
                                    shimmerInProductToCategory()
                                    //view.setBackgroundResource(R.drawable.bg_cat_active)
                                    binding.btnAll.setCardBackgroundColor(ContextCompat.getColor(this@HomePageActivity, R.color.catInactive))
                                    getProductData(apiInterface.getElectronicsProducts(), "Electronics")
                                }
                                1 -> {
                                    shimmerInProductToCategory()
                                    binding.btnAll.setCardBackgroundColor(ContextCompat.getColor(this@HomePageActivity, R.color.catInactive))
                                    getProductData(apiInterface.getJeweleryProducts(), "Jewelery")
                                }
                                2 -> {
                                    shimmerInProductToCategory()
                                    binding.btnAll.setCardBackgroundColor(ContextCompat.getColor(this@HomePageActivity, R.color.catInactive))
                                    getProductData(apiInterface.getMenClothingProducts(), "Men's Clothing")
                                }
                                3 -> {
                                    shimmerInProductToCategory()
                                    binding.btnAll.setCardBackgroundColor(ContextCompat.getColor(this@HomePageActivity, R.color.catInactive))
                                    getProductData(apiInterface.getWomenClothingProducts(), "Women's Clothing")
                                }
                                else -> {
                                    shimmerInProductToCategory()
                                    binding.btnAll.setCardBackgroundColor(ContextCompat.getColor(this@HomePageActivity, R.color.catInactive))
                                    getProductData(apiInterface.getAllProducts(), "All")
                                }
                            }
                        }
                    })

                }
            }

            override fun onFailure(call: Call<Array<String>>, t: Throwable) {
                Toast.makeText(this@HomePageActivity, t.toString(), Toast.LENGTH_SHORT).show()
            }
        })
    }

    fun shimmerInProductToCategory(){
        binding.shimmerProductList.startShimmer()
        binding.shimmerProductList.visibility = View.VISIBLE
        binding.rvProduct.visibility = View.GONE
    }

    override fun onResume() {
        super.onResume()
        // Shimmer effect will be started when the activity is resumed to restore battery life.
        binding.shimmerSingleProduct.startShimmer()
        binding.shimmerCategoryList.startShimmer()
        binding.shimmerProductList.startShimmer()
    }
    override fun onPause() {
        // Shimmer effect will be stopped when the activity is paused to save battery life.
        binding.shimmerSingleProduct.startShimmer()
        binding.shimmerCategoryList.startShimmer()
        binding.shimmerProductList.startShimmer()
        super.onPause()
    }
}