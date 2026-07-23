package com.pmdm.mygamestore.data.local

import com.pmdm.mygamestore.domain.model.Game
import com.pmdm.mygamestore.domain.model.GameCategory
import com.pmdm.mygamestore.domain.model.Platform
import com.pmdm.mygamestore.domain.model.Publisher
import com.pmdm.mygamestore.domain.model.Store
import com.pmdm.mygamestore.domain.model.Tag

/**
 * MockDataSource es un objeto que simula una fuente de datos para una aplicación centrada en videojuegos.
 * Sirve como recurso inicial para proporcionar información estática relacionada con géneros, plataformas,
 * editores, tiendas, etiquetas y juegos.
 *
 * Datos proveídos:
 * - `genres`: Lista de géneros populares de videojuegos, definidos por un identificador único, nombre y slug.
 * - `platforms`: Lista de plataformas disponibles para videojuegos, con su respectivo identificador, nombre y slug.
 * - `publishers`: Lista de editores de videojuegos reconocidos, identificados por un ID, nombre y slug.
 * - `stores`: Lista de tiendas en las que se pueden adquirir juegos o contenido relacionado, con su correspondiente identificador, nombre y slug.
 * - `tags`: Lista de etiquetas (tags) relevantes para clasificar los videojuegos, definidas por ID, nombre y slug.
 * - `games`: Lista de detalles de juegos predefinidos, incluyendo información como título, descripción, precio, calificación, fecha de lanzamiento,
 *  plataformas soportadas, géneros relacionados, tiendas donde están disponibles, etiquetas asociadas, capturas de pantalla, entre otros atributos específicos
 * .
 */
object MockDataSource {

    /**
     *  Lista de juegos mock para desarrollo
     *
     * En producción real, esto vendría de:
     * - API REST: GET https://api.example.com/games
     * - Base de datos: Room + SQLite
     * - Firebase: Firestore collection "games"
     * - Caché híbrida: Room + Retrofit con política de caché
     */
    val games = listOf(
        // 2025 - Monster Hunter Wilds
        Game(
            id = 101,
            slug = "monster-hunter-wilds",
            title = "Monster Hunter Wilds",
            description = "Unleash your hunting spirit in a dynamic, ever-changing environment with vast ecosystems and intense battles.",
            imageUrl = "https://example.com/images/mh_wilds.jpg",
            price = 69.99,
            rating = 4.8,
            releaseDate = "2025-02-28",
            category = GameCategory.ACTION, // Adapta a tu Enum de categorías
            metacritic = 88,
            playtime = 80,
            ratingsCount = 12000
        ),

        // 2024 - Black Myth: Wukong
        Game(
            id = 102,
            slug = "black-myth-wukong",
            title = "Black Myth: Wukong",
            description = "An action RPG rooted in Chinese mythology based on Journey to the West, where you set out as the Destined One.",
            imageUrl = "https://example.com/images/wukong.jpg",
            price = 59.99,
            rating = 4.7,
            releaseDate = "2024-08-20",
            category = GameCategory.ACTION,
            metacritic = 81,
            playtime = 35,
            ratingsCount = 45000
        ),

        // 2024 - Astro Bot
        Game(
            id = 103,
            slug = "astro-bot",
            title = "Astro Bot",
            description = "Join Astro in a massive space adventure to rescue his scattered crew across dozens of vibrant planets.",
            imageUrl = "https://example.com/images/astro_bot.jpg",
            price = 59.99,
            rating = 4.9,
            releaseDate = "2024-09-06",
            category = GameCategory.PLATFORMER,
            metacritic = 94,
            playtime = 15,
            ratingsCount = 8500
        ),

        // 2023 - Baldur's Gate 3
        Game(
            id = 104,
            slug = "baldurs-gate-3",
            title = "Baldur's Gate 3",
            description = "An epic story-rich, party-based RPG set in the universe of Dungeons & Dragons, offering unmatched freedom.",
            imageUrl = "https://example.com/images/bg3.jpg",
            price = 59.99,
            rating = 4.9,
            releaseDate = "2023-08-03",
            category = GameCategory.RPG,
            metacritic = 96,
            playtime = 120,
            ratingsCount = 95000
        ),

        // 2023 - The Legend of Zelda: Tears of the Kingdom
        Game(
            id = 105,
            slug = "zelda-tears-of-the-kingdom",
            title = "The Legend of Zelda: Tears of the Kingdom",
            description = "An epic adventure across the land and skies of Hyrule, fueled by your imagination and new abilities.",
            imageUrl = "https://example.com/images/totk.jpg",
            price = 69.99,
            rating = 4.9,
            releaseDate = "2023-05-12",
            category = GameCategory.ADVENTURE,
            metacritic = 96,
            playtime = 100,
            ratingsCount = 68000
        ),

        // 2022 - Elden Ring
        Game(
            id = 106,
            slug = "elden-ring",
            title = "Elden Ring",
            description = "A vast world where open fields with a variety of situations and huge dungeons with complex 3D designs seamlessly connect.",
            imageUrl = "https://example.com/images/elden_ring.jpg",
            price = 59.99,
            rating = 4.8,
            releaseDate = "2022-02-25",
            category = GameCategory.ACTION,
            metacritic = 96,
            playtime = 90,
            ratingsCount = 110000
        )
    )

    val platforms = listOf(
        Platform(id = 4, name = "PC", slug = "pc"),
        Platform(id = 187, name = "PlayStation 5", slug = "playstation5"),
        Platform(id = 18, name = "PlayStation 4", slug = "playstation4"),
        Platform(id = 1, name = "PlayStation 3", slug = "playstation3"),
        Platform(id = 186, name = "Xbox Series S/X", slug = "xbox-series-x"),
        Platform(id = 14, name = "Xbox One", slug = "xbox-one"),
        Platform(id = 80, name = "Xbox 360", slug = "xbox360"),
        Platform(id = 7, name = "Nintendo Switch", slug = "nintendo-switch"),
        Platform(id = 3, name = "iOS", slug = "ios"),
        Platform(id = 21, name = "Android", slug = "android")
    )

    val publishers = listOf(
        Publisher(id = 354, name = "Electronic Arts", slug = "electronic-arts"),
        Publisher(id = 3408, name = "Ubisoft Entertainment", slug = "ubisoft-entertainment"),
        Publisher(id = 339, name = "Bethesda Softworks", slug = "bethesda-softworks"),
        Publisher(id = 3399, name = "Rockstar Games", slug = "rockstar-games"),
        Publisher(id = 33, name = "Warner Bros. Interactive", slug = "warner-bros-interactive"),
        Publisher(id = 209, name = "Sony Interactive Entertainment", slug = "sony-interactive-entertainment"),
        Publisher(id = 45, name = "Microsoft Xbox Game Studios", slug = "microsoft-xbox-game-studios"),
        Publisher(id = 16257, name = "Nintendo", slug = "nintendo"),
        Publisher(id = 9191, name = "Sega", slug = "sega-2"),
        Publisher(id = 3390, name = "Square Enix", slug = "square-enix"),
        Publisher(id = 347, name = "Capcom", slug = "capcom"),
        Publisher(id = 345, name = "Activision Blizzard", slug = "activision-blizzard"),
        Publisher(id = 25, name = "2K Games", slug = "2k-games"),
        Publisher(id = 208, name = "Bandai Namco Entertainment", slug = "bandai-namco-entertainment"),
        Publisher(id = 3410, name = "Deep Silver", slug = "deep-silver")
    )

    val stores = listOf(
        Store(id = 1, name = "Steam", slug = "steam"),
        Store(id = 3, name = "PlayStation Store", slug = "playstation-store"),
        Store(id = 2, name = "Xbox Store", slug = "xbox-store"),
        Store(id = 4, name = "App Store", slug = "apple-appstore"),
        Store(id = 8, name = "Google Play", slug = "google-play"),
        Store(id = 5, name = "GOG", slug = "gog"),
        Store(id = 6, name = "Nintendo Store", slug = "nintendo"),
        Store(id = 7, name = "Xbox 360 Store", slug = "xbox360"),
        Store(id = 9, name = "itch.io", slug = "itch"),
        Store(id = 11, name = "Epic Games", slug = "epic-games")
    )

    val tags = listOf(
        Tag(id = 31, name = "Singleplayer", slug = "singleplayer"),
        Tag(id = 40836, name = "Full controller support", slug = "full-controller-support"),
        Tag(id = 7, name = "Multiplayer", slug = "multiplayer"),
        Tag(id = 40847, name = "Steam Achievements", slug = "steam-achievements"),
        Tag(id = 13, name = "Atmospheric", slug = "atmospheric"),
        Tag(id = 40849, name = "Steam Cloud", slug = "steam-cloud"),
        Tag(id = 42, name = "Great Soundtrack", slug = "great-soundtrack"),
        Tag(id = 24, name = "RPG", slug = "rpg"),
        Tag(id = 18, name = "Co-op", slug = "co-op"),
        Tag(id = 118, name = "Story Rich", slug = "story-rich"),
        Tag(id = 411, name = "Cooperative", slug = "cooperative"),
        Tag(id = 8, name = "First-Person", slug = "first-person"),
        Tag(id = 32, name = "Sci-fi", slug = "sci-fi"),
        Tag(id = 149, name = "Third Person", slug = "third-person"),
        Tag(id = 4, name = "Funny", slug = "funny"),
        Tag(id = 37, name = "Sandbox", slug = "sandbox"),
        Tag(id = 123, name = "Comedy", slug = "comedy"),
        Tag(id = 64, name = "Fantasy", slug = "fantasy"),
        Tag(id = 147, name = "2D", slug = "2d")
    )
}