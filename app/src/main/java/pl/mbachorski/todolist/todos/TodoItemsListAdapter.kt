package pl.mbachorski.roomwordsamplecodelab

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import pl.mbachorski.todolist.R
import pl.mbachorski.todolist.todos.TodoItem

class TodoItemsListAdapter internal constructor(
    context: Context
) : RecyclerView.Adapter<TodoItemsListAdapter.TodoItemViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var todoItems = emptyList<TodoItem>() // Cached copy of items

    inner class TodoItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val wordItemView: TextView = itemView.findViewById(R.id.textView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoItemViewHolder {
        val itemView = inflater.inflate(R.layout.todo_recyclerview_item, parent, false)
        return TodoItemViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: TodoItemViewHolder, position: Int) {
        val current = todoItems[position]
        holder.wordItemView.text = current.text
    }

    internal fun setTodoItems(todoItems: List<TodoItem>) {
        this.todoItems = todoItems
        notifyDataSetChanged()
    }

    override fun getItemCount() = todoItems.size

}