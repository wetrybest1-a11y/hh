package com.example.game.engine

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import com.example.data.model.CountryOutfit
import com.example.data.model.EmblemType
import com.example.data.model.FlagPatternType
import kotlin.math.cos
import kotlin.math.sin

data class Vertex3D(var x: Float, var y: Float, var z: Float)

data class Polygon3D(
    val vertices: List<Vertex3D>,
    val baseColor: Color,
    val isFlagTexture: Boolean = false,
    val flagPart: String = "", // "torso_top", "torso_mid", "torso_bot", "shoulder_patch", "chest_badge"
    val normal: Vertex3D = Vertex3D(0f, 0f, 1f)
)

class Character3DModel(
    var outfit: CountryOutfit,
    var isFemale: Boolean = false,
    var hasHelmet: Boolean = true,
    var hasBackpack: Boolean = true
) {
    var yawDeg: Float = 0f
    var pitchDeg: Float = 10f
    var scale: Float = 1.0f

    // Render 3D character to Compose Canvas DrawScope
    fun draw(
        drawScope: DrawScope,
        centerX: Float,
        centerY: Float,
        cameraDist: Float = 280f,
        aimPose: Float = 0f // 0f = rest, 1f = aim down sights
    ) {
        val yawRad = Math.toRadians(yawDeg.toDouble()).toFloat()
        val pitchRad = Math.toRadians(pitchDeg.toDouble()).toFloat()

        val cosYaw = cos(yawRad)
        val sinYaw = sin(yawRad)
        val cosPitch = cos(pitchRad)
        val sinPitch = sin(pitchRad)

        val polygons = buildCharacterMesh(aimPose)

        // Transform, project, and depth sort (Painter's algorithm)
        data class TransformedPoly(
            val projectedPoints: List<Offset>,
            val avgZ: Float,
            val shadedColor: Color,
            val originalPoly: Polygon3D
        )

        val transformedList = mutableListOf<TransformedPoly>()

        for (poly in polygons) {
            val transformedVerts = poly.vertices.map { v ->
                // Scale
                val sx = v.x * scale
                val sy = v.y * scale
                val sz = v.z * scale

                // Yaw rotation (around Y axis)
                val x1 = sx * cosYaw - sz * sinYaw
                val y1 = sy
                val z1 = sx * sinYaw + sz * cosYaw

                // Pitch rotation (around X axis)
                val x2 = x1
                val y2 = y1 * cosPitch - z1 * sinPitch
                val z2 = y1 * sinPitch + z1 * cosPitch

                Vertex3D(x2, y2, z2 + cameraDist)
            }

            // Simple backface culling approximation & normal lighting
            val v0 = transformedVerts[0]
            val v1 = transformedVerts[1]
            val v2 = transformedVerts[2]

            val ax = v1.x - v0.x
            val ay = v1.y - v0.y
            val bx = v2.x - v0.x
            val by = v2.y - v0.y

            val normalZ = ax * by - ay * bx
            // Keep front and partial sides for full body volume
            if (normalZ < -0.05f && poly.vertices.size > 2) {
                // Directional light vector (top-right-front)
                val lightIntensity = (0.55f + 0.45f * (poly.normal.y * 0.4f + poly.normal.z * 0.6f).coerceIn(0f, 1f))

                // Apply shading
                val r = (poly.baseColor.red * lightIntensity).coerceIn(0f, 1f)
                val g = (poly.baseColor.green * lightIntensity).coerceIn(0f, 1f)
                val b = (poly.baseColor.blue * lightIntensity).coerceIn(0f, 1f)
                val shaded = Color(r, g, b, poly.baseColor.alpha)

                // Perspective projection
                val fov = 350f
                val proj = transformedVerts.map { tv ->
                    val factor = fov / tv.z.coerceAtLeast(50f)
                    Offset(centerX + tv.x * factor, centerY - tv.y * factor)
                }

                val avgZ = transformedVerts.map { it.z }.average().toFloat()
                transformedList.add(TransformedPoly(proj, avgZ, shaded, poly))
            }
        }

        // Draw sorted back-to-front
        transformedList.sortByDescending { it.avgZ }

        for (item in transformedList) {
            val path = Path().apply {
                if (item.projectedPoints.isNotEmpty()) {
                    moveTo(item.projectedPoints[0].x, item.projectedPoints[0].y)
                    for (i in 1 until item.projectedPoints.size) {
                        lineTo(item.projectedPoints[i].x, item.projectedPoints[i].y)
                    }
                    close()
                }
            }
            drawScope.drawPath(path, item.shadedColor, style = Fill)
            // Delicate polygon edge wireframe for tactical mech aesthetic
            drawScope.drawPath(path, Color(0x33000000), style = Stroke(width = 1.2f))

            // Specialized details on key tactical points
            if (item.originalPoly.isFlagTexture && item.originalPoly.flagPart == "shoulder_patch") {
                // Draw national flag shoulder insignia
                drawShoulderFlagPatch(drawScope, item.projectedPoints)
            } else if (item.originalPoly.flagPart == "chest_badge") {
                drawChestEmblem(drawScope, item.projectedPoints)
            }
        }
    }

    private fun drawShoulderFlagPatch(drawScope: DrawScope, pts: List<Offset>) {
        if (pts.size < 4) return
        val minX = pts.minOf { it.x }
        val maxX = pts.maxOf { it.x }
        val minY = pts.minOf { it.y }
        val maxY = pts.maxOf { it.y }
        val w = maxX - minX
        val h = maxY - minY
        if (w < 4f || h < 4f) return

        // Border patch
        drawScope.drawRect(
            color = Color.Black,
            topLeft = Offset(minX, minY),
            size = androidx.compose.ui.geometry.Size(w, h),
            style = Stroke(width = 1.5f)
        )

        val pCol = Color(outfit.primaryColor)
        val sCol = Color(outfit.secondaryColor)
        val aCol = Color(outfit.accentColor)

        when (outfit.flagPattern) {
            FlagPatternType.HORIZONTAL_TRICOLOR -> {
                val seg = h / 3f
                drawScope.drawRect(pCol, Offset(minX, minY), androidx.compose.ui.geometry.Size(w, seg))
                drawScope.drawRect(sCol, Offset(minX, minY + seg), androidx.compose.ui.geometry.Size(w, seg))
                drawScope.drawRect(aCol, Offset(minX, minY + seg * 2), androidx.compose.ui.geometry.Size(w, seg))
            }
            FlagPatternType.VERTICAL_TRICOLOR -> {
                val seg = w / 3f
                drawScope.drawRect(pCol, Offset(minX, minY), androidx.compose.ui.geometry.Size(seg, h))
                drawScope.drawRect(sCol, Offset(minX + seg, minY), androidx.compose.ui.geometry.Size(seg, h))
                drawScope.drawRect(aCol, Offset(minX + seg * 2, minY), androidx.compose.ui.geometry.Size(seg, h))
            }
            FlagPatternType.DISC_CENTER -> {
                drawScope.drawRect(pCol, Offset(minX, minY), androidx.compose.ui.geometry.Size(w, h))
                drawScope.drawCircle(sCol, radius = minOf(w, h) * 0.28f, center = Offset(minX + w / 2f, minY + h / 2f))
            }
            else -> {
                drawScope.drawRect(pCol, Offset(minX, minY), androidx.compose.ui.geometry.Size(w, h / 2f))
                drawScope.drawRect(sCol, Offset(minX, minY + h / 2f), androidx.compose.ui.geometry.Size(w, h / 2f))
            }
        }
    }

    private fun drawChestEmblem(drawScope: DrawScope, pts: List<Offset>) {
        if (pts.isEmpty()) return
        val cx = pts.map { it.x }.average().toFloat()
        val cy = pts.map { it.y }.average().toFloat()

        when (outfit.emblem) {
            EmblemType.ASHOKA_CHAKRA -> {
                // Navy blue Ashoka Chakra disc & spokes (India)
                val chakraColor = Color(0xFF000080)
                drawScope.drawCircle(chakraColor, radius = 5f, center = Offset(cx, cy), style = Stroke(width = 1.2f))
                drawScope.drawCircle(chakraColor, radius = 1.5f, center = Offset(cx, cy))
            }
            EmblemType.RED_DISC -> {
                drawScope.drawCircle(Color(0xFFBC002D), radius = 5f, center = Offset(cx, cy))
            }
            EmblemType.WHITE_CROSS -> {
                drawScope.drawLine(Color.White, Offset(cx - 4f, cy), Offset(cx + 4f, cy), strokeWidth = 2.5f)
                drawScope.drawLine(Color.White, Offset(cx, cy - 4f), Offset(cx, cy + 4f), strokeWidth = 2.5f)
            }
            EmblemType.SINGLE_STAR, EmblemType.SOUTHERN_CROSS -> {
                drawScope.drawCircle(Color(outfit.accentColor), radius = 4f, center = Offset(cx, cy))
            }
            else -> {
                // Tactical shield crest
                drawScope.drawCircle(Color(outfit.accentColor), radius = 3.5f, center = Offset(cx, cy), style = Stroke(width = 1.2f))
            }
        }
    }

    // Constructs modular, reusable 3D tactical operative mesh
    private fun buildCharacterMesh(aimPose: Float): List<Polygon3D> {
        val polys = mutableListOf<Polygon3D>()

        val primaryCol = Color(outfit.primaryColor)
        val secondaryCol = Color(outfit.secondaryColor)
        val accentCol = Color(outfit.accentColor)
        val tacticalVestCol = Color(0xFF21252B)
        val pantsCol = Color(0xFF2C323D)
        val bootsCol = Color(0xFF14171C)
        val skinCol = Color(0xFFD4A373)
        val visorCol = Color(0xFFFF9800)
        val metalCol = Color(0xFF455A64)

        // 1. HEAD & HELMET / CAP
        val headY = 82f
        val headR = 12f
        // Tactical Ops Helmet (Top, Sides, Visor)
        polys.add(createBox(0f, headY, 0f, headR * 1.8f, headR * 1.8f, headR * 1.8f, primaryCol))
        // High-tech Glowing Visor
        polys.add(createQuad(
            Vertex3D(-headR * 0.7f, headY + 1f, headR * 0.95f),
            Vertex3D(headR * 0.7f, headY + 1f, headR * 0.95f),
            Vertex3D(headR * 0.6f, headY - 4f, headR * 0.95f),
            Vertex3D(-headR * 0.6f, headY - 4f, headR * 0.95f),
            visorCol
        ))
        // Helmet National Stripe / Badge
        polys.add(createQuad(
            Vertex3D(-headR * 0.25f, headY + 9f, headR * 0.92f),
            Vertex3D(headR * 0.25f, headY + 9f, headR * 0.92f),
            Vertex3D(headR * 0.25f, headY + 3f, headR * 0.92f),
            Vertex3D(-headR * 0.25f, headY + 3f, headR * 0.92f),
            accentCol
        ))

        // 2. NECK & COLLAR
        polys.add(createBox(0f, 69f, 0f, 10f, 6f, 10f, skinCol))

        // 3. TORSO (Tactical Jacket with Country Flag Stripes & Plate Carrier)
        // Upper Chest: Primary Flag Color
        polys.add(createBox(0f, 58f, 0f, 26f, 16f, 16f, primaryCol))
        // Mid Torso Stripe: Secondary Flag Color
        polys.add(createBox(0f, 44f, 0f, 24f, 12f, 15f, secondaryCol))
        // Lower Waist / Belt: Accent Flag Color
        polys.add(createBox(0f, 33f, 0f, 23f, 10f, 14f, accentCol))

        // Tactical Ballistic Plate Carrier Over Torso
        polys.add(createQuad(
            Vertex3D(-11f, 64f, 8.5f),
            Vertex3D(11f, 64f, 8.5f),
            Vertex3D(9f, 40f, 8.5f),
            Vertex3D(-9f, 40f, 8.5f),
            tacticalVestCol
        ))

        // Chest National Emblem Badge
        polys.add(Polygon3D(
            vertices = listOf(
                Vertex3D(-4f, 56f, 9.2f),
                Vertex3D(4f, 56f, 9.2f),
                Vertex3D(4f, 48f, 9.2f),
                Vertex3D(-4f, 48f, 9.2f)
            ),
            baseColor = secondaryCol,
            isFlagTexture = true,
            flagPart = "chest_badge"
        ))

        // Tactical Belt & Pouches
        polys.add(createBox(0f, 26f, 0f, 24f, 5f, 16f, metalCol))
        polys.add(createBox(-8f, 26f, 8.5f, 5f, 6f, 3f, Color(0xFF1B1E24)))
        polys.add(createBox(8f, 26f, 8.5f, 5f, 6f, 3f, Color(0xFF1B1E24)))

        // 4. SHOULDERS & ARMS
        // Left Shoulder Armor with Country Flag Patch
        val leftShoulderX = -18f
        polys.add(createBox(leftShoulderX, 58f, 0f, 8f, 10f, 12f, primaryCol))
        polys.add(Polygon3D(
            vertices = listOf(
                Vertex3D(leftShoulderX - 4.2f, 62f, -3.5f),
                Vertex3D(leftShoulderX - 4.2f, 62f, 3.5f),
                Vertex3D(leftShoulderX - 4.2f, 54f, 3.5f),
                Vertex3D(leftShoulderX - 4.2f, 54f, -3.5f)
            ),
            baseColor = primaryCol,
            isFlagTexture = true,
            flagPart = "shoulder_patch"
        ))

        // Right Shoulder Armor
        val rightShoulderX = 18f
        polys.add(createBox(rightShoulderX, 58f, 0f, 8f, 10f, 12f, primaryCol))
        polys.add(Polygon3D(
            vertices = listOf(
                Vertex3D(rightShoulderX + 4.2f, 62f, 3.5f),
                Vertex3D(rightShoulderX + 4.2f, 62f, -3.5f),
                Vertex3D(rightShoulderX + 4.2f, 54f, -3.5f),
                Vertex3D(rightShoulderX + 4.2f, 54f, 3.5f)
            ),
            baseColor = primaryCol,
            isFlagTexture = true,
            flagPart = "shoulder_patch"
        ))

        // Arms (Sleeves & Combat Gloves holding weapon)
        val armAngleZ = 12f * aimPose
        polys.add(createBox(leftShoulderX, 44f, armAngleZ, 7f, 16f, 8f, secondaryCol))
        polys.add(createBox(leftShoulderX, 30f, armAngleZ + 8f, 6f, 12f, 6f, Color(0xFF1E222A))) // Left Glove

        polys.add(createBox(rightShoulderX, 44f, armAngleZ, 7f, 16f, 8f, secondaryCol))
        polys.add(createBox(rightShoulderX, 30f, armAngleZ + 8f, 6f, 12f, 6f, Color(0xFF1E222A))) // Right Glove

        // Tactical Assault Rifle held in front
        val gunZ = 16f + 8f * aimPose
        val gunY = 38f + 14f * aimPose
        // Barrel
        polys.add(createBox(4f, gunY, gunZ, 5f, 6f, 36f, Color(0xFF263238)))
        // Magazine
        polys.add(createBox(4f, gunY - 7f, gunZ - 4f, 3f, 8f, 6f, Color(0xFF1A1D20)))
        // Stock
        polys.add(createBox(4f, gunY - 2f, gunZ - 18f, 4f, 7f, 10f, Color(0xFF37474F)))

        // 5. LEGS & COMBAT TROUSERS
        val legW = 9f
        val legH = 26f
        val legD = 10f
        // Left Leg
        polys.add(createBox(-6.5f, 12f, 0f, legW, legH, legD, pantsCol))
        // Left Knee Pad (accent color)
        polys.add(createBox(-6.5f, 8f, 5.5f, 7f, 6f, 3f, accentCol))

        // Right Leg
        polys.add(createBox(6.5f, 12f, 0f, legW, legH, legD, pantsCol))
        // Right Knee Pad (accent color)
        polys.add(createBox(6.5f, 8f, 5.5f, 7f, 6f, 3f, accentCol))

        // 6. TACTICAL COMBAT BOOTS
        polys.add(createBox(-6.5f, -8f, 2f, 9.5f, 12f, 14f, bootsCol))
        polys.add(createBox(6.5f, -8f, 2f, 9.5f, 12f, 14f, bootsCol))

        // 7. BACKPACK (if enabled)
        if (hasBackpack) {
            polys.add(createBox(0f, 48f, -12f, 20f, 22f, 10f, Color(0xFF1E232A)))
            // Bedroll / Radio on top of pack
            polys.add(createBox(0f, 61f, -12f, 16f, 5f, 7f, primaryCol))
        }

        return polys
    }

    private fun createQuad(v0: Vertex3D, v1: Vertex3D, v2: Vertex3D, v3: Vertex3D, col: Color): Polygon3D {
        return Polygon3D(listOf(v0, v1, v2, v3), col)
    }

    private fun createBox(cx: Float, cy: Float, cz: Float, w: Float, h: Float, d: Float, col: Color): Polygon3D {
        val hw = w / 2f
        val hh = h / 2f
        val hd = d / 2f

        // Front Face
        return Polygon3D(
            vertices = listOf(
                Vertex3D(cx - hw, cy + hh, cz + hd),
                Vertex3D(cx + hw, cy + hh, cz + hd),
                Vertex3D(cx + hw, cy - hh, cz + hd),
                Vertex3D(cx - hw, cy - hh, cz + hd)
            ),
            baseColor = col,
            normal = Vertex3D(0f, 0f, 1f)
        )
    }
}
