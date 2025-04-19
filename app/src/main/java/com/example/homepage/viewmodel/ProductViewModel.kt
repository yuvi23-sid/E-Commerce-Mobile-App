package com.example.homepage.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.homepage.model.CartItem
import com.example.homepage.model.Product
import com.example.homepage.network.RetrofitClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ProductViewModel : ViewModel() {

    private val _products = MutableStateFlow<List<Product>>(emptyList())
    val products: StateFlow<List<Product>> = _products

    private val _cartItems = MutableStateFlow<List<CartItem>>(emptyList())
    val cartItems: StateFlow<List<CartItem>> = _cartItems

    init {
        fetchProducts()
    }

    private fun fetchProducts() {
        viewModelScope.launch {
            try {
                val fetchedProducts = RetrofitClient.api.getProducts()
                _products.value = fetchedProducts
            } catch (e: Exception) {
                // Handle error (you might want to show an error state in your UI)
                e.printStackTrace()
            }
        }
    }

    fun addToCart(productId: Int) {
        val product = _products.value.find { it.id == productId } ?: return
        val newItem = CartItem(
            id = product.id,
            title = product.title,
            price = product.price,
            image = product.image
        )
        _cartItems.value += newItem
    }

    fun removeFromCart(productId: Int) {
        _cartItems.value = _cartItems.value.filterNot { it.id == productId }
    }
}