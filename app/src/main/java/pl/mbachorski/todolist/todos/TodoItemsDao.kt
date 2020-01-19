package pl.mbachorski.todolist.todos

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface TodoItemsDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(todoItem: TodoItem)

    @Query("Select * from todo_items_table ORDER BY id DESC")
    fun getAll(): LiveData<List<TodoItem>>

    @Query("DELETE FROM todo_items_table")
    suspend fun deleteAll()
}