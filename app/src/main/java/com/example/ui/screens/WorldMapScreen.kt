package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Explore
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.WorldDestination
import com.example.data.model.WorldDestinationRegistry
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
fun WorldMapScreen(viewModel: GameViewModel) {
    val profile by viewModel.playerProfile.collectAsState()
    val selectedDest by viewModel.selectedDestination.collectAsState()
    var selectedRegionIndex by remember { mutableIntStateOf(0) }

    val destinationsInRegion = remember(selectedRegionIndex) {
        viewModel.all120Destinations.filter { it.regionIndex == selectedRegionIndex }
    }

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
                modifier = Modifier.testTag("back_to_lobby_button_from_map")
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = TacticalAmber
                )
            }

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "120-LOCATION WORLD PROGRESSION",
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Bold,
                    color = TacticalTextPrimary,
                    letterSpacing = 1.sp
                )
                Text(
                    text = "12 Themed Regions • 10 Destinations Per Region",
                    fontSize = 11.sp,
                    color = TacticalAmber
                )
            }
        }

        // REGION SELECTOR TABS
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 10.dp, horizontal = 12.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            itemsIndexed(WorldDestinationRegistry.regions) { idx, regionName ->
                FilterChip(
                    selected = selectedRegionIndex == idx,
                    onClick = { selectedRegionIndex = idx },
                    label = {
                        Text(
                            text = "${idx + 1}. $regionName",
                            fontSize = 12.sp,
                            fontWeight = if (selectedRegionIndex == idx) FontWeight.Bold else FontWeight.Normal
                        )
                    },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = TacticalAmber,
                        selectedLabelColor = TacticalDarkBg,
                        containerColor = TacticalSurface,
                        labelColor = TacticalTextSecondary
                    ),
                    border = FilterChipDefaults.filterChipBorder(
                        borderColor = TacticalCardBorder,
                        selectedBorderColor = TacticalAmber,
                        enabled = true,
                        selected = selectedRegionIndex == idx
                    )
                )
            }
        }

        // DESTINATION CARDS LIST
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 12.dp)
                .testTag("destinations_list"),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items(destinationsInRegion, key = { it.id }) { dest ->
                val isUnlocked = profile.unlockedDestinationIds.contains(dest.id)
                val isSelected = selectedDest.id == dest.id

                DestinationItemCard(
                    destination = dest,
                    isUnlocked = isUnlocked,
                    isSelected = isSelected,
                    onSelect = {
                        if (isUnlocked) {
                            viewModel.selectDestination(dest)
                        }
                    },
                    onDeploy = {
                        viewModel.selectDestination(dest)
                        viewModel.startMatch()
                    }
                )
            }

            item {
                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }
}

@Composable
fun DestinationItemCard(
    destination: WorldDestination,
    isUnlocked: Boolean,
    isSelected: Boolean,
    onSelect: () -> Unit,
    onDeploy: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(10.dp))
            .clickable(enabled = isUnlocked) { onSelect() }
            .testTag("destination_card_${destination.id}"),
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected) TacticalSurfaceVariant else TacticalSurface
        ),
        border = androidx.compose.foundation.BorderStroke(
            width = if (isSelected) 1.5.dp else 1.dp,
            color = if (isSelected) TacticalAmber else TacticalCardBorder
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.LocationOn,
                    contentDescription = null,
                    tint = if (isUnlocked) TacticalAmber else TacticalTextMuted,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = destination.name,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = TacticalTextPrimary,
                    modifier = Modifier.weight(1f)
                )

                // Difficulty Badge
                Surface(
                    color = when (destination.difficulty) {
                        "Easy" -> Color(0xFF2E7D32)
                        "Normal" -> TacticalAmber
                        "Hard" -> CombatRed
                        else -> Color(0xFFD50000)
                    },
                    shape = RoundedCornerShape(4.dp)
                ) {
                    Text(
                        text = destination.difficulty.uppercase(),
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black,
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = "Terrain: ${destination.environmentType} • Loot: ${destination.lootTier}",
                fontSize = 12.sp,
                color = TacticalTextSecondary
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = destination.description,
                fontSize = 12.sp,
                color = TacticalTextMuted
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Mission Objective
            Surface(
                color = TacticalDarkBg,
                shape = RoundedCornerShape(6.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.padding(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Explore,
                        contentDescription = null,
                        tint = TacticalAmber,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "Objective: ${destination.missionBriefing}",
                        fontSize = 11.sp,
                        color = TacticalTextPrimary
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Action row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (isUnlocked) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.CheckCircle,
                            contentDescription = null,
                            tint = HealthGreen,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("UNLOCKED", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = HealthGreen)
                    }

                    Button(
                        onClick = { onDeploy() },
                        colors = ButtonDefaults.buttonColors(containerColor = TacticalAmber),
                        shape = RoundedCornerShape(6.dp)
                    ) {
                        Text(
                            text = if (isSelected) "DEPLOY NOW" else "SELECT MAP",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = TacticalDarkBg
                        )
                    }
                } else {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Lock,
                            contentDescription = null,
                            tint = CombatRed,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "LOCKED (Requires ${destination.unlockXp} XP)",
                            fontSize = 11.sp,
                            color = CombatRed,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }
}
