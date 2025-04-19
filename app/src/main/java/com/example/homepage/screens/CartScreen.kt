package com.example.homepage.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.homepage.viewmodel.ProductViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.input.KeyboardType
import com.example.homepage.ui.theme.TextColor

@Composable
fun CartScreen(
    viewModel: ProductViewModel = viewModel()
) {
    var showCheckout by remember { mutableStateOf(false) }
    val cartItems by viewModel.cartItems.collectAsState()

    if (showCheckout) {
        CheckoutScreen(onBack = { showCheckout = false })
    } else {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("🛒 Your Cart", style = MaterialTheme.typography.headlineMedium, color = TextColor)

            if (cartItems.isEmpty()) {
                Spacer(modifier = Modifier.height(16.dp))
                Text("Your cart is empty.")
            } else {
                cartItems.forEach { item ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(8.dp)
                        ) {
                            AsyncImage(
                                model = item.image,
                                contentDescription = item.title,
                                modifier = Modifier.size(80.dp)
                            )
                            Spacer(modifier = Modifier.width(12.dp))
                            Column(Modifier.weight(1f)) {
                                Text(item.title, style = MaterialTheme.typography.bodyMedium)
                                Text("$${item.price}", style = MaterialTheme.typography.titleMedium)
                            }
                            Spacer(modifier = Modifier.width(8.dp))
                            Button(
                                onClick = { viewModel.removeFromCart(item.id) },
                                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
                            ) {
                                Text("Remove")
                            }
                        }
                    }
                }

                val totalPrice = cartItems.sumOf { it.price.toDouble() }

                Spacer(modifier = Modifier.height(16.dp))
                Text("Total: $${"%.2f".format(totalPrice)}", style = MaterialTheme.typography.bodyMedium)

                Spacer(modifier = Modifier.height(8.dp))
                Button(
                    onClick = { showCheckout = true },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Proceed to Checkout")
                }
            }
        }
    }
}

@Composable
fun CheckoutScreen(onBack: () -> Unit) {
    var cardNumber by remember { mutableStateOf("") }
    var expirationDate by remember { mutableStateOf("") }
    var cvv by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }
    var name by remember { mutableStateOf("") }

    Column(modifier = Modifier.padding(16.dp)) {
        Text("💳 Checkout", style = MaterialTheme.typography.headlineMedium, color = TextColor)
        Spacer(modifier = Modifier.height(16.dp))

        // Card Number
        OutlinedTextField(
            value = cardNumber,
            onValueChange = { cardNumber = it },
            label = { Text("Card Number") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Expiration Date
        OutlinedTextField(
            value = expirationDate,
            onValueChange = { expirationDate = it },
            label = { Text("Expiration Date (MM/YY)") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(8.dp))

        // CVV
        OutlinedTextField(
            value = cvv,
            onValueChange = { cvv = it },
            label = { Text("CVV") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Password
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Password),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Address
        OutlinedTextField(
            value = address,
            onValueChange = { address = it },
            label = { Text("Address") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = false
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Name
        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Name on Card") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Proceed Payment Button (Mock Action)
        Button(
            onClick = {
                // You can mock some action here like showing a confirmation, etc.
                // For now, it's just a mock action.
                println("Payment processed for $name")
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Proceed to Payment")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Back to Cart Button
        Button(
            onClick = onBack,
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary)
        ) {
            Text("Back to Cart")
        }
    }
}
