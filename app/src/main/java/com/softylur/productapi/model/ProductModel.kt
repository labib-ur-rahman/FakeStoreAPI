package com.softylur.productapi.model

class ProductModel {

    private var id: Int = 0
    private lateinit var title: String
    private lateinit var price: String
    private lateinit var description: String
    private lateinit var category: String
    private lateinit var image: String
    private lateinit var rating: RateModel

    fun getId(): Int {
        return id
    }

    fun getTitle(): String {
        return title
    }

    fun getPrice(): String {
        return price
    }

    fun getDescription(): String {
        return description
    }

    fun getCategory(): String {
        return category
    }

    fun getImage(): String {
        return image
    }

    // Rating a problem hote pare

    fun getRating(): RateModel {
        return rating
    }
}