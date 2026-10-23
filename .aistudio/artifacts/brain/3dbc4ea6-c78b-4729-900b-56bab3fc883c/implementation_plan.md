# LAST LAND: 30 MINUTES TO SURVIVE — Country Flag Outfits & Battle Royale

A high-intensity, Indian-inspired 3D Battle Royale survival game featuring an expansive **Country Flag Outfits & National-Themed Character Customization System** with 120 authentic national outfits, interactive 3D character preview, 120-destination world progression, responsive third-person combat, shrinking safe zone, and tactical loot mechanics.

---

## User Review & Critical Decisions

> [!IMPORTANT]
> The following user preferences were established during Phase 1 interactive clarification:
> - **Game Experience Scope**: Full hybrid experience integrating both the 3D Battle Royale survival match and the comprehensive 120-Country Flag Outfits 3D Customization Locker.
> - **Visual & Outfit Aesthetic**: Tactical combat uniform styling featuring authentic national flag color palettes, arm camouflage, chest insignia, and high-fidelity shoulder flag patches.
> - **Match Flow & Pacing**: Action-packed battle royale survival loop with fast-paced bot encounters, dynamic shrinking hazard zones, weapon pickups, and a prominent countdown timer.

- **Confirmed Decision 1**: Default starting outfit is **India** (saffron, pristine white, forest green tactical uniform with navy blue Ashoka chakra emblem), with all 120 countries cataloged across Asia, Europe, Africa, North America, South America, and Oceania.
- **Confirmed Decision 2**: Mobile-optimized high-performance 3D rendering pipeline built directly on Compose Canvas / hardware-accelerated 3D projection, ensuring 60 FPS smooth rotation, front/side/back camera angles, and seamless in-match outfit fidelity.
- **Confirmed Decision 3**: Outfits are strictly cosmetic with zero pay-to-win advantages (balanced health, speed, and hitboxes).

---

## 1. Overview & Core Concept

**LAST LAND: 30 MINUTES TO SURVIVE** is a fictional tactical survival battle royale set in fictionalized Indian environments (riverlands, desert frontiers, Himalayan foothills, dense urban bazaars, coastal docks, and industrial corridors). 

Players gear up with tactical uniforms representing 120 countries, drop into the battlefield, scavenge weapons (assault rifles, SMGs, shotguns, sniper rifles, pistols), defeat hostile AI combatants, manage inventory and armor, and outrun the contracting radiation zone to stand as the ultimate victor.

### Key Value
- **120 Country Flag Outfits Collection**: Deep cosmetic customization with search, filtering by continent/rarity, favorites, and 3D rotating preview (front, back, left, right, free orbit).
- **Tactical Survival Combat**: Complete third-person shooting mechanics (virtual joystick, aim & swipe camera, fire, reload, jump, crouch, sprint stamina, weapon switching).
- **120-Location World Progression**: 12 themed regional zones featuring 10 destinations each with unlock requirements and mission challenges.
- **Persistent Progression**: Local persistence for equipped outfit, unlocked countries, player XP, level, match kills, survival duration, and match history.

---

## 2. User Experience & Visual Design

### Key User Flows

```
┌─────────────────────────────────────────────────────────────────────────────┐
│                             MAIN LOBBY SCREEN                               │
│  - Player Banner (Equipped Country Flag, Level, XP, Coins)                  │
│  - 3D Character Preview with equipped country uniform & animations          │
│  - Quick Match Modes: Solo Survival, Duo, Squad, Practice                   │
│  - Navigation Tabs: [PLAY]  [COUNTRY OUTFITS]  [WORLD MAP]  [CAREER/STATS]  │
└──────────────┬───────────────────────────────┬──────────────────────────────┘
               │                               │
               ▼                               ▼
┌──────────────────────────────┐ ┌────────────────────────────────────────────┐
│    COUNTRY OUTFITS LOCKER    │ │      120-DESTINATION WORLD MAP             │
│  - Search bar + Continent    │ │  - 12 Indian & Global Regions              │
│  - 120 Country Grid w/ Flags │ │  - 10 Locations per Region (120 total)     │
│  - Interactive 3D Turntable  │ │  - Mission details, loot quality, diff     │
│  - Front / Back / Side views │ │  - [DEPLOY TO REGION]                      │
│  - [FAVORITE] & [EQUIP]      │ └─────────────────────┬──────────────────────┘
└──────────────┬───────────────┘                       │
               │                                       │
               └───────────────────┬───────────────────┘
                                   │
                                   ▼
┌─────────────────────────────────────────────────────────────────────────────┐
│                    3D BATTLE ROYALE SURVIVAL MATCH                          │
│  - 3D Environment: Buildings, roads, cover, watchtowers, terrain            │
│  - Real-time 3D Player rendering equipped Country Flag tactical outfit       │
│  - Controls: Virtual movement stick, smooth touch look, Fire, Aim, Sprint   │
│  - Tactical HUD: Compass, Minimap, Safe Zone ring & timer, HP, Armor, Ammo  │
│  - Dynamic AI Enemies patrolling, looting, taking cover, engaging in combat │
│  - Contracting Danger Zone dealing progressive out-of-zone damage           │
│  - Victory / Defeat screen with detailed breakdown & XP awards              │
└─────────────────────────────────────────────────────────────────────────────┘
```

### Visual Identity & Theme
- **Color Palette**: Dark tactical charcoal (`#0F1115`), gunmetal surface (`#1A1E24`), vibrant tactical amber/orange accents (`#FF7A00` & `#FFA000`), combat crimson (`#FF3B30`), and neon tactical cyan (`#00E5FF`).
- **Outfit Rendering**: Authentic RGB national flag hex colors for each of the 120 countries, rendering high-contrast geometric striping, stars, circles, shields, and crisp shoulder patches.
- **Atmosphere**: Gritty battle-ready military aesthetic with brushed metal cards, glowing radar sweeps, tactical gridlines, and clean typography.
- **Audio Feedback**: Procedural audio synthesizer for gunshots, reload clicks, zone alerts, footsteps, button taps, and victory fanfares.

---

## 3. Key Product Decisions & Trade-Offs

### 1. 120 Country Flag Dataset & Procedural Texture Generation
- **Chosen Approach**: Built-in comprehensive registry of 120 sovereign nations with exact ISO codes, continental tags, canonical hex color palettes, and procedural vector flag heraldry (stripes, crosses, cantons, suns, emblems) paired with tactical textures.
- **Why**: Eliminates massive network dependencies or heavy external image downloads that could fail or lag on device; guarantees instantaneous 60 FPS offline responsiveness and crisp rendering at any zoom level.

### 2. High-Performance 3D Character & Arena Engine in Kotlin/Compose
- **Chosen Approach**: A high-efficiency 3D software rasterization / vector projection engine built directly into Compose hardware-accelerated Canvas with depth-sorting, backface culling, dynamic directional lighting, and skeletal rigging.
- **Why**: Ensures 100% build reliability without brittle native C++ NDK bindings or external WebViews, allowing fluid touch controls, instant rendering, camera orbiting, and smooth 60 FPS combat simulation on any Android device.

### 3. Complete 120-Location World Progression Matrix
- **Chosen Approach**: 12 structured regional sectors (Eastern Riverlands, Metropolitan Districts, Desert Frontier, Himalayan Highlands, Central Forests, Coastal Ports, Southern Heritage Towns, Northeastern Hills, Western Industrial, Island Outposts, International Frontiers, Special Survival Arenas) with 10 distinct destinations each, featuring difficulty ratings, environmental conditions, and unlock progress.
- **Why**: Fulfills the master specification with deep replayability, clear objectives, and progressive unlock milestones tied to match survival.

---

## 4. Technical Architecture & Data Strategy

### Component Hierarchy

```
┌─────────────────────────────────────────────────────────────────────────────┐
│                               MainActivity                                  │
│   ├── GameViewModel (StateFlow: GameState, PlayerProfile, Outfits, Match)   │
│   └── SoundManager (Synthesized procedural audio effects)                   │
└──────────────────────────────────────┬──────────────────────────────────────┘
                                       │
        ┌──────────────────────────────┼──────────────────────────────┐
        ▼                              ▼                              ▼
┌───────────────┐              ┌───────────────┐              ┌───────────────┐
│  LobbyScreen  │              │ CountryOutfit │              │ WorldMapScreen│
│  - Hero 3D    │              │ LockerScreen  │              │ - 12 Regions  │
│  - Mode Select│              │ - 120 Outfits │              │ - 120 Dests   │
│  - Daily Missions            │ - 3D Turntable│              │ - Unlock Path │
└───────┬───────┘              │ - Search/Filter              └───────┬───────┘
        │                      └───────┬───────┘                      │
        └──────────────────────────────┼──────────────────────────────┘
                                       ▼
                       ┌───────────────────────────────┐
                       │       BattleRoyaleScreen      │
                       │   ├── Game3DRenderer (Canvas) │
                       │   ├── VirtualJoystick         │
                       │   ├── TouchAimLookController  │
                       │   ├── HUDOverlay & Minimap    │
                       │   ├── LootPickupModal         │
                       │   └── GameOverDialog          │
                       └───────────────────────────────┘
```

### Core Data Models
- **`CountryOutfit`**: `id`, `name`, `isoCode`, `continent`, `primaryColor`, `secondaryColor`, `accentColor`, `flagPatternType`, `emblemType`, `isUnlocked`, `unlockRequirement`, `rarity`, `isFavorite`, `flavorText`.
- **`WorldDestination`**: `id`, `name`, `regionName`, `regionIndex`, `environmentType`, `difficulty`, `lootTier`, `isUnlocked`, `unlockXpRequired`, `bestPlacement`.
- **`Weapon`**: `type` (AssaultRifle, SMG, Shotgun, Sniper, Pistol), `name`, `damage`, `fireRateMs`, `magazineSize`, `ammo`, `maxAmmo`, `range`, `spread`, `recoil`.
- **`AICombatant`**: `id`, `name`, `position` (Vector3), `health`, `maxHealth`, `outfitId`, `weapon`, `state` (Patrol, SearchLoot, Engage, SeekSafeZone, Flee), `targetPos`.
- **`MatchState`**: `matchTimerSeconds` (30:00 countdown), `safeZoneRadius`, `nextSafeZoneRadius`, `safeZoneCenter`, `alivePlayersCount`, `killCount`, `damageDealt`, `isOver`, `isVictory`.

---

## Verification Plan

### Automated & Compilation Tests
- Run `compile_applet` to verify flawless compilation of Kotlin code, Jetpack Compose layouts, data registries, and 3D rendering pipeline.
- Verify that all 120 country outfits and 120 world destinations are instantiated, indexed, and queryable.

### Manual Verification of Acceptance Criteria
1. **120 Country Outfits Menu**: Browse, search by country name (e.g., India, Japan, Brazil, France, United States), filter by continent.
2. **Interactive 3D Preview**: Rotate character in 360 degrees, toggle Front / Back / Side views, observe flag patterns, chest emblem, and shoulder patch.
3. **Equip & Persistence**: Equip India or unlocked countries; verify persistence across app sessions and lobby display.
4. **Active 3D Battle Royale**: Start a match; verify equipped country outfit is rendered on the third-person player model while walking, sprinting, aiming, and firing.
5. **Combat & Zone Dynamics**: Collect ground loot weapons, engage intelligent AI bots with hitmarkers, observe 30:00 countdown and shrinking safe zone ring with out-of-zone damage.
6. **Victory & Progression**: Survive and claim #1 Victory Royale; verify XP gains, match stats, and unlock progress.
