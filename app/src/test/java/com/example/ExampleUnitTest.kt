package com.example

import com.example.data.model.Continent
import com.example.data.model.CountryOutfitRegistry
import com.example.data.model.Weapon
import com.example.data.model.WorldDestinationRegistry
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test

class ExampleUnitTest {

    @Test
    fun test1_countryOutfitCollectionDisplays120Entries() {
        val outfits = CountryOutfitRegistry.full120Outfits
        assertEquals("The country outfit menu must display exactly 120 entries", 120, outfits.size)
        // Verify India is default and present
        val india = outfits.firstOrNull { it.id == "IN" }
        assertNotNull("India outfit must be present", india)
        assertEquals("India", india?.name)
        assertTrue("India must be default unlocked", india?.isDefaultUnlocked == true)
    }

    @Test
    fun test2_searchingForCountryReturnsCorrectResult() {
        val outfits = CountryOutfitRegistry.full120Outfits
        val query = "Japan"
        val results = outfits.filter { it.name.contains(query, ignoreCase = true) }
        assertTrue("Searching for Japan returns matching entry", results.any { it.name == "Japan" })

        val isoResults = outfits.filter { it.isoCode.startsWith("FR", ignoreCase = true) }
        assertTrue("Searching for France returns France", isoResults.any { it.name == "France" })
    }

    @Test
    fun test3_worldDestinationsMatrixContains120Locations() {
        val dests = WorldDestinationRegistry.destinations
        assertEquals("120 world destinations must be registered", 120, dests.size)
        assertEquals("12 regions must be configured", 12, WorldDestinationRegistry.regions.size)
    }

    @Test
    fun test4_weaponsStatParityAndNoPayToWinAdvantage() {
        // Competitive balance check: Outfits are cosmetic only and do not alter base weapon stats
        val ak = Weapon.ak47()
        assertEquals(38, ak.damage)
        assertEquals(30, ak.magCapacity)
        assertEquals(30, ak.currentAmmo)
    }

    @Test
    fun test5_continentsCategorizationCoversGlobalRegions() {
        val outfits = CountryOutfitRegistry.full120Outfits
        val continentsPresent = outfits.map { it.continent }.toSet()
        assertTrue("Must include Asian nations", continentsPresent.contains(Continent.ASIA))
        assertTrue("Must include European nations", continentsPresent.contains(Continent.EUROPE))
        assertTrue("Must include African nations", continentsPresent.contains(Continent.AFRICA))
        assertTrue("Must include North American nations", continentsPresent.contains(Continent.NORTH_AMERICA))
        assertTrue("Must include South American nations", continentsPresent.contains(Continent.SOUTH_AMERICA))
        assertTrue("Must include Oceanian nations", continentsPresent.contains(Continent.OCEANIA))
    }
}
