import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class NoteServiceTest {

    private lateinit var noteService: NoteService

    @Before
    fun setUp() {
        noteService = NoteService()
        noteService.clear()
        noteService.add(Note(noteId = 1, noteText = "First Note"))
        noteService.add(Note(noteId = 2, noteText = "Second Note", isDeleted = true))
    }

    @Test
    fun addShouldAddNewNoteAndAssignId() {
        val note = noteService.add(Note(noteText = "Test Note"))
        assertEquals(3, note.noteId)
        assertEquals("Test Note", note.noteText)
    }

    @Test
    fun deleteShouldMarkNoteAsDeleted() {
        val note = noteService.add(Note(noteText = "To Delete"))
        noteService.delete(note.noteId)

        val deletedNote = noteService.getById(note.noteId)
        assertTrue(deletedNote.isDeleted)
    }

    @Test
    fun editShouldUpdateExistingNote() {
        val note = noteService.add(Note(noteText = "Old Note"))
        noteService.edit(Note(noteId = note.noteId, noteText = "Updated Note"))

        val updatedNote = noteService.getById(note.noteId)
        assertEquals("Updated Note", updatedNote.noteText)
    }

    @Test
    fun editShouldDoNothingIfNoteIdDoesNotExist() {
        noteService.edit(Note(noteId = 999, noteText = "Nonexistent"))
    }

    @Test
    fun readShouldReturnAllActiveNotes() {
        noteService.add(Note(noteText = "Note 1"))
        noteService.add(Note(noteText = "Note 2", isDeleted = true))
        noteService.add(Note(noteText = "Note 3"))

        val notes = noteService.read()
        assertEquals(3, notes.size)
        assertFalse(notes.any { it.isDeleted })
    }

    @Test
    fun getByIdShouldReturnCorrectNote() {
        val note = noteService.add(Note(noteText = "Find Me"))

        val foundNote = noteService.getById(note.noteId)
        assertEquals(note, foundNote)
    }

    @Test
    fun getByIdShouldReturnPlaceholderIfNoteDoesNotExist() {
        val note = noteService.getById(999)
        assertEquals(-1, note.noteId)
        assertEquals("Not Found", note.noteText)
    }

    @Test
    fun restoreShouldReactivateDeletedNote() {
        val note = noteService.add(Note(noteText = "Deleted Note"))
        noteService.delete(note.noteId)
        noteService.restore(note.noteId)

        val restoredNote = noteService.getById(note.noteId)
        assertFalse(restoredNote.isDeleted)
    }

    @Test
    fun addComment_toExistingNote_shouldAddComment() {
        noteService.addComment(1, "First comment for note 1")
        val comments = noteService.readComments(1)

        assertEquals(1, comments.size)
        assertEquals("First comment for note 1", comments[0].commentText)
    }

    @Test
    fun addComment_toDeletedNote_shouldNotAddComment() {
        noteService.addComment(2, "This comment should not be added")
        val comments = noteService.readComments(2)

        assertTrue(comments.isEmpty())
    }

    @Test
    fun addComment_toNonExistentNote_shouldNotAddComment() {
        noteService.addComment(3, "This comment should not be added")
        val comments = noteService.readComments(3)

        assertTrue(comments.isEmpty())
    }

    @Test
    fun readComments_forExistingNote_shouldReturnComments() {
        noteService.addComment(1, "Comment 1")
        noteService.addComment(1, "Comment 2")
        val comments = noteService.readComments(1)

        assertEquals(2, comments.size)
        assertEquals("Comment 1", comments[0].commentText)
        assertEquals("Comment 2", comments[1].commentText)
    }

    @Test
    fun readComments_forDeletedNote_shouldReturnEmptyList() {
        val comments = noteService.readComments(2)
        assertTrue(comments.isEmpty())
    }

    @Test
    fun readComments_forNonExistentNote_shouldReturnEmptyList() {
        val comments = noteService.readComments(3)
        assertTrue(comments.isEmpty())
    }
}
