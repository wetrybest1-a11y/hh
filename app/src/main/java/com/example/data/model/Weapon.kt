package com.example.data.model

enum class WeaponType(val displayName: String) {
    ASSAULT_RIFLE("Assault Rifle"),
    SMG("Submachine Gun"),
    SHOTGUN("Tactical Shotgun"),
    SNIPER("Precision Sniper"),
    PISTOL("Combat Pistol"),
    TRAINING("Non-Lethal Training")
}

data class Weapon(
    val id: String,
    val name: String,
    val type: WeaponType,
    val damage: Int,
    val fireRateMs: Long,        // Interval between shots in milliseconds
    val magCapacity: Int,
    var currentAmmo: Int,
    var reserveAmmo: Int,
    val effectiveRange: Float,   // In meters
    val spread: Float,           // Bullet spread angle
    val recoilIntensity: Float,  // Screen shake & camera kick
    val reloadTimeSeconds: Float = 2.0f,
    val iconResName: String = "ic_weapon",
    val description: String = ""
) {
    val isLoaded: Boolean get() = currentAmmo > 0
    val canReload: Boolean get() = currentAmmo < magCapacity && reserveAmmo > 0

    fun reload() {
        val needed = magCapacity - currentAmmo
        val toLoad = minOf(needed, reserveAmmo)
        currentAmmo += toLoad
        reserveAmmo -= toLoad
    }

    companion object {
        fun ak47() = Weapon(
            id = "w_ak47",
            name = "AK-47 Tactical",
            type = WeaponType.ASSAULT_RIFLE,
            damage = 38,
            fireRateMs = 120L,
            magCapacity = 30,
            currentAmmo = 30,
            reserveAmmo = 90,
            effectiveRange = 120f,
            spread = 0.04f,
            recoilIntensity = 1.2f,
            reloadTimeSeconds = 2.2f,
            description = "High-caliber assault rifle with heavy stopping power and manageable recoil."
        )

        fun m4a1() = Weapon(
            id = "w_m4a1",
            name = "M4A1 Carbine",
            type = WeaponType.ASSAULT_RIFLE,
            damage = 32,
            fireRateMs = 95L,
            magCapacity = 30,
            currentAmmo = 30,
            reserveAmmo = 120,
            effectiveRange = 135f,
            spread = 0.025f,
            recoilIntensity = 0.8f,
            reloadTimeSeconds = 1.9f,
            description = "Rapid burst carbine optimized for precision at medium combat ranges."
        )

        fun vectorSmg() = Weapon(
            id = "w_vector",
            name = "Vector-9 SMG",
            type = WeaponType.SMG,
            damage = 22,
            fireRateMs = 65L,
            magCapacity = 25,
            currentAmmo = 25,
            reserveAmmo = 100,
            effectiveRange = 60f,
            spread = 0.06f,
            recoilIntensity = 0.6f,
            reloadTimeSeconds = 1.5f,
            description = "Ultra high fire-rate submachine gun dominating close-quarters skirmishes."
        )

        fun pumpShotgun() = Weapon(
            id = "w_shotgun",
            name = "SPAS-12 Breacher",
            type = WeaponType.SHOTGUN,
            damage = 95,
            fireRateMs = 750L,
            magCapacity = 8,
            currentAmmo = 8,
            reserveAmmo = 24,
            effectiveRange = 30f,
            spread = 0.12f,
            recoilIntensity = 2.0f,
            reloadTimeSeconds = 2.5f,
            description = "Devastating close-range scattergun delivering immense burst damage."
        )

        fun sniperRifle() = Weapon(
            id = "w_awm",
            name = "AWM Magnum Sniper",
            type = WeaponType.SNIPER,
            damage = 110,
            fireRateMs = 1300L,
            magCapacity = 5,
            currentAmmo = 5,
            reserveAmmo = 15,
            effectiveRange = 300f,
            spread = 0.005f,
            recoilIntensity = 2.5f,
            reloadTimeSeconds = 3.0f,
            description = "Heavy bolt-action sniper rifle capable of one-shot armor breaches."
        )

        fun combatPistol() = Weapon(
            id = "w_pistol",
            name = "G-19 Sidearm",
            type = WeaponType.PISTOL,
            damage = 24,
            fireRateMs = 250L,
            magCapacity = 15,
            currentAmmo = 15,
            reserveAmmo = 45,
            effectiveRange = 50f,
            spread = 0.035f,
            recoilIntensity = 0.5f,
            reloadTimeSeconds = 1.4f,
            description = "Reliable lightweight tactical sidearm with rapid trigger response."
        )

        fun practiceWeapon() = Weapon(
            id = "w_practice",
            name = "Stun-Tac Training Rifle",
            type = WeaponType.TRAINING,
            damage = 15,
            fireRateMs = 150L,
            magCapacity = 40,
            currentAmmo = 40,
            reserveAmmo = 200,
            effectiveRange = 80f,
            spread = 0.02f,
            recoilIntensity = 0.3f,
            reloadTimeSeconds = 1.5f,
            description = "Non-lethal rubber pellet training rifle used in Practice Mode."
        )
    }
}
