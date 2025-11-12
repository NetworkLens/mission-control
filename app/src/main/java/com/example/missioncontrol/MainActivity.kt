package com.example.missioncontrol

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.missioncontrol.ui.theme.MissionControlTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MissionControlTheme {
                ChoreApp()
            }
        }
    }
}

data class Chore(val name: String, var isCompleted: Boolean = false)

@Composable
fun ChoreApp() {
    var chores by remember {
        mutableStateOf(
            listOf(
                Chore("Tidy Space Station"),
                Chore("Collect Star Dust"),
                Chore("Feed Galactic Pets"),
                Chore("Brush Teeth")
            )
        )
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Image(
            painter = painterResource(id = R.drawable.space_background), // Make sure you've added this image
            contentDescription = "Space background",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.mission_control_banner), // Make sure you've added this image to your drawables
                contentDescription = "Mission Control Banner"
            )
            Text(
                text = "Commander Daisy's Mission Log",
                fontSize = 18.sp,
                color = Color.White
            )
            Spacer(modifier = Modifier.height(32.dp))

            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                color = Color(0x33FFFFFF), // Translucent white
                shape = RoundedCornerShape(16.dp)
            ) {
                LazyColumn(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(chores) { chore ->
                        ChoreItem(chore = chore, onChoreClicked = {
                            // Find the chore and update its completion status
                            chores = chores.map {
                                if (it.name == chore.name) {
                                    it.copy(isCompleted = !it.isCompleted)
                                } else {
                                    it
                                }
                            }
                        })
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(onClick = { chores = chores + Chore("New chore") }) {
                Text("Add Chore")
            }

            Spacer(modifier = Modifier.height(16.dp))

            val completedChores = chores.count { it.isCompleted }
            LinearProgressIndicator(
                progress = if (chores.isNotEmpty()) completedChores.toFloat() / chores.size else 0f,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(24.dp)
                    .clip(RoundedCornerShape(12.dp)),
                color = Color(0xFF6C63FF), // Purple color for the progress
                trackColor = Color.White.copy(alpha = 0.3f)
            )
        }
    }
}

@Composable
fun ChoreItem(chore: Chore, onChoreClicked: () -> Unit) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onChoreClicked() },
        color = Color(0xFF6C63FF), // Purple button color
        shape = RoundedCornerShape(50) // Make it a pill shape
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 24.dp, vertical = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = chore.name, color = Color.White, fontSize = 16.sp)
            Icon(
                imageVector = Icons.Default.Star,
                contentDescription = "Star",
                modifier = Modifier.size(24.dp),
                tint = if (chore.isCompleted) Color.Yellow else Color.White.copy(alpha = 0.5f)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ChoreAppPreview() {
    MissionControlTheme {
        ChoreApp()
    }
}
