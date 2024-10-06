package com.softylur.productapi.model

class RateModel {
    private lateinit var rate: String
    private lateinit var count: String

    fun getRate(): String {
        return rate
    }

    fun getCount(): String {
        return count
    }

}