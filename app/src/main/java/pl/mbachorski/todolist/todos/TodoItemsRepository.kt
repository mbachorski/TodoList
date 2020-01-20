package pl.mbachorski.todolist.todos

import androidx.lifecycle.LiveData

class TodoItemsRepository(private val todoItemsDao: TodoItemsDao) {
    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    val allTodoItems: LiveData<List<TodoItem>> = todoItemsDao.getAll()

    suspend fun deleteById(id: Int) {
        todoItemsDao.deleteById(id)
    }

    suspend fun insert(text: String) {
        todoItemsDao.insert(TodoItem(text))
    }
}