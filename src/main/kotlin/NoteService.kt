class NoteService : CrudService<Note> {
    private val notes = mutableListOf<Note>()
    private var nextID = 1

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
            notes[index] = entity
            println("Note with ID ${entity.noteId} edited: $entity")
        } else {
            println("Note with ID ${entity.noteId} not found. Nothing to edit.")
        }
    }
}