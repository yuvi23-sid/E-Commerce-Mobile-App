package com.example.homepage.network

import com.example.homepage.model.Product
import retrofit2.http.GET

interface FakeStoreApi {
    @GET("products")
    suspend fun getProducts(): List<Product>
}