package ro.mihaiblaga.jetlagtool.presentation.questions

data class Field(
    val id: String,
    val label: String,
    val description: String? = null // Optional, as it's only in PHOTOS_MODE
)

data class Category(
    val title: String,
    val fields: List<Field>,
    val distance: Int? = null // Optional, as it's only in TENTACLES_MODE
)

data class QuestionType(
    val title: String,
    val cost: String,
    val time: String,
    val categories: List<Category>
)

object GameModes {

    val MATCHING_MODE = QuestionType(
        title = "Matching",
        cost = "Draw 3, Pick 1",
        time = "5 Minutes",
        categories = listOf(
            Category(
                title = "Transit",
                fields = listOf(
                    Field(id = "commercial-airport", label = "✈️ Commercial Airport"),
                    Field(id = "transit-line", label = "🚆 Transit Line"),
                    Field(id = "station-name-length", label = "📏 Station's Name Length"),
                    Field(id = "street-path", label = "🛣️ Street or Path")
                )
            ),
            Category(
                title = "Administrative Divisions",
                fields = listOf(
                    Field(id = "admin-div-1", label = "🗺️ 1st Admin. Division"),
                    Field(id = "admin-div-2", label = "📍 2nd Admin. Division"),
                    Field(id = "admin-div-3", label = "🏙️ 3rd Admin. Division"),
                    Field(id = "admin-div-4", label = "🏘️ 4th Admin. Division")
                )
            ),
            Category(
                title = "Natural",
                fields = listOf(
                    Field(id = "mountain", label = "⛰️ Mountain"),
                    Field(id = "landmass", label = "🏝️ Landmass"),
                    Field(id = "park", label = "🌳 Park")
                )
            ),
            Category(
                title = "Places of Interest",
                fields = listOf(
                    Field(id = "amusement-park", label = "🎢 Amusement Park"),
                    Field(id = "zoo", label = "🦁 Zoo"),
                    Field(id = "aquarium", label = "🐠 Aquarium"),
                    Field(id = "golf-course", label = "⛳ Golf Course"),
                    Field(id = "museum", label = "🏛️ Museum"),
                    Field(id = "movie-theater", label = "🎬 Movie Theater")
                )
            ),
            Category(
                title = "Public Utilities",
                fields = listOf(
                    Field(id = "hospital", label = "🏥 Hospital"),
                    Field(id = "library", label = "📚 Library"),
                    Field(id = "foreign-consulate", label = "🏛️🌍 Foreign Consulate")
                )
            )
        )
    )

    val MEASURING_MODE = QuestionType(
        title = "Measuring",
        cost = "Draw 3, Pick 1",
        time = "5 Minutes",
        categories = listOf(
            Category(
                title = "Transit",
                fields = listOf(
                    Field(id = "commercial-airport", label = "✈️ Commercial Airport"),
                    Field(id = "transit-line", label = "🚄 A High Speed Train Line"),
                    Field(id = "rail-station", label = "🚉 A Rail Station")
                )
            ),
            Category(
                title = "Borders",
                fields = listOf(
                    Field(id = "international-border", label = "🌍 An International Border"),
                    Field(id = "admin-div-1", label = "🗺️ A 1st Admin. Div. Border"),
                    Field(id = "admin-div-2", label = "📍 A 2nd Admin. Div. Border")
                )
            ),
            Category(
                title = "Natural",
                fields = listOf(
                    Field(id = "sea-level", label = "🌊 Sea Level"),
                    Field(id = "body-of-water", label = "💧 A Body of Water"),
                    Field(id = "coastline", label = "🏖️ A Coastline"),
                    Field(id = "mountain", label = "⛰️ A Mountain"),
                    Field(id = "park", label = "🌳 A Park")
                )
            ),
            Category(
                title = "Places of Interest",
                fields = listOf(
                    Field(id = "amusement-park", label = "🎢 An Amusement Park"),
                    Field(id = "zoo", label = "🦁 A Zoo"),
                    Field(id = "aquarium", label = "🐠 An Aquarium"),
                    Field(id = "golf-course", label = "⛳ A Golf Course"),
                    Field(id = "museum", label = "🏛️ A Museum"),
                    Field(id = "movie-theater", label = "🎬 A Movie Theater")
                )
            ),
            Category(
                title = "Public Utilities",
                fields = listOf(
                    Field(id = "hospital", label = "🏥 A Hospital"),
                    Field(id = "library", label = "📚 A Library"),
                    Field(id = "consulate", label = "🏛️🌍 A Foreign Consulate")
                )
            )
        )
    )

    val THERMOMETER_MODE = QuestionType(
        title = "Thermometer",
        cost = "Draw 2, Pick 1",
        time = "5 Minutes",
        categories = listOf(
            Category(
                title = "All Games",
                fields = listOf(
                    Field(id = "half-mile", label = "🔥 ½ Mile"),
                    Field(id = "three-miles", label = "🔥 3 Miles")
                )
            ),
            Category(
                title = "Medium Games",
                fields = listOf(
                    Field(id = "ten-miles", label = "🌡️ 10 Miles")
                )
            ),
            Category(
                title = "Large Games",
                fields = listOf(
                    Field(id = "fifty-miles", label = "❄️ 50 Miles")
                )
            )
        )
    )

    val RADAR_MODE = QuestionType(
        title = "Radar",
        cost = "Draw 2, Pick 1",
        time = "5 Minutes",
        categories = listOf(
            Category(
                title = "All Games",
                fields = listOf(
                    Field(id = "quarter-mile", label = "📍 ¼ Mile"),
                    Field(id = "half-mile", label = "📍 ½ Mile"),
                    Field(id = "one-mile", label = "📍 1 Mile"),
                    Field(id = "three-miles", label = "📍 3 Miles"),
                    Field(id = "five-miles", label = "📍 5 Miles"),
                    Field(id = "ten-miles", label = "🔍 10 Miles"),
                    Field(id = "twenty-five-miles", label = "🔍 25 Miles"),
                    Field(id = "fifty-miles", label = "🌐 50 Miles"),
                    Field(id = "hundred-miles", label = "🌐 100 Miles"),
                    Field(id = "choose", label = "🎯 Choose")
                )
            )
        )
    )

    val TENTACLES_MODE = QuestionType(
        title = "Tentacles",
        cost = "Draw 4, Pick 2",
        time = "5 Minutes",
        categories = listOf(
            Category(
                title = "Medium & Large Games",
                distance = 1,
                fields = listOf(
                    Field(id = "museums", label = "🏛️ Museums"),
                    Field(id = "libraries", label = "📚 Libraries"),
                    Field(id = "movie-theaters", label = "🎬 Movie Theaters"),
                    Field(id = "hospitals", label = "🏥 Hospitals")
                )
            ),
            Category(
                title = "Large Games Only",
                distance = 15,
                fields = listOf(
                    Field(id = "metro-lines", label = "🚇 Metro Lines"),
                    Field(id = "zoos", label = "🦁 Zoos"),
                    Field(id = "aquariums", label = "🐠 Aquariums"),
                    Field(id = "amusement-parks", label = "🎢 Amusement Parks")
                )
            )
        )
    )

    val PHOTOS_MODE = QuestionType(
        title = "Photos",
        cost = "Draw 1",
        time = "10 Minutes",
        categories = listOf(
            Category(
                title = "All Games",
                fields = listOf(
                    Field(
                        id = "tree",
                        label = "🌳 A Tree",
                        description = "Must include the entire tree."
                    ),
                    Field(
                        id = "sky",
                        label = "☁️ The Sky",
                        description = "Place phone on ground, shoot directly up."
                    ),
                    Field(
                        id = "you",
                        label = "🤳 You",
                        description = "Selfie mode. Arm parallel to the ground, fully extended."
                    ),
                    Field(
                        id = "widest-street",
                        label = "🛣️ Widest Street",
                        description = "Must include both sides of the street."
                    ),
                    Field(
                        id = "tallest-structure",
                        label = "🏙️ Tallest Structure in Your Sightline",
                        description = "Tallest from your current perspective/sightline. Must include top and both sides. The top must be in the top ½ of the frame."
                    ),
                    Field(
                        id = "building-from-station",
                        label = "🚉 Any Building Visible from Station",
                        description = "Must stand directly outside transit station entrance. If multiple entrances, you may choose. Must include roof, both sides, with the top of building in top ½ of the frame."
                    )
                )
            ),
            Category(
                title = "Add for Medium & Large",
                fields = listOf(
                    Field(
                        id = "tallest-building-from-station",
                        label = "🏢 Tallest Building Visible from Station",
                        description = "Tallest from your perspective/sightline. Must stand directly outside transit station entrance. If multiple entrances, you may choose. Must include roof, both sides, with the top of building in top ½ of the frame."
                    ),
                    Field(
                        id = "trace-street",
                        label = "🗺️ Trace Nearest Street/Path",
                        description = "Street/path must be visible on mapping app. Trace intersection to intersection."
                    ),
                    Field(
                        id = "two-buildings",
                        label = "🏘️ Two Buildings",
                        description = "Must include bottom and up to four stories."
                    ),
                    Field(
                        id = "restaurant-interior",
                        label = "🍽️ Restaurant Interior",
                        description = "No zoom. Must take the picture through the window from outside the restaurant."
                    ),
                    Field(
                        id = "train-platform",
                        label = "🚆 Train Platform",
                        description = "Must include a 5' x 5' section with three distinct elements."
                    ),
                    Field(
                        id = "park",
                        label = "🌲 Park",
                        description = "No zoom, phone perpendicular to ground. Must stand 5' from any obstruction."
                    ),
                    Field(
                        id = "grocery-store",
                        label = "🛒 Grocery Store Aisle",
                        description = "No zoom. Stand at the end of the aisle, shoot directly down."
                    ),
                    Field(
                        id = "place-of-worship",
                        label = "🏛️ Place of Worship",
                        description = "Must include a 5' x 5' section with three distinct elements."
                    )
                )
            ),
            Category(
                title = "Add for Large",
                fields = listOf(
                    Field(
                        id = "streets-traced",
                        label = "🗺️ ½ Mile of Streets Traced",
                        description = "Must be continuous, including 5 turns, no doubling back. Send N-S oriented. Streets must appear on mapping app."
                    ),
                    Field(
                        id = "tallest-mountain",
                        label = "⛰️ Tallest Mountain Visible from Station",
                        description = "Tallest from your perspective/sightline. Max 3x zoom. Top of mountain must be in top ½ of frame."
                    ),
                    Field(
                        id = "body-of-water",
                        label = "💧 Biggest Body of Water in Your Zone",
                        description = "Max 3x zoom. Must include either both sides of body of water or the horizon."
                    ),
                    Field(
                        id = "five-buildings",
                        label = "🏙️ Five Buildings",
                        description = "Must include bottom and up to four stories."
                    )
                )
            )
        )
    )
}

val ALL_GAME_MODES_ORDERED: List<QuestionType> = listOf(
    GameModes.MATCHING_MODE,
    GameModes.MEASURING_MODE,
    GameModes.THERMOMETER_MODE,
    GameModes.RADAR_MODE,
    GameModes.TENTACLES_MODE,
    GameModes.PHOTOS_MODE
)

