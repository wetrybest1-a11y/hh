package com.example.data.model

import androidx.compose.ui.graphics.Color

enum class Continent(val displayName: String) {
    ALL("All Regions"),
    ASIA("Asia"),
    EUROPE("Europe"),
    AFRICA("Africa"),
    NORTH_AMERICA("North America"),
    SOUTH_AMERICA("South America"),
    OCEANIA("Oceania")
}

enum class FlagPatternType {
    HORIZONTAL_TRICOLOR,   // e.g. India, Germany, Russia, Netherlands
    VERTICAL_TRICOLOR,     // e.g. France, Italy, Ireland, Belgium
    NORDIC_CROSS,          // e.g. Sweden, Norway, Denmark, Finland
    UNION_JACK,            // e.g. United Kingdom
    DISC_CENTER,           // e.g. Japan, Bangladesh, Niger
    CANTON_STRIPES,        // e.g. USA, Greece, Malaysia, Liberia
    DIAGONAL_CROSS,        // e.g. Scotland, Jamaica
    DOUBLE_PENNANT,        // e.g. Nepal
    SOLID_EMBLEM,          // e.g. Brazil, Canada, Vietnam, Switzerland, Turkey
    HORIZONTAL_BICOLOR,    // e.g. Poland, Ukraine, Indonesia
    VERTICAL_BICOLOR       // e.g. Portugal, Vatican
}

enum class EmblemType {
    NONE,
    ASHOKA_CHAKRA,  // India
    RED_DISC,       // Japan
    MAPLE_LEAF,     // Canada
    STAR_CRESCENT,  // Turkey, Pakistan, Algeria, Malaysia
    SINGLE_STAR,    // Vietnam, Chile, Ghana, Cuba
    BRAZIL_GLOBE,   // Brazil
    WHITE_CROSS,    // Switzerland
    EAGLE,          // Mexico, Egypt, Albania
    CEDAR_TREE,     // Lebanon
    SOUTHERN_CROSS, // Australia, New Zealand
    SUN_RAYS,       // Argentina, Uruguay, Philippines
    NEPAL_SHAPES,   // Nepal
    SHIELD
}

enum class OutfitRarity(val label: String, val colorValue: Long) {
    COMMON("Common", 0xFFB0BEC5),
    RARE("Rare", 0xFF29B6F6),
    EPIC("Epic", 0xFFAB47BC),
    LEGENDARY("Legendary", 0xFFFFB300),
    MYTHIC("Mythic", 0xFFFF1744)
}

data class CountryOutfit(
    val id: String,
    val name: String,
    val isoCode: String,
    val continent: Continent,
    val primaryColor: Long,    // Primary jacket/shirt color
    val secondaryColor: Long,  // Secondary pattern/stripe color
    val accentColor: Long,     // Accent trim/emblem color
    val flagPattern: FlagPatternType,
    val emblem: EmblemType = EmblemType.NONE,
    val rarity: OutfitRarity = OutfitRarity.COMMON,
    val unlockRequirement: String = "Unlocked by default",
    val isDefaultUnlocked: Boolean = false,
    val tacticalGearStyle: String = "Urban Commando",
    val description: String = ""
)

object CountryOutfitRegistry {
    val outfits: List<CountryOutfit> = listOf(
        // ================= ASIA (Featured: INDIA) =================
        CountryOutfit(
            id = "IN",
            name = "India",
            isoCode = "IND",
            continent = Continent.ASIA,
            primaryColor = 0xFFFF9933,   // Saffron
            secondaryColor = 0xFFFFFFFF, // White
            accentColor = 0xFF138808,    // Green
            flagPattern = FlagPatternType.HORIZONTAL_TRICOLOR,
            emblem = EmblemType.ASHOKA_CHAKRA,
            rarity = OutfitRarity.MYTHIC,
            unlockRequirement = "Default Starter Outfit",
            isDefaultUnlocked = true,
            tacticalGearStyle = "Tricolor Elite Operative",
            description = "National tactical uniform featuring Saffron, White, and Green tricolor banding with navy Ashoka Chakra chest badge."
        ),
        CountryOutfit(
            id = "JP",
            name = "Japan",
            isoCode = "JPN",
            continent = Continent.ASIA,
            primaryColor = 0xFFF5F5F5,   // White
            secondaryColor = 0xFFBC002D, // Crimson Red
            accentColor = 0xFF212121,
            flagPattern = FlagPatternType.DISC_CENTER,
            emblem = EmblemType.RED_DISC,
            rarity = OutfitRarity.LEGENDARY,
            unlockRequirement = "Reach Survivor Level 2",
            isDefaultUnlocked = true,
            tacticalGearStyle = "Rising Sun Spec-Ops",
            description = "Crisp arctic white ballistic weave with bold crimson disc crest on chest and shoulders."
        ),
        CountryOutfit(
            id = "BD",
            name = "Bangladesh",
            isoCode = "BGD",
            continent = Continent.ASIA,
            primaryColor = 0xFF006A4E,   // Bottle Green
            secondaryColor = 0xFFF42A41, // Red Disc
            accentColor = 0xFF1B5E20,
            flagPattern = FlagPatternType.DISC_CENTER,
            emblem = EmblemType.RED_DISC,
            rarity = OutfitRarity.RARE,
            unlockRequirement = "Survive 5 minutes in any match",
            isDefaultUnlocked = true,
            tacticalGearStyle = "Sundarbans Ranger",
            description = "Deep bottle green combat jacket with crimson disc shoulder insignia."
        ),
        CountryOutfit(
            id = "NP",
            name = "Nepal",
            isoCode = "NPL",
            continent = Continent.ASIA,
            primaryColor = 0xFFDC143C,   // Crimson
            secondaryColor = 0xFF003893, // Deep Blue border
            accentColor = 0xFFFFFFFF,    // White emblems
            flagPattern = FlagPatternType.DOUBLE_PENNANT,
            emblem = EmblemType.NEPAL_SHAPES,
            rarity = OutfitRarity.EPIC,
            unlockRequirement = "Complete Himalayan Foothills exploration",
            isDefaultUnlocked = true,
            tacticalGearStyle = "Gorkha Mountain Division",
            description = "Iconic double-pennant angled accents with deep Himalayan crimson and royal blue trim."
        ),
        CountryOutfit(
            id = "KR",
            name = "South Korea",
            isoCode = "KOR",
            continent = Continent.ASIA,
            primaryColor = 0xFFEEEEEE,   // White
            secondaryColor = 0xFFCD2E3A, // Red
            accentColor = 0xFF0047A0,    // Blue
            flagPattern = FlagPatternType.DISC_CENTER,
            emblem = EmblemType.RED_DISC,
            rarity = OutfitRarity.EPIC,
            unlockRequirement = "Eliminate 3 enemy combatants",
            isDefaultUnlocked = true,
            tacticalGearStyle = "Taegeuk Vanguard",
            description = "White high-durability polymers with dynamic red-and-blue Yin-Yang chest emblem."
        ),
        CountryOutfit(
            id = "LK",
            name = "Sri Lanka",
            isoCode = "LKA",
            continent = Continent.ASIA,
            primaryColor = 0xFF8D153A,   // Maroon
            secondaryColor = 0xFFFFBE29, // Gold
            accentColor = 0xFF00534E,    // Green
            flagPattern = FlagPatternType.HORIZONTAL_TRICOLOR,
            emblem = EmblemType.SHIELD,
            rarity = OutfitRarity.RARE,
            unlockRequirement = "Score top 10 finish in Solo mode",
            isDefaultUnlocked = true,
            tacticalGearStyle = "Serendib Coastal Guard",
            description = "Regal maroon and gold trim with lion heraldry chest emblem."
        ),
        CountryOutfit(
            id = "PK",
            name = "Pakistan",
            isoCode = "PAK",
            continent = Continent.ASIA,
            primaryColor = 0xFF01411C,   // Dark Green
            secondaryColor = 0xFFFFFFFF, // White
            accentColor = 0xFFE0E0E0,
            flagPattern = FlagPatternType.SOLID_EMBLEM,
            emblem = EmblemType.STAR_CRESCENT,
            rarity = OutfitRarity.RARE,
            unlockRequirement = "Loot 5 weapon crates",
            isDefaultUnlocked = true,
            tacticalGearStyle = "Northern Ridge Scout",
            description = "Dark forest green body armor with white crescent and star insignia."
        ),
        CountryOutfit(
            id = "BT",
            name = "Bhutan",
            isoCode = "BTN",
            continent = Continent.ASIA,
            primaryColor = 0xFFFFD520,   // Golden Yellow
            secondaryColor = 0xFFFF4E12, // Orange
            accentColor = 0xFFFFFFFF,    // White dragon motif
            flagPattern = FlagPatternType.DIAGONAL_CROSS,
            emblem = EmblemType.SHIELD,
            rarity = OutfitRarity.EPIC,
            unlockRequirement = "Reach Survivor Level 3",
            isDefaultUnlocked = true,
            tacticalGearStyle = "Thunder Dragon Commando",
            description = "Diagonal golden yellow and fiery orange divide with white dragon motifs."
        ),
        CountryOutfit(
            id = "TH",
            name = "Thailand",
            isoCode = "THA",
            continent = Continent.ASIA,
            primaryColor = 0xFF2D2A4A,   // Indigo Blue
            secondaryColor = 0xFFA51931, // Red
            accentColor = 0xFFF4F5F8,    // White
            flagPattern = FlagPatternType.HORIZONTAL_TRICOLOR,
            rarity = OutfitRarity.RARE,
            unlockRequirement = "Earn 500 combat XP",
            isDefaultUnlocked = true,
            tacticalGearStyle = "Siam Strike Force",
            description = "Red, white, and deep indigo five-stripe tactical layout."
        ),
        CountryOutfit(
            id = "VN",
            name = "Vietnam",
            isoCode = "VNM",
            continent = Continent.ASIA,
            primaryColor = 0xFFDA251D,   // Red
            secondaryColor = 0xFFFFCD00, // Gold star
            accentColor = 0xFF212121,
            flagPattern = FlagPatternType.SOLID_EMBLEM,
            emblem = EmblemType.SINGLE_STAR,
            rarity = OutfitRarity.RARE,
            unlockRequirement = "Survive 10 minutes outside the danger zone",
            isDefaultUnlocked = true,
            tacticalGearStyle = "Mekong Delta Pathfinder",
            description = "Fiery red tactical weave emblazoned with a prominent golden five-point star."
        ),
        CountryOutfit(
            id = "ID",
            name = "Indonesia",
            isoCode = "IDN",
            continent = Continent.ASIA,
            primaryColor = 0xFFFF0000,   // Red
            secondaryColor = 0xFFFFFFFF, // White
            accentColor = 0xFF212121,
            flagPattern = FlagPatternType.HORIZONTAL_BICOLOR,
            rarity = OutfitRarity.COMMON,
            unlockRequirement = "Unlocked by default",
            isDefaultUnlocked = true,
            tacticalGearStyle = "Garuda Tactical Operative",
            description = "Crisp red and white horizontal tactical split."
        ),
        CountryOutfit(
            id = "MY",
            name = "Malaysia",
            isoCode = "MYS",
            continent = Continent.ASIA,
            primaryColor = 0xFF010066,   // Royal Blue
            secondaryColor = 0xFFCC0000, // Red stripes
            accentColor = 0xFFFFCC00,    // Yellow crescent
            flagPattern = FlagPatternType.CANTON_STRIPES,
            emblem = EmblemType.STAR_CRESCENT,
            rarity = OutfitRarity.RARE,
            unlockRequirement = "Collect 3 assault rifles",
            isDefaultUnlocked = true,
            tacticalGearStyle = "Harimau Jungle Striker",
            description = "Red and white striped sleeves with a royal blue chest plate and golden star."
        ),
        CountryOutfit(
            id = "SG",
            name = "Singapore",
            isoCode = "SGP",
            continent = Continent.ASIA,
            primaryColor = 0xFFED2939,   // Red
            secondaryColor = 0xFFFFFFFF, // White
            accentColor = 0xFF37474F,
            flagPattern = FlagPatternType.HORIZONTAL_BICOLOR,
            emblem = EmblemType.STAR_CRESCENT,
            rarity = OutfitRarity.EPIC,
            unlockRequirement = "Finish in Top 5 in Solo mode",
            isDefaultUnlocked = true,
            tacticalGearStyle = "Lion City Urban Guard",
            description = "High-tech red and white body suit with crescent and five stars on collar."
        ),
        CountryOutfit(
            id = "PH",
            name = "Philippines",
            isoCode = "PHL",
            continent = Continent.ASIA,
            primaryColor = 0xFF0038A8,   // Royal Blue
            secondaryColor = 0xFFCE1126, // Scarlet Red
            accentColor = 0xFFFCD116,    // Golden Sun
            flagPattern = FlagPatternType.HORIZONTAL_BICOLOR,
            emblem = EmblemType.SUN_RAYS,
            rarity = OutfitRarity.RARE,
            unlockRequirement = "Revive a squadmate or survive 8 min",
            isDefaultUnlocked = true,
            tacticalGearStyle = "Archipelago Marine",
            description = "Blue and scarlet two-tone armor with golden sun insignia."
        ),
        CountryOutfit(
            id = "AE",
            name = "United Arab Emirates",
            isoCode = "ARE",
            continent = Continent.ASIA,
            primaryColor = 0xFF00732F,   // Green
            secondaryColor = 0xFFFFFFFF, // White
            accentColor = 0xFFFF0000,    // Red vertical strip
            flagPattern = FlagPatternType.HORIZONTAL_TRICOLOR,
            rarity = OutfitRarity.EPIC,
            unlockRequirement = "Earn 1,000 match XP",
            isDefaultUnlocked = true,
            tacticalGearStyle = "Dune Mirage Recon",
            description = "Pan-Arab green, white, black, and red bold tactical layout."
        ),
        CountryOutfit(
            id = "SA",
            name = "Saudi Arabia",
            isoCode = "SAU",
            continent = Continent.ASIA,
            primaryColor = 0xFF006C35,   // Saudi Green
            secondaryColor = 0xFFFFFFFF, // White
            accentColor = 0xFF1B5E20,
            flagPattern = FlagPatternType.SOLID_EMBLEM,
            emblem = EmblemType.SHIELD,
            rarity = OutfitRarity.RARE,
            unlockRequirement = "Eliminate 5 enemies across all matches",
            isDefaultUnlocked = true,
            tacticalGearStyle = "Desert Falcon Operative",
            description = "Rich emerald green desert weave with white emblem and dual saber patch."
        ),
        CountryOutfit(
            id = "IL",
            name = "Israel",
            isoCode = "ISR",
            continent = Continent.ASIA,
            primaryColor = 0xFFFFFFFF,   // White
            secondaryColor = 0xFF0038B8, // Blue
            accentColor = 0xFF1976D2,
            flagPattern = FlagPatternType.HORIZONTAL_TRICOLOR,
            emblem = EmblemType.SINGLE_STAR,
            rarity = OutfitRarity.RARE,
            unlockRequirement = "Use 3 first-aid medkits",
            isDefaultUnlocked = true,
            tacticalGearStyle = "Desert Recon Specialist",
            description = "Crisp white battle gear with dual horizontal cobalt stripes."
        ),
        CountryOutfit(
            id = "QA",
            name = "Qatar",
            isoCode = "QAT",
            continent = Continent.ASIA,
            primaryColor = 0xFF8A1538,   // Maroon
            secondaryColor = 0xFFFFFFFF, // White serrated
            accentColor = 0xFF4A0E17,
            flagPattern = FlagPatternType.VERTICAL_BICOLOR,
            rarity = OutfitRarity.EPIC,
            unlockRequirement = "Reach Survivor Level 4",
            isDefaultUnlocked = true,
            tacticalGearStyle = "Oryx Vanguard",
            description = "Maroon and white segmented tactical combat armor."
        ),
        CountryOutfit(
            id = "TR",
            name = "Turkey",
            isoCode = "TUR",
            continent = Continent.ASIA,
            primaryColor = 0xFFE30A17,   // Crimson Red
            secondaryColor = 0xFFFFFFFF, // White
            accentColor = 0xFFB71C1C,
            flagPattern = FlagPatternType.SOLID_EMBLEM,
            emblem = EmblemType.STAR_CRESCENT,
            rarity = OutfitRarity.RARE,
            unlockRequirement = "Win 1 match or deal 1,000 damage",
            isDefaultUnlocked = true,
            tacticalGearStyle = "Anatolian Commando",
            description = "Crimson assault fatigues with crisp white crescent and star emblem."
        ),
        CountryOutfit(
            id = "KZ",
            name = "Kazakhstan",
            isoCode = "KAZ",
            continent = Continent.ASIA,
            primaryColor = 0xFF00AFCA,   // Sky Blue
            secondaryColor = 0xFFFEC50C, // Gold
            accentColor = 0xFF00838F,
            flagPattern = FlagPatternType.SOLID_EMBLEM,
            emblem = EmblemType.SUN_RAYS,
            rarity = OutfitRarity.EPIC,
            unlockRequirement = "Survive 12 minutes in any match",
            isDefaultUnlocked = true,
            tacticalGearStyle = "Steppe Eagle Pathfinder",
            description = "Sky blue ballistic plates with golden soaring eagle motif."
        ),
        CountryOutfit(
            id = "UZ",
            name = "Uzbekistan",
            isoCode = "UZB",
            continent = Continent.ASIA,
            primaryColor = 0xFF0099B5,   // Cyan Blue
            secondaryColor = 0xFFFFFFFF, // White
            accentColor = 0xFF1EB53A,    // Green
            flagPattern = FlagPatternType.HORIZONTAL_TRICOLOR,
            emblem = EmblemType.STAR_CRESCENT,
            rarity = OutfitRarity.RARE,
            unlockRequirement = "Collect 100 rounds of rifle ammo",
            isDefaultUnlocked = true,
            tacticalGearStyle = "Silk Road Ranger",
            description = "Vibrant azure, white, and emerald horizontal tactical banding."
        ),
        CountryOutfit(
            id = "MN",
            name = "Mongolia",
            isoCode = "MNG",
            continent = Continent.ASIA,
            primaryColor = 0xFFE4002B,   // Red
            secondaryColor = 0xFF003865, // Blue
            accentColor = 0xFFFFD100,    // Soyombo Gold
            flagPattern = FlagPatternType.VERTICAL_TRICOLOR,
            emblem = EmblemType.SHIELD,
            rarity = OutfitRarity.EPIC,
            unlockRequirement = "Reach Survivor Level 5",
            isDefaultUnlocked = false,
            tacticalGearStyle = "Steppe Nomad Raider",
            description = "Red and deep blue vertical split with golden Soyombo crest."
        ),
        CountryOutfit(
            id = "MM",
            name = "Myanmar",
            isoCode = "MMR",
            continent = Continent.ASIA,
            primaryColor = 0xFFFECB00,   // Yellow
            secondaryColor = 0xFF34B233, // Green
            accentColor = 0xFFEA2839,    // Red
            flagPattern = FlagPatternType.HORIZONTAL_TRICOLOR,
            emblem = EmblemType.SINGLE_STAR,
            rarity = OutfitRarity.COMMON,
            unlockRequirement = "Survive 3 minutes",
            isDefaultUnlocked = true,
            tacticalGearStyle = "Teak Forest Tracker",
            description = "Yellow, green, and red stripes with prominent white central star."
        ),
        CountryOutfit(
            id = "KH",
            name = "Cambodia",
            isoCode = "KHM",
            continent = Continent.ASIA,
            primaryColor = 0xFF032EA6,   // Blue
            secondaryColor = 0xFFE00025, // Red
            accentColor = 0xFFFFFFFF,    // White Angkor Wat
            flagPattern = FlagPatternType.HORIZONTAL_TRICOLOR,
            emblem = EmblemType.SHIELD,
            rarity = OutfitRarity.RARE,
            unlockRequirement = "Collect 2 first-aid kits",
            isDefaultUnlocked = true,
            tacticalGearStyle = "Angkor Guardian",
            description = "Blue and crimson bands featuring Angkor silhouette shoulder badge."
        ),

        // ================= EUROPE =================
        CountryOutfit(
            id = "GB",
            name = "United Kingdom",
            isoCode = "GBR",
            continent = Continent.EUROPE,
            primaryColor = 0xFF012169,   // Navy Blue
            secondaryColor = 0xFFC8102E, // Royal Red
            accentColor = 0xFFFFFFFF,    // White
            flagPattern = FlagPatternType.UNION_JACK,
            rarity = OutfitRarity.LEGENDARY,
            unlockRequirement = "Achieve 5 headshot eliminations",
            isDefaultUnlocked = true,
            tacticalGearStyle = "Royal Marine Commando",
            description = "Dynamic Union Jack cross patterns across tactical vest and shoulders."
        ),
        CountryOutfit(
            id = "FR",
            name = "France",
            isoCode = "FRA",
            continent = Continent.EUROPE,
            primaryColor = 0xFF002395,   // Blue
            secondaryColor = 0xFFFFFFFF, // White
            accentColor = 0xFFED2939,    // Red
            flagPattern = FlagPatternType.VERTICAL_TRICOLOR,
            rarity = OutfitRarity.RARE,
            unlockRequirement = "Reach Survivor Level 2",
            isDefaultUnlocked = true,
            tacticalGearStyle = "GIGN Urban Tactical",
            description = "Classic blue, white, and red vertical tricolor panels on dark armor."
        ),
        CountryOutfit(
            id = "DE",
            name = "Germany",
            isoCode = "DEU",
            continent = Continent.EUROPE,
            primaryColor = 0xFF212121,   // Black
            secondaryColor = 0xFFFF0000, // Red
            accentColor = 0xFFFFCC00,    // Gold
            flagPattern = FlagPatternType.HORIZONTAL_TRICOLOR,
            rarity = OutfitRarity.RARE,
            unlockRequirement = "Eliminate 2 enemies with an assault rifle",
            isDefaultUnlocked = true,
            tacticalGearStyle = "GSG Heavy Assault",
            description = "Matte black body armor with vibrant red and gold horizontal chest striping."
        ),
        CountryOutfit(
            id = "IT",
            name = "Italy",
            isoCode = "ITA",
            continent = Continent.EUROPE,
            primaryColor = 0xFF009246,   // Green
            secondaryColor = 0xFFFFFFFF, // White
            accentColor = 0xFFCE2B37,    // Red
            flagPattern = FlagPatternType.VERTICAL_TRICOLOR,
            rarity = OutfitRarity.RARE,
            unlockRequirement = "Sprint a total of 1,000 meters",
            isDefaultUnlocked = true,
            tacticalGearStyle = "Alpine Bersaglieri",
            description = "Green, white, and red vertical tricolor tactical uniform with carbon accents."
        ),
        CountryOutfit(
            id = "ES",
            name = "Spain",
            isoCode = "ESP",
            continent = Continent.EUROPE,
            primaryColor = 0xFFAA151B,   // Red
            secondaryColor = 0xFFF1BF00, // Yellow
            accentColor = 0xFF7B0000,
            flagPattern = FlagPatternType.HORIZONTAL_TRICOLOR,
            emblem = EmblemType.SHIELD,
            rarity = OutfitRarity.RARE,
            unlockRequirement = "Deal 500 damage in a single match",
            isDefaultUnlocked = true,
            tacticalGearStyle = "Matador Vanguard",
            description = "Bold red and radiant gold tactical plates with royal crest insignia."
        ),
        CountryOutfit(
            id = "PT",
            name = "Portugal",
            isoCode = "PRT",
            continent = Continent.EUROPE,
            primaryColor = 0xFF006600,   // Green
            secondaryColor = 0xFFFF0000, // Red
            accentColor = 0xFFFFCC00,    // Yellow armillary sphere
            flagPattern = FlagPatternType.VERTICAL_BICOLOR,
            emblem = EmblemType.SHIELD,
            rarity = OutfitRarity.COMMON,
            unlockRequirement = "Unlocked by default",
            isDefaultUnlocked = true,
            tacticalGearStyle = "Navigators Strike Division",
            description = "Split green and red assault suit with golden armillary shield."
        ),
        CountryOutfit(
            id = "NL",
            name = "Netherlands",
            isoCode = "NLD",
            continent = Continent.EUROPE,
            primaryColor = 0xFF21468B,   // Cobalt Blue
            secondaryColor = 0xFFAE1C28, // Vermilion Red
            accentColor = 0xFFFF6600,    // Royal Orange accent
            flagPattern = FlagPatternType.HORIZONTAL_TRICOLOR,
            rarity = OutfitRarity.EPIC,
            unlockRequirement = "Reach Survivor Level 3",
            isDefaultUnlocked = true,
            tacticalGearStyle = "Oranje Delta Trooper",
            description = "Red, white, and cobalt blue base with electric orange shoulder trims."
        ),
        CountryOutfit(
            id = "BE",
            name = "Belgium",
            isoCode = "BEL",
            continent = Continent.EUROPE,
            primaryColor = 0xFF1C1C1C,   // Black
            secondaryColor = 0xFFFFD90C, // Yellow
            accentColor = 0xFFFF0000,    // Red
            flagPattern = FlagPatternType.VERTICAL_TRICOLOR,
            rarity = OutfitRarity.COMMON,
            unlockRequirement = "Unlocked by default",
            isDefaultUnlocked = true,
            tacticalGearStyle = "Ardennes Heavy Scout",
            description = "Black, yellow, and red vertical tri-panel battle dress."
        ),
        CountryOutfit(
            id = "SE",
            name = "Sweden",
            isoCode = "SWE",
            continent = Continent.EUROPE,
            primaryColor = 0xFF006AA7,   // Swedish Blue
            secondaryColor = 0xFFFECC00, // Yellow Cross
            accentColor = 0xFF004D7A,
            flagPattern = FlagPatternType.NORDIC_CROSS,
            rarity = OutfitRarity.RARE,
            unlockRequirement = "Survive to final 15 players",
            isDefaultUnlocked = true,
            tacticalGearStyle = "Viking Ridge Operative",
            description = "Nordic yellow cross emblazoned across midnight blue tactical armor."
        ),
        CountryOutfit(
            id = "NO",
            name = "Norway",
            isoCode = "NOR",
            continent = Continent.EUROPE,
            primaryColor = 0xFFBA0C2F,   // Red
            secondaryColor = 0xFF00205B, // Navy Blue Cross
            accentColor = 0xFFFFFFFF,    // White outline
            flagPattern = FlagPatternType.NORDIC_CROSS,
            rarity = OutfitRarity.EPIC,
            unlockRequirement = "Reach Survivor Level 4",
            isDefaultUnlocked = true,
            tacticalGearStyle = "Fjord Arctic Pathfinder",
            description = "Crimson armor with white-bordered deep indigo Nordic cross."
        ),
        CountryOutfit(
            id = "DK",
            name = "Denmark",
            isoCode = "DNK",
            continent = Continent.EUROPE,
            primaryColor = 0xFFC60C30,   // Red
            secondaryColor = 0xFFFFFFFF, // White Cross
            accentColor = 0xFF8A0822,
            flagPattern = FlagPatternType.NORDIC_CROSS,
            rarity = OutfitRarity.RARE,
            unlockRequirement = "Pick up a sniper rifle",
            isDefaultUnlocked = true,
            tacticalGearStyle = "Danbrog Elite Guard",
            description = "Classic Danish red combat coat with stark white cross overlay."
        ),
        CountryOutfit(
            id = "FI",
            name = "Finland",
            isoCode = "FIN",
            continent = Continent.EUROPE,
            primaryColor = 0xFFFFFFFF,   // White
            secondaryColor = 0xFF002F6C, // Blue Cross
            accentColor = 0xFF37474F,
            flagPattern = FlagPatternType.NORDIC_CROSS,
            rarity = OutfitRarity.EPIC,
            unlockRequirement = "Achieve 1 sniper elimination",
            isDefaultUnlocked = true,
            tacticalGearStyle = "White Death Marksman",
            description = "Winter camouflage white with bold navy blue cross pattern."
        ),
        CountryOutfit(
            id = "CH",
            name = "Switzerland",
            isoCode = "CHE",
            continent = Continent.EUROPE,
            primaryColor = 0xFFFF0000,   // Red
            secondaryColor = 0xFFFFFFFF, // White Cross
            accentColor = 0xFFB71C1C,
            flagPattern = FlagPatternType.SOLID_EMBLEM,
            emblem = EmblemType.WHITE_CROSS,
            rarity = OutfitRarity.LEGENDARY,
            unlockRequirement = "Revive teammates 2 times or win a Duo match",
            isDefaultUnlocked = false,
            tacticalGearStyle = "Alpine Fortress Guard",
            description = "Vibrant crimson reinforced plating with bold central Swiss white cross."
        ),
        CountryOutfit(
            id = "AT",
            name = "Austria",
            isoCode = "AUT",
            continent = Continent.EUROPE,
            primaryColor = 0xFFED2939,   // Red
            secondaryColor = 0xFFFFFFFF, // White
            accentColor = 0xFFB71C1C,
            flagPattern = FlagPatternType.HORIZONTAL_TRICOLOR,
            rarity = OutfitRarity.COMMON,
            unlockRequirement = "Unlocked by default",
            isDefaultUnlocked = true,
            tacticalGearStyle = "Tyrol Mountain Jaeger",
            description = "Horizontal red-white-red Austrian triband combat fatigues."
        ),
        CountryOutfit(
            id = "PL",
            name = "Poland",
            isoCode = "POL",
            continent = Continent.EUROPE,
            primaryColor = 0xFFFFFFFF,   // White
            secondaryColor = 0xFFDC143C, // Crimson
            accentColor = 0xFF212121,
            flagPattern = FlagPatternType.HORIZONTAL_BICOLOR,
            rarity = OutfitRarity.RARE,
            unlockRequirement = "Reach Top 10 in Solo survival",
            isDefaultUnlocked = true,
            tacticalGearStyle = "Winged Hussar Commando",
            description = "White top and crimson lower tactical suit with eagle crest patch."
        ),
        CountryOutfit(
            id = "UA",
            name = "Ukraine",
            isoCode = "UKR",
            continent = Continent.EUROPE,
            primaryColor = 0xFF0057B7,   // Blue
            secondaryColor = 0xFFFFD700, // Yellow
            accentColor = 0xFF003D82,
            flagPattern = FlagPatternType.HORIZONTAL_BICOLOR,
            emblem = EmblemType.SHIELD,
            rarity = OutfitRarity.EPIC,
            unlockRequirement = "Survive 15 minutes in a single match",
            isDefaultUnlocked = true,
            tacticalGearStyle = "Cossack Vanguard",
            description = "Azure sky blue and wheat golden yellow split combat armor with trident badge."
        ),
        CountryOutfit(
            id = "IE",
            name = "Ireland",
            isoCode = "IRL",
            continent = Continent.EUROPE,
            primaryColor = 0xFF169B62,   // Green
            secondaryColor = 0xFFFFFFFF, // White
            accentColor = 0xFFFF883E,    // Orange
            flagPattern = FlagPatternType.VERTICAL_TRICOLOR,
            rarity = OutfitRarity.RARE,
            unlockRequirement = "Loot 10 supply drops or crates",
            isDefaultUnlocked = true,
            tacticalGearStyle = "Emerald Isle Ranger",
            description = "Green, white, and energetic orange vertical tri-color storm gear."
        ),
        CountryOutfit(
            id = "GR",
            name = "Greece",
            isoCode = "GRC",
            continent = Continent.EUROPE,
            primaryColor = 0xFF0D5EAF,   // Blue
            secondaryColor = 0xFFFFFFFF, // White
            accentColor = 0xFF083C70,
            flagPattern = FlagPatternType.CANTON_STRIPES,
            rarity = OutfitRarity.RARE,
            unlockRequirement = "Deal 1,500 total damage",
            isDefaultUnlocked = true,
            tacticalGearStyle = "Spartan Legionnaire",
            description = "Cobalt blue and white striped tactical sleeves with cross canton chest shield."
        ),
        CountryOutfit(
            id = "CZ",
            name = "Czechia",
            isoCode = "CZE",
            continent = Continent.EUROPE,
            primaryColor = 0xFF11457E,   // Blue wedge
            secondaryColor = 0xFFFFFFFF, // White
            accentColor = 0xFFD7141A,    // Red
            flagPattern = FlagPatternType.DIAGONAL_CROSS,
            rarity = OutfitRarity.COMMON,
            unlockRequirement = "Unlocked by default",
            isDefaultUnlocked = true,
            tacticalGearStyle = "Bohemian Strike Guard",
            description = "Blue triangular wedge chest plate over white and red armor."
        ),
        CountryOutfit(
            id = "HR",
            name = "Croatia",
            isoCode = "HRV",
            continent = Continent.EUROPE,
            primaryColor = 0xFFFF0000,   // Red
            secondaryColor = 0xFFFFFFFF, // White Checkerboard
            accentColor = 0xFF171796,    // Blue
            flagPattern = FlagPatternType.HORIZONTAL_TRICOLOR,
            emblem = EmblemType.SHIELD,
            rarity = OutfitRarity.EPIC,
            unlockRequirement = "Achieve 3 eliminations in one game",
            isDefaultUnlocked = true,
            tacticalGearStyle = "Adriatic Checkerboard Trooper",
            description = "Iconic red-and-white checkerboard shield pattern over deep blue trousers."
        ),
        CountryOutfit(
            id = "RO",
            name = "Romania",
            isoCode = "ROU",
            continent = Continent.EUROPE,
            primaryColor = 0xFF002B7F,   // Blue
            secondaryColor = 0xFFFCD116, // Yellow
            accentColor = 0xFFCE1126,    // Red
            flagPattern = FlagPatternType.VERTICAL_TRICOLOR,
            rarity = OutfitRarity.COMMON,
            unlockRequirement = "Unlocked by default",
            isDefaultUnlocked = true,
            tacticalGearStyle = "Carpathian Vanguard",
            description = "Blue, golden yellow, and red vertical battle ribbons."
        ),
        CountryOutfit(
            id = "HU",
            name = "Hungary",
            isoCode = "HUN",
            continent = Continent.EUROPE,
            primaryColor = 0xFFCE2939,   // Red
            secondaryColor = 0xFFFFFFFF, // White
            accentColor = 0xFF477050,    // Green
            flagPattern = FlagPatternType.HORIZONTAL_TRICOLOR,
            rarity = OutfitRarity.COMMON,
            unlockRequirement = "Unlocked by default",
            isDefaultUnlocked = true,
            tacticalGearStyle = "Danube Scout",
            description = "Red, white, and forest green horizontal bands."
        ),
        CountryOutfit(
            id = "RS",
            name = "Serbia",
            isoCode = "SRB",
            continent = Continent.EUROPE,
            primaryColor = 0xFFC6363C,   // Red
            secondaryColor = 0xFF0C4076, // Blue
            accentColor = 0xFFFFFFFF,    // White
            flagPattern = FlagPatternType.HORIZONTAL_TRICOLOR,
            emblem = EmblemType.SHIELD,
            rarity = OutfitRarity.RARE,
            unlockRequirement = "Survive 7 minutes",
            isDefaultUnlocked = true,
            tacticalGearStyle = "Balkan Eagle Operative",
            description = "Red, blue, and white triband with crowned double-headed eagle emblem."
        ),
        CountryOutfit(
            id = "BG",
            name = "Bulgaria",
            isoCode = "BGR",
            continent = Continent.EUROPE,
            primaryColor = 0xFFFFFFFF,   // White
            secondaryColor = 0xFF00966E, // Green
            accentColor = 0xFFD62612,    // Red
            flagPattern = FlagPatternType.HORIZONTAL_TRICOLOR,
            rarity = OutfitRarity.COMMON,
            unlockRequirement = "Unlocked by default",
            isDefaultUnlocked = true,
            tacticalGearStyle = "Thracian Scout",
            description = "White, emerald green, and red horizontal combat stripes."
        ),
        CountryOutfit(
            id = "IS",
            name = "Iceland",
            isoCode = "ISL",
            continent = Continent.EUROPE,
            primaryColor = 0xFF02529C,   // Blue
            secondaryColor = 0xFFDC1E35, // Fire Red Cross
            accentColor = 0xFFFFFFFF,    // Ice White border
            flagPattern = FlagPatternType.NORDIC_CROSS,
            rarity = OutfitRarity.LEGENDARY,
            unlockRequirement = "Reach Survivor Level 6",
            isDefaultUnlocked = false,
            tacticalGearStyle = "Volcanic Ice Commando",
            description = "Deep Atlantic blue with fiery red and ice white Nordic cross."
        ),

        // ================= NORTH AMERICA =================
        CountryOutfit(
            id = "US",
            name = "United States",
            isoCode = "USA",
            continent = Continent.NORTH_AMERICA,
            primaryColor = 0xFF3C3B6E,   // Navy Blue
            secondaryColor = 0xFFB22234, // Stripes Red
            accentColor = 0xFFFFFFFF,    // Stars White
            flagPattern = FlagPatternType.CANTON_STRIPES,
            emblem = EmblemType.SINGLE_STAR,
            rarity = OutfitRarity.LEGENDARY,
            unlockRequirement = "Win 1 Battle Royale match",
            isDefaultUnlocked = true,
            tacticalGearStyle = "Delta Spec-Ops Patriot",
            description = "Deep navy blue tactical vest with red-and-white sleeve stripes and 50-star chest patch."
        ),
        CountryOutfit(
            id = "CA",
            name = "Canada",
            isoCode = "CAN",
            continent = Continent.NORTH_AMERICA,
            primaryColor = 0xFFFF0000,   // Red
            secondaryColor = 0xFFFFFFFF, // White
            accentColor = 0xFFB71C1C,
            flagPattern = FlagPatternType.VERTICAL_TRICOLOR,
            emblem = EmblemType.MAPLE_LEAF,
            rarity = OutfitRarity.EPIC,
            unlockRequirement = "Reach Survivor Level 3",
            isDefaultUnlocked = true,
            tacticalGearStyle = "JTF-2 Arctic Vanguard",
            description = "Vibrant red panels framing a pure white core with red Maple Leaf crest."
        ),
        CountryOutfit(
            id = "MX",
            name = "Mexico",
            isoCode = "MEX",
            continent = Continent.NORTH_AMERICA,
            primaryColor = 0xFF006847,   // Green
            secondaryColor = 0xFFFFFFFF, // White
            accentColor = 0xFFCE1126,    // Red
            flagPattern = FlagPatternType.VERTICAL_TRICOLOR,
            emblem = EmblemType.EAGLE,
            rarity = OutfitRarity.RARE,
            unlockRequirement = "Eliminate 4 enemies with a shotgun",
            isDefaultUnlocked = true,
            tacticalGearStyle = "Aztec Eagle Warrior",
            description = "Green, white, and red vertical armor segments featuring golden eagle on cactus insignia."
        ),
        CountryOutfit(
            id = "JM",
            name = "Jamaica",
            isoCode = "JAM",
            continent = Continent.NORTH_AMERICA,
            primaryColor = 0xFF009B3A,   // Green
            secondaryColor = 0xFFFED100, // Gold Saltire Cross
            accentColor = 0xFF000000,    // Black
            flagPattern = FlagPatternType.DIAGONAL_CROSS,
            rarity = OutfitRarity.EPIC,
            unlockRequirement = "Sprint at maximum speed for 60 seconds",
            isDefaultUnlocked = true,
            tacticalGearStyle = "Kingston Sprint Commando",
            description = "Striking black, green, and golden yellow diagonal cross body armor."
        ),
        CountryOutfit(
            id = "CU",
            name = "Cuba",
            isoCode = "CUB",
            continent = Continent.NORTH_AMERICA,
            primaryColor = 0xFF002A8F,   // Blue stripes
            secondaryColor = 0xFFCF142B, // Red Triangle
            accentColor = 0xFFFFFFFF,    // White star
            flagPattern = FlagPatternType.CANTON_STRIPES,
            emblem = EmblemType.SINGLE_STAR,
            rarity = OutfitRarity.RARE,
            unlockRequirement = "Survive 6 minutes",
            isDefaultUnlocked = true,
            tacticalGearStyle = "Caribbean Pathfinder",
            description = "Blue and white horizontal stripes with red chevron and solitary white star."
        ),
        CountryOutfit(
            id = "CR",
            name = "Costa Rica",
            isoCode = "CRI",
            continent = Continent.NORTH_AMERICA,
            primaryColor = 0xFF002B7F,   // Blue
            secondaryColor = 0xFFCE1126, // Red
            accentColor = 0xFFFFFFFF,    // White
            flagPattern = FlagPatternType.HORIZONTAL_TRICOLOR,
            rarity = OutfitRarity.COMMON,
            unlockRequirement = "Unlocked by default",
            isDefaultUnlocked = true,
            tacticalGearStyle = "Pura Vida Ranger",
            description = "Blue, white, and broad red horizontal tactical striping."
        ),
        CountryOutfit(
            id = "PA",
            name = "Panama",
            isoCode = "PAN",
            continent = Continent.NORTH_AMERICA,
            primaryColor = 0xFFFFFFFF,   // White quarters
            secondaryColor = 0xFF005293, // Blue star
            accentColor = 0xFFD21034,    // Red star
            flagPattern = FlagPatternType.CANTON_STRIPES,
            emblem = EmblemType.SINGLE_STAR,
            rarity = OutfitRarity.RARE,
            unlockRequirement = "Collect 3 weapon attachments",
            isDefaultUnlocked = true,
            tacticalGearStyle = "Canal Zone Protector",
            description = "Quartered white, blue, and red tactical panels with dual stars."
        ),
        CountryOutfit(
            id = "DO",
            name = "Dominican Republic",
            isoCode = "DOM",
            continent = Continent.NORTH_AMERICA,
            primaryColor = 0xFF002D62,   // Ultramarine Blue
            secondaryColor = 0xFFCE1126, // Red
            accentColor = 0xFFFFFFFF,    // White Cross
            flagPattern = FlagPatternType.NORDIC_CROSS,
            rarity = OutfitRarity.COMMON,
            unlockRequirement = "Unlocked by default",
            isDefaultUnlocked = true,
            tacticalGearStyle = "Quisqueya Marine",
            description = "White cross dividing blue and red tactical quarters."
        ),
        CountryOutfit(
            id = "GT",
            name = "Guatemala",
            isoCode = "GTM",
            continent = Continent.NORTH_AMERICA,
            primaryColor = 0xFF4997D0,   // Sky Blue
            secondaryColor = 0xFFFFFFFF, // White
            accentColor = 0xFF0072CE,
            flagPattern = FlagPatternType.VERTICAL_TRICOLOR,
            emblem = EmblemType.SHIELD,
            rarity = OutfitRarity.COMMON,
            unlockRequirement = "Unlocked by default",
            isDefaultUnlocked = true,
            tacticalGearStyle = "Mayan Highland Scout",
            description = "Sky blue and white vertical panels with Quetzal emblem."
        ),
        CountryOutfit(
            id = "TT",
            name = "Trinidad and Tobago",
            isoCode = "TTO",
            continent = Continent.NORTH_AMERICA,
            primaryColor = 0xFFCE1126,   // Red
            secondaryColor = 0xFF000000, // Black diagonal
            accentColor = 0xFFFFFFFF,    // White fimbriation
            flagPattern = FlagPatternType.DIAGONAL_CROSS,
            rarity = OutfitRarity.RARE,
            unlockRequirement = "Reach Survivor Level 2",
            isDefaultUnlocked = true,
            tacticalGearStyle = "Calypso Storm Striker",
            description = "Fiery red battle suit slashed with a bold white-edged black diagonal band."
        ),

        // ================= SOUTH AMERICA =================
        CountryOutfit(
            id = "BR",
            name = "Brazil",
            isoCode = "BRA",
            continent = Continent.SOUTH_AMERICA,
            primaryColor = 0xFF009739,   // Green
            secondaryColor = 0xFFFEDD00, // Yellow Rhombus
            accentColor = 0xFF012169,    // Blue Globe
            flagPattern = FlagPatternType.SOLID_EMBLEM,
            emblem = EmblemType.BRAZIL_GLOBE,
            rarity = OutfitRarity.LEGENDARY,
            unlockRequirement = "Achieve 20 kills across all career matches",
            isDefaultUnlocked = true,
            tacticalGearStyle = "Amazon Jungle Commando",
            description = "Vibrant forest green combat weave with golden rhombus and starry blue celestial sphere."
        ),
        CountryOutfit(
            id = "AR",
            name = "Argentina",
            isoCode = "ARG",
            continent = Continent.SOUTH_AMERICA,
            primaryColor = 0xFF74ACDF,   // Celestial Blue
            secondaryColor = 0xFFFFFFFF, // White
            accentColor = 0xFFF6B40E,    // Sun of May
            flagPattern = FlagPatternType.HORIZONTAL_TRICOLOR,
            emblem = EmblemType.SUN_RAYS,
            rarity = OutfitRarity.LEGENDARY,
            unlockRequirement = "Finish 1st place in Solo or Squad",
            isDefaultUnlocked = true,
            tacticalGearStyle = "Pampas Vanguard",
            description = "Celestial light blue and white stripes centered with golden Sun of May badge."
        ),
        CountryOutfit(
            id = "CO",
            name = "Colombia",
            isoCode = "COL",
            continent = Continent.SOUTH_AMERICA,
            primaryColor = 0xFFFCD116,   // Yellow
            secondaryColor = 0xFF003893, // Blue
            accentColor = 0xFFCE1126,    // Red
            flagPattern = FlagPatternType.HORIZONTAL_TRICOLOR,
            rarity = OutfitRarity.RARE,
            unlockRequirement = "Reach Survivor Level 2",
            isDefaultUnlocked = true,
            tacticalGearStyle = "Andean Falcon Operative",
            description = "Broad golden yellow torso with rich blue and red bottom armor."
        ),
        CountryOutfit(
            id = "CL",
            name = "Chile",
            isoCode = "CHL",
            continent = Continent.SOUTH_AMERICA,
            primaryColor = 0xFF0039A6,   // Blue
            secondaryColor = 0xFFFFFFFF, // White
            accentColor = 0xFFD52B1E,    // Red
            flagPattern = FlagPatternType.CANTON_STRIPES,
            emblem = EmblemType.SINGLE_STAR,
            rarity = OutfitRarity.RARE,
            unlockRequirement = "Survive 8 minutes in a match",
            isDefaultUnlocked = true,
            tacticalGearStyle = "Patagonia Mountain Trooper",
            description = "Blue canton with solitary white star over crisp white and red body plating."
        ),
        CountryOutfit(
            id = "PE",
            name = "Peru",
            isoCode = "PER",
            continent = Continent.SOUTH_AMERICA,
            primaryColor = 0xFFD91023,   // Red
            secondaryColor = 0xFFFFFFFF, // White
            accentColor = 0xFF8A0815,
            flagPattern = FlagPatternType.VERTICAL_TRICOLOR,
            rarity = OutfitRarity.COMMON,
            unlockRequirement = "Unlocked by default",
            isDefaultUnlocked = true,
            tacticalGearStyle = "Inca Ridge Sentinel",
            description = "Red, white, red vertical tactical panels with vicuna crest."
        ),
        CountryOutfit(
            id = "UY",
            name = "Uruguay",
            isoCode = "URY",
            continent = Continent.SOUTH_AMERICA,
            primaryColor = 0xFFFFFFFF,   // White
            secondaryColor = 0xFF0038A8, // Blue stripes
            accentColor = 0xFFFCD116,    // Golden Sun
            flagPattern = FlagPatternType.CANTON_STRIPES,
            emblem = EmblemType.SUN_RAYS,
            rarity = OutfitRarity.EPIC,
            unlockRequirement = "Earn 800 combat XP",
            isDefaultUnlocked = true,
            tacticalGearStyle = "Rio de la Plata Strike",
            description = "Nine blue and white horizontal stripes topped with golden Sun of May canton."
        ),
        CountryOutfit(
            id = "VE",
            name = "Venezuela",
            isoCode = "VEN",
            continent = Continent.SOUTH_AMERICA,
            primaryColor = 0xFFFCE300,   // Yellow
            secondaryColor = 0xFF00247D, // Blue
            accentColor = 0xFFCF142B,    // Red
            flagPattern = FlagPatternType.HORIZONTAL_TRICOLOR,
            emblem = EmblemType.SINGLE_STAR,
            rarity = OutfitRarity.COMMON,
            unlockRequirement = "Unlocked by default",
            isDefaultUnlocked = true,
            tacticalGearStyle = "Orinoco Delta Ranger",
            description = "Yellow, blue, and red horizontal tricolor with an arc of white stars."
        ),
        CountryOutfit(
            id = "EC",
            name = "Ecuador",
            isoCode = "ECU",
            continent = Continent.SOUTH_AMERICA,
            primaryColor = 0xFFFFDD00,   // Yellow
            secondaryColor = 0xFF034EA2, // Blue
            accentColor = 0xFFED1C24,    // Red
            flagPattern = FlagPatternType.HORIZONTAL_TRICOLOR,
            emblem = EmblemType.SHIELD,
            rarity = OutfitRarity.COMMON,
            unlockRequirement = "Unlocked by default",
            isDefaultUnlocked = true,
            tacticalGearStyle = "Equator Special Force",
            description = "Yellow, blue, red tricolor with Chimborazo condor coat of arms."
        ),
        CountryOutfit(
            id = "BO",
            name = "Bolivia",
            isoCode = "BOL",
            continent = Continent.SOUTH_AMERICA,
            primaryColor = 0xFFD52B1E,   // Red
            secondaryColor = 0xFFFCD116, // Yellow
            accentColor = 0xFF007934,    // Green
            flagPattern = FlagPatternType.HORIZONTAL_TRICOLOR,
            rarity = OutfitRarity.COMMON,
            unlockRequirement = "Unlocked by default",
            isDefaultUnlocked = true,
            tacticalGearStyle = "Altiplano High-Altitude Unit",
            description = "Red, yellow, and green high-altitude tactical fatigues."
        ),
        CountryOutfit(
            id = "PY",
            name = "Paraguay",
            isoCode = "PRY",
            continent = Continent.SOUTH_AMERICA,
            primaryColor = 0xFFD52B1E,   // Red
            secondaryColor = 0xFFFFFFFF, // White
            accentColor = 0xFF0038A8,    // Blue
            flagPattern = FlagPatternType.HORIZONTAL_TRICOLOR,
            emblem = EmblemType.SHIELD,
            rarity = OutfitRarity.COMMON,
            unlockRequirement = "Unlocked by default",
            isDefaultUnlocked = true,
            tacticalGearStyle = "Chaco Tactical Guard",
            description = "Red, white, and blue bands with palm and olive branch seal."
        ),

        // ================= AFRICA =================
        CountryOutfit(
            id = "ZA",
            name = "South Africa",
            isoCode = "ZAF",
            continent = Continent.AFRICA,
            primaryColor = 0xFF007A3D,   // Green Y-band
            secondaryColor = 0xFFDE3831, // Red
            accentColor = 0xFF002395,    // Blue
            flagPattern = FlagPatternType.DIAGONAL_CROSS,
            rarity = OutfitRarity.LEGENDARY,
            unlockRequirement = "Survive into the top 3 survivors",
            isDefaultUnlocked = true,
            tacticalGearStyle = "Rainbow Nation Commando",
            description = "Six-color dynamic Y-converging tactical pattern with gold, black, red, blue, and green."
        ),
        CountryOutfit(
            id = "EG",
            name = "Egypt",
            isoCode = "EGY",
            continent = Continent.AFRICA,
            primaryColor = 0xFFCE1126,   // Red
            secondaryColor = 0xFFFFFFFF, // White
            accentColor = 0xFF000000,    // Black
            flagPattern = FlagPatternType.HORIZONTAL_TRICOLOR,
            emblem = EmblemType.EAGLE,
            rarity = OutfitRarity.EPIC,
            unlockRequirement = "Reach Survivor Level 3",
            isDefaultUnlocked = true,
            tacticalGearStyle = "Pharaoh Gold Vanguard",
            description = "Red, white, and black horizontal panels featuring the golden Eagle of Saladin."
        ),
        CountryOutfit(
            id = "NG",
            name = "Nigeria",
            isoCode = "NGA",
            continent = Continent.AFRICA,
            primaryColor = 0xFF008751,   // Green
            secondaryColor = 0xFFFFFFFF, // White
            accentColor = 0xFF005A36,
            flagPattern = FlagPatternType.VERTICAL_TRICOLOR,
            rarity = OutfitRarity.RARE,
            unlockRequirement = "Eliminate 5 enemies",
            isDefaultUnlocked = true,
            tacticalGearStyle = "Super Eagles Strike Force",
            description = "Vibrant emerald green and stark white vertical assault tactical dress."
        ),
        CountryOutfit(
            id = "KE",
            name = "Kenya",
            isoCode = "KEN",
            continent = Continent.AFRICA,
            primaryColor = 0xFF000000,   // Black
            secondaryColor = 0xFFBB0000, // Red
            accentColor = 0xFF006600,    // Green
            flagPattern = FlagPatternType.HORIZONTAL_TRICOLOR,
            emblem = EmblemType.SHIELD,
            rarity = OutfitRarity.EPIC,
            unlockRequirement = "Sprint for 500 meters without resting",
            isDefaultUnlocked = true,
            tacticalGearStyle = "Maasai Warrior Scout",
            description = "Black, red, and green bands featuring traditional Maasai shield and spears on chest."
        ),
        CountryOutfit(
            id = "MA",
            name = "Morocco",
            isoCode = "MAR",
            continent = Continent.AFRICA,
            primaryColor = 0xFFC1272D,   // Deep Red
            secondaryColor = 0xFF006233, // Green Pentagram
            accentColor = 0xFF8A171C,
            flagPattern = FlagPatternType.SOLID_EMBLEM,
            emblem = EmblemType.SINGLE_STAR,
            rarity = OutfitRarity.RARE,
            unlockRequirement = "Reach Top 10 in Practice or Solo",
            isDefaultUnlocked = true,
            tacticalGearStyle = "Atlas Mountain Commando",
            description = "Deep red reinforced body armor with interlaced emerald green pentagram."
        ),
        CountryOutfit(
            id = "GH",
            name = "Ghana",
            isoCode = "GHA",
            continent = Continent.AFRICA,
            primaryColor = 0xFFCE1126,   // Red
            secondaryColor = 0xFFFCD116, // Gold
            accentColor = 0xFF006B3F,    // Green
            flagPattern = FlagPatternType.HORIZONTAL_TRICOLOR,
            emblem = EmblemType.SINGLE_STAR,
            rarity = OutfitRarity.RARE,
            unlockRequirement = "Collect 3 medkits",
            isDefaultUnlocked = true,
            tacticalGearStyle = "Black Star Vanguard",
            description = "Pan-African red, gold, and green tricolor with prominent Black Star of Africa."
        ),
        CountryOutfit(
            id = "ET",
            name = "Ethiopia",
            isoCode = "ETH",
            continent = Continent.AFRICA,
            primaryColor = 0xFF078930,   // Green
            secondaryColor = 0xFFFCDD09, // Yellow
            accentColor = 0xFFDA121A,    // Red
            flagPattern = FlagPatternType.HORIZONTAL_TRICOLOR,
            emblem = EmblemType.SUN_RAYS,
            rarity = OutfitRarity.RARE,
            unlockRequirement = "Survive 10 minutes",
            isDefaultUnlocked = true,
            tacticalGearStyle = "Lion of Judah Operative",
            description = "Green, yellow, and red bands with radiant blue star medallion."
        ),
        CountryOutfit(
            id = "DZ",
            name = "Algeria",
            isoCode = "DZA",
            continent = Continent.AFRICA,
            primaryColor = 0xFF006633,   // Green
            secondaryColor = 0xFFFFFFFF, // White
            accentColor = 0xFFD21034,    // Red Crescent
            flagPattern = FlagPatternType.VERTICAL_BICOLOR,
            emblem = EmblemType.STAR_CRESCENT,
            rarity = OutfitRarity.COMMON,
            unlockRequirement = "Unlocked by default",
            isDefaultUnlocked = true,
            tacticalGearStyle = "Sahara Desert Recon",
            description = "Split green and white battle vest with central red crescent and star."
        ),
        CountryOutfit(
            id = "TN",
            name = "Tunisia",
            isoCode = "TUN",
            continent = Continent.AFRICA,
            primaryColor = 0xFFE70013,   // Red
            secondaryColor = 0xFFFFFFFF, // White disc
            accentColor = 0xFFB0000E,
            flagPattern = FlagPatternType.DISC_CENTER,
            emblem = EmblemType.STAR_CRESCENT,
            rarity = OutfitRarity.COMMON,
            unlockRequirement = "Unlocked by default",
            isDefaultUnlocked = true,
            tacticalGearStyle = "Carthaginian Legion",
            description = "Crimson red combat chassis with white circle enclosing red crescent."
        ),
        CountryOutfit(
            id = "SN",
            name = "Senegal",
            isoCode = "SEN",
            continent = Continent.AFRICA,
            primaryColor = 0xFF00853F,   // Green
            secondaryColor = 0xFFFDEF42, // Yellow
            accentColor = 0xFFE31B23,    // Red
            flagPattern = FlagPatternType.VERTICAL_TRICOLOR,
            emblem = EmblemType.SINGLE_STAR,
            rarity = OutfitRarity.COMMON,
            unlockRequirement = "Unlocked by default",
            isDefaultUnlocked = true,
            tacticalGearStyle = "Lions of Teranga Striker",
            description = "Green, yellow, and red vertical panels with central green star."
        ),

        // ================= OCEANIA =================
        CountryOutfit(
            id = "AU",
            name = "Australia",
            isoCode = "AUS",
            continent = Continent.OCEANIA,
            primaryColor = 0xFF00008B,   // Dark Navy Blue
            secondaryColor = 0xFFFFFFFF, // White Stars
            accentColor = 0xFFCC0000,    // Red Union Jack cross
            flagPattern = FlagPatternType.CANTON_STRIPES,
            emblem = EmblemType.SOUTHERN_CROSS,
            rarity = OutfitRarity.LEGENDARY,
            unlockRequirement = "Achieve 10 total eliminations",
            isDefaultUnlocked = true,
            tacticalGearStyle = "Outback SASR Operative",
            description = "Deep navy blue tactical uniform with Union canton and Southern Cross constellation."
        ),
        CountryOutfit(
            id = "NZ",
            name = "New Zealand",
            isoCode = "NZL",
            continent = Continent.OCEANIA,
            primaryColor = 0xFF00247D,   // Royal Navy
            secondaryColor = 0xFFCC142B, // Red Stars
            accentColor = 0xFFFFFFFF,    // White border
            flagPattern = FlagPatternType.CANTON_STRIPES,
            emblem = EmblemType.SOUTHERN_CROSS,
            rarity = OutfitRarity.EPIC,
            unlockRequirement = "Reach Survivor Level 3",
            isDefaultUnlocked = true,
            tacticalGearStyle = "All Black Kiwi Division",
            description = "Midnight navy body suit with four red-and-white Southern Cross stars."
        ),
        CountryOutfit(
            id = "FJ",
            name = "Fiji",
            isoCode = "FJI",
            continent = Continent.OCEANIA,
            primaryColor = 0xFF68BFE5,   // Sky Blue
            secondaryColor = 0xFFFFFFFF, // White
            accentColor = 0xFFCC0000,    // Red
            flagPattern = FlagPatternType.SOLID_EMBLEM,
            emblem = EmblemType.SHIELD,
            rarity = OutfitRarity.RARE,
            unlockRequirement = "Survive 5 minutes",
            isDefaultUnlocked = true,
            tacticalGearStyle = "Pacific Island Warrior",
            description = "Pacific azure light blue combat gear with national shield."
        ),
        CountryOutfit(
            id = "PG",
            name = "Papua New Guinea",
            isoCode = "PNG",
            continent = Continent.OCEANIA,
            primaryColor = 0xFF000000,   // Black
            secondaryColor = 0xFFCE1126, // Red
            accentColor = 0xFFFFCC00,    // Yellow Raggiana bird
            flagPattern = FlagPatternType.DIAGONAL_CROSS,
            emblem = EmblemType.SOUTHERN_CROSS,
            rarity = OutfitRarity.EPIC,
            unlockRequirement = "Reach Survivor Level 4",
            isDefaultUnlocked = true,
            tacticalGearStyle = "Kumul Rainforest Commando",
            description = "Diagonal black and red division with soaring bird of paradise motif."
        ),
        CountryOutfit(
            id = "WS",
            name = "Samoa",
            isoCode = "WSM",
            continent = Continent.OCEANIA,
            primaryColor = 0xFFCE1126,   // Red
            secondaryColor = 0xFF002B7F, // Blue canton
            accentColor = 0xFFFFFFFF,    // White Southern Cross
            flagPattern = FlagPatternType.CANTON_STRIPES,
            emblem = EmblemType.SOUTHERN_CROSS,
            rarity = OutfitRarity.RARE,
            unlockRequirement = "Deal 300 damage in a single encounter",
            isDefaultUnlocked = true,
            tacticalGearStyle = "Toa Tactical Heavy",
            description = "Rich red armor with blue canton holding white Southern Cross."
        )
    )

    // Programmatically generates remaining unique country outfits up to 120 total
    val full120Outfits: List<CountryOutfit> by lazy {
        val existingIds = outfits.map { it.id }.toSet()
        val list = outfits.toMutableList()

        // Additional 65 authentic sovereign nations to reach exactly 120 country outfits!
        val additionalCountries = listOf(
            Triple("AF", "Afghanistan", Continent.ASIA),
            Triple("AL", "Albania", Continent.EUROPE),
            Triple("AM", "Armenia", Continent.ASIA),
            Triple("AO", "Angola", Continent.AFRICA),
            Triple("AZ", "Azerbaijan", Continent.ASIA),
            Triple("BA", "Bosnia and Herzegovina", Continent.EUROPE),
            Triple("BB", "Barbados", Continent.NORTH_AMERICA),
            Triple("BF", "Burkina Faso", Continent.AFRICA),
            Triple("BH", "Bahrain", Continent.ASIA),
            Triple("BJ", "Benin", Continent.AFRICA),
            Triple("BN", "Brunei", Continent.ASIA),
            Triple("BW", "Botswana", Continent.AFRICA),
            Triple("BY", "Belarus", Continent.EUROPE),
            Triple("CD", "DR Congo", Continent.AFRICA),
            Triple("CI", "Ivory Coast", Continent.AFRICA),
            Triple("CM", "Cameroon", Continent.AFRICA),
            Triple("CY", "Cyprus", Continent.EUROPE),
            Triple("EE", "Estonia", Continent.EUROPE),
            Triple("GE", "Georgia", Continent.ASIA),
            Triple("HN", "Honduras", Continent.NORTH_AMERICA),
            Triple("HT", "Haiti", Continent.NORTH_AMERICA),
            Triple("IQ", "Iraq", Continent.ASIA),
            Triple("IR", "Iran", Continent.ASIA),
            Triple("JO", "Jordan", Continent.ASIA),
            Triple("KW", "Kuwait", Continent.ASIA),
            Triple("LB", "Lebanon", Continent.ASIA),
            Triple("LT", "Lithuania", Continent.EUROPE),
            Triple("LU", "Luxembourg", Continent.EUROPE),
            Triple("LV", "Latvia", Continent.EUROPE),
            Triple("LY", "Libya", Continent.AFRICA),
            Triple("MD", "Moldova", Continent.EUROPE),
            Triple("ME", "Montenegro", Continent.EUROPE),
            Triple("MG", "Madagascar", Continent.AFRICA),
            Triple("MK", "North Macedonia", Continent.EUROPE),
            Triple("ML", "Mali", Continent.AFRICA),
            Triple("MT", "Malta", Continent.EUROPE),
            Triple("MU", "Mauritius", Continent.AFRICA),
            Triple("MV", "Maldives", Continent.ASIA),
            Triple("MW", "Malawi", Continent.AFRICA),
            Triple("MZ", "Mozambique", Continent.AFRICA),
            Triple("NA", "Namibia", Continent.AFRICA),
            Triple("NE", "Niger", Continent.AFRICA),
            Triple("NI", "Nicaragua", Continent.NORTH_AMERICA),
            Triple("OM", "Oman", Continent.ASIA),
            Triple("PW", "Palau", Continent.OCEANIA),
            Triple("RW", "Rwanda", Continent.AFRICA),
            Triple("SB", "Solomon Islands", Continent.OCEANIA),
            Triple("SC", "Seychelles", Continent.AFRICA),
            Triple("SD", "Sudan", Continent.AFRICA),
            Triple("SI", "Slovenia", Continent.EUROPE),
            Triple("SK", "Slovakia", Continent.EUROPE),
            Triple("SL", "Sierra Leone", Continent.AFRICA),
            Triple("SO", "Somalia", Continent.AFRICA),
            Triple("SR", "Suriname", Continent.SOUTH_AMERICA),
            Triple("SY", "Syria", Continent.ASIA),
            Triple("TG", "Togo", Continent.AFRICA),
            Triple("TJ", "Tajikistan", Continent.ASIA),
            Triple("TM", "Turkmenistan", Continent.ASIA),
            Triple("TO", "Tonga", Continent.OCEANIA),
            Triple("TZ", "Tanzania", Continent.AFRICA),
            Triple("UG", "Uganda", Continent.AFRICA),
            Triple("VU", "Vanuatu", Continent.OCEANIA),
            Triple("YE", "Yemen", Continent.ASIA),
            Triple("ZM", "Zambia", Continent.AFRICA),
            Triple("ZW", "Zimbabwe", Continent.AFRICA)
        )

        for ((id, name, continent) in additionalCountries) {
            if (list.size >= 120) break
            if (id in existingIds) continue

            // Procedurally assign distinctive national palettes and patterns
            val (pCol, sCol, aCol, pattern, emblem) = generatePaletteForCountry(id, name)
            list.add(
                CountryOutfit(
                    id = id,
                    name = name,
                    isoCode = id + "X",
                    continent = continent,
                    primaryColor = pCol,
                    secondaryColor = sCol,
                    accentColor = aCol,
                    flagPattern = pattern,
                    emblem = emblem,
                    rarity = if (list.size % 7 == 0) OutfitRarity.EPIC else if (list.size % 4 == 0) OutfitRarity.RARE else OutfitRarity.COMMON,
                    unlockRequirement = if (list.size < 70) "Unlocked by default" else "Unlock at Level ${(list.size - 60) / 10 + 2}",
                    isDefaultUnlocked = list.size < 70,
                    tacticalGearStyle = "$name National Defense",
                    description = "Official national tactical combat gear of $name with authentic flag color striping."
                )
            )
        }

        list.take(120)
    }

    private fun generatePaletteForCountry(id: String, name: String): Quintuple<Long, Long, Long, FlagPatternType, EmblemType> {
        val hash = (id.hashCode() * 31 + name.hashCode())
        val patterns = listOf(
            FlagPatternType.HORIZONTAL_TRICOLOR,
            FlagPatternType.VERTICAL_TRICOLOR,
            FlagPatternType.HORIZONTAL_BICOLOR,
            FlagPatternType.VERTICAL_BICOLOR,
            FlagPatternType.DIAGONAL_CROSS,
            FlagPatternType.SOLID_EMBLEM
        )
        val selectedPattern = patterns[kotlin.math.abs(hash) % patterns.size]

        val primaryColors = listOf(
            0xFF1E88E5, 0xFF43A047, 0xFFE53935, 0xFFFB8C00,
            0xFF8E24AA, 0xFF00ACC1, 0xFF3949AB, 0xFF546E7A, 0xFFD81B60
        )
        val secondaryColors = listOf(0xFFFFFFFF, 0xFFFFEB3B, 0xFF000000, 0xFF039BE5, 0xFF7CB342)
        val accentColors = listOf(0xFFFFD600, 0xFFFF1744, 0xFF00E676, 0xFF00B0FF)

        val p = primaryColors[kotlin.math.abs(hash / 3) % primaryColors.size]
        val s = secondaryColors[kotlin.math.abs(hash / 7) % secondaryColors.size]
        val a = accentColors[kotlin.math.abs(hash / 11) % accentColors.size]
        val emb = if (kotlin.math.abs(hash) % 3 == 0) EmblemType.SINGLE_STAR else EmblemType.SHIELD

        return Quintuple(p, s, a, selectedPattern, emb)
    }
}

data class Quintuple<A, B, C, D, E>(
    val first: A,
    val second: B,
    val third: C,
    val fourth: D,
    val fifth: E
)
