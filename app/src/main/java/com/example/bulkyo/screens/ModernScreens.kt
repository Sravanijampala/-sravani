package com.example.bulkyo.screens

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ModernHomeDashboard(
    onSearch: () -> Unit,
    onCategoryClick: (String) -> Unit,
    onItemClick: (String) -> Unit,
    onProfileClick: () -> Unit,
    onCartClick: () -> Unit,
    onNotificationsClick: () -> Unit,
    onSavedClick: () -> Unit
) {
    Scaffold(
        containerColor = Color.Transparent,
        topBar = {
            TopAppBar(
                title = { Text("BulkyO", fontWeight = FontWeight.Bold) },
                actions = {
                    IconButton(onClick = onNotificationsClick) { Icon(Icons.Default.Notifications, contentDescription = null) }
                    IconButton(onClick = onCartClick) { Icon(Icons.Default.ShoppingCart, contentDescription = null) }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent
                )
            )
        },
        bottomBar = {
            NavigationBar {
                NavigationBarItem(selected = true, onClick = {}, icon = { Icon(Icons.Default.Home, null) }, label = { Text("Home") })
                NavigationBarItem(selected = false, onClick = onSearch, icon = { Icon(Icons.Default.Search, null) }, label = { Text("Search") })
                NavigationBarItem(selected = false, onClick = onSavedClick, icon = { Icon(Icons.Default.Favorite, null) }, label = { Text("Saved") })
                NavigationBarItem(selected = false, onClick = onProfileClick, icon = { Icon(Icons.Default.Person, null) }, label = { Text("Profile") })
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
        ) {
            // Hero Banner
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(220.dp)
                    .padding(16.dp),
                shape = RoundedCornerShape(24.dp)
            ) {
                AnimatedGradientBackground(
                    color1Start = MaterialTheme.colorScheme.primary,
                    color1End = MaterialTheme.colorScheme.secondary,
                    color2Start = MaterialTheme.colorScheme.tertiary,
                    color2End = MaterialTheme.colorScheme.primary
                ) {
                    Box(
                        modifier = Modifier.fillMaxSize().padding(24.dp),
                        contentAlignment = Alignment.CenterStart
                    ) {
                        Column {
                            Surface(
                                color = Color.White.copy(alpha = 0.2f),
                                shape = RoundedCornerShape(8.dp)
                            ) {
                                Text(
                                    "AI-POWERED GNN",
                                    color = Color.White,
                                    fontSize = 10.sp,
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                                    fontWeight = FontWeight.Bold
                                )
                            }
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                "Smart Catering\nSolutions",
                                color = Color.White,
                                fontSize = 26.sp,
                                fontWeight = FontWeight.ExtraBold,
                                lineHeight = 32.sp
                            )
                            Text(
                                "Predictive ordering & menu optimization",
                                color = Color.White.copy(alpha = 0.8f),
                                fontSize = 12.sp
                            )
                        }
                    }
                }
            }

            // Categories Section
            SectionHeader(title = "Catering Categories", onSeeAll = { onCategoryClick("All") })
            LazyRow(contentPadding = PaddingValues(horizontal = 16.dp), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                items(listOf("Wedding", "Corporate", "Events", "Supplies", "Staffing")) { category ->
                    CategoryChip(category) { onCategoryClick(category) }
                }
            }

            // AI & Tools Section
            SectionHeader(title = "Smart Tools", onSeeAll = {})
            Row(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                ToolCard("AI Assistant", Icons.Default.AutoAwesome, Modifier.weight(1f)) { onItemClick("ai_assistant") }
                ToolCard("Reports", Icons.Default.BarChart, Modifier.weight(1f)) { onItemClick("reports") }
                ToolCard("Community", Icons.Default.Groups, Modifier.weight(1f)) { onItemClick("leaderboard") }
            }

            // Featured Products
            SectionHeader(title = "Smart Recommended Deals", onSeeAll = { onCategoryClick("Featured") })
            Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                val cateringItems = listOf(
                    Triple("Premium Buffet Set", "₹45,000", "Best for Weddings"),
                    Triple("Corporate Lunch Box", "₹250", "Save 20% on Bulk"),
                    Triple("Eco-Friendly Cutlery", "₹5", "Wholesale Only"),
                    Triple("Event Staffing (4h)", "₹2,500", "Verified Personnel")
                )
                cateringItems.forEachIndexed { index, item ->
                    ProductCard(
                        title = item.first,
                        price = item.second,
                        discount = item.third,
                        onClick = { onItemClick("catering_$index") },
                        onAddClick = { onCartClick() }
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                }
            }
        }
    }
}

@Composable
fun ToolCard(title: String, icon: androidx.compose.ui.graphics.vector.ImageVector, modifier: Modifier = Modifier, onClick: () -> Unit) {
    Card(
        onClick = onClick,
        modifier = modifier.height(100.dp),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.1f))
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(icon, null, tint = Color.White, modifier = Modifier.size(32.dp))
            Spacer(modifier = Modifier.height(8.dp))
            Text(title, color = Color.White, fontSize = 12.sp, fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
fun SectionHeader(title: String, onSeeAll: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(title, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold, color = Color.White)
        TextButton(onClick = onSeeAll) { Text("See All", color = Color(0xFFBB86FC)) }
    }
}

@Composable
fun CategoryChip(name: String, onClick: () -> Unit) {
    Card(
        onClick = onClick,
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.1f))
    ) {
        Box(modifier = Modifier.padding(horizontal = 20.dp, vertical = 12.dp)) {
            Text(name, fontWeight = FontWeight.Medium, color = Color.White)
        }
    }
}

@Composable
fun ProductCard(title: String, price: String, discount: String, onClick: () -> Unit, onAddClick: () -> Unit) {
    Card(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.1f)),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Row(modifier = Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .size(80.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color.White.copy(alpha = 0.2f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(Icons.Default.Restaurant, null, tint = Color.White.copy(alpha = 0.6f))
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(title, fontWeight = FontWeight.Bold, fontSize = 18.sp, color = Color.White)
                Text(price, color = Color(0xFFBB86FC), fontWeight = FontWeight.Bold)
                Surface(
                    color = Color(0xFFBB86FC).copy(alpha = 0.2f),
                    shape = RoundedCornerShape(4.dp)
                ) {
                    Text(
                        text = discount,
                        color = Color(0xFFE1BEE7),
                        fontSize = 12.sp,
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                    )
                }
            }
            IconButton(onClick = onAddClick) { Icon(Icons.Default.Add, contentDescription = null, tint = Color.White) }
        }
    }
}
