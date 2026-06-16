package com.example.bulkyo.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
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
fun ChatListScreen(onChatClick: (String) -> Unit, onBack: () -> Unit) {
    Scaffold(
        containerColor = Color.Transparent,
        topBar = {
            TopAppBar(
                title = { Text("Messages", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBack) { Icon(Icons.Default.ArrowBack, null) }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent)
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier.padding(padding).fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            val chats = listOf(
                "Manufacturer Support" to "Your order #1234 is confirmed.",
                "Bulk Logistics" to "Driver is 5 mins away from warehouse.",
                "Catering Manager" to "Menu for Saturday event updated.",
                "Billing Dept" to "Invoice for June is ready.",
                "Kitchen Staff" to "Inventory levels are low for salt."
            )
            items(chats) { chat ->
                Card(
                    onClick = { onChatClick(chat.first) },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.1f))
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier.size(50.dp).clip(CircleShape).background(Color.White.copy(alpha = 0.2f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(Icons.Default.Person, null, tint = Color.White)
                        }
                        Spacer(modifier = Modifier.width(16.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text(chat.first, fontWeight = FontWeight.Bold, color = Color.White)
                            Text(chat.second, color = Color.White.copy(alpha = 0.7f), fontSize = 13.sp, maxLines = 1)
                        }
                        Text("12:30 PM", color = Color.White.copy(alpha = 0.5f), fontSize = 11.sp)
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun IndividualChatScreen(chatId: String, onBack: () -> Unit) {
    Scaffold(
        containerColor = Color.Transparent,
        topBar = {
            TopAppBar(
                title = { 
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(modifier = Modifier.size(32.dp).clip(CircleShape).background(Color.White.copy(alpha = 0.2f)), contentAlignment = Alignment.Center) {
                            Icon(Icons.Default.Person, null, modifier = Modifier.size(16.dp), tint = Color.White)
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(chatId, fontSize = 18.sp)
                    }
                },
                navigationIcon = {
                    IconButton(onClick = onBack) { Icon(Icons.Default.ArrowBack, null) }
                },
                actions = {
                    IconButton(onClick = {}) { Icon(Icons.Default.Call, null) }
                    IconButton(onClick = {}) { Icon(Icons.Default.Videocam, null) }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent)
            )
        },
        bottomBar = {
            BottomAppBar(
                containerColor = Color.Black.copy(alpha = 0.3f),
                modifier = Modifier.height(80.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = {}) { Icon(Icons.Default.Add, null, tint = Color.White) }
                    OutlinedTextField(
                        value = "",
                        onValueChange = {},
                        placeholder = { Text("Type a message...", color = Color.White.copy(alpha = 0.5f)) },
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(24.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            unfocusedBorderColor = Color.White.copy(alpha = 0.3f),
                            focusedBorderColor = Color.White,
                            cursorColor = Color.White
                        )
                    )
                    IconButton(onClick = {}) { Icon(Icons.Default.Send, null, tint = Color.White) }
                }
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier.padding(padding).fillMaxSize().padding(16.dp),
            verticalArrangement = Arrangement.Bottom
        ) {
            ChatBubble("Hello! How can I help with your catering order?", isMe = false)
            ChatBubble("I need to add 50 more servings to the Premium Buffet Set.", isMe = true)
            ChatBubble("Sure, let me check the GNN prediction for your supply needs...", isMe = false)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotificationsScreen(onBack: () -> Unit) {
    Scaffold(
        containerColor = Color.Transparent,
        topBar = {
            TopAppBar(
                title = { Text("Notifications", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBack) { Icon(Icons.Default.ArrowBack, null) }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent)
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier.padding(padding).fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            val notifications = listOf(
                Triple("Order Shipped", "Your bulk order for 'Royal Buffet' has been dispatched.", Icons.Default.LocalShipping),
                Triple("Price Drop Alert", "Beverage Supply packs are now 15% off!", Icons.Default.TrendingDown),
                Triple("New AI Insights", "GNN model suggests restocking 'Premium Cutlery' by Friday.", Icons.Default.AutoAwesome),
                Triple("System Update", "New features added to the Business Reports dashboard.", Icons.Default.SystemUpdate),
                Triple("Payment Received", "Successfully processed payment for Invoice #8821.", Icons.Default.CheckCircle)
            )
            items(notifications) { notification ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.1f))
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier.size(48.dp).clip(CircleShape).background(Color(0xFFBB86FC).copy(alpha = 0.2f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(notification.third, null, tint = Color(0xFFBB86FC))
                        }
                        Spacer(modifier = Modifier.width(16.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text(notification.first, fontWeight = FontWeight.Bold, color = Color.White)
                            Text(notification.second, color = Color.White.copy(alpha = 0.7f), fontSize = 13.sp)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ChatBubble(message: String, isMe: Boolean) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
        horizontalArrangement = if (isMe) Arrangement.End else Arrangement.Start
    ) {
        Card(
            shape = RoundedCornerShape(
                topStart = 16.dp,
                topEnd = 16.dp,
                bottomStart = if (isMe) 16.dp else 4.dp,
                bottomEnd = if (isMe) 4.dp else 16.dp
            ),
            colors = CardDefaults.cardColors(
                containerColor = if (isMe) Color(0xFFBB86FC).copy(alpha = 0.8f) else Color.White.copy(alpha = 0.2f)
            )
        ) {
            Text(message, modifier = Modifier.padding(12.dp), color = Color.White, fontSize = 14.sp)
        }
    }
}
