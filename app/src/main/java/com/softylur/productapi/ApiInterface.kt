package com.softylur.productapi

import com.softylur.productapi.model.ProductModel
import retrofit2.Call
import retrofit2.http.GET

interface ApiInterface {

    @GET("/products")
    fun getAllProducts(): Call<List<ProductModel>>

    @GET("/products/categories")
    fun getCategories(): Call<Array<String>>

    @GET("/products/category/jewelery")
    fun getJeweleryProducts(): Call<List<ProductModel>>

    @GET("/products/category/electronics")
    fun getElectronicsProducts(): Call<List<ProductModel>>

    @GET("/products/category/men's clothing")
    fun getMenClothingProducts(): Call<List<ProductModel>>

    @GET("/products/category/women's clothing")
    fun getWomenClothingProducts(): Call<List<ProductModel>>

}