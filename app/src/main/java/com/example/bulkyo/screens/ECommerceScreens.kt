package com.example.bulkyo.screens

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryListScreen(onCategoryClick: (String) -> Unit, onBack: () -> Unit) {
    Scaffold(
        containerColor = Color.Transparent,
        topBar = {
            TopAppBar(
                title = { Text("Catering Categories") },
                navigationIcon = {
                    IconButton(onClick = onBack) { Icon(Icons.Default.ArrowBack, null) }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent)
            )
        }
    ) { padding ->
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier.padding(padding).padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            val categories = listOf("Wedding Buffet", "Corporate Lunch", "Premium Cutlery", "Staffing Services", "Dessert Bar", "Equipment Rental", "Beverage Supply", "Floral Decor")
            items(categories) { category ->
                Card(
                    onClick = { onCategoryClick(category) },
                    modifier = Modifier.height(120.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
                ) {
                    Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
                        Text(category, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartScreen(onCheckout: () -> Unit, onBack: () -> Unit) {
    Scaffold(
        containerColor = Color.Transparent,
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("My Cart") },
                navigationIcon = {
                    IconButton(onClick = onBack) { Icon(Icons.Default.ArrowBack, null) }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent)
            )
        },
        bottomBar = {
            Surface(
                shadowElevation = 8.dp,
                color = MaterialTheme.colorScheme.surface.copy(alpha = 0.9f)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth().padding(24.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text("Total", color = Color.Gray)
                        Text("₹450.00", style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold)
                    }
                    Button(
                        onClick = onCheckout,
                        modifier = Modifier.height(56.dp).width(160.dp),
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Text("Checkout")
                    }
                }
            }
        }
    ) { padding ->
        Column(modifier = Modifier.padding(padding).padding(16.dp).verticalScroll(rememberScrollState())) {
            repeat(3) {
                CartItem()
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

@Composable
fun CartItem() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.1f))
    ) {
        Row(modifier = Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
            Box(modifier = Modifier.size(80.dp).clip(RoundedCornerShape(12.dp)).background(Color.White.copy(alpha = 0.2f))) {
                Icon(Icons.Default.Restaurant, null, modifier = Modifier.align(Alignment.Center), tint = Color.White)
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text("Bulk Buffet Pack", fontWeight = FontWeight.Bold, color = Color.White)
                Text("100 Servings", color = Color.White.copy(alpha = 0.7f), fontSize = 12.sp)
                Text("₹450.00", fontWeight = FontWeight.ExtraBold, color = Color(0xFFBB86FC))
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = {}) { Icon(Icons.Default.Remove, null, tint = Color.White) }
                Text("1", fontWeight = FontWeight.Bold, color = Color.White)
                IconButton(onClick = {}) { Icon(Icons.Default.Add, null, tint = Color.White) }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(onBack: () -> Unit, onItemClick: (String) -> Unit) {
    var query by remember { mutableStateOf("") }
    Scaffold(
        containerColor = Color.Transparent
    ) { padding ->
        Column(modifier = Modifier.padding(padding).padding(16.dp)) {
            SearchBar(
                query = query,
                onQueryChange = { query = it },
                onSearch = {},
                active = false,
                onActiveChange = {},
                placeholder = { Text("Search catering services...") },
                leadingIcon = { IconButton(onClick = onBack) { Icon(Icons.Default.ArrowBack, null, tint = Color.White) } },
                trailingIcon = { Icon(Icons.Default.Search, null, tint = Color.White) },
                colors = SearchBarDefaults.colors(containerColor = Color.White.copy(alpha = 0.1f)),
                modifier = Modifier.fillMaxWidth()
            ) {}
            
            Spacer(modifier = Modifier.height(24.dp))
            Text("Popular Searches", fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleMedium, color = Color.White)
            Spacer(modifier = Modifier.height(12.dp))
            val popular = listOf("Wedding Thali", "Cocktail Party", "Corporate High Tea", "Dessert Buffet")
            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                popular.forEach { tag ->
                    SuggestionChip(
                        onClick = { query = tag },
                        label = { Text(tag, color = Color.White) },
                        border = BorderStroke(1.dp, Color.White.copy(alpha = 0.3f)),
                        colors = SuggestionChipDefaults.suggestionChipColors(containerColor = Color.Transparent)
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(32.dp))
            Text("Search Results", fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleMedium, color = Color.White)
            LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp), modifier = Modifier.padding(top = 16.dp)) {
                val results = listOf("Traditional South Indian", "Luxury Continental", "North Indian Feast")
                items(results) { result ->
                    Card(
                        onClick = { onItemClick(result) },
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.05f))
                    ) {
                        Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.Restaurant, null, tint = Color.White.copy(alpha = 0.5f))
                            Spacer(modifier = Modifier.width(16.dp))
                            Text(result, color = Color.White)
                            Spacer(modifier = Modifier.weight(1f))
                            Icon(Icons.Default.ArrowForwardIos, null, tint = Color.White.copy(alpha = 0.3f), modifier = Modifier.size(14.dp))
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class, ExperimentalMaterial3Api::class)
@Composable
fun FavoritesScreen(onBack: () -> Unit, onItemClick: (String) -> Unit) {
    Scaffold(
        containerColor = Color.Transparent,
        topBar = {
            TopAppBar(
                title = { Text("My Favorites") },
                navigationIcon = {
                    IconButton(onClick = onBack) { Icon(Icons.Default.ArrowBack, null) }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent)
            )
        }
    ) { padding ->
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier.padding(padding).padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            val favorites = listOf(
                "Royal Buffet" to "₹45,000",
                "Classic Lunch" to "₹15,000",
                "High Tea" to "₹8,000"
            )
            items(favorites) { item ->
                Card(
                    onClick = { onItemClick(item.first) },
                    modifier = Modifier.height(180.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.1f))
                ) {
                    Box {
                        Column(modifier = Modifier.fillMaxSize().padding(12.dp)) {
                            Box(
                                modifier = Modifier.fillMaxWidth().height(100.dp).clip(RoundedCornerShape(12.dp)).background(Color.White.copy(alpha = 0.1f)),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(Icons.Default.Restaurant, null, tint = Color.White.copy(alpha = 0.3f), modifier = Modifier.size(48.dp))
                            }
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(item.first, fontWeight = FontWeight.Bold, color = Color.White)
                            Text(item.second, color = Color(0xFFBB86FC), fontSize = 14.sp)
                        }
                        IconButton(
                            onClick = {},
                            modifier = Modifier.align(Alignment.TopEnd).padding(4.dp)
                        ) {
                            Icon(Icons.Default.Favorite, null, tint = Color.Red)
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ItemDetailsScreen(itemId: String, onBack: () -> Unit, onAddToCart: () -> Unit) {
    Scaffold(
        containerColor = Color.Transparent,
        topBar = {
            TopAppBar(
                title = { Text("Product Details") },
                navigationIcon = {
                    IconButton(onClick = onBack) { Icon(Icons.Default.ArrowBack, null) }
                },
                actions = {
                    IconButton(onClick = {}) { Icon(Icons.Default.FavoriteBorder, null) }
                    IconButton(onClick = {}) { Icon(Icons.Default.Share, null) }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent)
            )
        },
        bottomBar = {
            Surface(
                color = Color.White.copy(alpha = 0.15f),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.padding(16.dp).fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text("Wholesale Price", color = Color.White.copy(alpha = 0.7f), fontSize = 12.sp)
                        Text("₹120.00", color = Color.White, style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold)
                    }
                    Button(
                        onClick = onAddToCart,
                        modifier = Modifier.height(56.dp).weight(1.5f),
                        shape = RoundedCornerShape(16.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color.White, contentColor = Color(0xFF6200EE))
                    ) {
                        Text("Add to Cart", fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            // Product Image Placeholder
            Card(
                modifier = Modifier.fillMaxWidth().height(300.dp).padding(16.dp),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.1f))
            ) {
                Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
                    Icon(Icons.Default.ShoppingCart, contentDescription = null, modifier = Modifier.size(100.dp), tint = Color.White.copy(alpha = 0.3f))
                }
            }

            Column(modifier = Modifier.padding(24.dp)) {
                Text(
                    text = "Premium Buffet Service Pack",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                Spacer(modifier = Modifier.height(8.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.Star, contentDescription = null, tint = Color(0xFFFFD700), modifier = Modifier.size(20.dp))
                    Text(" 4.9 (250+ Verified Events)", color = Color.White.copy(alpha = 0.8f))
                }
                
                Spacer(modifier = Modifier.height(24.dp))
                Card(
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFBB86FC).copy(alpha = 0.15f)),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.AutoAwesome, contentDescription = null, tint = Color(0xFFBB86FC))
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(
                            "AI Prediction: Best value for 50-200 guests based on your recent activity.",
                            color = Color.White,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))
                Text("Bulk Service Options", fontWeight = FontWeight.Bold, color = Color.White, fontSize = 18.sp)
                Spacer(modifier = Modifier.height(12.dp))
                
                BulkPriceRow("50-100 Servings", "₹450.00/pack")
                BulkPriceRow("101-300 Servings", "₹400.00/pack")
                BulkPriceRow("300+ Servings", "₹350.00/pack")

                Spacer(modifier = Modifier.height(24.dp))
                Text("Service Description", fontWeight = FontWeight.Bold, color = Color.White, fontSize = 18.sp)
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "A complete smart catering solution optimized by our Graph Neural Networks. This pack includes full buffet setup, premium cutlery, and AI-assisted menu management. Sourced from top-tier wholesale suppliers for maximum reliability.",
                    color = Color.White.copy(alpha = 0.7f),
                    lineHeight = 22.sp
                )
                
                Spacer(modifier = Modifier.height(32.dp))
            }
        }
    }
}

@Composable
fun BulkPriceRow(range: String, price: String) {
    Card(
        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.05f)),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(modifier = Modifier.padding(16.dp), horizontalArrangement = Arrangement.SpaceBetween) {
            Text(range, color = Color.White)
            Text(price, fontWeight = FontWeight.Bold, color = Color.White)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryDetailsScreen(categoryName: String, onBack: () -> Unit, onItemClick: (String) -> Unit) {
    Scaffold(
        containerColor = Color.Transparent,
        topBar = {
            TopAppBar(
                title = { Text(categoryName) },
                navigationIcon = {
                    IconButton(onClick = onBack) { Icon(Icons.Default.ArrowBack, null) }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent)
            )
        }
    ) { padding ->
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier.padding(padding).padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            val menuItems = listOf(
                "Royal Buffet" to "₹45,000",
                "Classic Lunch" to "₹15,000",
                "High Tea" to "₹8,000",
                "Evening Snacks" to "₹5,000",
                "Dessert Mix" to "₹12,000",
                "Beverage Pack" to "₹4,000"
            )
            items(menuItems) { item ->
                Card(
                    onClick = { onItemClick(item.first) },
                    modifier = Modifier.height(180.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.1f))
                ) {
                    Column(modifier = Modifier.fillMaxSize().padding(12.dp)) {
                        Box(
                            modifier = Modifier.fillMaxWidth().height(100.dp).clip(RoundedCornerShape(12.dp)).background(Color.White.copy(alpha = 0.1f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(Icons.Default.Restaurant, null, tint = Color.White.copy(alpha = 0.3f), modifier = Modifier.size(48.dp))
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(item.first, fontWeight = FontWeight.Bold, color = Color.White)
                        Text(item.second, color = Color(0xFFBB86FC), fontSize = 14.sp)
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CheckoutScreen(onAddPayment: () -> Unit, onBack: () -> Unit, onOrderPlaced: () -> Unit) {
    Scaffold(
        containerColor = Color.Transparent,
        topBar = {
            TopAppBar(
                title = { Text("Checkout") },
                navigationIcon = {
                    IconButton(onClick = onBack) { Icon(Icons.Default.ArrowBack, null) }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent)
            )
        },
        bottomBar = {
            Button(
                onClick = onOrderPlaced,
                modifier = Modifier.fillMaxWidth().padding(16.dp).height(56.dp),
                shape = RoundedCornerShape(16.dp)
            ) {
                Text("Place Order - ₹450.00")
            }
        }
    ) { padding ->
        Column(modifier = Modifier.padding(padding).padding(16.dp).verticalScroll(rememberScrollState())) {
            Text("Shipping Address", fontWeight = FontWeight.Bold, color = Color.White)
            Card(
                modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.1f))
            ) {
                Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.LocationOn, null, tint = Color.White)
                    Spacer(modifier = Modifier.width(16.dp))
                    Text("123 BulkyO Lane, Catering Hub, Mumbai", color = Color.White)
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
            Text("Payment Method", fontWeight = FontWeight.Bold, color = Color.White)
            Card(
                modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.1f)),
                onClick = onAddPayment
            ) {
                Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.AddCard, null, tint = Color.White)
                    Spacer(modifier = Modifier.width(16.dp))
                    Text("Add New Card", color = Color.White)
                    Spacer(modifier = Modifier.weight(1f))
                    Icon(Icons.Default.ChevronRight, null, tint = Color.White.copy(alpha = 0.5f))
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
            Text("Order Summary", fontWeight = FontWeight.Bold, color = Color.White)
            repeat(2) {
                Row(modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text("Bulk Buffet Pack x1", color = Color.White.copy(alpha = 0.7f))
                    Text("₹450.00", color = Color.White)
                }
            }
            Divider(color = Color.White.copy(alpha = 0.2f), modifier = Modifier.padding(vertical = 8.dp))
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text("Total Amount", fontWeight = FontWeight.Bold, color = Color.White)
                Text("₹450.00", fontWeight = FontWeight.Bold, color = Color(0xFFBB86FC), fontSize = 18.sp)
            }
        }
    }
}

@Composable
fun PaymentSuccessScreen(onContinue: () -> Unit) {
    Box(modifier = Modifier.fillMaxSize().padding(24.dp), contentAlignment = Alignment.Center) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(
                Icons.Default.CheckCircle,
                contentDescription = null,
                modifier = Modifier.size(100.dp),
                tint = Color(0xFF4CAF50)
            )
            Spacer(modifier = Modifier.height(24.dp))
            Text("Order Placed Successfully!", style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold, color = Color.White)
            Text("Your bulk catering request is being processed by our GNN optimizer.", color = Color.White.copy(alpha = 0.7f), textAlign = androidx.compose.ui.text.style.TextAlign.Center)
            Spacer(modifier = Modifier.height(48.dp))
            Button(
                onClick = onContinue,
                modifier = Modifier.fillMaxWidth().height(56.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.White, contentColor = Color(0xFF6200EE))
            ) {
                Text("Back to Home", fontWeight = FontWeight.Bold)
            }
        }
    }
}
