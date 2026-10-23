package com.example.ui.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.model.AICombatant
import com.example.data.model.AIState
import com.example.data.model.Continent
import com.example.data.model.CountryOutfit
import com.example.data.model.CountryOutfitRegistry
import com.example.data.model.GameMode
import com.example.data.model.LootItem
import com.example.data.model.LootType
import com.example.data.model.MatchStats
import com.example.data.model.PlayerProfile
import com.example.data.model.SafeZone
import com.example.data.model.Weapon
import com.example.data.model.WorldDestination
import com.example.data.model.WorldDestinationRegistry
import com.example.data.repository.GameRepository
import com.example.game.SoundSynthesizer
import com.example.game.engine.BattleArena3DRenderer
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import java.util.Random
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt

enum class ScreenState {
    LOBBY,
    COUNTRY_OUTFITS,
    WORLD_MAP,
    BATTLE_ROYALE,
    MATCH_RESULTS,
    CAREER_STATS
}

enum class CameraPresetView {
    FRONT,
    BACK,
    LEFT_SIDE,
    RIGHT_SIDE
}

class GameViewModel(context: Context) : ViewModel() {
    private val repository = GameRepository(context)
    private val random = Random()

    // 1. Navigation State
    private val _currentScreen = MutableStateFlow(ScreenState.LOBBY)
    val currentScreen: StateFlow<ScreenState> = _currentScreen.asStateFlow()

    // 2. Player Profile & Outfits State
    private val _playerProfile = MutableStateFlow(repository.loadProfile())
    val playerProfile: StateFlow<PlayerProfile> = _playerProfile.asStateFlow()

    val all120Outfits: List<CountryOutfit> = CountryOutfitRegistry.full120Outfits
    val all120Destinations: List<WorldDestination> = WorldDestinationRegistry.destinations

    private val _selectedOutfit = MutableStateFlow(
        all120Outfits.firstOrNull { it.id == _playerProfile.value.equippedOutfitId }
            ?: all120Outfits.first()
    )
    val selectedOutfit: StateFlow<CountryOutfit> = _selectedOutfit.asStateFlow()

    private val _equippedOutfit = MutableStateFlow(
        all120Outfits.firstOrNull { it.id == _playerProfile.value.equippedOutfitId }
            ?: all120Outfits.first()
    )
    val equippedOutfit: StateFlow<CountryOutfit> = _equippedOutfit.asStateFlow()

    // Outfit Locker Filters
    private val _outfitSearch = MutableStateFlow("")
    val outfitSearch: StateFlow<String> = _outfitSearch.asStateFlow()

    private val _selectedContinent = MutableStateFlow(Continent.ALL)
    val selectedContinent: StateFlow<Continent> = _selectedContinent.asStateFlow()

    private val _onlyFavorites = MutableStateFlow(false)
    val onlyFavorites: StateFlow<Boolean> = _onlyFavorites.asStateFlow()

    // 3D Turntable Preview State
    private val _turntableYaw = MutableStateFlow(0f)
    val turntableYaw: StateFlow<Float> = _turntableYaw.asStateFlow()

    private val _turntablePitch = MutableStateFlow(10f)
    val turntablePitch: StateFlow<Float> = _turntablePitch.asStateFlow()

    private val _isFemaleModel = MutableStateFlow(false)
    val isFemaleModel: StateFlow<Boolean> = _isFemaleModel.asStateFlow()

    private val _hasHelmet = MutableStateFlow(true)
    val hasHelmet: StateFlow<Boolean> = _hasHelmet.asStateFlow()

    // 3. Match Simulation State
    private val _selectedMode = MutableStateFlow(GameMode.SOLO)
    val selectedMode: StateFlow<GameMode> = _selectedMode.asStateFlow()

    private val _selectedDestination = MutableStateFlow(all120Destinations.first())
    val selectedDestination: StateFlow<WorldDestination> = _selectedDestination.asStateFlow()

    // Match HUD state
    private val _matchTimerSeconds = MutableStateFlow(1800) // 30:00 exact timer
    val matchTimerSeconds: StateFlow<Int> = _matchTimerSeconds.asStateFlow()

    private val _alivePlayers = MutableStateFlow(100)
    val alivePlayers: StateFlow<Int> = _alivePlayers.asStateFlow()

    private val _kills = MutableStateFlow(0)
    val kills: StateFlow<Int> = _kills.asStateFlow()

    private val _damageDealt = MutableStateFlow(0)
    val damageDealt: StateFlow<Int> = _damageDealt.asStateFlow()

    private val _playerHp = MutableStateFlow(100)
    val playerHp: StateFlow<Int> = _playerHp.asStateFlow()

    private val _playerArmor = MutableStateFlow(50)
    val playerArmor: StateFlow<Int> = _playerArmor.asStateFlow()

    private val _playerStamina = MutableStateFlow(100f)
    val playerStamina: StateFlow<Float> = _playerStamina.asStateFlow()

    private val _medkits = MutableStateFlow(3)
    val medkits: StateFlow<Int> = _medkits.asStateFlow()

    // Player position and weapon in 3D arena
    val playerX = MutableStateFlow(0f)
    val playerY = MutableStateFlow(0f)
    val playerZ = MutableStateFlow(0f)
    val cameraYaw = MutableStateFlow(0f)
    val cameraPitch = MutableStateFlow(5f)

    private val _currentWeapon = MutableStateFlow(Weapon.ak47())
    val currentWeapon: StateFlow<Weapon> = _currentWeapon.asStateFlow()

    private val _isAiming = MutableStateFlow(false)
    val isAiming: StateFlow<Boolean> = _isAiming.asStateFlow()

    private val _isSprinting = MutableStateFlow(false)
    val isSprinting: StateFlow<Boolean> = _isSprinting.asStateFlow()

    private val _isCrouching = MutableStateFlow(false)
    val isCrouching: StateFlow<Boolean> = _isCrouching.asStateFlow()

    private val _muzzleFlashSec = MutableStateFlow(0f)
    val muzzleFlashSec: StateFlow<Float> = _muzzleFlashSec.asStateFlow()

    private val _hitmarkerSec = MutableStateFlow(0f)
    val hitmarkerSec: StateFlow<Float> = _hitmarkerSec.asStateFlow()

    val safeZone = MutableStateFlow(SafeZone())
    val aiBots = MutableStateFlow<List<AICombatant>>(emptyList())
    val lootItems = MutableStateFlow<List<LootItem>>(emptyList())
    val killfeed = MutableStateFlow<List<String>>(emptyList())

    private val _matchStats = MutableStateFlow(MatchStats())
    val matchStats: StateFlow<MatchStats> = _matchStats.asStateFlow()

    val arenaRenderer = BattleArena3DRenderer()
    private var gameLoopJob: Job? = null

    // Navigation triggers
    fun navigateTo(screen: ScreenState) {
        SoundSynthesizer.playButtonClick()
        _currentScreen.value = screen
    }

    // Outfit Locker Operations
    fun searchOutfits(query: String) {
        _outfitSearch.value = query
    }

    fun selectContinent(continent: Continent) {
        SoundSynthesizer.playButtonClick()
        _selectedContinent.value = continent
    }

    fun toggleFavoritesOnly() {
        SoundSynthesizer.playButtonClick()
        _onlyFavorites.value = !_onlyFavorites.value
    }

    fun selectOutfit(outfit: CountryOutfit) {
        SoundSynthesizer.playButtonClick()
        _selectedOutfit.value = outfit
    }

    fun equipOutfit(outfit: CountryOutfit) {
        if (!_playerProfile.value.unlockedOutfitIds.contains(outfit.id)) return
        SoundSynthesizer.playLootPickup()
        _equippedOutfit.value = outfit
        repository.saveEquippedOutfit(outfit.id)
        _playerProfile.value = _playerProfile.value.copy(equippedOutfitId = outfit.id)
    }

    fun toggleFavorite(outfitId: String) {
        SoundSynthesizer.playButtonClick()
        val updated = repository.toggleFavoriteOutfit(outfitId, _playerProfile.value.favoriteOutfitIds)
        _playerProfile.value = _playerProfile.value.copy(favoriteOutfitIds = updated)
    }

    fun unlockSelectedOutfit() {
        val outfit = _selectedOutfit.value
        val cost = 500
        if (_playerProfile.value.coins >= cost) {
            val updatedCoins = _playerProfile.value.coins - cost
            val updatedUnlocked = repository.unlockOutfit(outfit.id, _playerProfile.value.unlockedOutfitIds)
            _playerProfile.value = _playerProfile.value.copy(
                coins = updatedCoins,
                unlockedOutfitIds = updatedUnlocked
            )
            SoundSynthesizer.playLootPickup()
        }
    }

    // 3D Turntable Orbit Controls
    fun rotateTurntable(deltaX: Float) {
        _turntableYaw.value = (_turntableYaw.value + deltaX * 0.6f) % 360f
    }

    fun setCameraPreset(preset: CameraPresetView) {
        SoundSynthesizer.playButtonClick()
        when (preset) {
            CameraPresetView.FRONT -> _turntableYaw.value = 0f
            CameraPresetView.BACK -> _turntableYaw.value = 180f
            CameraPresetView.LEFT_SIDE -> _turntableYaw.value = 90f
            CameraPresetView.RIGHT_SIDE -> _turntableYaw.value = 270f
        }
    }

    fun toggleGender() {
        SoundSynthesizer.playButtonClick()
        _isFemaleModel.value = !_isFemaleModel.value
    }

    fun toggleHelmet() {
        SoundSynthesizer.playButtonClick()
        _hasHelmet.value = !_hasHelmet.value
    }

    fun selectGameMode(mode: GameMode) {
        SoundSynthesizer.playButtonClick()
        _selectedMode.value = mode
    }

    fun selectDestination(dest: WorldDestination) {
        SoundSynthesizer.playButtonClick()
        _selectedDestination.value = dest
    }

    // ==========================================
    // BATTLE ROYALE MATCH SYSTEM
    // ==========================================
    fun startMatch() {
        SoundSynthesizer.playLootPickup()
        // Reset match values
        _matchTimerSeconds.value = 1800 // 30:00 exact countdown
        val totalBots = _selectedMode.value.targetBots
        _alivePlayers.value = totalBots + 1
        _kills.value = 0
        _damageDealt.value = 0
        _playerHp.value = 100
        _playerArmor.value = 50
        _playerStamina.value = 100f
        _medkits.value = 3

        playerX.value = 0f
        playerY.value = 0f
        playerZ.value = 0f
        cameraYaw.value = 0f
        cameraPitch.value = 5f

        _currentWeapon.value = if (_selectedMode.value == GameMode.PRACTICE) {
            Weapon.practiceWeapon()
        } else {
            Weapon.ak47()
        }

        // Spawn Safe Zone
        safeZone.value = SafeZone(
            centerX = 0f,
            centerZ = 20f,
            currentRadius = 260f,
            targetCenterX = 10f,
            targetCenterZ = 30f,
            targetRadius = 140f,
            secondsUntilShrink = 180
        )

        // Spawn Ground Loot
        val loots = mutableListOf<LootItem>()
        val lootPositions = listOf(
            Triple(15f, 20f, LootType.WEAPON),
            Triple(-25f, 30f, LootType.AMMO),
            Triple(40f, -15f, LootType.MEDKIT),
            Triple(-35f, -40f, LootType.ARMOR_VEST),
            Triple(5f, 60f, LootType.WEAPON),
            Triple(-60f, 10f, LootType.AMMO),
            Triple(70f, 40f, LootType.MEDKIT),
            Triple(-10f, -80f, LootType.WEAPON)
        )
        lootPositions.forEachIndexed { idx, (lx, lz, type) ->
            val wep = when (idx % 3) {
                0 -> Weapon.vectorSmg()
                1 -> Weapon.pumpShotgun()
                else -> Weapon.sniperRifle()
            }
            loots.add(
                LootItem(
                    id = "loot_$idx",
                    type = type,
                    name = when (type) {
                        LootType.WEAPON -> wep.name
                        LootType.AMMO -> "Assault Rifle Ammo (30x)"
                        LootType.MEDKIT -> "First Aid Medkit"
                        LootType.ARMOR_VEST -> "Tactical Kevlar Vest"
                    },
                    x = lx,
                    y = 0.2f,
                    z = lz,
                    weapon = wep
                )
            )
        }
        lootItems.value = loots

        // Spawn AI Combatants (wearing diverse national flag outfits!)
        val bots = mutableListOf<AICombatant>()
        val outfitPool = all120Outfits.shuffled(random)
        for (i in 0 until totalBots.coerceAtMost(25)) { // 25 active rendering bots for mobile 60 FPS
            val botOutfit = outfitPool[i % outfitPool.size]
            val angle = random.nextFloat() * 2f * Math.PI.toFloat()
            val dist = 30f + random.nextFloat() * 180f
            val bx = cos(angle) * dist
            val bz = sin(angle) * dist

            bots.add(
                AICombatant(
                    id = "bot_$i",
                    name = "${botOutfit.name} Operative #${i + 1}",
                    x = bx,
                    y = 0f,
                    z = bz,
                    health = 100,
                    armor = 50,
                    outfit = botOutfit,
                    weapon = if (i % 2 == 0) Weapon.ak47() else Weapon.vectorSmg(),
                    targetX = bx + (random.nextFloat() * 40f - 20f),
                    targetZ = bz + (random.nextFloat() * 40f - 20f)
                )
            )
        }
        aiBots.value = bots
        killfeed.value = listOf("Match started! 100 survivors deployed to ${_selectedDestination.value.name}.")

        _currentScreen.value = ScreenState.BATTLE_ROYALE
        launchGameLoop()
    }

    private fun launchGameLoop() {
        gameLoopJob?.cancel()
        gameLoopJob = viewModelScope.launch {
            var secondAccumulator = 0f
            while (isActive) {
                val dt = 0.05f // 20 updates per second
                delay(50)

                // 1. Countdown timer
                secondAccumulator += dt
                if (secondAccumulator >= 1.0f) {
                    secondAccumulator -= 1.0f
                    if (_matchTimerSeconds.value > 0) {
                        _matchTimerSeconds.value -= 1
                    }

                    // Safe zone logic
                    val zone = safeZone.value
                    if (zone.secondsUntilShrink > 0) {
                        zone.secondsUntilShrink--
                        if (zone.secondsUntilShrink == 15) {
                            SoundSynthesizer.playZoneWarning()
                            addFeedMessage("WARNING: Safe zone begins contracting in 15 seconds!")
                        }
                    } else {
                        // Shrink safe zone
                        zone.isShrinking = true
                        zone.currentRadius = (zone.currentRadius - 0.8f).coerceAtLeast(zone.targetRadius)
                        if (zone.currentRadius <= zone.targetRadius) {
                            zone.phaseIndex++
                            zone.targetRadius = (zone.targetRadius * 0.6f).coerceAtLeast(20f)
                            zone.secondsUntilShrink = 120
                            zone.isShrinking = false
                            addFeedMessage("Safe zone stabilized. Next contraction in 2:00.")
                        }
                    }

                    // Zone damage check
                    if (!zone.isInside(playerX.value, playerZ.value)) {
                        takeDamage(zone.damagePerSec.toInt().coerceAtLeast(2))
                    }

                    // Stamina regeneration
                    if (!_isSprinting.value) {
                        _playerStamina.value = (_playerStamina.value + 5f).coerceAtMost(100f)
                    }
                }

                // 2. Muzzle flash & Hitmarker decay
                if (_muzzleFlashSec.value > 0f) {
                    _muzzleFlashSec.value = (_muzzleFlashSec.value - dt).coerceAtLeast(0f)
                }
                if (_hitmarkerSec.value > 0f) {
                    _hitmarkerSec.value = (_hitmarkerSec.value - dt).coerceAtLeast(0f)
                }
                arenaRenderer.updateTracers(dt)

                // 3. Update AI Bots
                updateAiBots(dt)

                // 4. Check Victory / Defeat conditions
                if (_playerHp.value <= 0) {
                    endMatch(isVictory = false)
                    break
                }
                if (_alivePlayers.value <= 1) {
                    endMatch(isVictory = true)
                    break
                }
            }
        }
    }

    private fun updateAiBots(dt: Float) {
        val bots = aiBots.value
        val px = playerX.value
        val pz = playerZ.value
        val zone = safeZone.value

        var aliveCount = 1 // Player
        for (bot in bots) {
            if (!bot.isAlive) continue
            aliveCount++

            // AI Decision Making
            val distToPlayer = sqrt((bot.x - px) * (bot.x - px) + (bot.z - pz) * (bot.z - pz))

            // Check if outside safe zone
            if (!zone.isInside(bot.x, bot.z)) {
                bot.state = AIState.SEEK_SAFE_ZONE
                bot.targetX = zone.centerX
                bot.targetZ = zone.centerZ
                bot.health -= 1
                if (bot.health <= 0) {
                    bot.isAlive = false
                    addFeedMessage("${bot.name} eliminated by danger zone")
                    continue
                }
            } else if (distToPlayer < 45f) {
                bot.state = AIState.ENGAGE_PLAYER
            } else {
                bot.state = AIState.PATROL
            }

            // Movement logic
            when (bot.state) {
                AIState.ENGAGE_PLAYER -> {
                    // Face player and strafe
                    val angle = Math.atan2((pz - bot.z).toDouble(), (px - bot.x).toDouble()).toFloat()
                    bot.yaw = Math.toDegrees(angle.toDouble()).toFloat()

                    // Move closer if far, or strafe
                    if (distToPlayer > 18f) {
                        bot.x += cos(angle) * 3.5f * dt
                        bot.z += sin(angle) * 3.5f * dt
                    }

                    // Fire at player
                    bot.shootCooldownMs += (dt * 1000).toLong()
                    if (bot.shootCooldownMs >= 850L) {
                        bot.shootCooldownMs = 0L
                        // Fire bullet with spread
                        arenaRenderer.addTracer(
                            bot.x, 1.4f, bot.z,
                            px + (random.nextFloat() * 2f - 1f),
                            1.2f,
                            pz + (random.nextFloat() * 2f - 1f)
                        )
                        // Bot hit chance depends on distance
                        if (random.nextFloat() < 0.45f && distToPlayer < 35f) {
                            takeDamage(random.nextInt(12) + 8)
                        }
                    }
                }
                AIState.PATROL, AIState.SEEK_SAFE_ZONE -> {
                    val dx = bot.targetX - bot.x
                    val dz = bot.targetZ - bot.z
                    val dist = sqrt(dx * dx + dz * dz)
                    if (dist < 4f) {
                        bot.targetX = bot.x + (random.nextFloat() * 60f - 30f)
                        bot.targetZ = bot.z + (random.nextFloat() * 60f - 30f)
                    } else {
                        bot.x += (dx / dist) * 2.8f * dt
                        bot.z += (dz / dist) * 2.8f * dt
                    }
                }
                else -> {}
            }
        }

        // Random bot-vs-bot eliminations in the distance to simulate 100-player battle
        if (random.nextFloat() < 0.04f && aliveCount > 10) {
            val randomCasualty = all120Outfits[random.nextInt(all120Outfits.size)].name
            val randomKiller = all120Outfits[random.nextInt(all120Outfits.size)].name
            addFeedMessage("$randomKiller Operative eliminated $randomCasualty Operative")
            _alivePlayers.value = (_alivePlayers.value - 1).coerceAtLeast(aliveCount)
        } else {
            _alivePlayers.value = aliveCount
        }
    }

    // Player Actions
    fun movePlayer(joystickX: Float, joystickY: Float) {
        if (joystickX == 0f && joystickY == 0f) return

        val yawRad = Math.toRadians(cameraYaw.value.toDouble()).toFloat()
        val speed = if (_isSprinting.value && _playerStamina.value > 5f) {
            _playerStamina.value = (_playerStamina.value - 0.4f).coerceAtLeast(0f)
            8.5f
        } else if (_isCrouching.value) {
            2.5f
        } else {
            5.0f
        }

        val forwardX = sin(yawRad)
        val forwardZ = cos(yawRad)
        val rightX = cos(yawRad)
        val rightZ = -sin(yawRad)

        val moveX = (forwardX * joystickY + rightX * joystickX) * speed * 0.05f
        val moveZ = (forwardZ * joystickY + rightZ * joystickX) * speed * 0.05f

        val nx = playerX.value + moveX
        val nz = playerZ.value + moveZ

        // Collision check with buildings
        var blocked = false
        for (b in arenaRenderer.buildings) {
            val hw = b.width / 2f + 1.2f
            val hd = b.depth / 2f + 1.2f
            if (nx in (b.x - hw)..(b.x + hw) && nz in (b.z - hd)..(b.z + hd)) {
                blocked = true
                break
            }
        }
        if (!blocked) {
            playerX.value = nx
            playerZ.value = nz
        }

        // Check loot proximity
        checkNearbyLoot()
    }

    fun rotateCamera(deltaYaw: Float, deltaPitch: Float) {
        cameraYaw.value = (cameraYaw.value + deltaYaw * 0.35f) % 360f
        cameraPitch.value = (cameraPitch.value - deltaPitch * 0.25f).coerceIn(-35f, 50f)
    }

    fun toggleSprint(enable: Boolean) {
        _isSprinting.value = enable
    }

    fun toggleCrouch() {
        SoundSynthesizer.playButtonClick()
        _isCrouching.value = !_isCrouching.value
    }

    fun toggleAim() {
        SoundSynthesizer.playButtonClick()
        _isAiming.value = !_isAiming.value
    }

    fun fireWeapon() {
        val wep = _currentWeapon.value
        if (!wep.isLoaded) {
            if (wep.canReload) reloadWeapon()
            return
        }

        wep.currentAmmo--
        _muzzleFlashSec.value = 0.12f
        SoundSynthesizer.playGunshot()

        val yawRad = Math.toRadians(cameraYaw.value.toDouble()).toFloat()
        val dirX = sin(yawRad)
        val dirZ = cos(yawRad)

        // Bullet trajectory & hit detection
        val px = playerX.value
        val py = playerY.value + 1.4f
        val pz = playerZ.value

        var hitRegistered = false
        for (bot in aiBots.value) {
            if (!bot.isAlive) continue
            val toBotX = bot.x - px
            val toBotZ = bot.z - pz
            val dist = sqrt(toBotX * toBotX + toBotZ * toBotZ)
            if (dist > wep.effectiveRange) continue

            // Angle alignment check
            val dot = (dirX * (toBotX / dist) + dirZ * (toBotZ / dist))
            if (dot > 0.94f) {
                // Direct Hit!
                val dmg = wep.damage + (random.nextInt(7) - 3)
                bot.health -= dmg
                _damageDealt.value += dmg
                _hitmarkerSec.value = 0.25f
                SoundSynthesizer.playHitMarker()
                hitRegistered = true

                arenaRenderer.addTracer(px, py, pz, bot.x, bot.y + 1.2f, bot.z)

                if (bot.health <= 0) {
                    bot.isAlive = false
                    _kills.value++
                    _alivePlayers.value = (_alivePlayers.value - 1).coerceAtLeast(1)
                    addFeedMessage("You eliminated ${bot.name} with ${wep.name}!")
                }
                break
            }
        }

        if (!hitRegistered) {
            val endX = px + dirX * 70f
            val endZ = pz + dirZ * 70f
            arenaRenderer.addTracer(px, py, pz, endX, 0f, endZ)
        }
    }

    fun reloadWeapon() {
        val wep = _currentWeapon.value
        if (wep.canReload) {
            SoundSynthesizer.playReload()
            wep.reload()
        }
    }

    fun useMedkit() {
        if (_medkits.value > 0 && _playerHp.value < 100) {
            _medkits.value--
            _playerHp.value = (_playerHp.value + 50).coerceAtMost(100)
            SoundSynthesizer.playLootPickup()
        }
    }

    private fun takeDamage(amount: Int) {
        var remaining = amount
        if (_playerArmor.value > 0) {
            val armorDmg = minOf(_playerArmor.value, remaining)
            _playerArmor.value -= armorDmg
            remaining -= armorDmg
        }
        if (remaining > 0) {
            _playerHp.value = (_playerHp.value - remaining).coerceAtLeast(0)
        }
    }

    private fun checkNearbyLoot() {
        val px = playerX.value
        val pz = playerZ.value
        for (loot in lootItems.value) {
            if (loot.isCollected) continue
            val dist = sqrt((loot.x - px) * (loot.x - px) + (loot.z - pz) * (loot.z - pz))
            if (dist < 4f) {
                loot.isCollected = true
                SoundSynthesizer.playLootPickup()
                when (loot.type) {
                    LootType.WEAPON -> {
                        loot.weapon?.let { _currentWeapon.value = it }
                        addFeedMessage("Picked up ${loot.name}")
                    }
                    LootType.AMMO -> {
                        _currentWeapon.value.reserveAmmo += 60
                        addFeedMessage("Collected 60x Ammo")
                    }
                    LootType.MEDKIT -> {
                        _medkits.value++
                        addFeedMessage("Collected First Aid Medkit")
                    }
                    LootType.ARMOR_VEST -> {
                        _playerArmor.value = 100
                        addFeedMessage("Equipped Level 3 Body Armor")
                    }
                }
                break
            }
        }
    }

    private fun addFeedMessage(msg: String) {
        val updated = (killfeed.value + msg).takeLast(5)
        killfeed.value = updated
    }

    private fun endMatch(isVictory: Boolean) {
        gameLoopJob?.cancel()
        val survivalTime = 1800 - _matchTimerSeconds.value
        val xp = if (isVictory) 1200 + _kills.value * 150 else 300 + _kills.value * 120
        repository.recordMatchResult(_kills.value, isVictory, xp)
        _playerProfile.value = repository.loadProfile()

        _matchStats.value = MatchStats(
            placement = if (isVictory) 1 else _alivePlayers.value,
            totalPlayers = 100,
            kills = _kills.value,
            damageDealt = _damageDealt.value,
            survivalSeconds = survivalTime,
            lootCollected = lootItems.value.count { it.isCollected },
            xpEarned = xp,
            isVictory = isVictory
        )

        if (isVictory) {
            SoundSynthesizer.playVictoryFanfare()
        }
        _currentScreen.value = ScreenState.MATCH_RESULTS
    }
}
