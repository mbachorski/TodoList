package pl.mbachorski.todolist.todos

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import pl.mbachorski.todolist.R

class NewTodoItemActivity : AppCompatActivity() {

    private lateinit var todoItemEditText: EditText

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.new_todo_item_activity)
        todoItemEditText = findViewById(R.id.todo_item_edit_text)

        val button = findViewById<Button>(R.id.save_button)
        button.setOnClickListener {
            val replyIntent = Intent()
            if (TextUtils.isEmpty(todoItemEditText.text)) {
                setResult(Activity.RESULT_CANCELED, replyIntent)
            } else {
                val word = todoItemEditText.text.toString()
                replyIntent.putExtra(EXTRA_REPLY, word)
                setResult(Activity.RESULT_OK, replyIntent)
            }
            finish()
        }
    }

    companion object {
        const val EXTRA_REPLY = "pl.mbachorski.newTodoItem.REPLY"
    }
}
