package manga

data class MinMange(
    val id:String,
    val title: String,
    val cover: String?,
    val lastChapter: String,
    val status: Status,
    val publicationDemographic :MangaDexPublicationDemographic?,
)