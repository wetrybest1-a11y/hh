package com.example.data.model

enum class GameMode(val title: String, val subtitle: String, val targetBots: Int) {
    SOLO("Solo Survival", "1 vs 99 AI Combatants — Last Land standing wins", 99),
    DUO("Duo Survival", "2-Player Squad with Revive capability", 48),
    SQUAD("Squad Survival", "4-Player Squad tactical teamwork", 24),
    PRACTICE("Practice Arena", "Unlimited ammo, target dummies, weapon testing", 10)
}

enum class LootType {
    WEAPON,
    AMMO,
    MEDKIT,
    ARMOR_VEST
}

data class LootItem(
    val id: String,
    val type: LootType,
    val name: String,
    var x: Float,
    var y: Float,
    var z: Float,
    val weapon: Weapon? = null,
    val ammoCount: Int = 30,
    val healAmount: Int = 50,
    val armorAmount: Int = 50,
    var isCollected: Boolean = false
)

enum class AIState {
    PATROL,
    SEARCH_LOOT,
    ENGAGE_PLAYER,
    SEEK_SAFE_ZONE,
    FLEE
}

data class AICombatant(
    val id: String,
    val name: String,
    var x: Float,
    var y: Float,
    var z: Float,
    var yaw: Float = 0f,
    var health: Int = 100,
    val maxHealth: Int = 100,
    var armor: Int = 50,
    val outfit: CountryOutfit,
    var weapon: Weapon = Weapon.ak47(),
    var state: AIState = AIState.PATROL,
    var targetX: Float = 0f,
    var targetZ: Float = 0f,
    var shootCooldownMs: Long = 0L,
    var isAlive: Boolean = true,
    var reactionDelayMs: Long = 0L
)

data class SafeZone(
    var centerX: Float = 0f,
    var centerZ: Float = 0f,
    var currentRadius: Float = 400f,
    var targetCenterX: Float = 0f,
    var targetCenterZ: Float = 0f,
    var targetRadius: Float = 250f,
    var phaseIndex: Int = 1,
    var secondsUntilShrink: Int = 180, // 3 minutes for first phase
    var isShrinking: Boolean = false,
    var damagePerSec: Float = 2.0f
) {
    fun isInside(x: Float, z: Float): Boolean {
        val dx = x - centerX
        val dz = z - centerZ
        return (dx * dx + dz * dz) <= (currentRadius * currentRadius)
    }

    fun distanceFromZone(x: Float, z: Float): Float {
        val dx = x - centerX
        val dz = z - centerZ
        val dist = kotlin.math.sqrt(dx * dx + dz * dz)
        return dist - currentRadius
    }
}

data class MatchStats(
    val placement: Int = 1,
    val totalPlayers: Int = 100,
    val kills: Int = 0,
    val damageDealt: Int = 0,
    val survivalSeconds: Int = 0,
    val lootCollected: Int = 0,
    val xpEarned: Int = 0,
    val isVictory: Boolean = false
)

data class PlayerProfile(
    val displayName: String = "Commander",
    val level: Int = 1,
    val xp: Int = 0,
    val nextLevelXp: Int = 1000,
    val coins: Int = 2500,
    val equippedOutfitId: String = "IN",
    val favoriteOutfitIds: Set<String> = setOf("IN"),
    val unlockedOutfitIds: Set<String> = setOf("IN"),
    val unlockedDestinationIds: Set<String> = setOf("dest_1_1", "dest_1_2", "dest_2_1"),
    val totalMatches: Int = 0,
    val totalWins: Int = 0,
    val totalKills: Int = 0
)
