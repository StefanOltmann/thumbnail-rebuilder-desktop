package model

@SuppressWarnings("MagicNumber")
enum class Size(
    longSidePx: Int
) {

    MIN(160),
    MEDIUM(256),
    GOOD(320),
    HIGH(480),
    HIGHER(512),
    MAX(720);

    val displayString: String =
        "$longSidePx x ${longSidePx / 4 * 3}"

    fun lower() =
        if (ordinal > 0) entries[ordinal - 1] else null

    fun higher() =
        if (ordinal < entries.size - 1) entries[ordinal + 1] else null

}
