package com.example

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.example.data.repository.GameRepository
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [34])
class ExampleRobolectricTest {

    @Test
    fun `read string from context`() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val appName = context.getString(R.string.app_name)
        assertEquals("Last Land", appName)
    }

    @Test
    fun `test repository outfit persistence and default equipped India`() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val repo = GameRepository(context)
        val profile = repo.loadProfile()

        assertNotNull(profile)
        assertEquals("Default equipped outfit must be India (IN)", "IN", profile.equippedOutfitId)
        assertTrue("India must be unlocked initially", profile.unlockedOutfitIds.contains("IN"))

        // Test equipping another outfit
        repo.saveEquippedOutfit("JP")
        val updatedProfile = repo.loadProfile()
        assertEquals("Equipped outfit must persist in repository", "JP", updatedProfile.equippedOutfitId)
    }
}
