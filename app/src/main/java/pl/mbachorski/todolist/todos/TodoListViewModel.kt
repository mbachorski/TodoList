package pl.mbachorski.roomwordsamplecodelab

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import pl.mbachorski.todolist.todos.TodoItem
import pl.mbachorski.todolist.todos.TodoItemsRepository
import pl.mbachorski.todolist.todos.TodoItemsRoomDatabase

class TodoListViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: TodoItemsRepository

    // LiveData gives us updated words when they change.
    val allTodoItems: LiveData<List<TodoItem>>

    init {
        val todoItemsDao =
            TodoItemsRoomDatabase.getDatabase(application, viewModelScope).todoItemsDao()
        repository = TodoItemsRepository(todoItemsDao)
        allTodoItems = repository.allTodoItems
    }

    /**
     * The implementation of insert() in the database is completely hidden from the UI.
     * Room ensures that you're not doing any long running operations on
     * the main thread, blocking the UI, so we don't need to handle changing Dispatchers.
     * ViewModels have a coroutine scope based on their lifecycle called
     * viewModelScope which we can use here.
     */
    fun insert(todoText: String) = viewModelScope.launch {
        repository.insert(todoText)
    }

    fun deleteById(id: Int) = viewModelScope.launch {
        repository.deleteById(id)
    }
}