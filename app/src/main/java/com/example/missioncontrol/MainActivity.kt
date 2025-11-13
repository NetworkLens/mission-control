package com.example.missioncontrol

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
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

// Define the custom font family
val playfulFontFamily = FontFamily(
    Font(R.font.playful_font, FontWeight.Normal) // Assumes you have playful_font.ttf in res/font
)

data class Chore(@DrawableRes val imageRes: Int, val name: String, var isCompleted: Boolean = false)

val allChores = listOf(
    Chore(R.drawable.laundry, "Do laundry"),
    Chore(R.drawable.shopping, "Put shopping away"),
    Chore(R.drawable.bath_time, "Have a bath"),
    Chore(R.drawable.rubbish, "Take the rubbish out"),
    Chore(R.drawable.cooking, "Help with cooking"),
    Chore(R.drawable.dishes, "Help do the dishes"),
    Chore(R.drawable.feed_pet, "Help feed your pet"),
    Chore(R.drawable.hoover, "Clean the floor"),
    Chore(R.drawable.dresser, "Put things away"),
    Chore(R.drawable.maths, "Do maths homework"),
    Chore(R.drawable.bed, "Make your bed"),
    Chore(R.drawable.teeth, "Brush your teeth"),
)

@Composable
fun ChoreApp() {
    var chores by remember { mutableStateOf(emptyList<Chore>()) }
    var showDialog by remember { mutableStateOf(false) }

    if (showDialog) {
        ChoreSelectionDialog(
            availableChores = allChores.filter { it !in chores },
            onChoreSelected = { chore -> chores = chores + chore },
            onDismiss = { showDialog = false }
        )
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Image(
            painter = painterResource(id = R.drawable.space_background), // Make sure you\'ve added this image
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
                painter = painterResource(id = R.drawable.mission_control_banner), // Make sure you\'ve added this image to your drawables
                contentDescription = "Mission Control Banner"
            )
            Text(
                text = "Commander Daisy\'s Mission Log",
                fontSize = 18.sp,
                color = Color.White,
                fontFamily = FontFamily(Font(R.font.orbitron))
                fontFamily = playfulFontFamily
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
                        }, onDeleteClicked = { chores = chores - chore })
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Button(onClick = { showDialog = true }) {
                Text("Add Mission")
            }

            Spacer(modifier = Modifier.height(16.dp))

            val completedChores = chores.count { it.isCompleted }
            val progress = if (chores.isNotEmpty()) completedChores.toFloat() / chores.size else 0f

            BoxWithConstraints(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(70.dp)
            ) {
                // Progress bar
                LinearProgressIndicator(
                    progress = { progress },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(24.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .align(Alignment.Center),
                    color = Color(0xFF6C63FF), // Purple color for the progress
                    trackColor = Color.White.copy(alpha = 0.3f)
                )

                val rocketSize = 70.dp
                // Rocket image that moves with progress
                Image(
                    painter = painterResource(id = R.drawable.rocket), // Make sure you have rocket.png in drawables
                    contentDescription = "Rocket",
                    modifier = Modifier
                        .size(rocketSize)
                        .offset(x = (maxWidth - rocketSize) * progress)
                        .align(Alignment.CenterStart)
                )
            }
            RocketProgressBar(
                progress = progress,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(75.dp)
            )
        }
    }
}

@Composable
fun ChoreItem(chore: Chore, onChoreClicked: () -> Unit, onDeleteClicked: () -> Unit) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onChoreClicked() },
        color = Color(0xFF6C63FF), // Purple button color
        shape = RoundedCornerShape(50) // Make it a pill shape
    ) {
        Row(
            modifier = Modifier.padding(start= 5.dp, end = 4.dp, top = 0.dp, bottom = 0.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Image(
                painter = painterResource(id = chore.imageRes),
                contentDescription = chore.name,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(70.dp)
                    .clip(RoundedCornerShape(50))
            )
            Spacer(Modifier.size(16.dp))
            Text(text = chore.name, color = Color.White, fontSize = 16.sp, modifier = Modifier.weight(1f))
            Icon(
                imageVector = Icons.Default.Star,
                contentDescription = "Star",
                modifier = Modifier.size(30.dp),
                tint = if (chore.isCompleted) Color.Yellow else Color.White.copy(alpha = 0.5f)
            )
            IconButton(onClick = onDeleteClicked) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Delete chore",
                    tint = Color.White.copy(alpha = 0.5f)
                )
            }
        }
    }
}

@Composable
fun RocketProgressBar(progress: Float, modifier: Modifier = Modifier) {
    BoxWithConstraints(modifier = modifier) { // modifier from ChoreApp has .height(75.dp)

        // 1. The full progress bar (track and fill)
        // This Box acts as the container for the entire bar, and the rocket will be aligned relative to its center.
        Box(
            modifier = Modifier
                .align(Alignment.Center) // Keep the entire bar centered vertically within the BoxWithConstraints
                .height(20.dp) // The thickness of the bar
                .fillMaxWidth()
                .clip(RoundedCornerShape(50)) // Clip the outer container
                .background(Color.White.copy(alpha = 0.3f))
        ) {
            // The fill portion. It will be clipped by the parent Box.
            Box(
                modifier = Modifier
                    .fillMaxHeight() // Takes its height from the parent (20.dp)
                    .fillMaxWidth(progress) // Its width is based on progress
            ) {
                Image(
                    painter = painterResource(id = R.drawable.progress_bar),
                    contentDescription = "Progress bar fill",
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier.fillMaxSize()
                )
            }
        }

        // Rocket
        val rocketWidth = 120.dp
        val rocketHeight = 75.dp
        val travelDistance = maxWidth - rocketWidth
        val rocketOffset = travelDistance * progress

        Image(
            painter = painterResource(id = R.drawable.rocket),
            contentDescription = "Rocket indicator",
            modifier = Modifier
                .align(Alignment.CenterStart)
                .size(width = rocketWidth, height = rocketHeight)
                .offset(x = rocketOffset)
        )
    }
}


@Preview(showBackground = true)
@Composable
fun ChoreAppPreview() {
    MissionControlTheme {
        ChoreApp()
    }
}
