package com.example.ui.screens

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
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.DirectionsRun
import androidx.compose.material.icons.filled.LocalHospital
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.TrackChanges
import androidx.compose.material.icons.filled.Visibility
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
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.SafeZone
import com.example.ui.theme.CombatRed
import com.example.ui.theme.DangerZoneRed
import com.example.ui.theme.HealthGreen
import com.example.ui.theme.SafeZoneCyan
import com.example.ui.theme.ShieldBlue
import com.example.ui.theme.TacticalAmber
import com.example.ui.theme.TacticalCardBorder
import com.example.ui.theme.TacticalDarkBg
import com.example.ui.theme.TacticalSurface
import com.example.ui.theme.TacticalSurfaceVariant
import com.example.ui.theme.TacticalTextMuted
import com.example.ui.theme.TacticalTextPrimary
import com.example.ui.viewmodel.GameViewModel
import com.example.ui.viewmodel.ScreenState
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt

@Composable
fun BattleRoyaleScreen(viewModel: GameViewModel) {
    val timerSeconds by viewModel.matchTimerSeconds.collectAsState()
    val aliveCount by viewModel.alivePlayers.collectAsState()
    val kills by viewModel.kills.collectAsState()
    val hp by viewModel.playerHp.collectAsState()
    val armor by viewModel.playerArmor.collectAsState()
    val stamina by viewModel.playerStamina.collectAsState()
    val weapon by viewModel.currentWeapon.collectAsState()
    val medkits by viewModel.medkits.collectAsState()
    val isAiming by viewModel.isAiming.collectAsState()
    val isSprinting by viewModel.isSprinting.collectAsState()
    val isCrouching by viewModel.isCrouching.collectAsState()
    val muzzleFlash by viewModel.muzzleFlashSec.collectAsState()
    val hitmarker by viewModel.hitmarkerSec.collectAsState()

    val px by viewModel.playerX.collectAsState()
    val py by viewModel.playerY.collectAsState()
    val pz by viewModel.playerZ.collectAsState()
    val camYaw by viewModel.cameraYaw.collectAsState()
    val camPitch by viewModel.cameraPitch.collectAsState()
    val outfit by viewModel.equippedOutfit.collectAsState()
    val bots by viewModel.aiBots.collectAsState()
    val loots by viewModel.lootItems.collectAsState()
    val safeZone by viewModel.safeZone.collectAsState()
    val feed by viewModel.killfeed.collectAsState()

    // Virtual Joystick local state
    var joyThumbX by remember { mutableFloatStateOf(0f) }
    var joyThumbY by remember { mutableFloatStateOf(0f) }

    // Format timer mm:ss
    val minutes = timerSeconds / 60
    val seconds = timerSeconds % 60
    val formattedTime = String.format("%02d:%02d", minutes, seconds)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        // 1. 3D BATTLE ARENA VIEWPORT (Real-time Canvas Rendering)
        Canvas(
            modifier = Modifier
                .fillMaxSize()
                .testTag("battle_arena_3d_viewport")
        ) {
            viewModel.arenaRenderer.drawWorld(
                drawScope = this,
                viewWidth = size.width,
                viewHeight = size.height,
                playerX = px,
                playerY = py,
                playerZ = pz,
                cameraYaw = camYaw,
                cameraPitch = camPitch,
                playerOutfit = outfit,
                aiEnemies = bots,
                lootItems = loots,
                safeZone = safeZone,
                isAiming = isAiming,
                muzzleFlashSec = muzzleFlash
            )
        }

        // 2. TOUCH LOOK CONTROLLER (Right half of the screen handles camera aim)
        Box(
            modifier = Modifier
                .fillMaxSize()
                .pointerInput(Unit) {
                    detectDragGestures { change, dragAmount ->
                        // Only right half or upper screen controls aim to prevent joystick clash
                        if (change.position.x > size.width * 0.35f) {
                            change.consume()
                            viewModel.rotateCamera(dragAmount.x, dragAmount.y)
                        }
                    }
                }
        )

        // 3. TOP HUD: 30:00 TIMER, ALIVE, KILLS, MINIMAP
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .statusBarsPadding()
                .padding(horizontal = 12.dp, vertical = 6.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                // Left: Match Timer & Kill Count
                Column {
                    Surface(
                        color = Color(0xCC0F1115),
                        shape = RoundedCornerShape(8.dp),
                        border = androidx.compose.foundation.BorderStroke(1.dp, TacticalAmber)
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(text = "⏱", fontSize = 14.sp)
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = formattedTime,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Black,
                                color = TacticalAmber,
                                letterSpacing = 1.sp,
                                modifier = Modifier.testTag("match_countdown_timer")
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(4.dp))

                    Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                        Surface(
                            color = Color(0xCC0F1115),
                            shape = RoundedCornerShape(6.dp)
                        ) {
                            Text(
                                text = "👤 $aliveCount ALIVE",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = TacticalTextPrimary,
                                modifier = Modifier
                                    .padding(horizontal = 6.dp, vertical = 3.dp)
                                    .testTag("alive_players_badge")
                            )
                        }

                        Surface(
                            color = Color(0xCC0F1115),
                            shape = RoundedCornerShape(6.dp)
                        ) {
                            Text(
                                text = "🎯 $kills KILLS",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = CombatRed,
                                modifier = Modifier
                                    .padding(horizontal = 6.dp, vertical = 3.dp)
                                    .testTag("player_kills_badge")
                            )
                        }
                    }
                }

                // Center: Safe Zone Indicator Pill
                Surface(
                    color = Color(0xCC0F1115),
                    shape = RoundedCornerShape(16.dp),
                    border = androidx.compose.foundation.BorderStroke(
                        1.dp,
                        if (safeZone.isInside(px, pz)) SafeZoneCyan else DangerZoneRed
                    )
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Shield,
                            contentDescription = null,
                            tint = if (safeZone.isInside(px, pz)) SafeZoneCyan else DangerZoneRed,
                            modifier = Modifier.size(14.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = if (safeZone.isInside(px, pz)) {
                                if (safeZone.isShrinking) "ZONE CLOSING!" else "ZONE IN ${safeZone.secondsUntilShrink}s"
                            } else {
                                "OUT OF ZONE! RETURN!"
                            },
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = if (safeZone.isInside(px, pz)) SafeZoneCyan else DangerZoneRed
                        )
                    }
                }

                // Right: Tactical Minimap
                Box(
                    modifier = Modifier
                        .size(80.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(Color(0xDD0F1115))
                        .border(1.dp, TacticalCardBorder, RoundedCornerShape(8.dp))
                        .testTag("tactical_minimap")
                ) {
                    Canvas(modifier = Modifier.fillMaxSize()) {
                        val mw = size.width
                        val mh = size.height
                        val mapScale = 0.16f // 500m -> 80px

                        // Draw Safe Zone Circle
                        val zx = mw / 2f + (safeZone.centerX - px) * mapScale
                        val zy = mh / 2f + (safeZone.centerZ - pz) * mapScale
                        val zr = safeZone.currentRadius * mapScale

                        drawCircle(
                            color = Color(0x6600E5FF),
                            radius = zr.coerceAtLeast(4f),
                            center = Offset(zx, zy),
                            style = androidx.compose.ui.graphics.drawscope.Stroke(1.5f)
                        )

                        // Draw Player icon (center)
                        drawCircle(
                            color = TacticalAmber,
                            radius = 3.5f,
                            center = Offset(mw / 2f, mh / 2f)
                        )
                        // Player heading indicator
                        val yawRad = Math.toRadians(camYaw.toDouble()).toFloat()
                        drawLine(
                            color = TacticalAmber,
                            start = Offset(mw / 2f, mh / 2f),
                            end = Offset(mw / 2f + sin(yawRad) * 7f, mh / 2f - cos(yawRad) * 7f),
                            strokeWidth = 2f
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(4.dp))

            // Killfeed Messages
            Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
                for (msg in feed.takeLast(3)) {
                    Surface(
                        color = Color(0x99000000),
                        shape = RoundedCornerShape(4.dp)
                    ) {
                        Text(
                            text = msg,
                            fontSize = 10.sp,
                            color = TacticalTextPrimary,
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                        )
                    }
                }
            }
        }

        // 4. CENTER RETICLE & HITMARKER
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Canvas(modifier = Modifier.size(36.dp)) {
                val cx = size.width / 2f
                val cy = size.height / 2f
                val spreadPx = if (isAiming) 4f else 9f

                // Crosshairs
                drawLine(Color.White, Offset(cx - spreadPx - 6f, cy), Offset(cx - spreadPx, cy), strokeWidth = 1.5f)
                drawLine(Color.White, Offset(cx + spreadPx, cy), Offset(cx + spreadPx + 6f, cy), strokeWidth = 1.5f)
                drawLine(Color.White, Offset(cx, cy - spreadPx - 6f), Offset(cx, cy - spreadPx), strokeWidth = 1.5f)
                drawLine(Color.White, Offset(cx, cy + spreadPx), Offset(cx, cy + spreadPx + 6f), strokeWidth = 1.5f)

                // Hitmarker (Red X Flash)
                if (hitmarker > 0f) {
                    val hitDist = 7f
                    drawLine(Color(0xFFFF1744), Offset(cx - hitDist, cy - hitDist), Offset(cx + hitDist, cy + hitDist), strokeWidth = 2.5f)
                    drawLine(Color(0xFFFF1744), Offset(cx - hitDist, cy + hitDist), Offset(cx + hitDist, cy - hitDist), strokeWidth = 2.5f)
                }
            }
        }

        // 5. BOTTOM-LEFT: VIRTUAL JOYSTICK FOR MOVEMENT
        Box(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(start = 24.dp, bottom = 48.dp)
                .size(120.dp)
                .clip(CircleShape)
                .background(Color(0x550F1115))
                .border(2.dp, Color(0x66FFFFFF), CircleShape)
                .pointerInput(Unit) {
                    detectDragGestures(
                        onDragEnd = {
                            joyThumbX = 0f
                            joyThumbY = 0f
                            viewModel.movePlayer(0f, 0f)
                        },
                        onDragCancel = {
                            joyThumbX = 0f
                            joyThumbY = 0f
                            viewModel.movePlayer(0f, 0f)
                        }
                    ) { change, dragAmount ->
                        change.consume()
                        val newX = (joyThumbX + dragAmount.x).coerceIn(-45f, 45f)
                        val newY = (joyThumbY + dragAmount.y).coerceIn(-45f, 45f)
                        joyThumbX = newX
                        joyThumbY = newY
                        viewModel.movePlayer(newX / 45f, -newY / 45f)
                    }
                }
                .testTag("virtual_joystick")
        ) {
            // Joystick Handle Thumb
            Surface(
                modifier = Modifier
                    .size(48.dp)
                    .align(Alignment.Center)
                    .padding(0.dp),
                color = TacticalAmber,
                shape = CircleShape,
                border = androidx.compose.foundation.BorderStroke(1.5.dp, Color.White)
            ) {
                Box(modifier = Modifier.fillMaxSize())
            }
        }

        // 6. BOTTOM-RIGHT: COMBAT ACTION BUTTONS (Fire, Aim, Sprint, Crouch, Reload)
        Column(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .navigationBarsPadding()
                .padding(end = 16.dp, bottom = 24.dp),
            horizontalAlignment = Alignment.End,
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            // Secondary row: Medkit, Crouch, Aim
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                // Use Medkit
                IconButton(
                    onClick = { viewModel.useMedkit() },
                    modifier = Modifier
                        .size(46.dp)
                        .background(Color(0xCC0F1115), CircleShape)
                        .border(1.dp, HealthGreen, CircleShape)
                        .testTag("use_medkit_button")
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(Icons.Default.LocalHospital, contentDescription = "Medkit", tint = HealthGreen, modifier = Modifier.size(18.dp))
                        Text(text = "$medkits", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = Color.White)
                    }
                }

                // Crouch
                IconButton(
                    onClick = { viewModel.toggleCrouch() },
                    modifier = Modifier
                        .size(46.dp)
                        .background(if (isCrouching) TacticalAmber else Color(0xCC0F1115), CircleShape)
                        .border(1.dp, TacticalCardBorder, CircleShape)
                        .testTag("crouch_button")
                ) {
                    Text(
                        text = "CRCH",
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        color = if (isCrouching) TacticalDarkBg else Color.White
                    )
                }

                // Aim Down Sights (ADS)
                IconButton(
                    onClick = { viewModel.toggleAim() },
                    modifier = Modifier
                        .size(46.dp)
                        .background(if (isAiming) TacticalAmber else Color(0xCC0F1115), CircleShape)
                        .border(1.dp, TacticalAmber, CircleShape)
                        .testTag("aim_button")
                ) {
                    Icon(
                        imageVector = Icons.Default.TrackChanges,
                        contentDescription = "Aim",
                        tint = if (isAiming) TacticalDarkBg else TacticalAmber,
                        modifier = Modifier.size(22.dp)
                    )
                }
            }

            // Primary row: Sprint, Reload, Big Fire Trigger
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                // Sprint
                IconButton(
                    onClick = { viewModel.toggleSprint(!isSprinting) },
                    modifier = Modifier
                        .size(50.dp)
                        .background(if (isSprinting) TacticalAmber else Color(0xCC0F1115), CircleShape)
                        .border(1.dp, TacticalCardBorder, CircleShape)
                        .testTag("sprint_button")
                ) {
                    Icon(
                        imageVector = Icons.Default.DirectionsRun,
                        contentDescription = "Sprint",
                        tint = if (isSprinting) TacticalDarkBg else Color.White,
                        modifier = Modifier.size(24.dp)
                    )
                }

                // Reload
                IconButton(
                    onClick = { viewModel.reloadWeapon() },
                    modifier = Modifier
                        .size(50.dp)
                        .background(Color(0xCC0F1115), CircleShape)
                        .border(1.dp, TacticalCardBorder, CircleShape)
                        .testTag("reload_button")
                ) {
                    Icon(
                        imageVector = Icons.Default.Refresh,
                        contentDescription = "Reload",
                        tint = Color.White,
                        modifier = Modifier.size(22.dp)
                    )
                }

                // BIG FIRE BUTTON
                Box(
                    modifier = Modifier
                        .size(72.dp)
                        .clip(CircleShape)
                        .background(CombatRed)
                        .border(2.5.dp, Color.White, CircleShape)
                        .clickable { viewModel.fireWeapon() }
                        .testTag("fire_button"),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "FIRE",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Black,
                        color = Color.White,
                        letterSpacing = 1.sp
                    )
                }
            }
        }

        // 7. BOTTOM STATUS PANEL: HP, ARMOR, WEAPON & AMMO
        Surface(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth(0.55f)
                .navigationBarsPadding()
                .padding(bottom = 8.dp),
            color = Color(0xDD0F1115),
            shape = RoundedCornerShape(10.dp),
            border = androidx.compose.foundation.BorderStroke(1.dp, TacticalCardBorder)
        ) {
            Column(
                modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
            ) {
                // Health & Armor bars
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "HP $hp / 100", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = HealthGreen)
                    Text(text = "ARMOR $armor / 100", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = ShieldBlue)
                }
                LinearProgressIndicator(
                    progress = { (hp / 100f).coerceIn(0f, 1f) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(5.dp)
                        .clip(RoundedCornerShape(2.dp)),
                    color = HealthGreen,
                    trackColor = Color(0xFF1B5E20)
                )
                Spacer(modifier = Modifier.height(2.dp))
                LinearProgressIndicator(
                    progress = { (armor / 100f).coerceIn(0f, 1f) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(3.dp)
                        .clip(RoundedCornerShape(2.dp)),
                    color = ShieldBlue,
                    trackColor = Color(0xFF0D47A1)
                )

                Spacer(modifier = Modifier.height(4.dp))

                // Weapon name & Ammo counter
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = weapon.name,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = TacticalAmber
                    )
                    Text(
                        text = "${weapon.currentAmmo} / ${weapon.reserveAmmo}",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Black,
                        color = TacticalTextPrimary,
                        modifier = Modifier.testTag("weapon_ammo_counter")
                    )
                }
            }
        }
    }
}
