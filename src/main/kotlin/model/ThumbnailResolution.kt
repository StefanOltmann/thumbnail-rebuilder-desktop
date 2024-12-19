package model

@SuppressWarnings("MagicNumber")
enum class ThumbnailResolution(
    longSidePx: Int
) {

    MIN(160),
    LOW(256),
    MEDIUM(320),
    GOOD(480),
    MAX(512);

    val displayString: String =
        "$longSidePx x ${longSidePx / 4 * 3}"

    fun lower() =
        if (ordinal > 0) entries[ordinal - 1] else null

    fun higher() =
        if (ordinal < entries.size - 1) entries[ordinal + 1] else null

}
