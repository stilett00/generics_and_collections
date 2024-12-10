fun main(){
    val photoAttachment = AttachmentPhoto(
        Photo(1, 22, "2200", "1080p")
    )
    WallService.add(Post(22, 1735034034, "My first post!", attachments = arrayOf(photoAttachment)))

    val videoAttachment = AttachmentVideo(
        Video(id = 2, title = "Amazing Video", duration = 300, resolution = "4K")
    )
    WallService.add(Post(1234, 1835034034, "My second post", attachments = arrayOf(videoAttachment)))

    WallService.printPosts()

    WallService.update(Post(22, 1900090000, "My edited post", id = 1))
    WallService.printPosts()

    val noteService = NoteService()

    noteService.add(Note(1, noteText = "note 01"))
    noteService.add(Note(2, noteText = "note 02"))

    noteService.read()

    val foundNote = noteService.getById(2)
    println("Found note: $foundNote")

    val missingNote = noteService.getById(999)
    println("Missing note: $missingNote")

    noteService.delete(2)

    noteService.delete(999)

    noteService.restore(2)

    noteService.restore(1)

    noteService.getById(1)
    noteService.getById(999)

    noteService.edit(Note(noteId = 2, noteText = "Updated Note 02"))

    noteService.read()

    noteService.addComment(1, "First comment for note 1")
    noteService.addComment(1, "Second comment for note 1")
    noteService.addComment(2, "First comment for note 2")

    noteService.readComments(1)
    noteService.readComments(2)

    noteService.read()

}