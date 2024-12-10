data class Comment(
    val commentId: Int,
    val noteId: Int,
    val commentText: String,
    val isDeleted: Boolean = false
)
