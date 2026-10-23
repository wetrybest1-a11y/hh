package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Checkroom
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.Map
import androidx.compose.material.icons.filled.MilitaryTech
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
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
fun CareerStatsScreen(viewModel: GameViewModel) {
    val profile by viewModel.playerProfile.collectAsState()
    val equippedOutfit by viewModel.equippedOutfit.collectAsState()

    val totalMatches = profile.totalMatches
    val totalWins = profile.totalWins
    val totalKills = profile.totalKills
    val winRate = if (totalMatches > 0) (totalWins.toFloat() / totalMatches * 100f).toInt() else 0
    val kdRatio = if (totalMatches > 0) String.format("%.2f", totalKills.toFloat() / totalMatches.coerceAtLeast(1)) else "0.00"

    val unlockedOutfitsCount = profile.unlockedOutfitIds.size
    val unlockedDestsCount = profile.unlockedDestinationIds.size

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(TacticalDarkBg)
            .statusBarsPadding()
    ) {
        // TOP HEADER
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(TacticalSurface)
                .padding(horizontal = 16.dp, vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = { viewModel.navigateTo(ScreenState.LOBBY) },
                modifier = Modifier.testTag("back_to_lobby_button_from_career")
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = TacticalAmber
                )
            }

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "CAREER & COMBAT DOSSIER",
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Bold,
                    color = TacticalTextPrimary,
                    letterSpacing = 1.sp
                )
                Text(
                    text = "Commander Profile & Collection Progress",
                    fontSize = 11.sp,
                    color = TacticalAmber
                )
            }
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            // PROFILE SUMMARY CARD
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = TacticalSurface),
                shape = RoundedCornerShape(10.dp),
                border = androidx.compose.foundation.BorderStroke(1.dp, TacticalCardBorder)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    FlagIconCanvas(outfit = equippedOutfit, sizeDp = 32)
                    Spacer(modifier = Modifier.width(14.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = profile.displayName,
                            fontSize = 17.sp,
                            fontWeight = FontWeight.Bold,
                            color = TacticalTextPrimary
                        )
                        Text(
                            text = "Level ${profile.level} Survivor • ${equippedOutfit.name} Active",
                            fontSize = 12.sp,
                            color = TacticalAmber
                        )
                    }
                    Surface(
                        color = TacticalAmber,
                        shape = RoundedCornerShape(6.dp)
                    ) {
                        Text(
                            text = "RANK 1",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = TacticalDarkBg,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                        )
                    }
                }
            }

            // CAREER STATS METRICS GRID
            Text(
                text = "COMBAT METRICS",
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = TacticalTextMuted,
                letterSpacing = 1.sp
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                MetricCard("MATCHES", "$totalMatches", TacticalTextPrimary, Modifier.weight(1f))
                MetricCard("VICTORIES", "$totalWins", TacticalAmber, Modifier.weight(1f))
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                MetricCard("TOTAL KILLS", "$totalKills", CombatRed, Modifier.weight(1f))
                MetricCard("WIN RATE", "$winRate%", HealthGreen, Modifier.weight(1f))
            }

            // COLLECTION PROGRESS CARD
            Text(
                text = "SYSTEM PROGRESSION",
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = TacticalTextMuted,
                letterSpacing = 1.sp
            )

            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = TacticalSurface),
                shape = RoundedCornerShape(10.dp),
                border = androidx.compose.foundation.BorderStroke(1.dp, TacticalCardBorder)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    // Outfits
                    Column {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(Icons.Default.Checkroom, contentDescription = null, tint = TacticalAmber, modifier = Modifier.size(16.dp))
                                Spacer(modifier = Modifier.width(6.dp))
                                Text("Country Flag Outfits", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = TacticalTextPrimary)
                            }
                            Text("$unlockedOutfitsCount / 120", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = TacticalAmber)
                        }
                        LinearProgressIndicator(
                            progress = { (unlockedOutfitsCount / 120f).coerceIn(0f, 1f) },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(6.dp)
                                .padding(top = 6.dp)
                                .clip(RoundedCornerShape(3.dp)),
                            color = TacticalAmber,
                            trackColor = TacticalSurfaceVariant
                        )
                    }

                    // Destinations
                    Column {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(Icons.Default.Map, contentDescription = null, tint = TacticalAmber, modifier = Modifier.size(16.dp))
                                Spacer(modifier = Modifier.width(6.dp))
                                Text("World Destinations", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = TacticalTextPrimary)
                            }
                            Text("$unlockedDestsCount / 120", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = TacticalAmber)
                        }
                        LinearProgressIndicator(
                            progress = { (unlockedDestsCount / 120f).coerceIn(0f, 1f) },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(6.dp)
                                .padding(top = 6.dp)
                                .clip(RoundedCornerShape(3.dp)),
                            color = TacticalAmber,
                            trackColor = TacticalSurfaceVariant
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun MetricCard(title: String, value: String, color: Color, modifier: Modifier) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(containerColor = TacticalSurface),
        shape = RoundedCornerShape(8.dp),
        border = androidx.compose.foundation.BorderStroke(1.dp, TacticalCardBorder)
    ) {
        Column(
            modifier = Modifier.padding(14.dp)
        ) {
            Text(text = title, fontSize = 11.sp, fontWeight = FontWeight.Bold, color = TacticalTextMuted)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = value, fontSize = 22.sp, fontWeight = FontWeight.Black, color = color)
        }
    }
}
