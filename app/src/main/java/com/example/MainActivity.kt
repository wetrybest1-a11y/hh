package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.example.ui.screens.BattleRoyaleScreen
import com.example.ui.screens.CareerStatsScreen
import com.example.ui.screens.CountryOutfitsScreen
import com.example.ui.screens.LobbyScreen
import com.example.ui.screens.MatchResultsScreen
import com.example.ui.screens.WorldMapScreen
import com.example.ui.theme.MyApplicationTheme
import com.example.ui.theme.TacticalDarkBg
import com.example.ui.viewmodel.GameViewModel
import com.example.ui.viewmodel.ScreenState

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                val viewModel = remember { GameViewModel(applicationContext) }
                MainAppNavigation(viewModel)
            }
        }
    }
}

@Composable
fun MainAppNavigation(viewModel: GameViewModel) {
    val currentScreen by viewModel.currentScreen.collectAsState()

    // Handle system back button for sub-screens
    BackHandler(enabled = currentScreen != ScreenState.LOBBY) {
        if (currentScreen == ScreenState.BATTLE_ROYALE) {
            // Confirm or return to lobby from match
            viewModel.navigateTo(ScreenState.LOBBY)
        } else {
            viewModel.navigateTo(ScreenState.LOBBY)
        }
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = TacticalDarkBg
    ) {
        when (currentScreen) {
            ScreenState.LOBBY -> LobbyScreen(viewModel)
            ScreenState.COUNTRY_OUTFITS -> CountryOutfitsScreen(viewModel)
            ScreenState.WORLD_MAP -> WorldMapScreen(viewModel)
            ScreenState.BATTLE_ROYALE -> BattleRoyaleScreen(viewModel)
            ScreenState.MATCH_RESULTS -> MatchResultsScreen(viewModel)
            ScreenState.CAREER_STATS -> CareerStatsScreen(viewModel)
        }
    }
}
