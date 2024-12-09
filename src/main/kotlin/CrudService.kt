interface CrudService<T> {
    fun add(entity: T): T
    fun delete(id: Int)
    fun edit(entity: T)
    fun read(): List<T>
    fun getById(id: Int): T
    fun restore(id: Int)
}