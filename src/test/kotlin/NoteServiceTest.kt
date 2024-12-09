import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class NoteServiceTest {

    private lateinit var noteService: NoteService

    @Before
    fun setUp() {
        noteService = NoteService()
    }

    @Test
    fun addShouldAddNewNoteAndAssignId() {
        val note = noteService.add(Note(noteText = "Test Note"))
        assertEquals(1, note.noteId)
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
        assertEquals(2, notes.size)
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
}
