package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Replay
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.CombatRed
import com.example.ui.theme.HealthGreen
import com.example.ui.theme.TacticalAmber
import com.example.ui.theme.TacticalCardBorder
import com.example.ui.theme.TacticalDarkBg
import com.example.ui.theme.TacticalSurface
import com.example.ui.theme.TacticalSurfaceVariant
import com.example.ui.theme.TacticalTextMuted
import com.example.ui.theme.TacticalTextPrimary
import com.example.ui.theme.TacticalTextSecondary
import com.example.ui.viewmodel.GameViewModel
import com.example.ui.viewmodel.ScreenState

@Composable
fun MatchResultsScreen(viewModel: GameViewModel) {
    val stats by viewModel.matchStats.collectAsState()
    val outfit by viewModel.equippedOutfit.collectAsState()

    val minutes = stats.survivalSeconds / 60
    val seconds = stats.survivalSeconds % 60
    val survivalTimeStr = String.format("%02d:%02d", minutes, seconds)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(TacticalDarkBg)
            .statusBarsPadding()
            .navigationBarsPadding()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // VICTORY / DEFEAT BANNER
        Surface(
            color = if (stats.isVictory) TacticalAmber else CombatRed,
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier.padding(bottom = 12.dp)
        ) {
            Row(
                modifier = Modifier.padding(horizontal = 20.dp, vertical = 10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.EmojiEvents,
                    contentDescription = null,
                    tint = TacticalDarkBg,
                    modifier = Modifier.size(28.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = if (stats.isVictory) "#1 VICTORY ROYALE" else "SURVIVAL TERMINATED",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Black,
                    color = TacticalDarkBg,
                    letterSpacing = 1.sp
                )
            }
        }

        Text(
            text = "Final Rank: #${stats.placement} of ${stats.totalPlayers}",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = TacticalTextPrimary
        )

        Spacer(modifier = Modifier.height(16.dp))

        // STATS CARD
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = TacticalSurface),
            shape = RoundedCornerShape(12.dp),
            border = androidx.compose.foundation.BorderStroke(1.dp, TacticalCardBorder)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    FlagIconCanvas(outfit = outfit, sizeDp = 24)
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = "Worn Uniform: ${outfit.name} (${outfit.isoCode})",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        color = TacticalAmber
                    )
                }

                StatRowItem("Eliminations", "${stats.kills} Combatants", CombatRed)
                StatRowItem("Damage Dealt", "${stats.damageDealt} HP", TacticalTextPrimary)
                StatRowItem("Survival Time", survivalTimeStr, HealthGreen)
                StatRowItem("Loot Crates Collected", "${stats.lootCollected} Crates", TacticalAmber)
                StatRowItem("Experience Earned", "+${stats.xpEarned} XP", TacticalAmber)
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // ACTION BUTTONS
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Button(
                onClick = { viewModel.navigateTo(ScreenState.LOBBY) },
                modifier = Modifier
                    .weight(1f)
                    .height(50.dp)
                    .testTag("return_to_lobby_button"),
                colors = ButtonDefaults.buttonColors(containerColor = TacticalSurfaceVariant),
                shape = RoundedCornerShape(8.dp)
            ) {
                Icon(Icons.Default.Home, contentDescription = null, tint = TacticalAmber)
                Spacer(modifier = Modifier.width(6.dp))
                Text("LOBBY", color = TacticalTextPrimary, fontWeight = FontWeight.Bold)
            }

            Button(
                onClick = { viewModel.startMatch() },
                modifier = Modifier
                    .weight(1f)
                    .height(50.dp)
                    .testTag("play_again_button"),
                colors = ButtonDefaults.buttonColors(containerColor = TacticalAmber),
                shape = RoundedCornerShape(8.dp)
            ) {
                Icon(Icons.Default.Replay, contentDescription = null, tint = TacticalDarkBg)
                Spacer(modifier = Modifier.width(6.dp))
                Text("PLAY AGAIN", color = TacticalDarkBg, fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Composable
fun StatRowItem(label: String, value: String, valueColor: Color) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = label, fontSize = 13.sp, color = TacticalTextSecondary)
        Text(text = value, fontSize = 14.sp, fontWeight = FontWeight.Bold, color = valueColor)
    }
}
