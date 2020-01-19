package pl.mbachorski.todolist.todos

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(entities = [TodoItem::class], version = 1, exportSchema = false)
public abstract class TodoItemsRoomDatabase : RoomDatabase() {
    abstract fun todoItemsDao(): TodoItemsDao

    companion object {

        // Marks the JVM backing field of the annotated property as `volatile`, meaning that writes to this field
        // are immediately made visible to other threads.
        @Volatile
        private var INSTANCE: TodoItemsRoomDatabase? = null

        fun getDatabase(context: Context, scope: CoroutineScope): TodoItemsRoomDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }

            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    TodoItemsRoomDatabase::class.java,
                    "todo_items_database"
                )
                    .addCallback(TodoItemsDatabaseCallback(scope))
                    .build()

                INSTANCE = instance
                return instance
            }
        }
    }

    private class TodoItemsDatabaseCallback(
        private val scope: CoroutineScope
    ) : RoomDatabase.Callback() {

        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onOpen(db)
            INSTANCE?.let { database ->
                scope.launch {
                    populateDatabase(database.todoItemsDao())
                }
            }
        }

        suspend fun populateDatabase(todoItemsDao: TodoItemsDao) {
            // Add sample items
            todoItemsDao.insert(TodoItem("Swim 3800m"))
            todoItemsDao.insert(TodoItem("Ride 180km"))
            todoItemsDao.insert(TodoItem("Run 42km"))
        }
    }
}