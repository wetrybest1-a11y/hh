package com.example.game.engine

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import com.example.data.model.AICombatant
import com.example.data.model.CountryOutfit
import com.example.data.model.LootItem
import com.example.data.model.LootType
import com.example.data.model.SafeZone
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt

data class WorldBuilding(
    val x: Float,
    val z: Float,
    val width: Float,
    val depth: Float,
    val height: Float,
    val wallColor: Color,
    val roofColor: Color,
    val name: String = "Building"
)

data class TracerEffect(
    val startX: Float,
    val startY: Float,
    val startZ: Float,
    val endX: Float,
    val endY: Float,
    val endZ: Float,
    var lifeSec: Float = 0.2f
)

class BattleArena3DRenderer {
    // Modular Indian-inspired architectural elements & buildings
    val buildings: List<WorldBuilding> = listOf(
        WorldBuilding(-60f, 80f, 35f, 40f, 28f, Color(0xFFD4A373), Color(0xFFBC6C25), "Clay Haveli"),
        WorldBuilding(70f, 90f, 30f, 35f, 24f, Color(0xFFCCD5AE), Color(0xFFE9EDC9), "Riverfront Shop"),
        WorldBuilding(-90f, -70f, 45f, 50f, 32f, Color(0xFFDDA15E), Color(0xFF9B2226), "Brick Warehouse"),
        WorldBuilding(80f, -80f, 40f, 40f, 36f, Color(0xFFC9ADA7), Color(0xFF4A4E69), "Watchtower Citadel"),
        WorldBuilding(0f, 160f, 50f, 45f, 30f, Color(0xFFE07A5F), Color(0xFF3D405B), "Bazaar Terminal"),
        WorldBuilding(-140f, 20f, 35f, 35f, 22f, Color(0xFFD4A373), Color(0xFF6B705C), "Village Post"),
        WorldBuilding(150f, -20f, 40f, 40f, 26f, Color(0xFFF4A261), Color(0xFFE76F51), "Granary Silo"),
        WorldBuilding(0f, -170f, 60f, 45f, 35f, Color(0xFFE9D8A6), Color(0xFF94D2BD), "Temple Courtyard")
    )

    val activeTracers = mutableListOf<TracerEffect>()

    fun addTracer(sx: Float, sy: Float, sz: Float, ex: Float, ey: Float, ez: Float) {
        activeTracers.add(TracerEffect(sx, sy, sz, ex, ey, ez))
    }

    fun updateTracers(deltaSec: Float) {
        val iter = activeTracers.iterator()
        while (iter.hasNext()) {
            val t = iter.next()
            t.lifeSec -= deltaSec
            if (t.lifeSec <= 0f) {
                iter.remove()
            }
        }
    }

    fun drawWorld(
        drawScope: DrawScope,
        viewWidth: Float,
        viewHeight: Float,
        playerX: Float,
        playerY: Float,
        playerZ: Float,
        cameraYaw: Float,
        cameraPitch: Float,
        playerOutfit: CountryOutfit,
        aiEnemies: List<AICombatant>,
        lootItems: List<LootItem>,
        safeZone: SafeZone,
        isAiming: Boolean,
        muzzleFlashSec: Float
    ) {
        val cx = viewWidth / 2f
        val cy = viewHeight / 2f

        // Camera position (third-person over shoulder)
        val yawRad = Math.toRadians(cameraYaw.toDouble()).toFloat()
        val pitchRad = Math.toRadians(cameraPitch.toDouble()).toFloat()

        val camDist = if (isAiming) 3.5f else 6.5f
        val camHeight = if (isAiming) 1.8f else 2.6f
        val camSide = if (isAiming) 0.9f else 0.4f

        val camX = playerX - sin(yawRad) * camDist + cos(yawRad) * camSide
        val camY = playerY + camHeight
        val camZ = playerZ - cos(yawRad) * camDist - sin(yawRad) * camSide

        val cosYaw = cos(-yawRad)
        val sinYaw = sin(-yawRad)
        val cosPitch = cos(pitchRad)
        val sinPitch = sin(pitchRad)
        val fov = 480f

        // Projection helper function
        fun project(wx: Float, wy: Float, wz: Float): Offset? {
            // Translate relative to camera
            val dx = wx - camX
            val dy = wy - camY
            val dz = wz - camZ

            // Rotate Yaw
            val x1 = dx * cosYaw - dz * sinYaw
            val y1 = dy
            val z1 = dx * sinYaw + dz * cosYaw

            // Rotate Pitch
            val x2 = x1
            val y2 = y1 * cosPitch - z1 * sinPitch
            val z2 = y1 * sinPitch + z1 * cosPitch

            if (z2 <= 0.8f) return null // Behind camera

            val factor = fov / z2
            val sx = cx + x2 * factor
            val sy = cy - y2 * factor
            return Offset(sx, sy)
        }

        // 1. SKY & DISTANT HORIZON
        val horizonY = cy + sinPitch * fov * 0.9f
        // Warm Indian afternoon sky gradient
        drawScope.drawRect(
            color = Color(0xFF2C3E50),
            topLeft = Offset(0f, 0f),
            size = Size(viewWidth, horizonY.coerceAtLeast(0f))
        )
        drawScope.drawRect(
            color = Color(0xFFD35400),
            topLeft = Offset(0f, (horizonY - 140f).coerceAtLeast(0f)),
            size = Size(viewWidth, 140f),
            alpha = 0.45f
        )

        // 2. GROUND TERRAIN
        drawScope.drawRect(
            color = Color(0xFF3E2723), // Dusty Indian soil
            topLeft = Offset(0f, horizonY.coerceAtLeast(0f)),
            size = Size(viewWidth, viewHeight - horizonY)
        )

        // Ground grid & roads
        val gridStep = 40f
        val gridMin = -240f
        val gridMax = 240f
        var gx = gridMin
        while (gx <= gridMax) {
            val p1 = project(gx, 0f, gridMin)
            val p2 = project(gx, 0f, gridMax)
            if (p1 != null && p2 != null) {
                drawScope.drawLine(Color(0x22FFFFFF), p1, p2, strokeWidth = 1.2f)
            }
            gx += gridStep
        }
        var gz = gridMin
        while (gz <= gridMax) {
            val p1 = project(gridMin, 0f, gz)
            val p2 = project(gridMax, 0f, gz)
            if (p1 != null && p2 != null) {
                drawScope.drawLine(Color(0x22FFFFFF), p1, p2, strokeWidth = 1.2f)
            }
            gz += gridStep
        }

        // 3. SAFE ZONE RING (Glowing Cylindrical Ring)
        val zonePts = mutableListOf<Offset>()
        val segments = 32
        for (i in 0..segments) {
            val angle = (i.toFloat() / segments) * 2f * Math.PI.toFloat()
            val zx = safeZone.centerX + cos(angle) * safeZone.currentRadius
            val zz = safeZone.centerZ + sin(angle) * safeZone.currentRadius
            val proj = project(zx, 0.2f, zz)
            if (proj != null) {
                zonePts.add(proj)
            }
        }
        if (zonePts.size > 2) {
            for (i in 0 until zonePts.size - 1) {
                drawScope.drawLine(
                    color = Color(0xFF00E5FF),
                    start = zonePts[i],
                    end = zonePts[i + 1],
                    strokeWidth = 4f
                )
            }
        }

        // 4. BUILDINGS (3D Obstacles & Tactical Cover)
        for (b in buildings) {
            val hw = b.width / 2f
            val hd = b.depth / 2f
            val h = b.height

            val pFLB = project(b.x - hw, 0f, b.z - hd)
            val pFRB = project(b.x + hw, 0f, b.z - hd)
            val pBRB = project(b.x + hw, 0f, b.z + hd)
            val pBLB = project(b.x - hw, 0f, b.z + hd)

            val pFLT = project(b.x - hw, h, b.z - hd)
            val pFRT = project(b.x + hw, h, b.z - hd)
            val pBRT = project(b.x + hw, h, b.z + hd)
            val pBLT = project(b.x - hw, h, b.z + hd)

            // Front wall
            if (pFLB != null && pFRB != null && pFRT != null && pFLT != null) {
                val wallPath = Path().apply {
                    moveTo(pFLB.x, pFLB.y)
                    lineTo(pFRB.x, pFRB.y)
                    lineTo(pFRT.x, pFRT.y)
                    lineTo(pFLT.x, pFLT.y)
                    close()
                }
                drawScope.drawPath(wallPath, b.wallColor, style = Fill)
                drawScope.drawPath(wallPath, Color(0x33000000), style = Stroke(1.5f))
            }

            // Flat rooftop (classic Indian town terrace)
            if (pFLT != null && pFRT != null && pBRT != null && pBLT != null) {
                val roofPath = Path().apply {
                    moveTo(pFLT.x, pFLT.y)
                    lineTo(pFRT.x, pFRT.y)
                    lineTo(pBRT.x, pBRT.y)
                    lineTo(pBLT.x, pBLT.y)
                    close()
                }
                drawScope.drawPath(roofPath, b.roofColor, style = Fill)
                drawScope.drawPath(roofPath, Color(0x44000000), style = Stroke(1.5f))
            }
        }

        // 5. LOOT CRATES & WEAPONS ON GROUND
        for (loot in lootItems) {
            if (loot.isCollected) continue
            val baseProj = project(loot.x, 0.1f, loot.z) ?: continue
            val topProj = project(loot.x, 1.8f, loot.z)

            // Vertical light beam for loot detection
            val lootCol = when (loot.type) {
                LootType.WEAPON -> Color(0xFFFFB300)
                LootType.AMMO -> Color(0xFF00E5FF)
                LootType.MEDKIT -> Color(0xFF00E676)
                LootType.ARMOR_VEST -> Color(0xFFAB47BC)
            }
            if (topProj != null) {
                drawScope.drawLine(lootCol, baseProj, topProj, strokeWidth = 3f, alpha = 0.7f)
            }
            drawScope.drawCircle(lootCol, radius = 6f, center = baseProj)
        }

        // 6. AI ENEMIES IN COMBAT FATIGUES
        for (bot in aiEnemies) {
            if (!bot.isAlive) continue
            val feetProj = project(bot.x, bot.y, bot.z) ?: continue
            val headProj = project(bot.x, bot.y + 1.8f, bot.z) ?: continue

            val heightPx = (feetProj.y - headProj.y).coerceAtLeast(10f)
            val widthPx = heightPx * 0.45f

            // Enemy Body with their Country Flag Colors
            val botPrimary = Color(bot.outfit.primaryColor)
            val botSecondary = Color(bot.outfit.secondaryColor)

            drawScope.drawRect(
                color = botPrimary,
                topLeft = Offset(headProj.x - widthPx / 2f, headProj.y + heightPx * 0.2f),
                size = Size(widthPx, heightPx * 0.45f)
            )
            // Head / Helmet
            drawScope.drawCircle(
                color = botSecondary,
                radius = widthPx * 0.35f,
                center = Offset(headProj.x, headProj.y + heightPx * 0.12f)
            )
            // Legs
            drawScope.drawRect(
                color = Color(0xFF1B1E24),
                topLeft = Offset(headProj.x - widthPx / 2f, headProj.y + heightPx * 0.65f),
                size = Size(widthPx, heightPx * 0.35f)
            )

            // Health bar above bot head
            val hpW = widthPx * 1.4f
            val hpRatio = (bot.health.toFloat() / bot.maxHealth).coerceIn(0f, 1f)
            drawScope.drawRect(
                color = Color.Black,
                topLeft = Offset(headProj.x - hpW / 2f, headProj.y - 8f),
                size = Size(hpW, 4f)
            )
            drawScope.drawRect(
                color = if (hpRatio > 0.4f) Color(0xFF00E676) else Color(0xFFFF1744),
                topLeft = Offset(headProj.x - hpW / 2f, headProj.y - 8f),
                size = Size(hpW * hpRatio, 4f)
            )
        }

        // 7. ACTIVE BULLET TRACERS
        for (tracer in activeTracers) {
            val pStart = project(tracer.startX, tracer.startY, tracer.startZ)
            val pEnd = project(tracer.endX, tracer.endY, tracer.endZ)
            if (pStart != null && pEnd != null) {
                drawScope.drawLine(
                    color = Color(0xFFFFD54F),
                    start = pStart,
                    end = pEnd,
                    strokeWidth = 2.5f
                )
            }
        }

        // 8. PLAYER CHARACTER (Third-Person Operative)
        val pFeet = project(playerX, playerY, playerZ)
        val pHead = project(playerX, playerY + 1.8f, playerZ)
        if (pFeet != null && pHead != null) {
            val pHeight = (pFeet.y - pHead.y).coerceAtLeast(15f)
            val pWidth = pHeight * 0.48f

            val pCol = Color(playerOutfit.primaryColor)
            val sCol = Color(playerOutfit.secondaryColor)
            val aCol = Color(playerOutfit.accentColor)

            // Torso (Country Flag Colors)
            drawScope.drawRect(
                color = pCol,
                topLeft = Offset(pHead.x - pWidth / 2f, pHead.y + pHeight * 0.2f),
                size = Size(pWidth, pHeight * 0.25f)
            )
            // Mid Stripe
            drawScope.drawRect(
                color = sCol,
                topLeft = Offset(pHead.x - pWidth / 2f, pHead.y + pHeight * 0.45f),
                size = Size(pWidth, pHeight * 0.15f)
            )
            // Waist / Belt
            drawScope.drawRect(
                color = aCol,
                topLeft = Offset(pHead.x - pWidth / 2f, pHead.y + pHeight * 0.60f),
                size = Size(pWidth, pHeight * 0.08f)
            )

            // Tactical Helmet
            drawScope.drawCircle(
                color = pCol,
                radius = pWidth * 0.38f,
                center = Offset(pHead.x, pHead.y + pHeight * 0.12f)
            )
            // Visor
            drawScope.drawRect(
                color = Color(0xFFFF9800),
                topLeft = Offset(pHead.x - pWidth * 0.25f, pHead.y + pHeight * 0.10f),
                size = Size(pWidth * 0.5f, pHeight * 0.06f)
            )

            // Combat Trousers & Boots
            drawScope.drawRect(
                color = Color(0xFF2C323D),
                topLeft = Offset(pHead.x - pWidth / 2f, pHead.y + pHeight * 0.68f),
                size = Size(pWidth, pHeight * 0.32f)
            )

            // Weapon in player hands pointing forward
            val gunX = pHead.x + (if (isAiming) 4f else pWidth * 0.45f)
            val gunY = pHead.y + pHeight * 0.42f
            drawScope.drawRect(
                color = Color(0xFF21252B),
                topLeft = Offset(gunX, gunY),
                size = Size(pWidth * 0.45f, 5f)
            )

            // Muzzle flash on fire
            if (muzzleFlashSec > 0f) {
                drawScope.drawCircle(
                    color = Color(0xFFFFD54F),
                    radius = 9f,
                    center = Offset(gunX + pWidth * 0.45f, gunY + 2.5f)
                )
            }
        }

        // 9. OUT-OF-ZONE VIGNETTE (Hazard Danger Feedback)
        if (!safeZone.isInside(playerX, playerZ)) {
            drawScope.drawRect(
                color = Color(0x55FF1744),
                topLeft = Offset(0f, 0f),
                size = Size(viewWidth, viewHeight)
            )
        }
    }
}
