package com.example.homepage.mainlayout

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.DrawerState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import kotlinx.coroutines.CoroutineScope
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.Text
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.ExperimentalMaterial3Api
import kotlinx.coroutines.launch
import com.example.homepage.Footer

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainLayout(
    navController: NavHostController,
    drawerState: DrawerState,
    scope: CoroutineScope,
    content: @Composable (PaddingValues) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("The Tech Hub") },
                navigationIcon = {
                    IconButton(onClick = {
                        scope.launch { drawerState.open() }
                    }) {
                        Icon(Icons.Default.Menu, contentDescription = "Menu")
                    }
                },
                actions = {
                    IconButton(onClick = {
                        navController.navigate("cart")
                    }) {
                        Icon(Icons.Default.ShoppingCart, contentDescription = "Cart")
                    }
                }
            )
        },
        bottomBar = { Footer() }
    ) { padding ->
        content(padding)
    }
}

// TODO: Implementation
//First the import import com.example.homepage.mainlayout.MainLayout
//Second inside the composable
// @Composable
//fun ElectronicsScreen(
//    navController: NavHostController,
//    drawerState: DrawerState,
//    scope: CoroutineScope
//) {
//    MainLayout(navController, drawerState, scope) { padding ->
//        Column(modifier = Modifier
//            .padding(padding)
//            .padding(16.dp)) {
//            Text("Category: Electronics")
//            Text("Browse all electronic gadgets here.")
//            // Grid or list here
//        }
//    }
//}