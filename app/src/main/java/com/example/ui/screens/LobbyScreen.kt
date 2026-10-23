package com.example.ui.screens

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Checkroom
import androidx.compose.material.icons.filled.Map
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Security
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.GameMode
import com.example.game.engine.Character3DModel
import com.example.ui.theme.CombatRed
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
fun LobbyScreen(viewModel: GameViewModel) {
    val profile by viewModel.playerProfile.collectAsState()
    val equippedOutfit by viewModel.equippedOutfit.collectAsState()
    val selectedMode by viewModel.selectedMode.collectAsState()
    val selectedDest by viewModel.selectedDestination.collectAsState()
    val isFemale by viewModel.isFemaleModel.collectAsState()
    val hasHelmet by viewModel.hasHelmet.collectAsState()

    // Smooth idle breathing and subtle turntable spin
    val infiniteTransition = rememberInfiniteTransition(label = "idle_anim")
    val idleSpin by infiniteTransition.animateFloat(
        initialValue = -15f,
        targetValue = 15f,
        animationSpec = infiniteRepeatable(
            animation = tween(4000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "idle_spin"
    )

    val characterModel = remember(equippedOutfit, isFemale, hasHelmet) {
        Character3DModel(
            outfit = equippedOutfit,
            isFemale = isFemale,
            hasHelmet = hasHelmet
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(TacticalDarkBg)
            .statusBarsPadding()
    ) {
        // TOP USER STATUS BAR
        Surface(
            modifier = Modifier.fillMaxWidth(),
            color = TacticalSurface,
            border = androidx.compose.foundation.BorderStroke(1.dp, TacticalCardBorder)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Equipped Country Flag Badge
                FlagIconCanvas(outfit = equippedOutfit, sizeDp = 28)

                Spacer(modifier = Modifier.width(10.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = profile.displayName,
                            fontWeight = FontWeight.Bold,
                            fontSize = 15.sp,
                            color = TacticalTextPrimary
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Surface(
                            color = TacticalAmber,
                            shape = RoundedCornerShape(4.dp)
                        ) {
                            Text(
                                text = "LVL ${profile.level}",
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                color = TacticalDarkBg,
                                modifier = Modifier.padding(horizontal = 4.dp, vertical = 1.dp)
                            )
                        }
                    }

                    // XP Progress bar
                    val xpProgress = (profile.xp.toFloat() / profile.nextLevelXp).coerceIn(0f, 1f)
                    LinearProgressIndicator(
                        progress = { xpProgress },
                        modifier = Modifier
                            .fillMaxWidth(0.85f)
                            .height(4.dp)
                            .padding(top = 3.dp),
                        color = TacticalAmber,
                        trackColor = TacticalSurfaceVariant
                    )
                }

                // Coins
                Surface(
                    color = TacticalSurfaceVariant,
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = "🪙", fontSize = 12.sp)
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "${profile.coins}",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            color = TacticalTextPrimary
                        )
                    }
                }
            }
        }

        // MAIN HERO VIEWPORT: 3D CHARACTER PREVIEW IN EQUIPPED OUTFIT
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(horizontal = 12.dp, vertical = 8.dp)
        ) {
            Card(
                modifier = Modifier.fillMaxSize(),
                colors = CardDefaults.cardColors(containerColor = TacticalSurface),
                shape = RoundedCornerShape(12.dp),
                border = androidx.compose.foundation.BorderStroke(1.5.dp, TacticalCardBorder)
            ) {
                Box(modifier = Modifier.fillMaxSize()) {
                    // 3D Canvas
                    Canvas(
                        modifier = Modifier
                            .fillMaxSize()
                            .testTag("lobby_3d_character_canvas")
                    ) {
                        characterModel.yawDeg = idleSpin
                        characterModel.scale = 2.7f
                        characterModel.draw(
                            drawScope = this,
                            centerX = size.width / 2f,
                            centerY = size.height * 0.54f,
                            cameraDist = 270f
                        )
                    }

                    // Game Title Banner
                    Column(
                        modifier = Modifier
                            .align(Alignment.TopStart)
                            .padding(14.dp)
                    ) {
                        Text(
                            text = "LAST LAND",
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Black,
                            color = TacticalAmber,
                            letterSpacing = 2.sp
                        )
                        Text(
                            text = "30 MINUTES TO SURVIVE",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = TacticalTextPrimary,
                            letterSpacing = 1.sp
                        )
                    }

                    // Equipped Outfit Info Pill
                    Surface(
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .padding(12.dp)
                            .clickable { viewModel.navigateTo(ScreenState.COUNTRY_OUTFITS) },
                        color = TacticalSurfaceVariant,
                        shape = RoundedCornerShape(8.dp),
                        border = androidx.compose.foundation.BorderStroke(1.dp, TacticalAmber)
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            FlagIconCanvas(outfit = equippedOutfit, sizeDp = 20)
                            Spacer(modifier = Modifier.width(6.dp))
                            Column {
                                Text(
                                    text = equippedOutfit.name.uppercase(),
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = TacticalTextPrimary
                                )
                                Text(
                                    text = "Tap to Customize",
                                    fontSize = 9.sp,
                                    color = TacticalAmber
                                )
                            }
                        }
                    }

                    // Selected Destination Info Bar
                    Surface(
                        modifier = Modifier
                            .align(Alignment.BottomStart)
                            .padding(12.dp)
                            .clickable { viewModel.navigateTo(ScreenState.WORLD_MAP) },
                        color = TacticalSurfaceVariant,
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.Map,
                                contentDescription = null,
                                tint = TacticalAmber,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Column {
                                Text(
                                    text = "ZONE: ${selectedDest.name.uppercase()}",
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = TacticalTextPrimary
                                )
                                Text(
                                    text = "${selectedDest.regionName} • ${selectedDest.difficulty}",
                                    fontSize = 9.sp,
                                    color = TacticalTextSecondary
                                )
                            }
                        }
                    }
                }
            }
        }

        // MODE SELECTOR ROW (Solo, Duo, Squad, Practice)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            GameMode.values().forEach { mode ->
                val isSelected = selectedMode == mode
                Card(
                    modifier = Modifier
                        .weight(1f)
                        .clickable { viewModel.selectGameMode(mode) }
                        .testTag("game_mode_${mode.name.lowercase()}"),
                    colors = CardDefaults.cardColors(
                        containerColor = if (isSelected) TacticalAmber else TacticalSurface
                    ),
                    shape = RoundedCornerShape(8.dp),
                    border = androidx.compose.foundation.BorderStroke(
                        1.dp,
                        if (isSelected) TacticalAmber else TacticalCardBorder
                    )
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = mode.title.split(" ")[0].uppercase(),
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = if (isSelected) TacticalDarkBg else TacticalTextPrimary
                        )
                        Text(
                            text = "${mode.targetBots + 1}P",
                            fontSize = 10.sp,
                            color = if (isSelected) TacticalDarkBg else TacticalTextMuted
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        // PRIMARY DEPLOY BUTTON
        Button(
            onClick = { viewModel.startMatch() },
            modifier = Modifier
                .fillMaxWidth()
                .height(54.dp)
                .padding(horizontal = 12.dp)
                .testTag("start_match_button"),
            colors = ButtonDefaults.buttonColors(containerColor = TacticalAmber),
            shape = RoundedCornerShape(10.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Icon(
                    imageVector = Icons.Default.PlayArrow,
                    contentDescription = null,
                    tint = TacticalDarkBg,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "DEPLOY TO BATTLE ROYALE (30 MIN)",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Black,
                    color = TacticalDarkBg,
                    letterSpacing = 1.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        // BOTTOM NAVIGATION BAR
        NavigationBar(
            containerColor = TacticalSurface,
            contentColor = TacticalTextPrimary,
            modifier = Modifier
                .fillMaxWidth()
                .navigationBarsPadding()
        ) {
            NavigationBarItem(
                selected = true,
                onClick = {},
                icon = { Icon(Icons.Default.PlayArrow, contentDescription = "Play") },
                label = { Text("Play", fontSize = 11.sp) },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = TacticalDarkBg,
                    selectedTextColor = TacticalAmber,
                    indicatorColor = TacticalAmber,
                    unselectedIconColor = TacticalTextMuted,
                    unselectedTextColor = TacticalTextMuted
                )
            )

            NavigationBarItem(
                selected = false,
                onClick = { viewModel.navigateTo(ScreenState.COUNTRY_OUTFITS) },
                icon = { Icon(Icons.Default.Checkroom, contentDescription = "Country Outfits") },
                label = { Text("Country Outfits", fontSize = 11.sp) },
                modifier = Modifier.testTag("nav_country_outfits"),
                colors = NavigationBarItemDefaults.colors(
                    unselectedIconColor = TacticalTextMuted,
                    unselectedTextColor = TacticalTextMuted
                )
            )

            NavigationBarItem(
                selected = false,
                onClick = { viewModel.navigateTo(ScreenState.WORLD_MAP) },
                icon = { Icon(Icons.Default.Map, contentDescription = "120 Destinations") },
                label = { Text("World Map", fontSize = 11.sp) },
                modifier = Modifier.testTag("nav_world_map"),
                colors = NavigationBarItemDefaults.colors(
                    unselectedIconColor = TacticalTextMuted,
                    unselectedTextColor = TacticalTextMuted
                )
            )

            NavigationBarItem(
                selected = false,
                onClick = { viewModel.navigateTo(ScreenState.CAREER_STATS) },
                icon = { Icon(Icons.Default.BarChart, contentDescription = "Career Stats") },
                label = { Text("Career", fontSize = 11.sp) },
                modifier = Modifier.testTag("nav_career_stats"),
                colors = NavigationBarItemDefaults.colors(
                    unselectedIconColor = TacticalTextMuted,
                    unselectedTextColor = TacticalTextMuted
                )
            )
        }
    }
}
