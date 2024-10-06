package com.softylur.productapi

import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance{
    // FakeStoreAPI er domain name ata.. Future a Real domain name add korte hobe
    private const val BASE_URL ="https://fakestoreapi.com"

    // Retrofit instance create kora
    fun getInstance(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .build()
    }


    // This is just a test in Design Branch




    // Another test in Design Branch
    //V2 from Design Branch
}