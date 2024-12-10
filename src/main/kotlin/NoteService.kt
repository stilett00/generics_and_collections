class NoteService : CrudService<Note> {
    private val notes = mutableListOf<Note>()
    private var nextID = 1
    private var nextCommentID = 1

    override fun add(entity: Note): Note {
        val newNote = entity.copy(noteId = nextID++)
        notes += newNote
        return newNote
    }

    override fun delete(id: Int) {
        val index = notes.indexOfFirst { it.noteId == id }
        if (index != -1) {
            notes[index] = notes[index].copy(isDeleted = true)
            println("Note with ID $id marked as deleted.")
        } else {
            println("Note with ID $id not found. Nothing to delete.")
        }
    }

    override fun read(): List<Note> {
        val activeNotes = notes.filter { !it.isDeleted }
        println("Reading notes: $activeNotes")
        return activeNotes
    }

    override fun getById(id: Int): Note {
        return notes.find { it.noteId == id && !it.isDeleted }
            ?: Note(noteId = -1, noteText = "Not Found", isDeleted = true)
    }

    override fun restore(id: Int) {
        val index = notes.indexOfFirst { it.noteId == id }
        if (index != -1 && notes[index].isDeleted) {
            notes[index] = notes[index].copy(isDeleted = false)
            println("Note with ID $id restored.")
        } else {
            println("Note with ID $id not found or not deleted. Nothing to restore.")
        }
    }

    override fun edit(entity: Note) {
        val index = notes.indexOfFirst { it.noteId == entity.noteId }
        if (index != -1) {
            notes[index] = entity.copy()
            println("Note with ID ${entity.noteId} edited: $entity")
        } else {
            println("Note with ID ${entity.noteId} not found. Nothing to edit.")
        }
    }

    fun addComment(noteId: Int, commentText: String) {
        val note = getById(noteId)
        if (note.noteId != -1 && !note.isDeleted) {
            val newComment = Comment(commentId = nextCommentID++, noteId = noteId, commentText = commentText)
            note.comments.add(newComment)
            println("Comment added to note ID $noteId: $newComment")
        } else {
            println("Cannot add comment. Note with ID $noteId not found or deleted.")
        }
    }

    fun readComments(noteId: Int): List<Comment> {
        val note = getById(noteId)
        if (note.noteId != -1 && !note.isDeleted) {
            println("Comments for note ID $noteId: ${note.comments}")
            return note.comments
        } else {
            println("Note with ID $noteId not found or deleted. No comments to read.")
            return emptyList()
        }
    }

    fun clear() {
        notes.clear()
        nextID = 1
    }
}