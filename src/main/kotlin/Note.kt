data class Note(
    val noteId: Int = 0,
    val noteText: String = "",
    val isDeleted: Boolean = false,
    val comments: MutableList<Comment> = mutableListOf()
)