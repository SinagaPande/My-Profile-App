package com.itera.myapplication

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*  // <-- GANTI DENGAN INI
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class Profile(
    val name: String,
    val bio: String,
    val email: String,
    val phone: String,
    val location: String
)

@Composable
fun ProfileHeader(name: String) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp)
    ) {
        Box(
            modifier = Modifier
                .size(120.dp)
                .background(Color.LightGray, CircleShape)
        ) {
            Text(
                text = "📷",
                fontSize = 80.sp,
                modifier = Modifier.align(Alignment.Center)
            )
            // Badge kecil di pojok foto (contoh Box untuk layer)
            Box(
                modifier = Modifier
                    .size(30.dp)
                    .background(Color.Blue, CircleShape)
                    .align(Alignment.BottomEnd)
            ) {
                Text(
                    text = "+",
                    color = Color.White,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }

        Text(
            text = name,
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(top = 8.dp)
        )
    }
}
@Composable
fun InfoItem(icon: String, label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = icon,
            fontSize = 24.sp,
            modifier = Modifier
                .size(40.dp)
                .background(Color(0xFFF0F0F0), CircleShape)
                .wrapContentSize(Alignment.Center)
        )
        Column(
            modifier = Modifier
                .padding(start = 16.dp)
                .weight(1f)
        ) {
            Text(
                text = label,
                fontSize = 14.sp,
                color = Color.Gray
            )
            Text(
                text = value,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium
            )
        }
    }
}