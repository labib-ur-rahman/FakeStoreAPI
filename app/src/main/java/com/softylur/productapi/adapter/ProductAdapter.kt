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
import java.text.DecimalFormat

class ProductAdapter(private var productList: List<ProductModel>) : RecyclerView.Adapter<ProductAdapter.MyViewHolder>() {

    private lateinit var mListener: OnItemClickListener

    interface OnItemClickListener{
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(listener: OnItemClickListener){
        mListener = listener
    }


    class MyViewHolder(itemView: View, listener: OnItemClickListener) : RecyclerView.ViewHolder(itemView) {
        var ivProductImage: ImageView = itemView.findViewById(R.id.ivProductImage)
        var tvTitle: TextView = itemView.findViewById(R.id.tvTitle)
        var tvRating: TextView = itemView.findViewById(R.id.tvRating)
        var tvCount: TextView = itemView.findViewById(R.id.tvCount)
        var tvPrice: TextView = itemView.findViewById(R.id.tvPrice)

        init {

            itemView.setOnClickListener {
                listener.onItemClick(adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_product, parent, false)
        return MyViewHolder(view, mListener)
    }

    override fun getItemCount(): Int {
        return productList.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
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