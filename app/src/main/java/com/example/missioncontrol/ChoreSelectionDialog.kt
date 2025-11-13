package com.example.missioncontrol

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog

@Composable
fun ChoreSelectionDialog(
    availableChores: List<Chore>,
    onChoreSelected: (Chore) -> Unit,
    onDismiss: () -> Unit
) {
    Dialog(onDismissRequest = onDismiss) {
        Surface(
            shape = RoundedCornerShape(16.dp),
            color = Color(0xFF0C0A2D)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "Choose a Mission", fontSize = 20.sp, color = Color.White)
                Spacer(modifier = Modifier.size(16.dp))
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(availableChores) { chore ->
                        ChoreSelectionItem(chore = chore, onChoreSelected = {
                            onChoreSelected(chore)
                            onDismiss()
                        })
                    }
                }
            }
        }
    }
}

@Composable
fun ChoreSelectionItem(chore: Chore, onChoreSelected: () -> Unit) {
    Surface(
        modifier = Modifier.fillMaxWidth().clickable { onChoreSelected() },
        shape = RoundedCornerShape(50),
        color = Color(0xFF6C63FF)
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = chore.imageRes),
                contentDescription = chore.name,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(40.dp)
                    .clip(RoundedCornerShape(50))
            )
            Spacer(Modifier.size(16.dp))
            Text(text = chore.name, color = Color.White, fontSize = 16.sp, modifier = Modifier.weight(1f))
        }
    }
}
