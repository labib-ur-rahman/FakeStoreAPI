package com.softylur.productapi.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.softylur.productapi.R
import com.softylur.productapi.databinding.ItemCatBinding
import java.util.Locale

class CategoryAdapter(private var categoryList: Array<String>?) : RecyclerView.Adapter<CategoryAdapter.myViewHolder>() {

    // Step 3 => Initialize the listener with null
    private lateinit var mListener : onItemClickListener

    // Step 1 => Define an interface named onItemClickListener
    // Step 2 => Create inside a function that calls onItemClickListener
    interface onItemClickListener{
        fun onItemClick(position: Int)
    }

    // Step 4 => Create a function inside the adapter class that calls onItemClickListener
    fun setOnItemClickListener(listener: onItemClickListener){
        mListener = listener
    }

    class myViewHolder(itemView: View, listener: onItemClickListener) : RecyclerView.ViewHolder(itemView) {

        var tvCategoryName: TextView
        var btnCategoryItem: CardView

        init {
            tvCategoryName = itemView.findViewById(R.id.tvCategoryName)
            btnCategoryItem = itemView.findViewById(R.id.btnCategoryItem)

            // Step 5 => Set the listener on the CardView
            itemView.setOnClickListener {
                listener.onItemClick(adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): myViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_cat, parent, false)

        // Step 6 => Return the ViewHolder with the initialized listener
        return myViewHolder(view, mListener)
    }

    override fun getItemCount(): Int {
        return categoryList?.size!!
    }

    override fun onBindViewHolder(holder: myViewHolder, position: Int) {
        holder.tvCategoryName.text = categoryList?.get(position)?.replaceFirstChar {
            if (it.isLowerCase()) it.titlecase(Locale.getDefault())
            else it.toString()
        }
    }
}