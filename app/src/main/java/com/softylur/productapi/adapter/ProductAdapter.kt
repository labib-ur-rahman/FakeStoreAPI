package com.softylur.productapi.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.softylur.productapi.R
import com.softylur.productapi.model.ProductModel
import kotlinx.coroutines.withContext
import java.text.DecimalFormat

class ProductAdapter(var productList: List<ProductModel>) : RecyclerView.Adapter<ProductAdapter.myViewHolder>() {

    private lateinit var mListener: onItemClickListener

    interface onItemClickListener{
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(listener: onItemClickListener){
        mListener = listener
    }


    class myViewHolder(itemView: View, listener: onItemClickListener) : RecyclerView.ViewHolder(itemView) {
        var ivProductImage: ImageView
        var tvTitle: TextView
        var tvRating: TextView
        var tvCount: TextView
        var tvPrice: TextView

        init {
            ivProductImage = itemView.findViewById(R.id.ivProductImage)
            tvTitle = itemView.findViewById(R.id.tvTitle)
            tvRating = itemView.findViewById(R.id.tvRating)
            tvCount = itemView.findViewById(R.id.tvCount)
            tvPrice = itemView.findViewById(R.id.tvPrice)

            itemView.setOnClickListener {
                listener.onItemClick(adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): myViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_product, parent, false)
        return myViewHolder(view, mListener)
    }

    override fun getItemCount(): Int {
        return productList.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: myViewHolder, position: Int) {
        val product = productList[position]
        product.getTitle().also { holder.tvTitle.text = it }

        holder.tvRating.text = product.getRating().getRate()
        holder.tvCount.text = "(${product.getRating().getCount()})"

        val priceValue = product.getPrice().toDouble().times(119.99)
        val price = priceValue.let { java.lang.Double.valueOf(it) }
        val dec = DecimalFormat("#,###.##")
        val bdtPrice = dec.format(price)
        holder.tvPrice.text = "৳ $bdtPrice"

        // Load image using Glide
        Glide
            .with(holder.tvTitle.context)
            .load(product.getImage())
            .into(holder.ivProductImage)
    }
}