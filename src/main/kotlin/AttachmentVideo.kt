class AttachmentVideo(val video: Video) : Attachment() {
    override val type: String = "video"
}

data class Video(
    val id: Int,
    val title: String,
    val duration: Int,
    val resolution: String
)