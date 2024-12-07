class AttachmentPhoto(val photo: Photo) : Attachment() {
    override val type: String = "photo"
}

data class Photo(
    val id: Int,
    val ownerId: Int,
    val size: String,
    val resolution: String
)
