package com.example.homepage.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.homepage.viewmodel.ProductViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.ui.Alignment


@Composable
fun ProductCategoryScreen(
    category: String,
    viewModel: ProductViewModel = viewModel(),
    onAddToCart: (productId: Int) -> Unit = {}
) {
    val products by viewModel.products.collectAsState()
    val filteredProducts = products.filter { it.category.equals(category, ignoreCase = true) }

    Column(modifier = Modifier.padding(16.dp)) {
        Text("Category: $category", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(12.dp))

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(8.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            items(filteredProducts) { product ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                ) {
                    Column(
                        modifier = Modifier
                            .padding(8.dp)
                            .fillMaxWidth()
                            .wrapContentHeight(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        AsyncImage(
                            model = product.image,
                            contentDescription = product.title,
                            modifier = Modifier
                                .size(100.dp)
                                .padding(bottom = 4.dp)
                        )
                        Text(
                            text = product.title.take(30) + if (product.title.length > 30) "…" else "",
                            style = MaterialTheme.typography.bodySmall
                        )
                        Text(
                            text = "$${product.price}",
                            style = MaterialTheme.typography.titleSmall
                        )
                        Button(
                            onClick = { onAddToCart(product.id) },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(40.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.primary,
                                contentColor = MaterialTheme.colorScheme.onPrimary
                            ),
                            shape = RoundedCornerShape(12.dp),
                            elevation = ButtonDefaults.buttonElevation(4.dp)
                        ) {
                            Text("Add to Cart", style = MaterialTheme.typography.bodyMedium)
                        }
                    }
                }
            }
        }
    }
}