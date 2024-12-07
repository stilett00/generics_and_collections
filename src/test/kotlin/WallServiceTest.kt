import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class WallServiceTest {

    @Before
    fun setUp(){
        WallService.clear()
    }

    @Test
    fun add_shouldAssignNonZeroId() {
        val post = Post(authorId = 1, date = 1735034034, text = "Test post")
        val addedPost = WallService.add(post)
        assertEquals(1, addedPost.id)
    }

    @Test
    fun update_shouldReturnTrue_whenIdExists() {
        val post = Post(authorId = 1, date = 1735034034, text = "Test post")
        val addedPost = WallService.add(post)
        val result = WallService.update(
            Post(id = addedPost.id, authorId = 1, date = 1735035000, text = "Updated text")
        )
        assertEquals(true, result)
    }

    @Test
    fun update_shouldReturnFalse_whenIdDoesNotExist() {
        WallService.add(Post(authorId = 1, date = 1735034034, text = "Test post"))
        val result = WallService.update(
            Post(id = 999, authorId = 1, date = 1735035000, text = "Updated text")
        )
        assertEquals(false, result)
    }
}