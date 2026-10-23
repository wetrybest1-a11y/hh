package com.example.data.model

data class WorldDestination(
    val id: String,
    val name: String,
    val regionIndex: Int,
    val regionName: String,
    val environmentType: String,
    val difficulty: String,      // Easy, Normal, Hard, Extreme
    val lootTier: String,        // Tier 1 (Common), Tier 2 (Tactical), Tier 3 (Military), Tier 4 (Supply Drop)
    val recommendedPlayers: Int = 100,
    val unlockXp: Int = 0,
    val isDefaultUnlocked: Boolean = false,
    val description: String = "",
    val missionBriefing: String = ""
)

object WorldDestinationRegistry {
    val regions = listOf(
        "Eastern Riverlands",
        "Metropolitan Districts",
        "Desert Frontier",
        "Himalayan Highlands",
        "Central Forests",
        "Coastal Settlements",
        "Southern Heritage Towns",
        "Northeastern Hills",
        "Western Industrial Belt",
        "Island Outposts",
        "International Frontier Maps",
        "Special Survival Arenas"
    )

    val destinations: List<WorldDestination> by lazy {
        val list = mutableListOf<WorldDestination>()

        // 1. Eastern Riverlands (Destinations 1-10)
        val r1 = listOf(
            "Sundar Delta Marsh", "Hooghly Riverfront", "Ganges Crossing", "Dhamra Estuary",
            "Brahmani Bend", "Mayurakshi Valley", "Bhairab Creek", "Subarnarekha Docks",
            "Matla Mangrove Outpost", "Padma River Ferry"
        )
        r1.forEachIndexed { idx, name ->
            list.add(
                WorldDestination(
                    id = "dest_1_${idx + 1}",
                    name = name,
                    regionIndex = 0,
                    regionName = "Eastern Riverlands",
                    environmentType = "River Delta & Wet Village",
                    difficulty = if (idx < 3) "Easy" else "Normal",
                    lootTier = if (idx < 5) "Tier 2 (Tactical)" else "Tier 3 (Military)",
                    unlockXp = idx * 100,
                    isDefaultUnlocked = idx < 2,
                    description = "Dense waterways, wooden bridges, bamboo docks, and silt islands.",
                    missionBriefing = "Survive 5 minutes and loot 2 weapon crates near the river crossing."
                )
            )
        }

        // 2. Metropolitan Districts (Destinations 11-20)
        val r2 = listOf(
            "Central Tramway Terminal", "Esplanade Clock Square", "Howrah Rail Yards", "Chowringhee Grand Plaza",
            "Bhowanipore Rooftops", "Sealdah Cargo Junction", "Park Street Boulevard", "Salt Lake Tech District",
            "Shyambazar Five-Point", "Maidan Armory Zone"
        )
        r2.forEachIndexed { idx, name ->
            list.add(
                WorldDestination(
                    id = "dest_2_${idx + 1}",
                    name = name,
                    regionIndex = 1,
                    regionName = "Metropolitan Districts",
                    environmentType = "Dense Urban & Multi-floor Buildings",
                    difficulty = if (idx < 4) "Normal" else "Hard",
                    lootTier = "Tier 3 (Military)",
                    unlockXp = 500 + idx * 120,
                    isDefaultUnlocked = idx == 0,
                    description = "Multi-story colonial facades, narrow alleyways, tram tracks, and rooftop snipers.",
                    missionBriefing = "Engage in CQB urban warfare. Secure 3 sniper kills from high rooftops."
                )
            )
        }

        // 3. Desert Frontier (Destinations 21-30)
        val r3 = listOf(
            "Thar Dune Fort", "Jaisal Sand Bastion", "Bikaner Oasis Post", "Shekhawati Haveli Ruins",
            "Luni Dry Riverbed", "Pokhran Proving Grounds", "Barmer Ridge Outpost", "Sam Dunes Convoy",
            "Kuldhara Ghost Hamlet", "Rantham Gate Pass"
        )
        r3.forEachIndexed { idx, name ->
            list.add(
                WorldDestination(
                    id = "dest_3_${idx + 1}",
                    name = name,
                    regionIndex = 2,
                    regionName = "Desert Frontier",
                    environmentType = "Arid Sand Dunes & Stone Fortresses",
                    difficulty = if (idx < 5) "Normal" else "Hard",
                    lootTier = "Tier 3 (Military)",
                    unlockXp = 1000 + idx * 150,
                    isDefaultUnlocked = false,
                    description = "Massive sandstone ramparts, blinding dust winds, dry gulches, and long sightlines.",
                    missionBriefing = "Control the central fortress tower and hold off advancing dune patrols."
                )
            )
        }

        // 4. Himalayan Highlands (Destinations 31-40)
        val r4 = listOf(
            "Kanchen Ridge Monolith", "Rohtang Pass Tunnel", "Spiti Cliff Monastery", "Zanskar Glacier Shelf",
            "Leh High-Altitude Camp", "Dhauladhar Snow Base", "Kinnaur Gorge Bridge", "Nubra Valley Airfield",
            "Pangong Shore Radar", "Changla Peak Outpost"
        )
        r4.forEachIndexed { idx, name ->
            list.add(
                WorldDestination(
                    id = "dest_4_${idx + 1}",
                    name = name,
                    regionIndex = 3,
                    regionName = "Himalayan Highlands",
                    environmentType = "Snow Peaks & Mountain Passes",
                    difficulty = "Hard",
                    lootTier = "Tier 4 (Supply Drop)",
                    unlockXp = 2000 + idx * 200,
                    isDefaultUnlocked = false,
                    description = "Freezing winds, steep elevation changes, hanging rope bridges, and pine tree concealment.",
                    missionBriefing = "Extreme verticality combat. Eliminate 2 enemies with long-range rifles."
                )
            )
        }

        // 5. Central Forests (Destinations 41-50)
        val r5 = listOf(
            "Satpura Teak Canopy", "Kanha Woodland Clearing", "Panna Diamond Ravine", "Pench Wildlife Bunker",
            "Bandhav Stone Ridge", "Vindhya Iron Bluff", "Narmada River Gorges", "Amarkantak Springs",
            "Chhindwara Quarry", "Bhopal Chemical Ruins"
        )
        r5.forEachIndexed { idx, name ->
            list.add(
                WorldDestination(
                    id = "dest_5_${idx + 1}",
                    name = name,
                    regionIndex = 4,
                    regionName = "Central Forests",
                    environmentType = "Dense Tropical Jungle & Hidden Bunkers",
                    difficulty = "Normal",
                    lootTier = "Tier 2 (Tactical)",
                    unlockXp = 3000 + idx * 150,
                    isDefaultUnlocked = false,
                    description = "Dense underbrush, ancient stone temple ruins buried in roots, and rocky bluffs.",
                    missionBriefing = "Stealth tracker mission. Ambush enemy patrols without breaking cover."
                )
            )
        }

        // 6. Coastal Settlements (Destinations 51-60)
        val r6 = listOf(
            "Malabar Fishing Cove", "Konkan Cliff Haven", "Coromandel Lighthouse", "Gokarna Cove Docks",
            "Alibaug Fortress Island", "Cochin Cargo Wharves", "Puri Shoreline Bazaars", "Diu Coastal Battery",
            "Tuticorin Salt Flats", "Veraval Trawler Yard"
        )
        r6.forEachIndexed { idx, name ->
            list.add(
                WorldDestination(
                    id = "dest_6_${idx + 1}",
                    name = name,
                    regionIndex = 5,
                    regionName = "Coastal Settlements",
                    environmentType = "Harbor Docks, Beaches & Lighthouses",
                    difficulty = "Normal",
                    lootTier = "Tier 3 (Military)",
                    unlockXp = 4000 + idx * 180,
                    isDefaultUnlocked = false,
                    description = "Crashing surf, container crane yards, beached rusty freighters, and palm groves.",
                    missionBriefing = "Secure the harbor crane and intercept incoming waterborne supplies."
                )
            )
        }

        // 7. Southern Heritage Towns (Destinations 61-70)
        val r7 = listOf(
            "Madurai Gopuram Complex", "Hampi Boulder Plateau", "Thanjavur Palace Courtyard", "Mysore Royal Armory",
            "Chettinad Mansion Grid", "Kanchipuram Silk Vaults", "Warangal Stone Citadel", "Badami Cave Fortress",
            "Tirupati Valley Pass", "Belur Carved Sanctuary"
        )
        r7.forEachIndexed { idx, name ->
            list.add(
                WorldDestination(
                    id = "dest_7_${idx + 1}",
                    name = name,
                    regionIndex = 6,
                    regionName = "Southern Heritage Towns",
                    environmentType = "Ancient Stone Temples & Monolithic Boulders",
                    difficulty = "Hard",
                    lootTier = "Tier 3 (Military)",
                    unlockXp = 5000 + idx * 200,
                    isDefaultUnlocked = false,
                    description = "Towering stone gateways, labyrinthine pillared corridors, and sacred stepped tanks.",
                    missionBriefing = "Fight through sacred courtyards and claim the royal heirloom armaments."
                )
            )
        }

        // 8. Northeastern Hills (Destinations 71-80)
        val r8 = listOf(
            "Cherra Mist Plateau", "Dawki Crystal Waters", "Majuli Sand Island", "Tawang Cloud Gompa",
            "Ziro Pine Basin", "Dzukou Flower Valley", "Mawlynnong Living Roots", "Loktak Floating Biomass",
            "Sela Pass Bunker", "Naga Ridge Palisade"
        )
        r8.forEachIndexed { idx, name ->
            list.add(
                WorldDestination(
                    id = "dest_8_${idx + 1}",
                    name = name,
                    regionIndex = 7,
                    regionName = "Northeastern Hills",
                    environmentType = "Rain-soaked Hills, Cloud Forests & Waterfalls",
                    difficulty = "Hard",
                    lootTier = "Tier 3 (Military)",
                    unlockXp = 6000 + idx * 220,
                    isDefaultUnlocked = false,
                    description = "Torrential rains, hanging root bridges, misty ridges, and wooden stilt villages.",
                    missionBriefing = "Navigate dense fog cover and outflank opponents on narrow hill paths."
                )
            )
        }

        // 9. Western Industrial Belt (Destinations 81-90)
        val r9 = listOf(
            "Kandla Refinery Complex", "Dahej Petrochemical Towers", "Ankleshwar Chemical Tanks", "Hazira Steel Smelter",
            "Mundra Container Terminal", "Vadodara Transformer Yard", "Tarapur Cooling Basin", "Sanand Auto Assembly",
            "Morbi Ceramic Kilns", "Vapi Industrial Siphon"
        )
        r9.forEachIndexed { idx, name ->
            list.add(
                WorldDestination(
                    id = "dest_9_${idx + 1}",
                    name = name,
                    regionIndex = 8,
                    regionName = "Western Industrial Belt",
                    environmentType = "Heavy Industry, Pipelines & Steel Structures",
                    difficulty = "Hard",
                    lootTier = "Tier 4 (Supply Drop)",
                    unlockXp = 7500 + idx * 250,
                    isDefaultUnlocked = false,
                    description = "Tangled maze of gas pipelines, metal catwalks, chemical silos, and train spurs.",
                    missionBriefing = "High hazard zone! Use industrial pipes as elevated sniper perches."
                )
            )
        }

        // 10. Island Outposts (Destinations 91-100)
        val r10 = listOf(
            "Havelock Coral Atoll", "Neil Island Radio Mast", "Ross Penal Settlement", "Barren Island Volcano",
            "Lakshadweep Lagoon Pier", "Minicoy Lighthouse Key", "Kavaratti Marine Base", "Agatti Runway Strip",
            "Rutland Dense Jungle", "Narcondam Ridge Spire"
        )
        r10.forEachIndexed { idx, name ->
            list.add(
                WorldDestination(
                    id = "dest_10_${idx + 1}",
                    name = name,
                    regionIndex = 9,
                    regionName = "Island Outposts",
                    environmentType = "Isolated Tropical Islands & Volcanic Rock",
                    difficulty = "Hard",
                    lootTier = "Tier 4 (Supply Drop)",
                    unlockXp = 9000 + idx * 300,
                    isDefaultUnlocked = false,
                    description = "White sand beaches ringed with coral reefs, abandoned colonial radio towers, and active vents.",
                    missionBriefing = "Island survival challenge. Secure the island runway before the zone closes."
                )
            )
        }

        // 11. International Frontier Maps (Destinations 101-110)
        val r11 = listOf(
            "Khyber Pass Redoubt", "Chumbi Valley Transit", "Wakhjir Border Post", "Nathula Frontier Gate",
            "Lipulekh Pass Outpost", "Moreh Border Crossing", "Jaigaon Checkpoint", "Raxaul Transit Depot",
            "Petrapole Cargo Barrier", "Sir Creek Salt Marsh"
        )
        r11.forEachIndexed { idx, name ->
            list.add(
                WorldDestination(
                    id = "dest_11_${idx + 1}",
                    name = name,
                    regionIndex = 10,
                    regionName = "International Frontier Maps",
                    environmentType = "Fortified Border Checkpoints & Mountain Gates",
                    difficulty = "Extreme",
                    lootTier = "Tier 4 (Supply Drop)",
                    unlockXp = 11000 + idx * 350,
                    isDefaultUnlocked = false,
                    description = "Watchtowers, barbed-wire security perimeters, customs warehouses, and armored gates.",
                    missionBriefing = "Break through fortified gates. High enemy concentration and military hardware."
                )
            )
        }

        // 12. Special Survival Arenas (Destinations 111-120)
        val r12 = listOf(
            "Last Land: Sector Zero", "The Iron Colosseum", "Zone Omega: Radiation Core", "30-Minute Apex Dome",
            "Vanguard Thunder Ring", "Deadlock Quarry Arena", "Skyline Final Perimeter", "Ragnarok Bunker Complex",
            "Titan Steel Foundry", "Grand Champion Citadel"
        )
        r12.forEachIndexed { idx, name ->
            list.add(
                WorldDestination(
                    id = "dest_12_${idx + 1}",
                    name = name,
                    regionIndex = 11,
                    regionName = "Special Survival Arenas",
                    environmentType = "High-Stakes Battle Royale Tournament Arenas",
                    difficulty = "Extreme",
                    lootTier = "Tier 4 (Supply Drop)",
                    unlockXp = 14000 + idx * 400,
                    isDefaultUnlocked = false,
                    description = "Custom-engineered survival arenas designed for rapid zone contractions and intense endgame combat.",
                    missionBriefing = "The ultimate 30-minute test. Final survivor takes all championship honors."
                )
            )
        }

        list
    }
}
