package model

@SuppressWarnings("MagicNumber")
enum class Quality(
    val percent: Int
) {

    LOWEST(65),
    LOWER(70),
    LOW(75),
    MEDIUM(80),
    GOOD(85),
    HIGH(90);

    val displayString: String =
        "$percent%"

    fun lower() =
        if (ordinal > 0) entries[ordinal - 1] else null

    fun higher() =
        if (ordinal < entries.size - 1) entries[ordinal + 1] else null
}
