package pl.mbachorski.todolist.todos

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import io.reactivex.subjects.PublishSubject

class TodoItemsListAdapter internal constructor(
    context: Context,
    private val doneClickSubject: PublishSubject<Int>
) : RecyclerView.Adapter<TodoItemsListAdapter.TodoItemViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var todoItems = emptyList<TodoItem>() // Cached copy of items

    inner class TodoItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val wordItemView: TextView = itemView.findViewById(pl.mbachorski.todolist.R.id.textView)
        val doneButton: ImageButton = itemView.findViewById(pl.mbachorski.todolist.R.id.doneButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoItemViewHolder {
        val itemView =
            inflater.inflate(pl.mbachorski.todolist.R.layout.todo_recyclerview_item, parent, false)
        return TodoItemViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: TodoItemViewHolder, position: Int) {
        val current = todoItems[position]
        holder.wordItemView.text = current.text
        holder.doneButton.setOnClickListener {
            doneClickSubject.onNext(current.id)
        }
    }

    internal fun setTodoItems(todoItems: List<TodoItem>) {
        this.todoItems = todoItems
        notifyDataSetChanged()
    }

    override fun getItemCount() = todoItems.size

}