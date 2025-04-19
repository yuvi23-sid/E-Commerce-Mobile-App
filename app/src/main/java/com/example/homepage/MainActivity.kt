package com.example.homepage

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.homepage.ui.theme.HomePageTheme
import com.example.homepage.viewmodel.ProductViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.homepage.mainlayout.MainLayout
import com.example.homepage.screens.CartScreen
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import com.example.homepage.screens.ProductCategoryScreen
import kotlin.String as String

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            HomeScreen()
        }
    }
}


@Composable
fun HomeScreen() {
    HomePageTheme {
        val navController = rememberNavController() // Create a NavController
        val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
        val scope = rememberCoroutineScope()
        val sharedViewModel: ProductViewModel = viewModel()

        ModalNavigationDrawer(
            drawerState = drawerState,
            drawerContent = {
                ModalDrawerSheet {
                    DrawerContent(navController, drawerState, scope)
                }
            }
        ) {
            MainLayout(navController = navController, drawerState = drawerState, scope = scope) { padding ->
                NavHost(
                    navController = navController,
                    startDestination = "home",
                    modifier = Modifier.padding(padding)
                ) {
                    composable("home") {
                        HomeContent(viewModel = sharedViewModel, navController = navController)
                    }

                    composable("products/electronics") {
                        ProductCategoryScreen(
                            category = "electronics",
                            viewModel = sharedViewModel,
                            onAddToCart = { sharedViewModel.addToCart(it) }
                        )
                    }

                    // Repeat similarly for other categories
                    composable("products/jewelery") {
                        ProductCategoryScreen(
                            category = "jewelery",
                            viewModel = sharedViewModel,
                            onAddToCart = { sharedViewModel.addToCart(it) }
                        )
                    }

                    composable("products/men") {
                        ProductCategoryScreen(
                            category = "men's clothing",
                            viewModel = sharedViewModel,
                            onAddToCart = { sharedViewModel.addToCart(it) }
                        )
                    }

                    composable("products/women") {
                        ProductCategoryScreen(
                            category = "women's clothing",
                            viewModel = sharedViewModel,
                            onAddToCart = { sharedViewModel.addToCart(it) }
                        )
                    }

                    composable("cart") {
                        CartScreen(viewModel = sharedViewModel)
                    }
                }
            }
        }
    }
}

@Composable
fun DrawerContent(
    navController: NavHostController,
    drawerState: DrawerState,
    scope: CoroutineScope
) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text("Menu", style = MaterialTheme.typography.titleMedium)
        HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))

        TextButton(onClick = {
            scope.launch { drawerState.close() }
            navController.navigate("home")
        }) {
            Text("Home")
        }

        Text("Products", style = MaterialTheme.typography.titleMedium)
        val categories = listOf(
            "Electronics" to "products/electronics",
            "Jewelery" to "products/jewelery",
            "Men's Clothing" to "products/men",
            "Women's Clothing" to "products/women"
        )

        categories.forEach { (label, route) ->
            TextButton(onClick = {
                scope.launch { drawerState.close() }
                navController.navigate(route)
            }) {
                Text(label)
            }
        }
    }
}

@Composable
fun HomeContent(
    modifier: Modifier = Modifier,
    viewModel: ProductViewModel,
    navController: NavHostController
) {
    val query = remember { mutableStateOf("") }
    val products by viewModel.products.collectAsState()
    val filteredProducts = products.filter {
        it.title.contains(query.value, ignoreCase = true)
    }

    Column(
        modifier = modifier
            .verticalScroll(rememberScrollState())
            .fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Welcome!,", style = MaterialTheme.typography.headlineMedium, color = colorScheme.onBackground)
            Text("Powering Your Digital World, One Gadget at a Time.", style = MaterialTheme.typography.bodyMedium, color = colorScheme.onBackground)

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = query.value,
                onValueChange = { newQuery -> query.value = newQuery },
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text("Search for products...") }
            )

            // Show suggestions based on search
            Spacer(modifier = Modifier.height(16.dp))
            Text("Suggested Products", style = MaterialTheme.typography.titleMedium)

            // Display filtered products as suggestions
            LazyRow {
                items(filteredProducts.size) { index ->
                    val product = filteredProducts[index]
                    Card(
                        modifier = Modifier
                            .padding(8.dp)
                            .size(160.dp),
                        colors = CardDefaults.cardColors(containerColor = colorScheme.surface)
                    ) {
                        Column(
                            modifier = Modifier.padding(8.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            AsyncImage(
                                model = product.image,
                                contentDescription = product.title,
                                modifier = Modifier
                                    .size(80.dp)
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
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
            Text("Categories", style = MaterialTheme.typography.titleMedium)
            CategoriesRow(navController)
        }
        BuyNowSection(
            modifier = Modifier.padding(12.dp),
            navController = navController
        )
    }
}

@Composable
fun CategoriesRow(navController: NavHostController) {
    val categories = listOf(
        "Electronics" to "products/electronics",
        "Jewelery" to "products/jewelery",
        "Men's Clothing" to "products/men",
        "Women's Clothing" to "products/women"
    )

    LazyRow {
        items(categories.size) { index ->
            val category = categories[index]
            Chip(
                text = category.first,
                onClick = {
                    navController.navigate(category.second)
                }
            )
        }
    }
}

@Composable
fun Chip(text: String, onClick: () -> Unit) {
    Card(
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = colorScheme.surface),
        modifier = Modifier
            .padding(8.dp)
            .clickable(onClick = onClick)
    ) {
        Text(
            text = text,
            modifier = Modifier.padding(12.dp),
            style = MaterialTheme.typography.bodySmall,
            color = colorScheme.onSurface
        )
    }
}

@Composable
fun BuyNowSection(modifier: Modifier = Modifier, navController: NavHostController) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(200.dp)
    ) {
        AsyncImage(
            model = "https://images.unsplash.com/photo-1524289286702-f07229da36f5?q=80&w=2070&auto=format&fit=crop",
            contentDescription = "Tech Deals",
            contentScale = ContentScale.Crop, // fill the box
            modifier = Modifier
                .matchParentSize()
                .clip(RoundedCornerShape(12.dp))
        )

        Column(
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .padding(end = 24.dp)
                .background(
                    colorScheme.surface.copy(alpha = 0.7f),
                    shape = RoundedCornerShape(10.dp)
                )
                .padding(16.dp),
            horizontalAlignment = Alignment.End
        ) {
            Text(
                text = "Don't miss our latest tech deals!",
                style = MaterialTheme.typography.labelMedium,
                color = colorScheme.onSurface
            )
            Spacer(modifier = Modifier.height(8.dp))
            Button(
                onClick = {
                    navController.navigate("products/electronics")
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorScheme.primary,
                    contentColor = colorScheme.onPrimary
                )
            ) {
                Text("Shop Now")
            }
        }
    }
}

@Composable
fun Footer() {
    BottomAppBar(
        containerColor = colorScheme.primary
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "© 2025 The Tech Hub",
                style = MaterialTheme.typography.bodyMedium,
                color = colorScheme.onPrimary
            )
            Text(
                text = "Empowering your digital life",
                style = MaterialTheme.typography.labelMedium,
                color = colorScheme.onPrimary.copy(alpha = 0.8f)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    HomePageTheme {
        HomeScreen()
    }
}