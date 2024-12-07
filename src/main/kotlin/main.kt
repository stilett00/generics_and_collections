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


}