package com.example.bulkyo.screens

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(onBack: () -> Unit) {
    Scaffold(
        containerColor = Color.Transparent,
        topBar = {
            TopAppBar(
                title = { Text("Settings") },
                navigationIcon = {
                    IconButton(onClick = onBack) { Icon(Icons.Default.ArrowBack, null) }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent)
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            SettingsGroup(title = "Account") {
                SettingsItem("Edit Profile", Icons.Default.Person) {}
                SettingsItem("Change Password", Icons.Default.Lock) {}
                SettingsItem("Privacy", Icons.Default.PrivacyTip) {}
            }
            
            SettingsGroup(title = "Notifications") {
                SettingsToggle("Push Notifications", true)
                SettingsToggle("Email Alerts", false)
            }
            
            SettingsGroup(title = "More") {
                SettingsItem("Help & Support", Icons.Default.Help) {}
                SettingsItem("About BulkyO", Icons.Default.Info) {}
            }
        }
    }
}

@Composable
fun SettingsGroup(title: String, content: @Composable ColumnScope.() -> Unit) {
    Column(modifier = Modifier.padding(vertical = 8.dp)) {
        Text(title, fontWeight = FontWeight.Bold, color = Color(0xFFBB86FC), fontSize = 14.sp)
        Spacer(modifier = Modifier.height(8.dp))
        Card(
            colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.1f)),
            shape = RoundedCornerShape(16.dp)
        ) {
            Column {
                content()
            }
        }
    }
}

@Composable
fun SettingsItem(title: String, icon: androidx.compose.ui.graphics.vector.ImageVector, onClick: () -> Unit) {
    ListItem(
        headlineContent = { Text(title, color = Color.White) },
        leadingContent = { Icon(icon, null, tint = Color.White) },
        trailingContent = { Icon(Icons.Default.ChevronRight, null, tint = Color.White.copy(alpha = 0.5f)) },
        modifier = Modifier.clickable(onClick = onClick),
        colors = ListItemDefaults.colors(containerColor = Color.Transparent)
    )
}

@Composable
fun SettingsToggle(title: String, initialValue: Boolean) {
    var checked by remember { mutableStateOf(initialValue) }
    ListItem(
        headlineContent = { Text(title, color = Color.White) },
        trailingContent = {
            Switch(checked = checked, onCheckedChange = { checked = it })
        },
        colors = ListItemDefaults.colors(containerColor = Color.Transparent)
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddPaymentDetailsScreen(onBack: () -> Unit, onSuccess: () -> Unit) {
    var cardNumber by remember { mutableStateOf("") }
    var expiryDate by remember { mutableStateOf("") }
    var cvv by remember { mutableStateOf("") }
    var cardHolder by remember { mutableStateOf("") }

    Scaffold(
        containerColor = Color.Transparent,
        topBar = {
            TopAppBar(
                title = { Text("Add Payment Method") },
                navigationIcon = {
                    IconButton(onClick = onBack) { Icon(Icons.Default.ArrowBack, null) }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent)
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(24.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.15f))
            ) {
                Box(modifier = Modifier.padding(24.dp)) {
                    Column {
                        Text("BulkyO Business Card", color = Color.White.copy(alpha = 0.7f), fontSize = 12.sp)
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            if (cardNumber.isEmpty()) "**** **** **** ****" else cardNumber.chunked(4).joinToString(" "),
                            color = Color.White,
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 2.sp
                        )
                        Spacer(modifier = Modifier.weight(1f))
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Column {
                                Text("CARD HOLDER", color = Color.White.copy(alpha = 0.5f), fontSize = 10.sp)
                                Text(if (cardHolder.isEmpty()) "YOUR NAME" else cardHolder.uppercase(), color = Color.White, fontSize = 14.sp)
                            }
                            Column {
                                Text("EXPIRES", color = Color.White.copy(alpha = 0.5f), fontSize = 10.sp)
                                Text(if (expiryDate.isEmpty()) "MM/YY" else expiryDate, color = Color.White, fontSize = 14.sp)
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            OutlinedTextField(
                value = cardHolder,
                onValueChange = { cardHolder = it },
                label = { Text("Card Holder Name") },
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(unfocusedTextColor = Color.White, focusedTextColor = Color.White, focusedLabelColor = Color(0xFFBB86FC), unfocusedLabelColor = Color.White.copy(alpha = 0.7f))
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = cardNumber,
                onValueChange = { if (it.length <= 16) cardNumber = it },
                label = { Text("Card Number") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = androidx.compose.foundation.text.KeyboardOptions(keyboardType = KeyboardType.Number),
                colors = OutlinedTextFieldDefaults.colors(unfocusedTextColor = Color.White, focusedTextColor = Color.White, focusedLabelColor = Color(0xFFBB86FC), unfocusedLabelColor = Color.White.copy(alpha = 0.7f))
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                OutlinedTextField(
                    value = expiryDate,
                    onValueChange = { if (it.length <= 5) expiryDate = it },
                    label = { Text("Expiry (MM/YY)") },
                    modifier = Modifier.weight(1f),
                    colors = OutlinedTextFieldDefaults.colors(unfocusedTextColor = Color.White, focusedTextColor = Color.White, focusedLabelColor = Color(0xFFBB86FC), unfocusedLabelColor = Color.White.copy(alpha = 0.7f))
                )
                OutlinedTextField(
                    value = cvv,
                    onValueChange = { if (it.length <= 3) cvv = it },
                    label = { Text("CVV") },
                    modifier = Modifier.weight(1f),
                    keyboardOptions = androidx.compose.foundation.text.KeyboardOptions(keyboardType = KeyboardType.Number),
                    colors = OutlinedTextFieldDefaults.colors(unfocusedTextColor = Color.White, focusedTextColor = Color.White, focusedLabelColor = Color(0xFFBB86FC), unfocusedLabelColor = Color.White.copy(alpha = 0.7f))
                )
            }

            Spacer(modifier = Modifier.height(48.dp))

            Button(
                onClick = onSuccess,
                modifier = Modifier.fillMaxWidth().height(56.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.White, contentColor = Color(0xFF6200EE))
            ) {
                Text("Save Card Details", fontWeight = FontWeight.Bold)
            }
        }
    }
}
