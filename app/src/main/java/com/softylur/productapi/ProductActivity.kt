package com.softylur.productapi

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.WindowManager
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.softylur.productapi.databinding.ActivityProductBinding
import java.text.DecimalFormat
import java.util.Locale

class ProductActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProductBinding

    // When the activity is created, it will initialize UI components, fetch data from API and apply shimmer effect.
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        window.setFlags( WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS )


        binding.tvProductTitle.text = intent.getStringExtra("title").toString()
        binding.tvProductName.text = intent.getStringExtra("title").toString()
        binding.tvProductTitle.setSelected(true)

        Glide.with(this)
            .load(intent.getStringExtra("image"))
            .into(binding.ivProductPic)

        binding.tvProductCategory.text = intent.getStringExtra("category").toString().replaceFirstChar {
            if (it.isLowerCase()) it.titlecase(Locale.getDefault())
            else it.toString()
        }

        val rate = intent.getStringExtra("rate")?.toFloat()
        rate?.let { fillRatingStars(it) }
        binding.tvProductRating.text = intent.getStringExtra("rate").toString()

        val count = intent.getStringExtra("count").toString()
        binding.tvProductReview.text = "($count Reviews)"

        val priceValue = intent.getStringExtra("price")?.toFloat()?.times(119.99)
        val price = priceValue?.let { java.lang.Double.valueOf(it) }
        val dec = DecimalFormat("#,###.##")
        val bdtPrice = dec.format(price)

        binding.tvProductPrice.text = "৳ $bdtPrice"

        binding.tvProductDescription.text = intent.getStringExtra("description").toString()


        binding.ivBack.setOnClickListener{
            val intent = Intent(this, HomePageActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT)
            startActivity(intent)
            finishAffinity()
        }
    }

    private fun fillRatingStars(rate: Float){
        if (rate < 0.8f){
            binding.ivStar1.setImageResource(R.drawable.ic_star_golden_svg)
            binding.ivStar2.setImageResource(R.drawable.ic_star_golden_svg)
            binding.ivStar3.setImageResource(R.drawable.ic_star_golden_svg)
            binding.ivStar4.setImageResource(R.drawable.ic_star_golden_svg)
            binding.ivStar5.setImageResource(R.drawable.ic_star_golden_svg)
        } else if (rate >= 0.8f && rate < 1.8f) {
            binding.ivStar1.setImageResource(R.drawable.ic_star_golden_svg_fill)
            binding.ivStar2.setImageResource(R.drawable.ic_star_golden_svg)
            binding.ivStar3.setImageResource(R.drawable.ic_star_golden_svg)
            binding.ivStar4.setImageResource(R.drawable.ic_star_golden_svg)
            binding.ivStar5.setImageResource(R.drawable.ic_star_golden_svg)
        } else if (rate >= 1.8f && rate < 2.8f) {
            binding.ivStar1.setImageResource(R.drawable.ic_star_golden_svg_fill)
            binding.ivStar2.setImageResource(R.drawable.ic_star_golden_svg_fill)
            binding.ivStar3.setImageResource(R.drawable.ic_star_golden_svg)
            binding.ivStar4.setImageResource(R.drawable.ic_star_golden_svg)
            binding.ivStar5.setImageResource(R.drawable.ic_star_golden_svg)
        } else if (rate >= 2.8f && rate < 3.8f) {
            binding.ivStar1.setImageResource(R.drawable.ic_star_golden_svg_fill)
            binding.ivStar2.setImageResource(R.drawable.ic_star_golden_svg_fill)
            binding.ivStar3.setImageResource(R.drawable.ic_star_golden_svg_fill)
            binding.ivStar4.setImageResource(R.drawable.ic_star_golden_svg)
            binding.ivStar5.setImageResource(R.drawable.ic_star_golden_svg)
        } else if (rate >= 3.8f && rate < 4.8f) {
            binding.ivStar1.setImageResource(R.drawable.ic_star_golden_svg_fill)
            binding.ivStar2.setImageResource(R.drawable.ic_star_golden_svg_fill)
            binding.ivStar3.setImageResource(R.drawable.ic_star_golden_svg_fill)
            binding.ivStar4.setImageResource(R.drawable.ic_star_golden_svg_fill)
            binding.ivStar5.setImageResource(R.drawable.ic_star_golden_svg)
        } else {
            binding.ivStar1.setImageResource(R.drawable.ic_star_golden_svg_fill)
            binding.ivStar2.setImageResource(R.drawable.ic_star_golden_svg_fill)
            binding.ivStar3.setImageResource(R.drawable.ic_star_golden_svg_fill)
            binding.ivStar4.setImageResource(R.drawable.ic_star_golden_svg_fill)
            binding.ivStar5.setImageResource(R.drawable.ic_star_golden_svg_fill)
        }
    }

}
