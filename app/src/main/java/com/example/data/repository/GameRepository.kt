package com.example.data.repository

import android.content.Context
import android.content.SharedPreferences
import com.example.data.model.CountryOutfit
import com.example.data.model.CountryOutfitRegistry
import com.example.data.model.PlayerProfile
import com.example.data.model.WorldDestination
import com.example.data.model.WorldDestinationRegistry

class GameRepository(context: Context) {
    private val prefs: SharedPreferences =
        context.getSharedPreferences("last_land_prefs", Context.MODE_PRIVATE)

    fun loadProfile(): PlayerProfile {
        val level = prefs.getInt("player_level", 1)
        val xp = prefs.getInt("player_xp", 150)
        val coins = prefs.getInt("player_coins", 3500)
        val equipped = prefs.getString("equipped_outfit", "IN") ?: "IN"
        val favs = prefs.getStringSet("favorite_outfits", setOf("IN")) ?: setOf("IN")

        val defaultUnlocked = CountryOutfitRegistry.full120Outfits
            .filter { it.isDefaultUnlocked }
            .map { it.id }
            .toSet()

        val unlocked = prefs.getStringSet("unlocked_outfits", defaultUnlocked) ?: defaultUnlocked

        val defaultDestUnlocked = WorldDestinationRegistry.destinations
            .filter { it.isDefaultUnlocked }
            .map { it.id }
            .toSet()

        val unlockedDests = prefs.getStringSet("unlocked_destinations", defaultDestUnlocked) ?: defaultDestUnlocked

        val matches = prefs.getInt("total_matches", 0)
        val wins = prefs.getInt("total_wins", 0)
        val kills = prefs.getInt("total_kills", 0)

        return PlayerProfile(
            displayName = "Commander Alpha",
            level = level,
            xp = xp,
            nextLevelXp = level * 1000,
            coins = coins,
            equippedOutfitId = equipped,
            favoriteOutfitIds = favs,
            unlockedOutfitIds = unlocked,
            unlockedDestinationIds = unlockedDests,
            totalMatches = matches,
            totalWins = wins,
            totalKills = kills
        )
    }

    fun saveEquippedOutfit(outfitId: String) {
        prefs.edit().putString("equipped_outfit", outfitId).apply()
    }

    fun toggleFavoriteOutfit(outfitId: String, currentFavs: Set<String>): Set<String> {
        val updated = currentFavs.toMutableSet()
        if (updated.contains(outfitId)) {
            updated.remove(outfitId)
        } else {
            updated.add(outfitId)
        }
        prefs.edit().putStringSet("favorite_outfits", updated).apply()
        return updated
    }

    fun unlockOutfit(outfitId: String, currentUnlocked: Set<String>): Set<String> {
        val updated = currentUnlocked + outfitId
        prefs.edit().putStringSet("unlocked_outfits", updated).apply()
        return updated
    }

    fun unlockDestination(destId: String, currentUnlocked: Set<String>): Set<String> {
        val updated = currentUnlocked + destId
        prefs.edit().putStringSet("unlocked_destinations", updated).apply()
        return updated
    }

    fun recordMatchResult(kills: Int, isWin: Boolean, xpEarned: Int) {
        val currentMatches = prefs.getInt("total_matches", 0) + 1
        val currentWins = prefs.getInt("total_wins", 0) + (if (isWin) 1 else 0)
        val currentKills = prefs.getInt("total_kills", 0) + kills
        var currentXp = prefs.getInt("player_xp", 150) + xpEarned
        var currentLevel = prefs.getInt("player_level", 1)

        while (currentXp >= currentLevel * 1000) {
            currentXp -= (currentLevel * 1000)
            currentLevel++
        }

        prefs.edit()
            .putInt("total_matches", currentMatches)
            .putInt("total_wins", currentWins)
            .putInt("total_kills", currentKills)
            .putInt("player_xp", currentXp)
            .putInt("player_level", currentLevel)
            .apply()
    }
}
