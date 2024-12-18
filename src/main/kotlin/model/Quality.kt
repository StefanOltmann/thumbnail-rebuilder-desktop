package model

enum class Quality(
    val percent: Int
) {

    LOWER(70),
    LOW(75),
    MEDIUM(80),
    GOOD(85),
    HIGH(90),
    MAX(95);

    val displayString: String =
        "$percent%"

}
