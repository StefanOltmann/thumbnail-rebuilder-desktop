package model

enum class Size(
    longSidePx: Int
) {

    LOW(160),
    MEDIUM(256),
    GOOD(320),
    HIGH(480),
    HIGHER(512),
    MAX(720);

    val displayString: String =
        "$longSidePx x ${longSidePx / 4 * 3} px"

}
