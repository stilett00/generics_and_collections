class AttachmentAudio(val audio: Audio) : Attachment() {
    override val type: String = "audio"
}

data class Audio(
    val id: Int,
    val artist: String,
    val title: String,
    val duration: Int
)
