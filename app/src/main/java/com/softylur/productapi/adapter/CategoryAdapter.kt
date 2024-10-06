package com.softylur.productapi.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.softylur.productapi.R
import java.util.Locale

class CategoryAdapter(private var categoryList: Array<String>?) : RecyclerView.Adapter<CategoryAdapter.MyViewHolder>() {

    // Step 3 => Initialize the listener with null
    private lateinit var mListener : OnItemClickListener

    // Step 1 => Define an interface named OnItemClickListener
    // Step 2 => Create inside a function that calls OnItemClickListener
    interface OnItemClickListener{
        fun onItemClick(position: Int)
    }

    // Step 4 => Create a function inside the adapter class that calls OnItemClickListener
    fun setOnItemClickListener(listener: OnItemClickListener){
        mListener = listener
    }

    class MyViewHolder(itemView: View, listener: OnItemClickListener) : RecyclerView.ViewHolder(itemView) {

        var tvCategoryName: TextView = itemView.findViewById(R.id.tvCategoryName)
        var btnCategoryItem: CardView = itemView.findViewById(R.id.btnCategoryItem)

        init {

            // Step 5 => Set the listener on the CardView
            itemView.setOnClickListener {
                listener.onItemClick(adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_cat, parent, false)

        // Step 6 => Return the ViewHolder with the initialized listener
        return MyViewHolder(view, mListener)
    }

    override fun getItemCount(): Int {
        return categoryList?.size!!
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.tvCategoryName.text = categoryList?.get(position)?.replaceFirstChar {
            if (it.isLowerCase()) it.titlecase(Locale.getDefault())
            else it.toString()
        }
    }
}