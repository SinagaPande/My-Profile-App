package com.itera.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*  // <-- GANTI DENGAN INI (semua layout import)
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.Card
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.foundation.background
import com.itera.myapplication.ui.theme.MyApplicationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ProfileScreen()
                }
            }
        }
    }
}

@Composable
fun ProfileScreen() {
    val profile = Profile(
        name = "Jonathan Sinaga | 123140153",
        bio = "Mahasiswa Informatika ITERA | Mobile Developer",
        email = "jonathan.123140153@student.itera.ac.id",
        phone = "+62 812-3456-7890",
        location = "Lampung, Indonesia"
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        ProfileHeader(name = profile.name)

        Text(
            text = profile.bio,
            fontSize = 16.sp,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        // CARD: bungkus InfoItem dalam Card
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = MaterialTheme.shapes.medium
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                InfoItem(icon = "📧", label = "Email", value = profile.email)
                InfoItem(icon = "📞", label = "Phone", value = profile.phone)
                InfoItem(icon = "📍", label = "Location", value = profile.location)
            }
        }

        // BOX: contoh penggunaan Box untuk efek layer
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
                .background(Color(0xFFE3F2FD), RoundedCornerShape(8.dp))
        ) {
            Text(
                text = "Total Projects: 12",
                modifier = Modifier
                    .align(Alignment.Center)
                    .background(Color.White, RoundedCornerShape(4.dp))
                    .padding(8.dp),
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        // BUTTON
        Button(
            onClick = { /* Aksi edit profile */ },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Edit Profile")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProfileScreenPreview() {
    MyApplicationTheme {
        ProfileScreen()
    }
}