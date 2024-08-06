package manga

enum class MangaDexContentRating {
    PORNOGRAPHIC, EROTICA, SUGGESTIVE, SAFE;

    override fun toString(): String {
        return super.toString().lowercase()
    }
}