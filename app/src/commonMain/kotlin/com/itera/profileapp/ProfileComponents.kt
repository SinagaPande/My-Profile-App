package com.itera.profileapp

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
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.TextButton
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

data class Profile(
    val name: String,
    val bio: String,
    val email: String,
    val phone: String,
    val location: String
)

@Composable
fun EditProfileDialog(
    initialName: String,
    initialBio: String,
    onDismiss: () -> Unit,
    onSave: (String, String) -> Unit
) {
    // State sementara untuk menyimpan ketikan user sebelum tombol Save ditekan
    var nameText by remember { mutableStateOf(initialName) }
    var bioText by remember { mutableStateOf(initialBio) }

    AlertDialog(
        onDismissRequest = onDismiss, // Dipanggil jika user menekan area luar dialog
        title = {
            Text(text = "Edit Profile", fontWeight = FontWeight.Bold)
        },
        text = {
            Column(
                modifier = Modifier.padding(vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // State Hoisting: OutlinedTextField tidak menyimpan state, 
                // ia hanya membaca 'nameText' dan memanggil 'onValueChange'
                OutlinedTextField(
                    value = nameText,
                    onValueChange = { nameText = it },
                    label = { Text("Name") },
                    singleLine = true
                )

                OutlinedTextField(
                    value = bioText,
                    onValueChange = { bioText = it },
                    label = { Text("Bio") }
                )
            }
        },
        confirmButton = {
            Button(
                onClick = { 
                    // Mengirim data yang sudah diketik ke atas (ke pemanggil komponen ini)
                    onSave(nameText, bioText) 
                }
            ) {
                Text("Save")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}

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