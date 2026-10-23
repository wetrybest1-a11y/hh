package com.example.ui.screens

import androidx.compose.foundation.Canvas
import androidx.compose.ui.geometry.Offset
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Female
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Male
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarBorder
import androidx.compose.material.icons.filled.Sync
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.Continent
import com.example.data.model.CountryOutfit
import com.example.data.model.EmblemType
import com.example.data.model.FlagPatternType
import com.example.game.engine.Character3DModel
import com.example.ui.theme.CombatRed
import com.example.ui.theme.RarityEpic
import com.example.ui.theme.RarityLegendary
import com.example.ui.theme.RarityMythic
import com.example.ui.theme.RarityRare
import com.example.ui.theme.TacticalAmber
import com.example.ui.theme.TacticalCardBorder
import com.example.ui.theme.TacticalDarkBg
import com.example.ui.theme.TacticalSurface
import com.example.ui.theme.TacticalSurfaceVariant
import com.example.ui.theme.TacticalTextMuted
import com.example.ui.theme.TacticalTextPrimary
import com.example.ui.theme.TacticalTextSecondary
import com.example.ui.viewmodel.CameraPresetView
import com.example.ui.viewmodel.GameViewModel
import com.example.ui.viewmodel.ScreenState

@Composable
fun CountryOutfitsScreen(viewModel: GameViewModel) {
    val profile by viewModel.playerProfile.collectAsState()
    val selectedOutfit by viewModel.selectedOutfit.collectAsState()
    val equippedOutfit by viewModel.equippedOutfit.collectAsState()
    val searchQuery by viewModel.outfitSearch.collectAsState()
    val selectedContinent by viewModel.selectedContinent.collectAsState()
    val onlyFavorites by viewModel.onlyFavorites.collectAsState()
    val turntableYaw by viewModel.turntableYaw.collectAsState()
    val isFemale by viewModel.isFemaleModel.collectAsState()
    val hasHelmet by viewModel.hasHelmet.collectAsState()

    val filteredOutfits = remember(searchQuery, selectedContinent, onlyFavorites, profile.favoriteOutfitIds) {
        viewModel.all120Outfits.filter { outfit ->
            val matchesSearch = outfit.name.contains(searchQuery, ignoreCase = true) ||
                    outfit.isoCode.contains(searchQuery, ignoreCase = true)
            val matchesContinent = selectedContinent == Continent.ALL || outfit.continent == selectedContinent
            val matchesFavorites = !onlyFavorites || profile.favoriteOutfitIds.contains(outfit.id)
            matchesSearch && matchesContinent && matchesFavorites
        }
    }

    val isEquipped = equippedOutfit.id == selectedOutfit.id
    val isUnlocked = profile.unlockedOutfitIds.contains(selectedOutfit.id)
    val isFav = profile.favoriteOutfitIds.contains(selectedOutfit.id)

    val characterModel = remember(selectedOutfit, isFemale, hasHelmet) {
        Character3DModel(
            outfit = selectedOutfit,
            isFemale = isFemale,
            hasHelmet = hasHelmet
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(TacticalDarkBg)
    ) {
        // TOP HEADER BAR
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(TacticalSurface)
                .padding(horizontal = 16.dp, vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = { viewModel.navigateTo(ScreenState.LOBBY) },
                modifier = Modifier.testTag("back_to_lobby_button")
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = TacticalAmber
                )
            }

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "COUNTRY OUTFITS",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = TacticalTextPrimary,
                    letterSpacing = 1.sp
                )
                Text(
                    text = "120 National Combat Uniforms • Featured: India",
                    fontSize = 12.sp,
                    color = TacticalAmber
                )
            }

            // Coin Currency Balance
            Surface(
                color = TacticalSurfaceVariant,
                shape = RoundedCornerShape(16.dp),
                border = androidx.compose.foundation.BorderStroke(1.dp, TacticalCardBorder)
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

        // MAIN CONTENT: 3D Turntable Preview (Top/Left) + 120 Outfits Grid/List (Bottom/Right)
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp)
        ) {
            // 1. 3D CHARACTER PREVIEW CARD
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(260.dp),
                colors = CardDefaults.cardColors(containerColor = TacticalSurface),
                shape = RoundedCornerShape(12.dp),
                border = androidx.compose.foundation.BorderStroke(1.5.dp, TacticalCardBorder)
            ) {
                Box(modifier = Modifier.fillMaxSize()) {
                    // 3D Canvas with drag rotation
                    Canvas(
                        modifier = Modifier
                            .fillMaxSize()
                            .pointerInput(Unit) {
                                detectDragGestures { change, dragAmount ->
                                    change.consume()
                                    viewModel.rotateTurntable(dragAmount.x)
                                }
                            }
                            .testTag("character_3d_preview_canvas")
                    ) {
                        characterModel.yawDeg = turntableYaw
                        characterModel.scale = 2.4f
                        characterModel.draw(
                            drawScope = this,
                            centerX = size.width / 2f,
                            centerY = size.height * 0.52f,
                            cameraDist = 260f
                        )
                    }

                    // Rotating Drag Hint & Rarity Ribbon
                    Row(
                        modifier = Modifier
                            .align(Alignment.TopStart)
                            .padding(10.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Surface(
                            color = Color(selectedOutfit.rarity.colorValue),
                            shape = RoundedCornerShape(6.dp)
                        ) {
                            Text(
                                text = selectedOutfit.rarity.label.uppercase(),
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.Black,
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                            )
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Drag to rotate 360°",
                            fontSize = 11.sp,
                            color = TacticalTextMuted
                        )
                    }

                    // Model toggles: Male/Female & Helmet
                    Row(
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .padding(8.dp)
                    ) {
                        IconButton(
                            onClick = { viewModel.toggleGender() },
                            modifier = Modifier
                                .size(36.dp)
                                .background(TacticalSurfaceVariant, CircleShape)
                        ) {
                            Icon(
                                imageVector = if (isFemale) Icons.Default.Female else Icons.Default.Male,
                                contentDescription = "Toggle Gender",
                                tint = TacticalAmber,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                        Spacer(modifier = Modifier.width(6.dp))
                        IconButton(
                            onClick = { viewModel.toggleHelmet() },
                            modifier = Modifier
                                .size(36.dp)
                                .background(TacticalSurfaceVariant, CircleShape)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Security,
                                contentDescription = "Toggle Helmet",
                                tint = if (hasHelmet) TacticalAmber else TacticalTextMuted,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    }

                    // Preset View Angle Buttons (Front, Left, Back, Right)
                    Row(
                        modifier = Modifier
                            .align(Alignment.BottomStart)
                            .padding(10.dp),
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        PresetButton("FRONT") { viewModel.setCameraPreset(CameraPresetView.FRONT) }
                        PresetButton("LEFT") { viewModel.setCameraPreset(CameraPresetView.LEFT_SIDE) }
                        PresetButton("BACK") { viewModel.setCameraPreset(CameraPresetView.BACK) }
                        PresetButton("RIGHT") { viewModel.setCameraPreset(CameraPresetView.RIGHT_SIDE) }
                    }

                    // Equip / Status Action Button on bottom right
                    Row(
                        modifier = Modifier
                            .align(Alignment.BottomEnd)
                            .padding(10.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // Favorite Toggle
                        IconButton(
                            onClick = { viewModel.toggleFavorite(selectedOutfit.id) },
                            modifier = Modifier
                                .size(42.dp)
                                .background(TacticalSurfaceVariant, RoundedCornerShape(8.dp))
                        ) {
                            Icon(
                                imageVector = if (isFav) Icons.Default.Star else Icons.Default.StarBorder,
                                contentDescription = "Favorite",
                                tint = if (isFav) TacticalAmber else TacticalTextMuted
                            )
                        }
                        Spacer(modifier = Modifier.width(8.dp))

                        if (isEquipped) {
                            Button(
                                onClick = {},
                                enabled = false,
                                colors = ButtonDefaults.buttonColors(
                                    disabledContainerColor = Color(0xFF2E7D32),
                                    disabledContentColor = Color.White
                                ),
                                shape = RoundedCornerShape(8.dp),
                                modifier = Modifier.testTag("equipped_status_badge")
                            ) {
                                Icon(Icons.Default.Check, contentDescription = null, modifier = Modifier.size(16.dp))
                                Spacer(modifier = Modifier.width(4.dp))
                                Text("EQUIPPED", fontWeight = FontWeight.Bold, fontSize = 12.sp)
                            }
                        } else if (isUnlocked) {
                            Button(
                                onClick = { viewModel.equipOutfit(selectedOutfit) },
                                colors = ButtonDefaults.buttonColors(containerColor = TacticalAmber),
                                shape = RoundedCornerShape(8.dp),
                                modifier = Modifier.testTag("equip_outfit_button")
                            ) {
                                Text("EQUIP OUTFIT", color = TacticalDarkBg, fontWeight = FontWeight.Bold, fontSize = 12.sp)
                            }
                        } else {
                            Button(
                                onClick = { viewModel.unlockSelectedOutfit() },
                                colors = ButtonDefaults.buttonColors(containerColor = CombatRed),
                                shape = RoundedCornerShape(8.dp),
                                modifier = Modifier.testTag("unlock_outfit_button")
                            ) {
                                Icon(Icons.Default.Lock, contentDescription = null, modifier = Modifier.size(14.dp))
                                Spacer(modifier = Modifier.width(4.dp))
                                Text("UNLOCK (500 🪙)", fontWeight = FontWeight.Bold, fontSize = 12.sp)
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // 2. SELECTED OUTFIT DETAILS CARD
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = TacticalSurfaceVariant),
                shape = RoundedCornerShape(8.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp, vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    FlagIconCanvas(outfit = selectedOutfit, sizeDp = 28)
                    Spacer(modifier = Modifier.width(10.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = selectedOutfit.name,
                                fontWeight = FontWeight.Bold,
                                fontSize = 15.sp,
                                color = TacticalTextPrimary
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "(${selectedOutfit.isoCode})",
                                fontSize = 12.sp,
                                color = TacticalTextMuted
                            )
                        }
                        Text(
                            text = if (isUnlocked) selectedOutfit.tacticalGearStyle else "Requires: ${selectedOutfit.unlockRequirement}",
                            fontSize = 11.sp,
                            color = if (isUnlocked) TacticalAmber else CombatRed,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // 3. SEARCH & REGION FILTER CONTROLS
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { viewModel.searchOutfits(it) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .testTag("outfit_search_input"),
                placeholder = { Text("Search 120 countries (e.g. India, Japan, France...)", fontSize = 13.sp) },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = null, tint = TacticalAmber) },
                trailingIcon = {
                    if (searchQuery.isNotEmpty()) {
                        IconButton(onClick = { viewModel.searchOutfits("") }) {
                            Icon(Icons.Default.Close, contentDescription = "Clear", tint = TacticalTextMuted)
                        }
                    }
                },
                singleLine = true,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = TacticalAmber,
                    unfocusedBorderColor = TacticalCardBorder,
                    focusedTextColor = TacticalTextPrimary,
                    unfocusedTextColor = TacticalTextPrimary,
                    focusedContainerColor = TacticalSurface,
                    unfocusedContainerColor = TacticalSurface
                ),
                shape = RoundedCornerShape(8.dp)
            )

            Spacer(modifier = Modifier.height(6.dp))

            // Continent Filter Chips
            LazyRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                items(Continent.values()) { continent ->
                    FilterChip(
                        selected = selectedContinent == continent,
                        onClick = { viewModel.selectContinent(continent) },
                        label = { Text(continent.displayName, fontSize = 11.sp) },
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
                            selected = selectedContinent == continent
                        )
                    )
                }
            }

            Spacer(modifier = Modifier.height(6.dp))

            // 4. SEARCHABLE 120 COUNTRY OUTFITS LIST
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "CATALOG (${filteredOutfits.size} OF 120 COUNTRIES)",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = TacticalTextMuted,
                    letterSpacing = 0.5.sp
                )
                Text(
                    text = if (onlyFavorites) "Showing Favorites" else "Tap country to preview 3D",
                    fontSize = 11.sp,
                    color = TacticalAmber
                )
            }

            Spacer(modifier = Modifier.height(4.dp))

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .testTag("country_outfits_list"),
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                items(filteredOutfits, key = { it.id }) { outfit ->
                    val itemUnlocked = profile.unlockedOutfitIds.contains(outfit.id)
                    val itemEquipped = equippedOutfit.id == outfit.id
                    val isSelected = selectedOutfit.id == outfit.id

                    CountryOutfitRowItem(
                        outfit = outfit,
                        isUnlocked = itemUnlocked,
                        isEquipped = itemEquipped,
                        isSelected = isSelected,
                        onSelect = { viewModel.selectOutfit(outfit) },
                        onEquip = { viewModel.equipOutfit(outfit) }
                    )
                }
            }
        }
    }
}

@Composable
fun CountryOutfitRowItem(
    outfit: CountryOutfit,
    isUnlocked: Boolean,
    isEquipped: Boolean,
    isSelected: Boolean,
    onSelect: () -> Unit,
    onEquip: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .clickable { onSelect() }
            .testTag("outfit_item_${outfit.id}"),
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected) TacticalSurfaceVariant else TacticalSurface
        ),
        border = androidx.compose.foundation.BorderStroke(
            width = if (isSelected) 1.5.dp else 1.dp,
            color = if (isSelected) TacticalAmber else TacticalCardBorder
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Flag Icon Canvas
            FlagIconCanvas(outfit = outfit, sizeDp = 24)

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = outfit.name,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = TacticalTextPrimary
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = outfit.isoCode,
                        fontSize = 11.sp,
                        color = TacticalTextMuted
                    )
                    if (outfit.id == "IN") {
                        Spacer(modifier = Modifier.width(6.dp))
                        Surface(
                            color = TacticalAmber,
                            shape = RoundedCornerShape(4.dp)
                        ) {
                            Text(
                                text = "FEATURED",
                                fontSize = 9.sp,
                                fontWeight = FontWeight.Bold,
                                color = TacticalDarkBg,
                                modifier = Modifier.padding(horizontal = 4.dp, vertical = 1.dp)
                            )
                        }
                    }
                }
                Text(
                    text = outfit.tacticalGearStyle,
                    fontSize = 11.sp,
                    color = TacticalTextSecondary
                )
            }

            if (isEquipped) {
                Surface(
                    color = Color(0xFF2E7D32),
                    shape = RoundedCornerShape(6.dp)
                ) {
                    Text(
                        text = "EQUIPPED",
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }
            } else if (!isUnlocked) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Lock,
                        contentDescription = "Locked",
                        tint = CombatRed,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("LOCKED", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = CombatRed)
                }
            } else {
                Button(
                    onClick = { onEquip() },
                    colors = ButtonDefaults.buttonColors(containerColor = TacticalAmber),
                    contentPadding = PaddingValues(horizontal = 12.dp, vertical = 4.dp),
                    shape = RoundedCornerShape(6.dp)
                ) {
                    Text("EQUIP", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = TacticalDarkBg)
                }
            }
        }
    }
}

// Crisp Vector Flag Renderer using exact National Flag Colors & Geometry
@Composable
fun FlagIconCanvas(outfit: CountryOutfit, sizeDp: Int = 24) {
    val pCol = Color(outfit.primaryColor)
    val sCol = Color(outfit.secondaryColor)
    val aCol = Color(outfit.accentColor)

    Box(
        modifier = Modifier
            .size(width = (sizeDp * 1.5).dp, height = sizeDp.dp)
            .clip(RoundedCornerShape(3.dp))
            .border(0.8.dp, Color(0x66FFFFFF), RoundedCornerShape(3.dp))
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val w = size.width
            val h = size.height

            when (outfit.flagPattern) {
                FlagPatternType.HORIZONTAL_TRICOLOR -> {
                    val seg = h / 3f
                    drawRect(pCol, Offset(0f, 0f), androidx.compose.ui.geometry.Size(w, seg))
                    drawRect(sCol, Offset(0f, seg), androidx.compose.ui.geometry.Size(w, seg))
                    drawRect(aCol, Offset(0f, seg * 2), androidx.compose.ui.geometry.Size(w, seg))
                    if (outfit.emblem == EmblemType.ASHOKA_CHAKRA) {
                        drawCircle(Color(0xFF000080), radius = seg * 0.35f, center = Offset(w / 2f, h / 2f))
                    }
                }
                FlagPatternType.VERTICAL_TRICOLOR -> {
                    val seg = w / 3f
                    drawRect(pCol, Offset(0f, 0f), androidx.compose.ui.geometry.Size(seg, h))
                    drawRect(sCol, Offset(seg, 0f), androidx.compose.ui.geometry.Size(seg, h))
                    drawRect(aCol, Offset(seg * 2, 0f), androidx.compose.ui.geometry.Size(seg, h))
                }
                FlagPatternType.DISC_CENTER -> {
                    drawRect(pCol, Offset(0f, 0f), size)
                    drawCircle(sCol, radius = h * 0.32f, center = Offset(w / 2f, h / 2f))
                }
                FlagPatternType.NORDIC_CROSS -> {
                    drawRect(pCol, Offset(0f, 0f), size)
                    val crossW = w * 0.18f
                    val crossH = h * 0.22f
                    val cx = w * 0.38f
                    drawRect(sCol, Offset(cx - crossW / 2f, 0f), androidx.compose.ui.geometry.Size(crossW, h))
                    drawRect(sCol, Offset(0f, h / 2f - crossH / 2f), androidx.compose.ui.geometry.Size(w, crossH))
                }
                FlagPatternType.CANTON_STRIPES -> {
                    val stripeH = h / 7f
                    for (i in 0..6) {
                        drawRect(if (i % 2 == 0) sCol else pCol, Offset(0f, i * stripeH), androidx.compose.ui.geometry.Size(w, stripeH))
                    }
                    drawRect(pCol, Offset(0f, 0f), androidx.compose.ui.geometry.Size(w * 0.45f, h * 0.55f))
                    drawCircle(Color.White, radius = 2f, center = Offset(w * 0.22f, h * 0.27f))
                }
                else -> {
                    drawRect(pCol, Offset(0f, 0f), androidx.compose.ui.geometry.Size(w, h / 2f))
                    drawRect(sCol, Offset(0f, h / 2f), androidx.compose.ui.geometry.Size(w, h / 2f))
                }
            }
        }
    }
}

@Composable
fun PresetButton(label: String, onClick: () -> Unit) {
    Surface(
        color = TacticalSurfaceVariant,
        shape = RoundedCornerShape(4.dp),
        border = androidx.compose.foundation.BorderStroke(1.dp, TacticalCardBorder),
        modifier = Modifier.clickable { onClick() }
    ) {
        Text(
            text = label,
            fontSize = 9.sp,
            fontWeight = FontWeight.Bold,
            color = TacticalTextPrimary,
            modifier = Modifier.padding(horizontal = 6.dp, vertical = 3.dp)
        )
    }
}
