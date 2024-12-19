package model

@SuppressWarnings("MagicNumber")
enum class CompressionQuality(
    val percent: Int
) {

    MIN(65),
    LOW(70),
    MEDIUM(75),
    GOOD(80),
    HIGH(85),
    MAX(90);

    val displayString: String =
        "$percent%"

    fun lower() =
        if (ordinal > 0) entries[ordinal - 1] else null

    fun higher() =
        if (ordinal < entries.size - 1) entries[ordinal + 1] else null
}
